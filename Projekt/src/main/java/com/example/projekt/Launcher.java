package com.example.projekt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Klasa Launcher
 * klasa potrzebna do odpalenia aplikacji i ustawienia odpowiednich parametrów
 *
 * @author Tomasz Szkaradek
 * @version 0.1
 *
 */
public class Launcher {
    /**
     * metoda main ustawiajaca zmienna url naszej bazy i odpalająca aplikacje
     * @param args lista argumentów z konsoli
     */
    public static void main(String[] args) throws IOException {
        if(args.length>0)
        {
            PSQL.url=args[0];
        }
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("URL.txt"));
            PSQL.url=reader.readLine();

        }
        catch(Exception e)
        {
            ;
        }
        Main.main(args);
    }
}