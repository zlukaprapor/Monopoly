package edu.sumdu.monopoly.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import edu.sumdu.monopoly.*;

import java.util.Iterator;
import java.util.List;

public class GUITradeDialog extends Stage implements TradeDialog {
    private Button btnOK, btnCancel;
    private ComboBox<Player> cboSellers;
    private ComboBox<edu.sumdu.monopoly.Cell> cboProperties;
    private TradeDeal deal;
    private TextField txtAmount;

    public GUITradeDialog() {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Trade Property");

        cboSellers = new ComboBox<>();
        cboProperties = new ComboBox<>();
        txtAmount = new TextField();
        btnOK = new Button("OK");
        btnCancel = new Button("Cancel");

        btnOK.setDisable(true);

        buildSellersCombo();

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Sellers"), 0, 0);
        grid.add(cboSellers, 1, 0);
        grid.add(new Label("Properties"), 0, 1);
        grid.add(cboProperties, 1, 1);
        grid.add(new Label("Amount"), 0, 2);
        grid.add(txtAmount, 1, 2);
        grid.add(btnOK, 0, 3);
        grid.add(btnCancel, 1, 3);

        btnCancel.setOnAction(e -> close());

        cboSellers.setOnAction(e -> {
            Player player = cboSellers.getValue();
            if (player != null) {
                updatePropertiesCombo(player);
            }
        });

        btnOK.setOnAction(e -> {
            int amount = 0;
            try {
                amount = Integer.parseInt(txtAmount.getText());
            } catch (NumberFormatException nfe) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Amount should be an integer");
                alert.showAndWait();
                return;
            }

            edu.sumdu.monopoly.Cell cell = cboProperties.getValue();
            if (cell == null) return;

            Player player = cboSellers.getValue();
            Player currentPlayer = GameMaster.instance().getCurrentPlayer();

            if (currentPlayer.getMoney() > amount) {
                deal = new TradeDeal();
                deal.setAmount(amount);
                deal.setPropertyName(cell.getName());
                deal.setSellerIndex(GameMaster.instance().getPlayerIndex(player));
            }
            close();
        });

        Scene scene = new Scene(grid);
        setScene(scene);
    }

    private void buildSellersCombo() {
        List sellers = GameMaster.instance().getSellerList();
        for (Iterator iter = sellers.iterator(); iter.hasNext();) {
            Player player = (Player) iter.next();
            cboSellers.getItems().add(player);
        }
        if (sellers.size() > 0) {
            cboSellers.getSelectionModel().selectFirst();
            updatePropertiesCombo((Player) sellers.get(0));
        }
    }

    public TradeDeal getTradeDeal() {
        return deal;
    }

    private void updatePropertiesCombo(Player player) {
        cboProperties.getItems().clear();
        edu.sumdu.monopoly.Cell[] cells = player.getAllProperties();
        btnOK.setDisable(cells.length == 0);
        for (edu.sumdu.monopoly.Cell cell : cells) {
            cboProperties.getItems().add(cell);
        }
        if (cells.length > 0) {
            cboProperties.getSelectionModel().selectFirst();
        }
    }
}