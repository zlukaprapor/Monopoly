package edu.sumdu.monopoly.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import edu.sumdu.monopoly.*;

public class PlayerPanel extends BorderPane {
    private Button btnBuyHouse;
    private Button btnDrawCard;
    private Button btnEndTurn;
    private Button btnGetOutOfJail;
    private Button btnPurchaseProperty;
    private Button btnRollDice;
    private Button btnTrade;

    private Label lblMoney;
    private Label lblName;

    private Player player;

    private TextArea txtProperty;

    public PlayerPanel(Player player) {
        this.player = player;

        GridPane pnlAction = new GridPane();
        VBox pnlInfo = new VBox(10);

        btnRollDice = new Button("Roll Dice");
        btnPurchaseProperty = new Button("Purchase Property");
        btnEndTurn = new Button("End Turn");
        btnBuyHouse = new Button("Buy House");
        btnGetOutOfJail = new Button("Get Out of Jail");
        btnDrawCard = new Button("Draw Card");
        btnTrade = new Button("Trade");

        lblName = new Label();
        lblMoney = new Label();
        txtProperty = new TextArea();
        txtProperty.setDisable(true);
        txtProperty.setPrefRowCount(10);
        txtProperty.setPrefColumnCount(30);

        HBox pnlName = new HBox(10, lblName, lblMoney);
        pnlName.setAlignment(Pos.CENTER);

        pnlInfo.getChildren().addAll(pnlName, txtProperty);
        pnlInfo.setPadding(new Insets(10));

        pnlAction.setPadding(new Insets(10));
        pnlAction.setHgap(10);
        pnlAction.setVgap(10);
        pnlAction.add(btnBuyHouse, 0, 0);
        pnlAction.add(btnRollDice, 1, 0);
        pnlAction.add(btnPurchaseProperty, 2, 0);
        pnlAction.add(btnGetOutOfJail, 0, 1);
        pnlAction.add(btnEndTurn, 1, 1);
        pnlAction.add(btnDrawCard, 2, 1);
        pnlAction.add(btnTrade, 0, 2);

        setCenter(pnlInfo);
        setBottom(pnlAction);

        btnRollDice.setDisable(true);
        btnPurchaseProperty.setDisable(true);
        btnEndTurn.setDisable(true);
        btnBuyHouse.setDisable(true);
        btnGetOutOfJail.setDisable(true);
        btnDrawCard.setDisable(true);
        btnTrade.setDisable(true);

        setBorder(new Border(new BorderStroke(
                Color.GRAY, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, new BorderWidths(2))));

        btnRollDice.setOnAction(e ->
                GameMaster.instance().btnRollDiceClicked());

        btnEndTurn.setOnAction(e ->
                GameMaster.instance().btnEndTurnClicked());

        btnPurchaseProperty.setOnAction(e ->
                GameMaster.instance().btnPurchasePropertyClicked());

        btnBuyHouse.setOnAction(e ->
                GameMaster.instance().btnBuyHouseClicked());

        btnGetOutOfJail.setOnAction(e ->
                GameMaster.instance().btnGetOutOfJailClicked());

        btnDrawCard.setOnAction(e -> {
            Card card = GameMaster.instance().btnDrawCardClicked();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Card");
            alert.setHeaderText(null);
            alert.setContentText(card.getLabel());
            alert.showAndWait();
            displayInfo();
        });

        btnTrade.setOnAction(e ->
                GameMaster.instance().btnTradeClicked());
    }

    public void displayInfo() {
        lblName.setText(player.getName());
        lblMoney.setText("$ " + player.getMoney());
        StringBuilder buf = new StringBuilder();
        edu.sumdu.monopoly.Cell[] cells = player.getAllProperties();
        for (edu.sumdu.monopoly.Cell cell : cells) {
            buf.append(cell).append("\n");
        }
        txtProperty.setText(buf.toString());
    }

    public boolean isBuyHouseButtonEnabled() {
        return !btnBuyHouse.isDisabled();
    }

    public boolean isDrawCardButtonEnabled() {
        return !btnDrawCard.isDisabled();
    }

    public boolean isEndTurnButtonEnabled() {
        return !btnEndTurn.isDisabled();
    }

    public boolean isGetOutOfJailButtonEnabled() {
        return !btnGetOutOfJail.isDisabled();
    }

    public boolean isPurchasePropertyButtonEnabled() {
        return !btnPurchaseProperty.isDisabled();
    }

    public boolean isRollDiceButtonEnabled() {
        return !btnRollDice.isDisabled();
    }

    public boolean isTradeButtonEnabled() {
        return !btnTrade.isDisabled();
    }

    public void setBuyHouseEnabled(boolean b) {
        btnBuyHouse.setDisable(!b);
    }

    public void setDrawCardEnabled(boolean b) {
        btnDrawCard.setDisable(!b);
    }

    public void setEndTurnEnabled(boolean enabled) {
        btnEndTurn.setDisable(!enabled);
    }

    public void setGetOutOfJailEnabled(boolean b) {
        btnGetOutOfJail.setDisable(!b);
    }

    public void setPurchasePropertyEnabled(boolean enabled) {
        btnPurchaseProperty.setDisable(!enabled);
    }

    public void setRollDiceEnabled(boolean enabled) {
        btnRollDice.setDisable(!enabled);
    }

    public void setTradeEnabled(boolean b) {
        btnTrade.setDisable(!b);
    }
}