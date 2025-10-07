package edu.sumdu.monopoly.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import edu.sumdu.monopoly.GameMaster;

public class UtilDiceRoll extends Stage {

    public static int showDialog() {
        UtilDiceRoll dialog = new UtilDiceRoll();
        dialog.showAndWait();
        return dialog.diceValue;
    }

    private Button btnDice = new Button("Roll the Dice!");
    private Button btnOK = new Button("OK");
    private int diceValue;
    private Label lblPrompt = new Label();

    public UtilDiceRoll() {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Utility Dice Roll");

        btnOK.setDisable(true);
        lblPrompt.setText("Please roll the dice to determine your utility bill.");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        HBox pnlButtons = new HBox(10, btnDice, btnOK);
        pnlButtons.setPadding(new Insets(10));

        root.setCenter(lblPrompt);
        root.setBottom(pnlButtons);

        btnDice.setOnAction(e -> rollDice());
        btnOK.setOnAction(e -> close());

        Scene scene = new Scene(root, 300, 150);
        setScene(scene);
    }

    public void rollDice() {
        int[] diceRoll = GameMaster.instance().rollDice();
        this.diceValue = diceRoll[0] + diceRoll[1];
        lblPrompt.setText("You rolled " + diceValue);
        btnDice.setDisable(true);
        btnOK.setDisable(false);
    }
}
