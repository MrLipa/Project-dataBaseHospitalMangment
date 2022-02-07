DROP SCHEMA IF EXISTS projekt CASCADE;

DROP ROLE IF EXISTS "11122233344";
DROP ROLE IF EXISTS "11122233355";
DROP ROLE IF EXISTS "11122233366";
DROP ROLE IF EXISTS "11122233377";
DROP ROLE IF EXISTS "11122233388";
DROP ROLE IF EXISTS "11122233399";
DROP ROLE IF EXISTS doktor;
DROP ROLE IF EXISTS personel;
DROP ROLE IF EXISTS role_creator;

CREATE SCHEMA projekt;

CREATE TABLE projekt.pracownik (
                pesel VARCHAR(60) NOT NULL,
                imie VARCHAR(60) NOT NULL,
                nazwisko VARCHAR(60) NOT NULL,
                mail VARCHAR(60) NOT NULL,
                haslo VARCHAR(60) NOT NULL,
                telefon VARCHAR(60) NOT NULL,
                CONSTRAINT pracownik_pk PRIMARY KEY (pesel)
);


CREATE SEQUENCE projekt.obecnosc_obecnosc_id_seq;

CREATE TABLE projekt.obecnosc (
                obecnosc_id INTEGER NOT NULL DEFAULT nextval('projekt.obecnosc_obecnosc_id_seq'),
                godziny VARCHAR NOT NULL,
                data DATE NOT NULL,
                pesel VARCHAR(60) NOT NULL,
                status VARCHAR(60) NOT NULL,
                CONSTRAINT obecnosc_pk PRIMARY KEY (obecnosc_id)
);


ALTER SEQUENCE projekt.obecnosc_obecnosc_id_seq OWNED BY projekt.obecnosc.obecnosc_id;

CREATE TABLE projekt.doktor (
                pesel_d VARCHAR(60) NOT NULL,
                tytul VARCHAR(60) NOT NULL,
                CONSTRAINT doktor_pk PRIMARY KEY (pesel_d)
);


CREATE SEQUENCE projekt.oddzial_oddzial_id_seq;

CREATE TABLE projekt.oddzial (
                oddzial_id INTEGER NOT NULL DEFAULT nextval('projekt.oddzial_oddzial_id_seq'),
                miejsca INTEGER NOT NULL,
                nazwa VARCHAR(60) NOT NULL,
                pesel_d VARCHAR(60) NOT NULL,
                CONSTRAINT oddzial_pk PRIMARY KEY (oddzial_id)
);


ALTER SEQUENCE projekt.oddzial_oddzial_id_seq OWNED BY projekt.oddzial.oddzial_id;

CREATE TABLE projekt.personel (
                pesel_n VARCHAR(60) NOT NULL,
                specjalizacja VARCHAR(60) NOT NULL,
                oddzial_id INTEGER NOT NULL,
                CONSTRAINT personel_pk PRIMARY KEY (pesel_n)
);


CREATE TABLE projekt.sala (
                numer INTEGER NOT NULL,
                oddzial_id INTEGER NOT NULL,
                rodzaj VARCHAR(60) NOT NULL,
                pesel_n VARCHAR(60) NOT NULL,
                miejsca INTEGER NOT NULL,
                status VARCHAR(60) NOT NULL,
                CONSTRAINT sala_pk PRIMARY KEY (numer)
);


CREATE TABLE projekt.pacjent (
                pesel_p VARCHAR NOT NULL,
                imie VARCHAR(60) NOT NULL,
                nazwisko VARCHAR(60) NOT NULL,
                oddzial_id INTEGER,
                schorzenie VARCHAR(60) NOT NULL,
                telefon VARCHAR(60) NOT NULL,
                numer INTEGER NOT NULL,
                CONSTRAINT pacjent_pk PRIMARY KEY (pesel_p)
);


CREATE SEQUENCE projekt.recepta_recepta_id_seq;

CREATE TABLE projekt.recepta (
                recepta_id INTEGER NOT NULL DEFAULT nextval('projekt.recepta_recepta_id_seq'),
                pesel_d VARCHAR(60) NOT NULL,
                opis VARCHAR(60) NOT NULL,
                lekarstwo VARCHAR(60) NOT NULL,
                pesel_p VARCHAR NOT NULL,
                CONSTRAINT recepta_pk PRIMARY KEY (recepta_id)
);


ALTER SEQUENCE projekt.recepta_recepta_id_seq OWNED BY projekt.recepta.recepta_id;

CREATE SEQUENCE projekt.wizyta_wizyta_id_seq;

CREATE TABLE projekt.wizyta (
                wizyta_id INTEGER NOT NULL DEFAULT nextval('projekt.wizyta_wizyta_id_seq'),
                opis VARCHAR(60) NOT NULL,
                pesel_d VARCHAR(60) NOT NULL,
                koszt DOUBLE PRECISION NOT NULL,
                pesel_p VARCHAR NOT NULL,
                data DATE NOT NULL,
                CONSTRAINT wizyta_pk PRIMARY KEY (wizyta_id)
);


ALTER SEQUENCE projekt.wizyta_wizyta_id_seq OWNED BY projekt.wizyta.wizyta_id;

CREATE SEQUENCE projekt.operacja_operacja_id_seq;

CREATE TABLE projekt.operacja (
                operacja_id INTEGER NOT NULL DEFAULT nextval('projekt.operacja_operacja_id_seq'),
                data DATE NOT NULL,
                nazwa VARCHAR(600) NOT NULL,
                pesel_p VARCHAR NOT NULL,
                numer INTEGER NOT NULL,
                pesel_d VARCHAR(60) NOT NULL,
                pesel_n VARCHAR(60) NOT NULL,
                CONSTRAINT operacja_pk PRIMARY KEY (operacja_id)
);


ALTER SEQUENCE projekt.operacja_operacja_id_seq OWNED BY projekt.operacja.operacja_id;

ALTER TABLE projekt.obecnosc ADD CONSTRAINT pracownik_aktywnosc_fk
FOREIGN KEY (pesel)
REFERENCES projekt.pracownik (pesel)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.doktor ADD CONSTRAINT pracownik_doktor_fk
FOREIGN KEY (pesel_d)
REFERENCES projekt.pracownik (pesel)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.personel ADD CONSTRAINT pracownik_pielegniarka_fk
FOREIGN KEY (pesel_n)
REFERENCES projekt.pracownik (pesel)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.operacja ADD CONSTRAINT doctor_operation_fk
FOREIGN KEY (pesel_d)
REFERENCES projekt.doktor (pesel_d)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.recepta ADD CONSTRAINT doktor_badania_fk1
FOREIGN KEY (pesel_d)
REFERENCES projekt.doktor (pesel_d)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.wizyta ADD CONSTRAINT doktor_wizyta_fk
FOREIGN KEY (pesel_d)
REFERENCES projekt.doktor (pesel_d)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.oddzial ADD CONSTRAINT doktor_oddzial_fk
FOREIGN KEY (pesel_d)
REFERENCES projekt.doktor (pesel_d)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.pacjent ADD CONSTRAINT department_patient_fk
FOREIGN KEY (oddzial_id)
REFERENCES projekt.oddzial (oddzial_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.sala ADD CONSTRAINT department_room_fk
FOREIGN KEY (oddzial_id)
REFERENCES projekt.oddzial (oddzial_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.personel ADD CONSTRAINT oddzial_pielegniarka_fk
FOREIGN KEY (oddzial_id)
REFERENCES projekt.oddzial (oddzial_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.operacja ADD CONSTRAINT pielegniarka_operacja_fk
FOREIGN KEY (pesel_n)
REFERENCES projekt.personel (pesel_n)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.sala ADD CONSTRAINT pielegniarka_sala_fk
FOREIGN KEY (pesel_n)
REFERENCES projekt.personel (pesel_n)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.operacja ADD CONSTRAINT pokoj_sala_operacja_fk
FOREIGN KEY (numer)
REFERENCES projekt.sala (numer)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.pacjent ADD CONSTRAINT sala_pacjent_fk
FOREIGN KEY (numer)
REFERENCES projekt.sala (numer)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.operacja ADD CONSTRAINT patient_operation_fk
FOREIGN KEY (pesel_p)
REFERENCES projekt.pacjent (pesel_p)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.wizyta ADD CONSTRAINT pacjent_wizyta_fk
FOREIGN KEY (pesel_p)
REFERENCES projekt.pacjent (pesel_p)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt.recepta ADD CONSTRAINT pacjent_recepta_fk
FOREIGN KEY (pesel_p)
REFERENCES projekt.pacjent (pesel_p)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;





CREATE VIEW "Etaty" as SELECT nazwa, count(*) "Liczba odzialow", sum(miejsca) as "Liczba etatow"
FROM projekt.oddzial GROUP BY nazwa HAVING SUM(miejsca)>0 ;

CREATE VIEW "Liczba godzin według wydziałów" as
SELECT o.nazwa ,MAX( (60*CAST( SUBSTRING(ob.godziny, 0,3) AS FLOAT)+CAST( SUBSTRING(ob.godziny, 4, 7) AS FLOAT))/60),
MIN( (60*CAST( SUBSTRING(ob.godziny, 0,3) AS FLOAT)+CAST( SUBSTRING(ob.godziny, 4, 7) AS FLOAT))/60),
AVG( (60*CAST( SUBSTRING(ob.godziny, 0,3) AS FLOAT)+CAST( SUBSTRING(ob.godziny, 4, 7) AS FLOAT))/60)
 FROM projekt.obecnosc OB
 LEFT JOIN projekt.pracownik PR USING (pesel)
 JOIN projekt.personel PE ON pesel=pesel_n
 LEFT JOIN projekt.oddzial O USING (oddzial_id)
 GROUP BY o.nazwa;

CREATE VIEW "Średnia ilość pacjentow na oddziale" as
WITH ilosc_obecnosci AS (SELECT COUNT(*) AS KU
					FROM projekt.obecnosc
					JOIN projekt.pracownik USING (pesel)
					JOIN projekt.personel ON pesel=pesel_n
					LEFT JOIN projekt.oddzial USING (oddzial_id)
					WHERE status='obecny'
					GROUP BY nazwa),
ilosc_calkowita AS (SELECT COUNT(*) AS UCZ
					FROM projekt.obecnosc
					JOIN projekt.pracownik USING (pesel)
					JOIN projekt.personel ON pesel=pesel_n
					LEFT JOIN projekt.oddzial USING (oddzial_id)
					GROUP BY nazwa )
SELECT UCZ::FLOAT/KU::FLOAT AS "Średnia ilość pacjentow na oddziale" FROM ilosc_obecnosci,ilosc_calkowita;

CREATE VIEW "Zarobki wedlug pacjentow" as
SELECT pesel_p,imie,nazwisko,sum(koszt)
FROM projekt.pacjent
LEFT JOIN projekt.wizyta USING(pesel_p)
GROUP BY pesel_p ORDER BY sum(koszt);

CREATE VIEW "Najczestsze schorzenia" as
SELECT schorzenie,count(*) as "Liczba"
FROM projekt.pacjent P
GROUP BY schorzenie;

CREATE VIEW "Oddzial z najwieksza liczba miejsc w salach" as
SELECT nazwa,sum(s.miejsca)
FROM projekt.sala S
LEFT JOIN projekt.oddzial O USING (oddzial_id)
GROUP BY nazwa;

CREATE VIEW "Oddzial z najbardziej zapracowanym personelem" as
SELECT nazwa,sum((60*CAST( SUBSTRING(ob.godziny, 0,3) AS FLOAT)+CAST( SUBSTRING(ob.godziny, 4, 7) AS FLOAT))/60)
FROM projekt.obecnosc OB
JOIN projekt.pracownik PR USING (pesel)
JOIN projekt.personel PE ON pesel_n=pesel
LEFT JOIN projekt.oddzial O USING(oddzial_id)
GROUP BY nazwa;

CREATE VIEW "Doktor z najwieksza liczbą operacji" as
SELECT cos.pesel_d,cos."Liczba operacji",imie,nazwisko FROM projekt.doktor
JOIN projekt.pracownik ON pesel=pesel_d
JOIN (SELECT pesel_d,count(*) as "Liczba operacji"
FROM projekt.operacja
LEFT JOIN projekt.doktor USING (pesel_d)
GROUP BY pesel_d) as cos USING (pesel_d);

CREATE VIEW "Pacjent z najwieksą liczbą operacji" as
SELECT cos.pesel_p,cos."Liczba operacji",imie,nazwisko FROM projekt.pacjent
JOIN (SELECT pesel_p,count(*) as "Liczba operacji"
FROM projekt.operacja
LEFT JOIN projekt.pacjent USING (pesel_p)
GROUP BY pesel_p) as cos USING (pesel_p);





CREATE ROLE role_creator WITH CREATEROLE LOGIN PASSWORD 'pass';
GRANT ALL PRIVILEGES ON SCHEMA projekt TO role_creator;
GRANT INSERT ON projekt.doktor TO role_creator;
GRANT INSERT ON projekt.personel TO role_creator;
GRANT INSERT ON projekt.pracownik TO role_creator;
GRANT SELECT ON "Etaty" TO role_creator;
GRANT SELECT ON projekt.oddzial TO role_creator;
GRANT SELECT ON projekt.personel TO role_creator;
GRANT SELECT ON projekt.doktor TO role_creator;

CREATE ROLE doktor;
GRANT ALL PRIVILEGES ON SCHEMA projekt TO doktor;
GRANT INSERT, SELECT, DELETE,UPDATE ON projekt.personel,projekt.sala,projekt.doktor,projekt.pracownik,projekt.oddzial,projekt.operacja,projekt.obecnosc,projekt.pacjent,projekt.wizyta,projekt.recepta TO doktor;
GRANT USAGE, SELECT ON SEQUENCE projekt.oddzial_oddzial_id_seq,projekt.operacja_operacja_id_seq,projekt.obecnosc_obecnosc_id_seq,projekt.wizyta_wizyta_id_seq,projekt.recepta_recepta_id_seq TO doktor;
GRANT SELECT ON "Etaty" TO doktor;
GRANT SELECT ON "Liczba godzin według wydziałów","Średnia ilość pacjentow na oddziale","Zarobki wedlug pacjentow","Najczestsze schorzenia","Oddzial z najwieksza liczba miejsc w salach","Oddzial z najbardziej zapracowanym personelem","Doktor z najwieksza liczbą operacji","Pacjent z najwieksą liczbą operacji" to doktor;

CREATE ROLE personel;
GRANT ALL PRIVILEGES ON SCHEMA projekt TO personel;
GRANT INSERT, SELECT, DELETE ON projekt.sala,projekt.personel,projekt.doktor,projekt.pracownik,projekt.oddzial,projekt.operacja,projekt.obecnosc,projekt.pacjent,projekt.wizyta,projekt.recepta TO personel;
GRANT USAGE, SELECT ON SEQUENCE projekt.oddzial_oddzial_id_seq,projekt.operacja_operacja_id_seq,projekt.obecnosc_obecnosc_id_seq,projekt.wizyta_wizyta_id_seq,projekt.recepta_recepta_id_seq TO personel;
GRANT SELECT ON "Liczba godzin według wydziałów","Średnia ilość pacjentow na oddziale","Zarobki wedlug pacjentow","Najczestsze schorzenia","Oddzial z najwieksza liczba miejsc w salach","Oddzial z najbardziej zapracowanym personelem","Doktor z najwieksza liczbą operacji","Pacjent z najwieksą liczbą operacji" to personel;



INSERT INTO projekt.pracownik values('11122233344','imie','nazwisko','mail@gmail.com','haslo','111222333');
INSERT INTO projekt.doktor values('11122233344','doktor');
CREATE ROLE "11122233344" LOGIN PASSWORD  'haslo';
GRANT doktor TO "11122233344";
INSERT INTO projekt.pracownik values('11122233355','imie','nazwisko','mail@gmail.com','haslo','111222333');
INSERT INTO projekt.doktor values('11122233355','doktor');
CREATE ROLE "11122233355" LOGIN PASSWORD  'haslo';
INSERT INTO projekt.pracownik values('11122233366','imie','nazwisko','mail@gmail.com','haslo','111222333');
INSERT INTO projekt.doktor values('11122233366','doktor');
CREATE ROLE "11122233366" LOGIN PASSWORD  'haslo';
GRANT doktor TO "11122233366";
INSERT INTO projekt.oddzial(miejsca,nazwa,pesel_d) values(10,'Alergologia','11122233344'),
(10,'Chirurgia dziecieca','11122233344'),
(10,'Chirurgia onkologiczna','11122233355'),
(10,'Choroby wewnetrzne','11122233355'),
(10,'Geriatria','11122233355'),
(10,'Kardiologia','11122233366'),
(10,'Neurologia','11122233366'),
(10,'Okulistyka','11122233344'),
(10,'Stomatologia','11122233344');


INSERT INTO projekt.pracownik values('11122233377','imie','nazwisko','mail@gmail.com','haslo','111222333');
INSERT INTO projekt.personel values('11122233377','pielegnierka',1);
CREATE ROLE "11122233377" LOGIN PASSWORD  'haslo';
GRANT personel TO "11122233377";
INSERT INTO projekt.pracownik values('11122233388','imie','nazwisko','mail@gmail.com','haslo','111222333');
INSERT INTO projekt.personel values('11122233388','pielegnierka',1);
CREATE ROLE "11122233388" LOGIN PASSWORD  'haslo';
GRANT personel TO "11122233388";
INSERT INTO projekt.pracownik values('11122233399','imie','nazwisko','mail@gmail.com','haslo','111222333');
INSERT INTO projekt.personel values('11122233399','pielegnierka',1);
CREATE ROLE "11122233399" LOGIN PASSWORD  'haslo';
GRANT personel TO "11122233399";
INSERT INTO projekt.sala values (100,1,'operacyjna','11122233377',10,'zajeta'),
(101,1,'operacyjna','11122233377',10,'zajeta'),
(102,1,'poloznicza','11122233377',10,'zajeta'),
(103,1,'operacyjna','11122233388',10,'zajeta'),
(104,2,'operacyjna','11122233388',10,'zajeta'),
(105,2,'poloznicza','11122233388',10,'zajeta'),
(106,2,'operacyjna','11122233399',10,'zajeta'),
(107,2,'operacyjna','11122233399',10,'zajeta');





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





INSERT INTO projekt.pacjent values('11122233344','imie','nazwisko',1,'bol','111222333',100);
INSERT INTO projekt.pacjent values('11122233355','imie','nazwisko',1,'bol','111222333',100);
INSERT INTO projekt.pacjent values('11122233366','imie','nazwisko',1,'bol','111222333',100);
INSERT INTO projekt.pacjent values('11122233377','imie','nazwisko',1,'bol','111222333',100);
INSERT INTO projekt.pacjent values('11122233388','imie','nazwisko',1,'bol','111222333',100);
INSERT INTO projekt.wizyta(opis,pesel_d,koszt,pesel_p,data) values('wysypka','11122233355',12,'11122233355','11.11.2000');
INSERT INTO projekt.wizyta(opis,pesel_d,koszt,pesel_p,data) values('wysypka','11122233366',12,'11122233355','11.11.2000');
INSERT INTO projekt.wizyta(opis,pesel_d,koszt,pesel_p,data) values('wysypka','11122233366',12,'11122233355','11.11.2000');
INSERT INTO projekt.wizyta(opis,pesel_d,koszt,pesel_p,data) values('wysypka','11122233344',12,'11122233355','11.11.2000');
INSERT INTO projekt.obecnosc(godziny,data,pesel,status) values('12:45','12.11.2000','11122233344','obecny');
INSERT INTO projekt.obecnosc(godziny,data,pesel,status) values('16:45','12.09.2000','11122233355','nieobecny');
INSERT INTO projekt.obecnosc(godziny,data,pesel,status) values('11:45','12.01.2000','11122233366','obecny');
INSERT INTO projekt.obecnosc(godziny,data,pesel,status) values('12:45','12.11.2000','11122233399','obecny');
INSERT INTO projekt.obecnosc(godziny,data,pesel,status) values('16:45','12.09.2000','11122233388','nieobecny');
INSERT INTO projekt.obecnosc(godziny,data,pesel,status) values('11:45','12.01.2000','11122233377','obecny');
INSERT INTO projekt.recepta(pesel_d,opis,lekarstwo,pesel_p) values('11122233344','doustnie','herbapect','11122233366');
INSERT INTO projekt.recepta(pesel_d,opis,lekarstwo,pesel_p) values('11122233355','doustnie','herbapect','11122233377');
INSERT INTO projekt.operacja(data,nazwa,pesel_p,numer,pesel_d,pesel_n) values('11.12.2000','wyciecie wyrostka','11122233344',100,'11122233355','11122233399');
INSERT INTO projekt.operacja(data,nazwa,pesel_p,numer,pesel_d,pesel_n) values('11.12.2000','wyciecie wyrostka','11122233344',100,'11122233355','11122233399');
