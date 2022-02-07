package com.example.projekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Klasa Main
 * klasa odpalajaca GUI naszej aplikacji ustawiajaca jej parametry tj.rozmiair okna
 * @author Tomasz Szkaradek
 * @version 0.1
 *
 */
public class Main extends Application {
    /**
     * Funkcja odpalająca GUI
     * @param stage scena do wyswietlenia
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Szpital");
        Image icon=new Image(getClass().getResourceAsStream("Images/icon.png"));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Funkcja main
     * @param args lista argumentów z konsoli
     */
    public static void main(String[] args) {
        launch(args);
    }
}