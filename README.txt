Aby uruchomić aplikacje na początku trzeba wykonac skrypt INSERT.SQL znajdujący sie w foldzerze SQL

W katalogu Projekt_jar w pliku URL.txt trzeba usunąć przykładowy URL i zastąpić go własnym adresem url do połączenia z bazą danych
jdbc:<Link>:<Port>/<Baza Danych>
jdbc:postgresql://localhost:5432/cos

A nastepnie uruchamiamy plik Projekt.jar z katalogu Projekt_jar
WAŻNE !!! ABY PROGRAM DZIAŁAŁ NALEŻY POSIADAĆ JAVA 17

Można uruchomic aplikacje za pomocą komendy:
java -jar [ścieżka do pliku jar] [url do bazy]
java -jar C:\Users\tomek\Desktop\Projekt\out\artifacts\Projekt_jar\Projekt.jar
"jdbc:postgresql://localhost:5432/cos"