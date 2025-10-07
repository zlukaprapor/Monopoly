package edu.sumdu.monopoly.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TestDiceRollDialog extends Stage {
    private Button btnOK, btnCancel;
    private TextField txtDiceRoll;
    private int[] diceRoll;

    public TestDiceRollDialog() {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Dice Roll Dialog");

        txtDiceRoll = new TextField();
        txtDiceRoll.setPrefColumnCount(5);
        btnOK = new Button("OK");
        btnCancel = new Button("Cancel");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Amount"), 0, 0);
        grid.add(txtDiceRoll, 1, 0);
        grid.add(btnOK, 0, 1);
        grid.add(btnCancel, 1, 1);

        btnCancel.setOnAction(e -> {
            diceRoll = new int[2];
            diceRoll[0] = 0;
            diceRoll[1] = 0;
            close();
        });

        btnOK.setOnAction(e -> {
            int amount = 0;
            try {
                amount = Integer.parseInt(txtDiceRoll.getText());
            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Amount should be an integer");
                alert.showAndWait();
                return;
            }

            if (amount > 0) {
                diceRoll = new int[2];
                if ((amount % 2) == 0) {
                    diceRoll[0] = amount / 2;
                    diceRoll[1] = amount / 2;
                } else {
                    diceRoll[0] = amount / 2;
                    diceRoll[1] = (amount / 2) + 1;
                }
            }
            close();
        });

        Scene scene = new Scene(grid);
        setScene(scene);
    }

    public int[] getDiceRoll() {
        return diceRoll;
    }
}
