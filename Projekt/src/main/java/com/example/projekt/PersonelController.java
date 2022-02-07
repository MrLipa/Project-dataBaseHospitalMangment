package com.example.projekt;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa PersonelController
 *
 * klasa kontrolera która obsługuje wszelkie zdażenia w scene Personel tj funkcje wywołujące podczas klikniecia czy połaczenie interfejsu GUI z bazą danych
 *
 * @author Tomasz Szkaradek
 * @version 0.1
 *
 */
public class PersonelController {

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    /**
     * Scena dodawanie obecnosci
     */
    @FXML
    private AnchorPane add_presence;

    @FXML
    private DatePicker add_presence_date;

    @FXML
    private TextField add_presence_hour;

    @FXML
    private Label add_presence_label;

    @FXML
    private TextField add_presence_status;

    /**
     * Scena dodawanie pokoju
     */
    @FXML
    private AnchorPane add_room;

    @FXML
    private TextField add_room_kind;

    @FXML
    private Label add_room_label;

    @FXML
    private TextField add_room_number;

    @FXML
    private TextField add_room_places;

    @FXML
    private TextField add_room_status;

    @FXML
    private TextField find;

    /**
     * Lista rozwijana z parametrami wyszukiwania
     */
    @FXML
    private ComboBox<String> find_option;

    /**
     * Scena profilu
     */
    @FXML
    private AnchorPane profil;

    /**
     * Lista oddzialów w scenie pokoj
     */
    @FXML
    private ComboBox<String> add_room_department;

    @FXML
    private Label profil_fname;

    @FXML
    private Label profil_lname;

    @FXML
    private Label profil_pesel;

    @FXML
    private Label profil_specialization;

    @FXML
    private Label profil_department;

    /**
     * Scena bazy danych
     */
    @FXML
    private AnchorPane select;

    /**
     * Lista opcji
     */
    @FXML
    private ComboBox<String> select_option;

    /**
     * Scena menu bocznego
     */
    @FXML
    private AnchorPane slider;

    /**
     * Tabela danch
     */
    @FXML
    private TableView<?> table;

    ArrayList<AnchorPane> listScene;

    ObservableList<String> select_option_list;
    ObservableList<String> select_operation_find_list;
    ObservableList<String> select_presence_find_list;
    ObservableList<String> select_room_find_list;

    /**
     * Metoda inicjalizujaca dane
     */
    @FXML
    public void initialize() {
        listScene =  new ArrayList<>() {{add(profil);add(add_room);add(select);add(add_presence);}};
        select_option_list= FXCollections.observableArrayList("Liczba godzin według wydziałów","Średnia ilość pacjentow na oddziale","Zarobki wedlug pacjentow","Najczestsze schorzenia","Oddzial z najwieksza liczba miejsc w salach","Oddzial z najbardziej zapracowanym personelem","Doktor z najwieksza liczbą operacji","Pacjent z najwieksą liczbą operacji");
        select_operation_find_list= FXCollections.observableArrayList("nazwa","pesel_p","numer","data","pesel_n");
        select_presence_find_list= FXCollections.observableArrayList("status","godziny","data");
        select_room_find_list= FXCollections.observableArrayList("numer","oddzial_id","rodzaj","miejsca","status");

        select_option.setItems(select_option_list);

        ObservableList<String> lista=PSQL.department1();
        add_room_department.setItems(lista);
    }

    /**
     * Metoda zmieniajaca widok na dodawanie obecnosci
     * @param event zdarzenie
     */
    @FXML
    void AddPresence(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        add_presence.setVisible(true);
    }

    /**
     * Metoda zmieniajaca widok na dodawanie pokoju
     * @param event zdarzenie
     */
    @FXML
    void AddRoom(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        add_room.setVisible(true);
    }

    /**
     * Metoda wylogowywujaca
     * @param event zdarzenie
     */
    @FXML
    void Logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Metoda otwierajaca menu
     * @throws IOException
     */
    @FXML
    public void Menu() throws IOException {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);

        slide.setToX(0);
        slide.play();

        slider.setTranslateX(-176);

        slide.setOnFinished((ActionEvent e)-> {
            Menu.setVisible(false);
            MenuClose.setVisible(true);
        });
    }

    /**
     * Metoda zamykajaca menu
     * @throws IOException
     */
    @FXML
    public void MenuClose() throws IOException{
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);

        slide.setToX(-176);
        slide.play();

        slider.setTranslateX(0);

        slide.setOnFinished((ActionEvent e)-> {
            Menu.setVisible(true);
            MenuClose.setVisible(false);
        });
    }

    /**
     * Metoda zmieniajaca widok na profil
     * @param event zdarzenie
     */
    @FXML
    void Profil(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        profil.setVisible(true);
        PSQL.profil1(profil_fname,profil_lname,profil_pesel,profil_specialization,profil_department);
    }

    /**
     * Metoda zmieniajaca widok na baze danych z operacjiami
     * @param event zdarzenie
     */
    @FXML
    void SelectOperation(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        select.setVisible(true);
        find_option.setItems(select_operation_find_list);
        PSQL.select(table,"SELECT * from projekt.operacja",(ObservableList p) ->
        {
            table.getItems().remove(p);
            String SQL="DELETE FROM projekt.obecnosc WHERE operacja_id='"+p.get(0)+"'";
            PSQL.delete(SQL);
            return p;
        });
    }

    /**
     * Metoda zmieniajaca widok na baze danych z obecnosciami
     * @param event zdarzenie
     */
    @FXML
    void SelectPresence(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        select.setVisible(true);
        find_option.setItems(select_presence_find_list);
        PSQL.select(table,"SELECT * from projekt.obecnosc",(ObservableList p) ->
        {
            table.getItems().remove(p);
            String SQL="DELETE FROM projekt.obecnosc WHERE obecnosc_id='"+p.get(0)+"'";
            PSQL.delete(SQL);
            return p;
        });
    }

    /**
     * Metoda zmieniajaca widok na baze danych z salami
     * @param event zdarzenie
     */
    @FXML
    void SelectRoom(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        select.setVisible(true);
        find_option.setItems(select_room_find_list);
        PSQL.select(table,"SELECT numer,oddzial_id,rodzaj,pesel_n,s.miejsca,status,nazwa from projekt.sala S\n" +
                "LEFT JOIN projekt.oddzial O USING (oddzial_id);",(ObservableList p) ->
        {
            table.getItems().remove(p);
            String SQL="DELETE FROM projekt.sala WHERE sala_id='"+p.get(0)+"'";
            PSQL.delete(SQL);
            return p;
        });
    }

    /**
     * Metoda dodajaca obecnosci do bazy danych
     * @param event zdarzenie
     */
    @FXML
    void clickAddPresence(ActionEvent event) {
        PSQL.addPresence(add_presence_status,add_presence_hour,add_presence_date,add_presence_label);
    }

    /**
     * Metoda dodajaca sale do bazy danych
     * @param event zdarzenie
     */
    @FXML
    void clickAddRoom(ActionEvent event) {
        PSQL.addRoom(add_room_number,add_room_department,add_room_kind,add_room_places,add_room_status,add_room_label);
    }

    /**
     * Metoda wyszukujaca podane rekordy
     * @param event zdarzenie
     */
    @FXML
    void clickFind(ActionEvent event) {
        String SQL="SELECT * from projekt.";
        String SQL1="DELETE * from projekt.";
        if(find_option.getItems().equals(select_operation_find_list))
        {
            SQL+="operacja";
            SQL1+="operacja";
        }
        else if(find_option.getItems().equals(select_presence_find_list))
        {
            SQL+="obecnosc";
            SQL1+="operacja";
        }
        else if(find_option.getItems().equals(select_room_find_list))
        {
            SQL+="sala";
            SQL1+="operacja";
        }
        if(find.getText().equals(""))
        {
            SQL = String.format(SQL);
        }
        else if(find_option.getValue().toString().equals("oddzial_id") || find_option.getValue().toString().equals("numer"))
        {
            SQL = String.format(SQL+" WHERE %s=%s;",find_option.getValue().toString(),find.getText());
            SQL1 = String.format(SQL+" WHERE %s=%s;",find_option.getValue().toString(),find.getText());
        }
        else
        {
            SQL = String.format(SQL+" WHERE %s='%s';",find_option.getValue().toString(),find.getText());
            SQL1 = String.format(SQL+" WHERE %s=%s;",find_option.getValue().toString(),find.getText());
        }
        PSQL.select(table,SQL,(ObservableList p) ->
        {
            table.getItems().remove(p);
            return p;
        });
    }

    /**
     * Metoda realizujaca wybrane zapytanie
     * @param event zdarzenie
     */
    @FXML
    void clickSelect(ActionEvent event) {
        String SQL="SELECT * from \""+select_option.getValue()+"\";";
        PSQL.selectOption(table,SQL);
    }

}
