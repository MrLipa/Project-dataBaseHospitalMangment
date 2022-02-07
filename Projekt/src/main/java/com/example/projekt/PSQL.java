package com.example.projekt;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;
import java.util.function.Function;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * Klasa PSQL
 *
 * klasa obsługująca baze danych psql dzięki niej jestesmy wstanie zapisac odzczytac usunąc czy zaktualizowac rekordy
 *
 * @author Tomasz Szkaradek
 * @version 0.1
 *
 */
public class PSQL {
    /**
     * Zmienna url do polaczenia z baza
     */
    public static String url;
    /**
     * Zmienna user czyli pesel uzytkownika
     */
    public static String user="";
    /**
     * Zmienna pass czyli haslo uzytkownika
     */
    public static String pass="";
    /**
     * Zmienna connection czyli zmienna do laczenia z baza danych
     */
    public static Connection c = null;
    /**
     * Zmienna access reprezentujaca poprawne logowanie rozrozniajaca 1-lekarz 2-peronel
     */
    public static int acces=2;
    /**
     * Metoda statyczna zapisujaca dane logowania
     * @param _user pesel uzytkownika
     * @param _pass haslo do konta
     */
    public static void login(String _user,String _pass)
    {
        user=_user;
        pass=_pass;
    }

    /**
     * Metoda statyczna nawiazujaca poleczenie uzytkownikiem
     * @return zwraca prawde w przypadku powodzenia falsz jesli zakonczylo sie porazka
     */
    public static boolean create_connection()
    {
        try {
            c = DriverManager.getConnection(url, user, pass);
            return true;
        } catch (SQLException se) {
            return false;
        }
    }

    /**
     * Metoda statyczna nawiazujaca poleczenie z role_creator
     * @return zwraca zmienna connection
     */
    public static Connection get_create_user_conn()
    {
        try {
            Connection c2= DriverManager.getConnection(url, "role_creator","pass");
            return c2;
        } catch (SQLException se) {
            return null;
        }
    }

    /**
     * Metoda statyczna nawiazujaca poleczenie uzytkownikiem
     * @return zwraca zmienna connection
     */
    public static Connection get_conn()
    {
        try {
            Connection c2= DriverManager.getConnection(url, user,pass);
            return c2;
        } catch (SQLException se) {
            return null;
        }
    }

    /**
     * Funkcja zwiarajaca liste dostępnych departamentow z iloscia etatow
     *
     * @return lista etatów
     */
    public static ObservableList<String> department()
    {
        ObservableList<String> lista=FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM \"Etaty\";";
            PreparedStatement pst = PSQL.get_create_user_conn().prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("nazwa")+"/"+rs.getString("Liczba odzialow")+"/"+rs.getString("Liczba etatow"));
            }
            rs.close();
        } catch (SQLException e) {
            return null;
        }
        return lista;
    }

    /**
     * Funkcja zwiarajaca liste dostępnych departamentow
     *
     * @return lista oddzialow
     */
    public static ObservableList<String> department1()
    {
        ObservableList<String> lista=FXCollections.observableArrayList();
        try {
            String query = "SELECT * FROM projekt.oddzial";
            PreparedStatement pst = PSQL.get_create_user_conn().prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lista.add(rs.getString("nazwa"));
            }
            rs.close();
        } catch (SQLException e) {
            return null;
        }
        return lista;
    }

    /**
     * Funkcja zwracajaca id oddzailu
     * @param name nazwa oddzialu
     * @return odzial id oddzialu
     */
    public static int department2(String name)
    {
        int oddzial=0;
        try {
            CallableStatement cst = PSQL.get_create_user_conn().prepareCall( "{call projekt.find_department(?)}" );
            cst.setString(1,name);
            ResultSet rs ;
            rs = cst.executeQuery();
            while (rs.next()) {
                oddzial = rs.getInt(1);
            }
            rs.close();
            cst.close();
        }
        catch(SQLException e)  {
            System.out.println("Blad podczas przetwarzania danych:"+e) ;
        }
        return oddzial;
    }

    /**
     * Funkcja wypełaniajac a tabele zawarotscia z bazy danych
     *
     * @param table tablla
     * @param SQL zapytanie
     * @param function funkcja wykonujaca onclick
     *
     */
    public static void select(TableView table, String SQL, Function< ObservableList, ObservableList> function)
    {
        Connection c=PSQL.c;
        table.getColumns().clear();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                table.getColumns().addAll(col);
            }

            TableColumn col = new TableColumn("Usuń");
            col.setCellFactory(ActionButtonTableCell.<ObservableList>forTableColumn("Usuń",function));
            table.getColumns().addAll(col);

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                row.add("cos");
                data.add(row);

            }
            table.setItems(data);
        }catch(Exception e){

            System.out.println("Error on Building Data");
        }
    }

    /**
     * Funkcja wypełaniajac a tabele zawarotscia z bazy danych
     *
     * @param table tablla
     * @param SQL zapytanie
     *
     */
    public static void selectOption(TableView table, String SQL)
    {
        Connection c=PSQL.c;
        table.getColumns().clear();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try{
            ResultSet rs = c.createStatement().executeQuery(SQL);

            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        if(param.getValue().get(j)==null)
                        {
                            return new SimpleStringProperty("null");
                        }
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                table.getColumns().addAll(col);
            }

            while(rs.next()){
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){

                    row.add(rs.getString(i));
                }
                data.add(row);
            }
            table.setItems(data);
        }catch(Exception e){
            System.out.println("Error on Building Data");
        }
    }

    /**
     * Funkcja wstawiajaca dane doktor do sceny profil
     *
     * @param profil_fname etykieta z imeiniem
     * @param profil_lname etykieta z nazwiksiem
     * @param profil_pesel etykieta z peselem
     * @param profil_specialization etykieta z specializacja
     */
    public static void profil(Label profil_fname,Label profil_lname,Label profil_pesel,Label profil_specialization)
    {
        try {
            String query = "SELECT * FROM projekt.doktor as d JOIN projekt.pracownik as p ON d.pesel_d=p.pesel WHERE d.pesel_d=?;";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setString(1, PSQL.user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                profil_fname.setText("Imie: " + rs.getString("imie"));
                profil_lname.setText("Nazwisko: " + rs.getString("nazwisko"));
                profil_pesel.setText("Pesel: " + rs.getString("pesel"));
                profil_specialization.setText("Specilizacja: " + rs.getString("tytul"));
            }
            rs.close();
        } catch (SQLException e) {
            return;
        }
    }
    /**
     * Funkcja wstawiajaca dane personelu do sceny profil
     *
     * @param profil_fname etykieta z imeiniem
     * @param profil_lname etykieta z nazwiksiem
     * @param profil_pesel etykieta z peselem
     * @param profil_specialization etykieta z specializacja
     * @param profil_department etykieta z oddzialem
     */
    public static void profil1(Label profil_fname,Label profil_lname,Label profil_pesel,Label profil_specialization,Label profil_department)
    {
        try {
            String query = "SELECT * FROM projekt.personel as d JOIN projekt.pracownik as p ON d.pesel_n=p.pesel WHERE d.pesel_n=?;";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setString(1, PSQL.user);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                profil_fname.setText("Imie: " + rs.getString("imie"));
                profil_lname.setText("Nazwisko: " + rs.getString("nazwisko"));
                profil_pesel.setText("Pesel: " + rs.getString("pesel"));
                profil_specialization.setText("Specilizacja: " + rs.getString("specjalizacja"));
                profil_department.setText("Department: " + rs.getString("oddzial_id"));
            }
            rs.close();
        } catch (SQLException e) {
            return;
        }
    }

    /**
     * Funkcja dodajaca pacjenta do bazy danych
     * @param add_patient_fname pole z imieniem
     * @param add_patient_lname pole z nazwiskiem
     * @param add_patient_pesel pole z peselem
     * @param add_patient_label etykieta z informacja
     * @param add_patient_department pole z odzialem
     * @param add_patient_disease pole z schorzeniem
     * @param add_patient_phone pole z telefonem
     */
    public static void addPatient(TextField add_patient_pesel,TextField add_patient_fname,TextField add_patient_lname,ComboBox<String> add_patient_department,TextField add_patient_disease,TextField add_patient_phone,Label add_patient_label)
    {
        int oddzial=0;
        try {
            CallableStatement cst = c.prepareCall( "{call projekt.find_department(?)}" );
            cst.setString(1,add_patient_department.getValue());
            ResultSet rs ;
            rs = cst.executeQuery();
            while (rs.next()) {
                oddzial = rs.getInt(1);
            }
            rs.close();
            cst.close();
        }
        catch(SQLException e)  {
            add_patient_label.setVisible(true);
            add_patient_label.setText("Nie prawidłowy oddzial");
        }
        try {
            if(oddzial==0)
            {
                add_patient_label.setVisible(true);
                add_patient_label.setText("Nie prawidłowy oddzial");
                return;
            }

            String query = "INSERT INTO projekt.pacjent values(?,?,?,?,?,?,?);";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setString(1, add_patient_pesel.getText());
            pst.setString(2, add_patient_fname.getText());
            pst.setString(3, add_patient_lname.getText());
            pst.setInt(4, oddzial);
            pst.setString(5, add_patient_disease.getText());
            pst.setString(6, add_patient_phone.getText());
            pst.setInt(7, 100);
            pst.executeUpdate();
            add_patient_label.setVisible(true);
            add_patient_label.setText("Dodano");
        } catch (SQLException e) {
            e.printStackTrace();
            add_patient_label.setVisible(true);
            add_patient_label.setText(e.toString().split("/")[1]);
            return;
        }
        catch (Exception e)
        {
            add_patient_label.setVisible(true);
            add_patient_label.setText("Nie poprawen dane");
            return;
        }
    }
    /**
     * Funkcja dodajaca operacje do bazy danych
     * @param add_operation_date pole z datą
     * @param add_operation_label etykieta z informacja
     * @param add_operation_pesel_n pole z peselem personelu
     * @param add_operation_name pole z imienem
     * @param add_operation_pesel_p pole z peselm pacjenta
     * @param add_operation_room pole z numerem pokoju
     */
    public static void addOperation(TextField add_operation_name,TextField add_operation_pesel_p,TextField add_operation_room,DatePicker add_operation_date,TextField add_operation_pesel_n,Label add_operation_label)
    {
        if((add_operation_pesel_p.getText().equals(add_operation_pesel_n.getText()) && !add_operation_pesel_n.equals("")) || add_operation_pesel_p.getText().equals(user))
        {
            add_operation_label.setVisible(true);
            add_operation_label.setText("Personel lub lekarz nie mogą być operowanymi pacjentami");
            return;
        }
        boolean ok=true;
        try {
            CallableStatement cst = c.prepareCall( "{call projekt.ifPeselPersonel(?)}" );
            cst.setString(1,add_operation_pesel_n.getText());
            ResultSet rs ;
            rs = cst.executeQuery();
            while (rs.next()) {
                ok = rs.getBoolean(1);
            }
            rs.close();
            cst.close();
        }
        catch(SQLException e)  {
            System.out.println("Blad podczas przetwarzania danych:"+e) ;
        }
        if(ok==false)
        {
            add_operation_label.setVisible(true);
            add_operation_label.setText("Zły pesel personelu medycznego");
            return;
        }
        try {
            String query = "INSERT INTO projekt.operacja(data,nazwa,pesel_p,numer,pesel_d,pesel_n) values(?,?,?,?,?,?);";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setDate(1, Date.valueOf(add_operation_date.getValue()));
            pst.setString(2, add_operation_name.getText());
            pst.setString(3, add_operation_pesel_p.getText());
            pst.setInt(4, Integer.parseInt(add_operation_room.getText()));
            pst.setString(5, user);
            pst.setString(6, add_operation_pesel_n.getText());
            pst.executeUpdate();
            add_operation_label.setVisible(true);
            add_operation_label.setText("Dodano");

        } catch (SQLException e) {
            if(e.toString().split("/")==null)
            {
                add_operation_label.setVisible(true);
                add_operation_label.setText("Nie poprawen dane");
            }
            add_operation_label.setVisible(true);
            add_operation_label.setText(e.toString().split("/")[1]);
            return;
        }
        catch (Exception e)
        {
            add_operation_label.setVisible(true);
            add_operation_label.setText("Nie poprawen dane");
            return;
        }
    }
    /**
     * Funkcja dodajaca recepte do bazy danych
     *
     * @param add_prescription_description pole z opisem
     * @param add_prescription_drug pole z nazwa lekarstwa
     * @param add_prescription_label etykieta z informacja
     * @param add_prescription_pesel_p pole z peselem pacjenta
     */
    public static void addPrescription(TextField add_prescription_pesel_p, TextField add_prescription_description,TextField add_prescription_drug,Label add_prescription_label)
    {
        try {
            String query = "INSERT INTO projekt.recepta(pesel_d,opis,lekarstwo,pesel_p) values(?,?,?,?);";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setString(1, user);
            pst.setString(2, add_prescription_description.getText());
            pst.setString(3, add_prescription_drug.getText());
            pst.setString(4, add_prescription_pesel_p.getText());
            pst.executeUpdate();
            add_prescription_label.setVisible(true);
            add_prescription_label.setText("Dodano");
        } catch (Exception e) {
            add_prescription_label.setVisible(true);
            add_prescription_label.setText("Niepoprawne dane");
            return;
        }
    }
    /**
     * Funkcja dodajaca obecnosc do bazy danych
     * @param add_presence_date pole z data
     * @param add_presence_hour poole z iloscia godzin
     * @param add_presence_label etykeita z informacja
     * @param add_presence_status polse z statusem
     */
    public static void addPresence(TextField add_presence_status,TextField add_presence_hour,DatePicker add_presence_date,Label add_presence_label)
    {
        try {
            String query = "INSERT INTO projekt.obecnosc(godziny,data,pesel,status) values(?,?,?,?);";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setString(1, add_presence_hour.getText());
            pst.setDate(2, Date.valueOf(add_presence_date.getValue()));
            pst.setString(3, user);
            pst.setString(4,add_presence_status.getText());
            pst.executeUpdate();
            add_presence_label.setVisible(true);
            add_presence_label.setText("Dodano");
        } catch (SQLException e) {
            add_presence_label.setText(e.toString().split("/")[1]);
            return;
        }
        catch (Exception e)
        {
            add_presence_label.setVisible(true);
            add_presence_label.setText("Nie poprawen dane");
            return;
        }
    }
    /**
     * Funkcja dodajaca wizyte do bazy danych
     *
     * @param add_visit_date pole z data
     * @param add_visit_description pole z opiesm
     * @param add_visit_label etykieta z informacja
     * @param add_visit_money pole z kosztem
     * @param add_visit_pesel_p pole z peselem pacjenta
     */
    public static void addVisit(TextField add_visit_pesel_p,DatePicker add_visit_date,TextField add_visit_description,TextField add_visit_money,Label add_visit_label)
    {
        try {
            String query = "INSERT INTO projekt.wizyta(opis,pesel_d,koszt,pesel_p,data) values(?,?,?,?,?);";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setString(1, add_visit_description.getText());
            pst.setString(2, user);
            pst.setDouble(3, Double.parseDouble(add_visit_money.getText()));
            pst.setString(4, add_visit_pesel_p.getText());
            pst.setDate(5,Date.valueOf(add_visit_date.getValue()));
            pst.executeUpdate();
            add_visit_label.setVisible(true);
            add_visit_label.setText("Dodano");
        } catch (SQLException e) {
            add_visit_label.setVisible(true);
            add_visit_label.setText(e.toString().split("/")[1]);
            return;
        }
        catch(Exception e)
        {
            add_visit_label.setVisible(true);
            add_visit_label.setText("Nie poprawne dane");
        }
    }
    /**
     * Funkcja dodajaca oddzial do bazy danych
     * @param add_department_label etykieta z informacja
     * @param add_department_name pole z nazwa
     * @param add_department_personel pole z iloscia etatow
     */
    public static void addDepartment(TextField add_department_name,TextField add_department_personel,Label add_department_label)
    {
        try {
            String query = "INSERT INTO projekt.oddzial(miejsca,nazwa,pesel_d) values(?,?,?);";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setInt(1, Integer.parseInt(add_department_personel.getText()));
            pst.setString(2, add_department_name.getText());
            pst.setString(3, user);
            pst.executeUpdate();
            add_department_label.setVisible(true);
            add_department_label.setText("Dodano");
        } catch (SQLException e) {
            return;
        }
        catch (Exception e)
        {
            add_department_label.setVisible(true);
            add_department_label.setText("Nie poprawen dane");
            return;
        }
    }
    /**
     * Funkcja dodajaca pokój do bazy danych
     *
     * @param add_room_department pole z oddizalem
     * @param add_room_kind pole z rodzajem
     * @param add_room_label etykeita z infomracja
     * @param add_room_number pole z numerem sali
     * @param add_room_places pole z iloscia miejsc
     * @param add_room_status pole z statusem
     */
    public static void addRoom(TextField add_room_number,ComboBox<String> add_room_department,TextField add_room_kind,TextField add_room_places,TextField add_room_status,Label add_room_label)
    {
        int oddzial=0;
        try {
            CallableStatement cst = c.prepareCall( "{call projekt.find_department(?)}" );
            cst.setString(1,add_room_department.getValue());
            ResultSet rs ;
            rs = cst.executeQuery();
            while (rs.next()) {
                oddzial = rs.getInt(1);
            }
            rs.close();
            cst.close();
        }
        catch(SQLException e)  {
            System.out.println("Blad podczas przetwarzania danych:"+e) ;
        }
        try {
            String query = "INSERT INTO projekt.sala values(?,?,?,?,?,?);";
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.setInt(1, Integer.parseInt(add_room_number.getText()));
            pst.setInt(2, oddzial);
            pst.setString(3, add_room_kind.getText());
            pst.setString(4, user);
            pst.setInt(5, Integer.parseInt(add_room_places.getText()));
            pst.setString(6, add_room_status.getText());
            pst.executeUpdate();
            add_room_label.setVisible(true);
            add_room_label.setText("Dodano");
        }
        catch (Exception e)
        {
            add_room_label.setVisible(true);
            add_room_label.setText("Nie poprawen dane");
            return;
        }
    }
    /**
     * Funkcja usuwajaca dany rekord z bazy
     * @param SQL komenda do wykonania
     */
    public static void delete(String SQL)
    {
        try {
            String query = SQL;
            PreparedStatement pst = PSQL.get_conn().prepareStatement(query);
            pst.executeUpdate();
        } catch (SQLException e) {
            return;
        }
    }


}