package edu.sumdu.monopoly.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import edu.sumdu.monopoly.Player;

public class BuyHouseDialog extends Stage {
    private ComboBox<String> cboMonopoly;
    private ComboBox<Integer> cboNumber;
    private Player player;

    public BuyHouseDialog(Player player) {
        this.player = player;
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Buy House");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Select monopoly"), 0, 0);
        grid.add(buildMonopolyComboBox(), 1, 0);
        grid.add(new Label("Number of houses"), 0, 1);
        grid.add(buildNumberComboBox(), 1, 1);
        grid.add(buildOKButton(), 0, 2);
        grid.add(buildCancelButton(), 1, 2);

        Scene scene = new Scene(grid);
        setScene(scene);
    }

    private Button buildCancelButton() {
        Button btn = new Button("Cancel");
        btn.setOnAction(e -> cancelClicked());
        return btn;
    }

    private ComboBox<String> buildMonopolyComboBox() {
        cboMonopoly = new ComboBox<>();
        String[] monopolies = player.getMonopolies();
        cboMonopoly.getItems().addAll(monopolies);
        if (monopolies.length > 0) {
            cboMonopoly.getSelectionModel().selectFirst();
        }
        return cboMonopoly;
    }

    private ComboBox<Integer> buildNumberComboBox() {
        cboNumber = new ComboBox<>();
        cboNumber.getItems().addAll(1, 2, 3, 4, 5);
        cboNumber.getSelectionModel().selectFirst();
        return cboNumber;
    }

    private Button buildOKButton() {
        Button btn = new Button("OK");
        btn.setOnAction(e -> okClicked());
        return btn;
    }

    private void cancelClicked() {
        close();
    }

    private void okClicked() {
        String monopoly = cboMonopoly.getValue();
        int number = cboNumber.getSelectionModel().getSelectedIndex() + 1;
        player.purchaseHouse(monopoly, number);
        close();
    }
}