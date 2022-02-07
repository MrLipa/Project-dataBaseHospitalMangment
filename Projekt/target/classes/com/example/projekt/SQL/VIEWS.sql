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
SELECT pesel_d,count(*) as "Liczba operacji"
FROM projekt.operacja
LEFT JOIN projekt.doktor USING (pesel_d)
GROUP BY pesel_d;

CREATE VIEW "Pacjent z najwieksą liczbą operacji" as
SELECT pesel_p,count(*) as "Liczba operacji"
FROM projekt.operacja
LEFT JOIN projekt.pacjent USING (pesel_p)
GROUP BY pesel_p;