package com.example.projekt;

import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Klasa StartController
 *
 * klasa kontrolera która obsługuje wszelkie zdażenia w scene Start tj funkcje wywołujące podczas klikniecia czy połaczenie interfejsu GUI z bazą danych
 * to w niej sie logujemi i rejestrujemy
 *
 * @author Tomasz Szkaradek
 * @version 0.1
 *
 */
public class StartController {

    /** Etykeita Menu*/
    @FXML
    private Label Menu;
    @FXML
    private Label MenuClose;
    /** Lista rozwijana z oddzialami*/
    @FXML
    private ComboBox<String> department;
    /** Miejsce do wpisani imienia*/
    @FXML
    private TextField fname;
    /** Scena z pomoca */
    @FXML
    private AnchorPane help;
    @FXML
    private TextField lname;
    /** Scena z logowaniem */
    @FXML
    private AnchorPane login;
    @FXML
    private Label login_label;
    @FXML
    private PasswordField login_password;
    @FXML
    private TextField login_pesel;
    @FXML
    private PasswordField password;
    /** Liczba p */
    @FXML
    private TextField pesel;
    @FXML
    private TextField phone;
    @FXML
    private TextField mail;
    /** Scena z rejestracja */
    @FXML
    private AnchorPane register;
    @FXML
    private Label register_label;
    /** Scena z bocznym menu */
    @FXML
    private AnchorPane slider;
    /** Liczba p */
    @FXML
    private TextField specialization;
    @FXML
    private Label specialization_label;
    @FXML
    private Label department_label;
    /** Scena startowa */
    @FXML
    private AnchorPane start;
    /** Lista opcji wyszukiwania */
    ObservableList<String> lista;
    /** Lista scen*/
    ArrayList<AnchorPane> listScene;

    /**
     *
     * Funkcja inicalizująca
     */
    @FXML
    public void initialize() {
        listScene =  new ArrayList<>() {{add(start);add(help);add(login);add(register);}};
        lista=PSQL.department();
    }
    /**
     * Funkcja wyswpietlajaca opcje do rejestorwania doktora
     * @param event zdarzenie
     */
    @FXML
    void DoctorClick(ActionEvent event) {
        specialization_label.setText("Tytuł");
        department_label.setVisible(false);
        department.setVisible(false);
        PSQL.acces=1;
    }
    /**
     * Funkcja przeprowadzajaca logowanie
     * @param event wartosc przechowujaca zdarzenie
     * @throws IOException
     */

    @FXML
    void Login(ActionEvent event) throws IOException {
        PSQL.login(login_pesel.getText(),login_password.getText());
        String SQL="{call projekt.ifPeselDoktor(?)}";
        if(PSQL.acces==2)
        {
            SQL="{call projekt.ifPeselPersonel(?)}";
        }
        try {
            CallableStatement cst = PSQL.get_create_user_conn().prepareCall(SQL);
            cst.setString(1,PSQL.user);
            ResultSet rs ;
            rs = cst.executeQuery();
            rs.next();
            if(!rs.getBoolean(1))
            {
                login_label.setVisible(true);
                return;
            }
            rs.close();
            cst.close();
        }
        catch(SQLException e)  {
            System.out.println("Blad podczas przetwarzania danych:") ;
        }

        if(PSQL.create_connection())
        {
            String view = switch (PSQL.acces) {
                case 1 -> "Doctor";
                case 2 -> "Personel";
                default -> "";
            };
            Parent root = FXMLLoader.load(getClass().getResource(view+".fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            login_label.setVisible(true);
        }
    }

    /**
     * Funkcja wyswietlajaca menu boczne.
     * @throws IOException
     */
    @FXML
    public void Menu() throws IOException {
        TranslateTransition slide = new TranslateTransition();
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
     * Funkcja zamykajaca menu boczne.
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
     * Funkcja wyswpietlajaca opcje do rejestorwania personelu
     * @param event zdarzenie
     */
    @FXML
    void NurseClick(ActionEvent event) {
        specialization_label.setText("Specializacja");
        department_label.setVisible(true);
        department.setVisible(true);
        PSQL.acces=2;
    }

    /**
     * Funkcja przeprowadzajaca rejestracje
     * @param event wartosc przechowujaca zdarzenie
     * @throws IOException
     */
    @FXML
    void Register(ActionEvent event) throws IOException {
        String table = switch (PSQL.acces) {
            case 1 -> "doktor VALUES (?,?);";
            case 2 -> "personel VALUES (?,?,?);";
            default -> "";
        };
        try {
            String query = "INSERT INTO projekt.pracownik VALUES(?,?,?,?,?,?);INSERT INTO projekt." + table;
            PreparedStatement pst = PSQL.get_create_user_conn().prepareStatement(query);
            pst.setString(1, pesel.getText());
            pst.setString(2, fname.getText());
            pst.setString(3, lname.getText());
            pst.setString(4, mail.getText());
            pst.setString(5, password.getText());
            pst.setString(6, phone.getText());
            pst.setString(7, pesel.getText());
            pst.setString(8, specialization.getText());
            if(table.equals("personel VALUES (?,?,?);"))
            {
                if(department.getValue()!=null)
                {
                    pst.setInt(9, PSQL.department2(department.getValue().split("/")[0]));
                }
            }
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            if(e.toString().indexOf("/")>0 ) {
                register_label.setVisible(true);
                register_label.setText(e.toString().split("/")[1]);
            }
            return;
        }
        catch (Exception e)
        {
            register_label.setVisible(true);
            register_label.setText("Nie poprawne dane");
        }
        table = switch (PSQL.acces) {
            case 1 -> "doktor";
            case 2 -> "personel";
            default -> "";
        };
        try {
            String query = "CREATE ROLE \"" + pesel.getText() + "\" LOGIN PASSWORD '" + password.getText() + "'";
            PreparedStatement pst = PSQL.get_create_user_conn().prepareStatement(query);
            pst.executeUpdate();
            query = "GRANT " + table + " TO \"" + pesel.getText() + "\"";
            pst = PSQL.get_create_user_conn().prepareStatement(query);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            register_label.setVisible(true);
            register_label.setText("Nie udana rejestracja\n(Urzytkownik juz istenieje lub brak polaczenia z baza danych)");
            return;
        }
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        login.setVisible(true);
    }
    /**
     * Zmienia n.
     *
     * Funkcja okreslajaca wybranie opcji lekarz
     *
     * @param event zdarzenie
     */

    @FXML
    void set_acces1(ActionEvent event) {
        PSQL.acces=1;
    }

    /**
     * Zmienia n.
     *
     * Funkcja okreslajaca wybranie opcji personel
     *
     * @param event zdarzenie
     */
    @FXML
    void set_acces2(ActionEvent event) {
        PSQL.acces=2;
    }

    /**
     * Funkcja wyswietlajaca scene pomoc
     *
     * @param event zdarzenie
     */
    @FXML
    void switchToHelp(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        help.setVisible(true);
    }

    /**
     * Funkcja wyswietlajaca scene login
     *
     * @param event zdarzenie
     */
    @FXML
    void switchToLogin(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        login.setVisible(true);
    }

    /**
     * Funkcja wyswietlajaca scene rejestrowanie
     *
     * @param event zdarzenie
     */
    @FXML
    void switchToRegister(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        register.setVisible(true);
        department.setItems(lista);
    }

    /**
     * Funkcja wyswietlajaca scene startowa
     *
     * @param event zdarzenie
     */
    @FXML
    void switchToStart(ActionEvent event) {
        for(AnchorPane el:listScene)
        {
            el.setVisible(false);
        }
        start.setVisible(true);
    }
}