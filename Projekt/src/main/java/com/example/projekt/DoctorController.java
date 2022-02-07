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
 * Klasa DoctorController
 *
 * klasa kontrolera która obsługuje wszelkie zdażenia w scene Doctor tj funkcje wywołujące podczas klikniecia czy połaczenie interfejsu GUI z bazą danych
 *
 * @author Tomasz Szkaradek
 * @version 0.1
 *
 */
public class DoctorController {

    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;

    /**
     * Scena dodawanie oddzialu
     */
    @FXML
    private AnchorPane add_department;

    @FXML
    private TextField add_department_name;

    @FXML
    private TextField add_department_personel;

    /**
     * Scena dodawanie operacji
     */
    @FXML
    private AnchorPane add_operation;

    @FXML
    private DatePicker add_operation_date;

    @FXML
    private Label add_operation_label;

    @FXML
    private TextField add_operation_name;

    @FXML
    private TextField add_operation_pesel_n;

    @FXML
    private TextField add_operation_pesel_p;

    @FXML
    private TextField add_operation_room;

    /**
     * Scena dodawanie pacjenta
     */
    @FXML
    private AnchorPane add_patient;

    /**
     * lista rozwijana z oddzialami
     */
    @FXML
    private ComboBox<String> add_patient_department;

    @FXML
    private TextField add_patient_disease;

    @FXML
    private TextField add_patient_fname;

    @FXML
    private Label add_patient_label;

    @FXML
    private TextField add_patient_lname;

    @FXML
    private TextField add_patient_pesel;

    @FXML
    private TextField add_patient_phone;

    @FXML
    private TextField add_prescription_description;

    @FXML
    private TextField add_prescription_drug;

    @FXML
    private Label add_prescription_label;

    @FXML
    private Label add_department_label;

    /**
     * Scena oddawania obecnosci
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
     * Scena oddawania wizyty
     */
    @FXML
    private AnchorPane add_visit;

    @FXML
    private DatePicker add_visit_date;

    @FXML
    private TextField add_visit_description;

    @FXML
    private Label add_visit_label;

    @FXML
    private TextField add_visit_money;

    @FXML
    private TextField add_visit_pesel_p;

    @FXML
    private TextField find;

    @FXML
    private TextField add_prescription_pesel_p;

    /**
     * Lista rozwijana z parametrami wyszukiwania
     */
    @FXML
    private ComboBox<String> find_option;

    /**
     * Scena z profilem
     */
    @FXML
    private AnchorPane profil;

    @FXML
    private Label profil_fname;

    @FXML
    private Label profil_lname;

    @FXML
    private Label profil_pesel;

    @FXML
    private Label profil_specialization;

    /**
     * Scena z bazą danych
     */
    @FXML
    private AnchorPane select;

    /**
     * Lista rozwijana z opcjami wyszukiwania
     */
    @FXML
    private ComboBox<String> select_option;

    /**
     * Scena bocznego menu
     */
    @FXML
    private AnchorPane slider;

    /**
     * Tabela z danymi
     */
    @FXML
    private TableView<?> table;

    ArrayList<AnchorPane> listScene;

    ObservableList<String> select_option_list;
    ObservableList<String> select_patient_find_list;
    ObservableList<String> select_visit_find_list;
    ObservableList<String> select_operation_find_list;
    ObservableList<String> select_presence_find_list;
    ObservableList<String> select_prescription_find_list;
    ObservableList<String> select_department_find_list;

    /**
     * Metoda inicalizująca dane
     */
    @FXML
    public void initialize() {
        listScene =  new ArrayList<>() {{add(profil);add(add_patient);add(select);add(add_visit);add(add_presence);add(add_operation);add(add_department);}};

        ObservableList<String> lista=PSQL.department1();
        add_patient_department.setItems(lista);

        select_option_list= FXCollections.observableArrayList("Liczba godzin według wydziałów","Średnia ilość pacjentow na oddziale","Zarobki wedlug pacjentow","Najczestsze schorzenia","Oddzial z najwieksza liczba miejsc w salach","Oddzial z najbardziej zapracowanym personelem","Doktor z najwieksza liczbą operacji","Pacjent z najwieksą liczbą operacji");
        select_patient_find_list= FXCollections.observableArrayList("pesel_p","imie","nazwisko","oddzial_id","schorzenie","telefon","numer");
        select_operation_find_list= FXCollections.observableArrayList("nazwa","pesel_p","numer","data","pesel_n");
        select_visit_find_list= FXCollections.observableArrayList("pesel_p","data","opis","koszt");
        select_presence_find_list= FXCollections.observableArrayList("status","godziny","data");
        select_prescription_find_list= FXCollections.observableArrayList("pesel_p","opis","lekarstwo");
        select_department_find_list= FXCollections.observableArrayList("miejsca","nazwa","pesel_d");

        select_option.setItems(select_option_list);

    }

    /**
     * Metoda zmieniajaca widok na dodawanie oddzialu
     *
     * @param event zdarzenie
     */
    @FXML
    void AddDepartment(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        add_department.setVisible(true);
    }

    /**
     * Metoda zmieniajaca widok na dodawanie operacji
     *
     * @param event zdarzenie
     */
    @FXML
    void AddOperation(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        add_operation.setVisible(true);
    }

    /**
     * Metoda zmieniajaca widok na dodawanie pacjenta
     *
     * @param event zdarzenie
     */
    @FXML
    void AddPatient(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        add_patient.setVisible(true);
    }

    /**
     * Metoda zmieniajaca widok na dodawanie obecnosci
     *
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
     * Metoda zmieniajaca widok na dodawanie wizyty
     *
     * @param event zdarzenie
     */
    @FXML
    void AddVisit(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        add_visit.setVisible(true);
    }

    /**
     * Metoda wylogowujaca
     *
     * @param event zdarzenie
     * @throws IOException
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
     * Metoda otwieracjaca menu
     *
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
     * metoda zamykajaca menu
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
     * Metoda otwierajaca scene z profilem
     * @param event zdarzenie
     */
    @FXML
    void Profil(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        profil.setVisible(true);
        PSQL.profil(profil_fname,profil_lname,profil_pesel,profil_specialization);
    }

    /**
     * Metoda otwierajaca scene z baza oddzialow
     * @param event zdarzenie
     */
    @FXML
    void SelectDepartment(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        select.setVisible(true);
        find_option.setItems(select_department_find_list);
        PSQL.select(table,"SELECT * from projekt.oddzial",(ObservableList p) ->
        {
            table.getItems().remove(p);
            String SQL="DELETE FROM projekt.oddzial WHERE oddzial_id='"+p.get(0)+"'";
            PSQL.delete(SQL);
            return p;
        });
    }

    /**
     * Metoda otwierajaca scene z baza operacji
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
     * Metoda otwierajaca scene z baza recept
     * @param event zdarzenie
     */
    @FXML
    void SelectPrescription(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        select.setVisible(true);
        find_option.setItems(select_prescription_find_list);
        PSQL.select(table,"SELECT * from projekt.recepta",(ObservableList p) ->
        {
            table.getItems().remove(p);
            String SQL="DELETE FROM projekt.recepta WHERE recepta_id='"+p.get(0)+"';";
            PSQL.delete(SQL);
            return p;
        });
    }

    /**
     * Metoda otwierajaca scene z baza pacjentow
     * @param event zdarzenie
     */
    @FXML
    void SelectPatient(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        select.setVisible(true);
        find_option.setItems(select_patient_find_list);
        PSQL.select(table,"SELECT pesel_p,imie,nazwisko,schorzenie,telefon,numer,nazwa,oddzial_id from projekt.pacjent \n" +
                "LEFT JOIN projekt.oddzial USING (oddzial_id);",(ObservableList p) ->
        {
            table.getItems().remove(p);
            String SQL="DELETE FROM projekt.wizyta WHERE pesel_p='"+p.get(0)+"';"+"DELETE FROM projekt.pacjent WHERE pesel_p='"+p.get(0)+"'";
            PSQL.delete(SQL);
            return p;
        });
    }

    /**
     * Metoda otwierajaca scene z baza obecnosci
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
     * Metoda otwierajaca scene z baza wizyt
     * @param event zdarzenie
     */
    @FXML
    void SelectVisit(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        select.setVisible(true);
        find_option.setItems(select_visit_find_list);
        PSQL.select(table,"SELECT * from projekt.wizyta",(ObservableList p) ->
        {
            table.getItems().remove(p);
            String SQL="DELETE FROM projekt.wizyta WHERE wizyta_id='"+p.get(0)+"'";
            PSQL.delete(SQL);
            return p;
        });
    }

    /**
     * Metoda dodajaca oddzial do bazy
     * @param event zdarzenie
     */
    @FXML
    void clickAddDepartment(ActionEvent event) {
        PSQL.addDepartment(add_department_name,add_department_personel,add_department_label);
    }

    /**
     * Metoda dodajaca operacje do bazy
     * @param event zdarzenie
     */
    @FXML
    void clickAddOperation(ActionEvent event) {
        PSQL.addOperation(add_operation_name,add_operation_pesel_p,add_operation_room,add_operation_date,add_operation_pesel_n,add_operation_label);
    }

    /**
     * Metoda dodajaca pacjenta do bazy
     * @param event zdarzenie
     */
    @FXML
    void clickAddPatient(ActionEvent event) {
        PSQL.addPatient(add_patient_pesel,add_patient_fname,add_patient_lname,add_patient_department,add_patient_disease,add_patient_phone,add_patient_label);
    }

    /**
     * Metoda dodajaca recepte do bazy
     * @param event zdarzenie
     */
    @FXML
    void clickAddPrescription(ActionEvent event) {
        PSQL.addPrescription(add_prescription_pesel_p,add_prescription_description,add_prescription_drug,add_prescription_label);
    }

    /**
     * Metoda dodajaca obecnosc do bazy
     * @param event zdarzenie
     */
    @FXML
    void clickAddPresence(ActionEvent event) {
        PSQL.addPresence(add_presence_status,add_presence_hour,add_presence_date,add_presence_label);
    }

    /**
     * Metoda dodajaca wizyte do bazy
     * @param event zdarzenie
     */
    @FXML
    void clickAddVisit(ActionEvent event) {
        PSQL.addVisit(add_visit_pesel_p,add_visit_date,add_visit_description,add_visit_money,add_visit_label);
    }

    /**
     * Metoda przeszukajaca baze
     * @param event zdarzenie
     */
    @FXML
    void clickFind(ActionEvent event) {
        String SQL="SELECT * from projekt.";
        if(find_option.getItems().equals(select_patient_find_list))
        {
            SQL+="pacjent";
        }
        else if(find_option.getItems().equals(select_visit_find_list))
        {
            SQL+="wizyta";
        }
        else if(find_option.getItems().equals(select_prescription_find_list))
        {
            SQL+="recepta";
        }
        else if(find_option.getItems().equals(select_operation_find_list))
        {
            SQL+="operacja";
        }
        else if(find_option.getItems().equals(select_presence_find_list))
        {
            SQL+="obecnosc";
        }
        else if(find_option.getItems().equals(select_department_find_list))
        {
            SQL+="oddzial";
        }
        if(find.getText().equals(""))
        {
            SQL = String.format(SQL);
        }
        else if(find_option.getValue().toString().equals("oddzial_id") || find_option.getValue().toString().equals("numer"))
        {
            SQL = String.format(SQL+" WHERE %s=%s;",find_option.getValue().toString(),find.getText());
        }
        else
        {
            SQL = String.format(SQL+" WHERE %s='%s';",find_option.getValue().toString(),find.getText());
        }
        PSQL.select(table,SQL,(ObservableList p) -> {table.getItems().remove(p);return p;});
    }

    /**
     * Metoda wykonujaca zapytanie z opcji
     * @param event zdarzenie
     */
    @FXML
    void clickSelect(ActionEvent event) {
        String SQL="SELECT * from \""+select_option.getValue()+"\";";
        PSQL.selectOption(table,SQL);
    }

}
