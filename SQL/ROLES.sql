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