package com.example.projekt;

import java.util.function.Function;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * Klasa ActionButtonTableCell
 *
 * klasa potrzebna to uzyskaniu przycisków w tabeli
 *
 * @author Tomasz Szkaradek
 * @version 0.1
 *
 */
public class ActionButtonTableCell<S> extends TableCell<S, Button> {
    /**
    * Przycisk służący do usuwania rekordu
    */
    private final Button actionButton;

    /**
     * metoda wypełniajaca tabele
     */
    public ActionButtonTableCell(String label, Function< S, S> function) {
        this.getStyleClass().add("action-button-table-cell");

        this.actionButton = new Button(label);
        this.actionButton.setOnAction((ActionEvent e) -> {
            function.apply(getCurrentItem());
        });
        this.actionButton.setMaxWidth(Double.MAX_VALUE);
    }
    /**
     * metoda popierajaca dany rekord
     */
    public S getCurrentItem() {
        return (S) getTableView().getItems().get(getIndex());
    }

    /**
     * metoda ustawiajaca przyciski w tabeli
     */
    public static <S> Callback<TableColumn<S, Button>, TableCell<S, Button>> forTableColumn(String label, Function< S, S> function) {
        return param -> new ActionButtonTableCell<>(label, function);
    }
    /**
     * metoda ustawiajaca przycisk
     */
    @Override
    public void updateItem(Button item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(actionButton);
        }
    }
}