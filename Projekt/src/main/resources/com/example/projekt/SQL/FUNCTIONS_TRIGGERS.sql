
CREATE OR REPLACE FUNCTION projekt.find_department(name varchar)
RETURNS int AS
$$
    SELECT oddzial_id from projekt.oddzial
    WHERE nazwa=name LIMIT 1;
$$
LANGUAGE SQL;

CREATE FUNCTION projekt.ifPeselDoktor(pesel VARCHAR)
  RETURNS BOOLEAN
AS $$
BEGIN
   RETURN EXISTS (SELECT * FROM projekt.doktor WHERE pesel_d = pesel);
END
$$ LANGUAGE plpgsql;

CREATE FUNCTION projekt.ifPeselPersonel(pesel VARCHAR)
  RETURNS BOOLEAN
AS $$
BEGIN
   RETURN EXISTS (SELECT * FROM projekt.personel WHERE pesel_n = pesel);
END
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION projekt.pracownik_validate()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $$
    BEGIN
    IF New.mail NOT LIKE '%@%.%' THEN
        RAISE EXCEPTION '/Niepoprawny e-mail/';
    ELSIF NOT (New.pesel ~ '^[0-9]{11}$') THEN
        RAISE EXCEPTION '/Nie prawidlowy pesel/';
    ELSIF NOT (New.telefon ~ '^[0-9]{9}$') THEN
        RAISE EXCEPTION '/Nie prawidlowy telefon/';
    ELSIF NOT (UPPER(New.imie) ~ '^[A-Z]*$' AND length(New.imie)>1 )  THEN
        RAISE EXCEPTION '/Nie prawidlowe imie/';
    ELSIF NOT (UPPER(New.nazwisko) ~ '^[A-Z]*$' AND length(New.nazwisko)>1 )  THEN
        RAISE EXCEPTION '/Nie prawidlowe nazwisko/';
    ELSIF length(New.haslo)<5 THEN
        RAISE EXCEPTION '/Za krotkie haslo/';
    END IF;
    NEW.imie:=upper(substring(NEW.imie,1,1))||lower(substring(NEW.imie from 2));
    NEW.nazwisko:=upper(substring(NEW.nazwisko,1,1))||lower(substring(NEW.nazwisko from 2));
    RETURN NEW;
    END;
    $$;

CREATE TRIGGER pracownik_validate
    BEFORE INSERT OR UPDATE OR DELETE ON projekt.pracownik
    FOR EACH ROW EXECUTE PROCEDURE projekt.pracownik_validate();

CREATE OR REPLACE FUNCTION projekt.obecnosc_validate()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $$
    BEGIN
    IF NOT (UPPER(New.godziny) ~ '^[0-9]{2}:[0-9]{2}$' AND CAST(substring(NEW.godziny,1,2) AS INT) <= 24 )  THEN
       RAISE EXCEPTION '/Niepoprawna liczba godzin/';
    END IF;
    RETURN NEW;
    END;
    $$;

CREATE TRIGGER obecnosc_validate
    AFTER INSERT OR UPDATE OR DELETE ON projekt.obecnosc
    FOR EACH ROW EXECUTE PROCEDURE projekt.obecnosc_validate();

CREATE OR REPLACE FUNCTION projekt.pacjent_validate()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $$
    BEGIN
    IF NOT (New.pesel_p ~ '^[0-9]{11}$') THEN
        RAISE EXCEPTION '/Nie prawidlowy pesel/';
    ELSIF NOT (New.telefon ~ '^[0-9]{9}$') THEN
        RAISE EXCEPTION '/Nie prawidlowy telefon/';
    ELSIF NOT (UPPER(New.imie) ~ '^[A-Z]*$')  THEN
        RAISE EXCEPTION '/Nie prawidlowe imie/';
    ELSIF NOT (UPPER(New.nazwisko) ~ '^[A-Z]*$')  THEN
        RAISE EXCEPTION '/Nie prawidlowe nazwisko/';
    ELSIF NOT ((SELECT numer FROM projekt.sala WHERE numer=NEW.numer)>0)  THEN
        RAISE EXCEPTION '/Nie mam miejsca w sali/';
    ELSIF NOT ((SELECT oddzial_id FROM projekt.sala WHERE numer=NEW.numer)=NEW.oddzial_id)  THEN
            RAISE EXCEPTION '/Niezgodnosc sali z danym oddzialem/';
    END IF;
    IF TG_OP = 'INSERT' THEN
      UPDATE projekt.sala SET miejsca=miejsca-1 where numer=NEW.numer;
      RETURN NEW;
    ELSIF TG_OP = 'DELETE' THEN
      UPDATE projekt.sala SET miejsca=miejsca+1 where numer=OLD.numer;
      RETURN NULL;
    ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.numer!=NULL AND NEW.numer!=NULL THEN
            UPDATE projekt.sala SET miejsca=miejsca+1 where numer=OLD.numer;
            UPDATE projekt.sala SET miejsca=miejsca-1 where numer=NEW.numer;
        ELSIF OLD.numer=NULL AND NEW.numer!=NULL THEN
            UPDATE projekt.sala SET pojemnosc=miejsca-1 where numer=NEW.numer;
        ELSIF OLD.numer!=NULL AND NEW.numer=NULL THEN
            UPDATE projekt.sala SET pojemnosc=miejsca+1 where onumer=OLD.numer;
        END IF;
        RETURN NEW;
    END IF;
    END;
    $$;

CREATE TRIGGER pacjent_validate
    AFTER INSERT OR UPDATE OR DELETE ON projekt.pacjent
    FOR EACH ROW EXECUTE PROCEDURE projekt.pacjent_validate();

CREATE OR REPLACE FUNCTION projekt.operacja_validate()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $$
    BEGIN
    IF NOT ((SELECT rodzaj FROM projekt.sala WHERE numer=NEW.numer)='operacyjna') THEN
        RAISE EXCEPTION '/Niepoproprawny rodzaj sali/';
    ELSIF NOT ((SELECT data FROM projekt.operacja WHERE data=NEW.data)!=NULL) THEN
        RAISE EXCEPTION '/Sala jest juz zarezerwowana/';
    END IF;
    RETURN NEW;
    END;
    $$;

CREATE TRIGGER operacja_validate
    BEFORE INSERT OR UPDATE OR DELETE ON projekt.operacja
    FOR EACH ROW EXECUTE PROCEDURE projekt.operacja_validate();

CREATE OR REPLACE FUNCTION projekt.wizyta_validate()
    RETURNS TRIGGER
    LANGUAGE plpgsql
    AS $$
    BEGIN
    IF NOT (New.koszt>0) THEN
        RAISE EXCEPTION '/Nie prawidlowy kwota/';
    END IF;
    RETURN NEW;
    END;
    $$;

CREATE TRIGGER wizyta_validate
    AFTER INSERT OR UPDATE OR DELETE ON projekt.wizyta
    FOR EACH ROW EXECUTE PROCEDURE projekt.wizyta_validate();