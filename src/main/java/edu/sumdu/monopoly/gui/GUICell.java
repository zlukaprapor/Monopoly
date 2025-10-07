package edu.sumdu.monopoly.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import edu.sumdu.monopoly.*;

public class GUICell extends StackPane {
    private Cell cell;
    private Label lblInfo;
    private Label[] lblPlayers = new Label[GameMaster.MAX_PLAYER];

    public GUICell(Cell cell) {
        this.cell = cell;

        setBorder(new Border(new BorderStroke(
                Color.GRAY, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        GridPane pnlPlayer = new GridPane();
        pnlPlayer.setAlignment(Pos.CENTER);
        createPlayerLabels(pnlPlayer);

        setPrefSize(100, 100);
        addCellInfo();

        getChildren().addAll(lblInfo, pnlPlayer);
    }

    private void addCellInfo() {
        lblInfo = new Label();
        lblInfo.setAlignment(Pos.CENTER);
        displayInfo();
    }

    public void addPlayer(int index) {
        Player player = GameMaster.instance().getPlayer(index);
        lblPlayers[index].setText(player.getName().substring(0, 1));
        lblPlayers[index].setStyle("-fx-background-color: green; -fx-padding: 2;");
    }

    private void createPlayerLabels(GridPane pnlPlayer) {
        for (int i = 0; i < GameMaster.MAX_PLAYER; i++) {
            lblPlayers[i] = new Label();
            lblPlayers[i].setPrefSize(20, 20);
            pnlPlayer.add(lblPlayers[i], i % 4, i / 4);
        }
    }

    public void displayInfo() {
        lblInfo.setText(InfoFormatter.cellInfo(cell));
    }

    public Cell getCell() {
        return cell;
    }

    public void removePlayer(int index) {
        lblPlayers[index].setText("");
        lblPlayers[index].setStyle("");
    }
}