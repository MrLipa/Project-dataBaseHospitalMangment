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