package edu.sumdu.monopoly.gui;

import javafx.scene.layout.HBox;
import edu.sumdu.monopoly.GameMaster;

public class InfoPanel extends HBox {
    public void displayInfo() {
        GameMaster master = GameMaster.instance();
        setSpacing(10);

        for (int i = 0; i < master.getNumberOfPlayers(); i++) {
            PlayerPanel panel = new PlayerPanel(master.getPlayer(i));
            getChildren().add(panel);
            panel.displayInfo();
        }
    }
}