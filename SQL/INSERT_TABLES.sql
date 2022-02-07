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
