package edu.sumdu.monopoly.gui;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import edu.sumdu.monopoly.*;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends Stage implements MonopolyGUI {
    private HBox eastPanel = new HBox();
    private ArrayList<GUICell> guiCells = new ArrayList<>();
    private HBox northPanel = new HBox();
    private PlayerPanel[] playerPanels;
    private HBox southPanel = new HBox();
    private VBox westPanel = new VBox();

    public MainWindow() {
        setBorder(northPanel);
        setBorder(southPanel);
        setBorder(westPanel);
        setBorder(eastPanel);

        BorderPane root = new BorderPane();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        root.setTop(northPanel);
        root.setBottom(southPanel);
        root.setLeft(westPanel);
        root.setRight(eastPanel);

        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        setScene(scene);
        setTitle("Monopoly Game");

        setOnCloseRequest(e -> System.exit(0));
    }

    private void setBorder(Pane panel) {
        panel.setBorder(new Border(new BorderStroke(
                Color.BLACK, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    private void addCells(Pane panel, List<Cell> cells) {
        for (Cell cell : cells) {
            GUICell guiCell = new GUICell(cell);
            panel.getChildren().add(guiCell);
            guiCells.add(guiCell);
        }
    }

    private void buildPlayerPanels() {
        GameMaster master = GameMaster.instance();
        GridPane infoPanel = new GridPane();
        int players = master.getNumberOfPlayers();
        infoPanel.setHgap(10);
        infoPanel.setVgap(10);

        ((BorderPane) getScene().getRoot()).setCenter(infoPanel);
        playerPanels = new PlayerPanel[master.getNumberOfPlayers()];

        for (int i = 0; i < master.getNumberOfPlayers(); i++) {
            playerPanels[i] = new PlayerPanel(master.getPlayer(i));
            infoPanel.add(playerPanels[i], i % 2, i / 2);
            playerPanels[i].displayInfo();
        }
    }

    @Override
    public void enableEndTurnBtn(int playerIndex) {
        playerPanels[playerIndex].setEndTurnEnabled(true);
    }

    @Override
    public void enablePlayerTurn(int playerIndex) {
        playerPanels[playerIndex].setRollDiceEnabled(true);
    }

    @Override
    public void enablePurchaseBtn(int playerIndex) {
        playerPanels[playerIndex].setPurchasePropertyEnabled(true);
    }

    public int[] getDiceRoll() {
        TestDiceRollDialog dialog = new TestDiceRollDialog();
        dialog.showAndWait();
        return dialog.getDiceRoll();
    }

    public boolean isDrawCardButtonEnabled() {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        return playerPanels[currentPlayerIndex].isDrawCardButtonEnabled();
    }

    public boolean isEndTurnButtonEnabled() {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        return playerPanels[currentPlayerIndex].isEndTurnButtonEnabled();
    }

    public boolean isGetOutOfJailButtonEnabled() {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        return playerPanels[currentPlayerIndex].isGetOutOfJailButtonEnabled();
    }

    public boolean isTradeButtonEnabled(int i) {
        return playerPanels[i].isTradeButtonEnabled();
    }

    @Override
    public void movePlayer(int index, int from, int to) {
        GUICell fromCell = queryCell(from);
        GUICell toCell = queryCell(to);
        fromCell.removePlayer(index);
        toCell.addPlayer(index);
    }

    public RespondDialog openRespondDialog(TradeDeal deal) {
        GUIRespondDialog dialog = new GUIRespondDialog();
        dialog.setDeal(deal);
        dialog.showAndWait();
        return dialog;
    }

    public TradeDialog openTradeDialog() {
        GUITradeDialog dialog = new GUITradeDialog();
        dialog.showAndWait();
        return dialog;
    }

    private GUICell queryCell(int index) {
        Cell cell = GameMaster.instance().getGameBoard().getCell(index);
        for (GUICell guiCell : guiCells) {
            if (guiCell.getCell() == cell) return guiCell;
        }
        return null;
    }

    public void setBuyHouseEnabled(boolean b) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setBuyHouseEnabled(b);
    }

    public void setDrawCardEnabled(boolean b) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setDrawCardEnabled(b);
    }

    public void setEndTurnEnabled(boolean enabled) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setEndTurnEnabled(enabled);
    }

    public void setGetOutOfJailEnabled(boolean b) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setGetOutOfJailEnabled(b);
    }

    public void setPurchasePropertyEnabled(boolean enabled) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setPurchasePropertyEnabled(enabled);
    }

    public void setRollDiceEnabled(boolean b) {
        int currentPlayerIndex = GameMaster.instance().getCurrentPlayerIndex();
        playerPanels[currentPlayerIndex].setRollDiceEnabled(b);
    }

    public void setTradeEnabled(int index, boolean b) {
        playerPanels[index].setTradeEnabled(b);
    }

    public void setupGameBoard(GameBoard board) {
        java.awt.Dimension dimension = GameBoardUtil.calculateDimension(board.getCellNumber());

        addCells(northPanel, GameBoardUtil.getNorthCells(board));
        addCells(southPanel, GameBoardUtil.getSouthCells(board));
        addCells(eastPanel, GameBoardUtil.getEastCells(board));
        addCells(westPanel, GameBoardUtil.getWestCells(board));
        buildPlayerPanels();
    }

    public void showBuyHouseDialog(Player currentPlayer) {
        BuyHouseDialog dialog = new BuyHouseDialog(currentPlayer);
        dialog.showAndWait();
    }

    public void showMessage(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public int showUtilDiceRoll() {
        return UtilDiceRoll.showDialog();
    }

    @Override
    public void startGame() {
        int numberOfPlayers = GameMaster.instance().getNumberOfPlayers();
        for (int i = 0; i < numberOfPlayers; i++) {
            movePlayer(i, 0, 0);
        }
    }

    @Override
    public void update() {
        for (PlayerPanel panel : playerPanels) {
            panel.displayInfo();
        }
        for (GUICell cell : guiCells) {
            cell.displayInfo();
        }
    }

    public void updateHouseButton(int i) {
        // Implementation needed
    }
}