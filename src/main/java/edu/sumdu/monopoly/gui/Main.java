package edu.sumdu.monopoly.gui;

import javafx.application.Application;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import edu.sumdu.monopoly.*;

import java.util.Optional;

public class Main extends Application {

    private static int inputNumberOfPlayers(MainWindow window) {
        int numPlayers = 0;
        while (numPlayers <= 0 || numPlayers > GameMaster.MAX_PLAYER) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Number of Players");
            dialog.setHeaderText(null);
            dialog.setContentText("How many players?");

            Optional<String> result = dialog.showAndWait();
            if (!result.isPresent()) {
                System.exit(0);
            }

            try {
                numPlayers = Integer.parseInt(result.get());
            } catch (NumberFormatException e) {
                showAlert("Please input a number");
                continue;
            }

            if (numPlayers <= 0 || numPlayers > GameMaster.MAX_PLAYER) {
                showAlert("Please input a number between one and eight");
            } else {
                GameMaster.instance().setNumberOfPlayers(numPlayers);
            }
        }
        return numPlayers;
    }

    private static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void start(Stage primaryStage) {
        GameMaster master = GameMaster.instance();
        MainWindow window = new MainWindow();

        Parameters params = getParameters();
        java.util.List<String> args = params.getRaw();

        GameBoard gameBoard = null;

        if (args.size() > 0) {
            if (args.get(0).equals("test")) {
                master.setTestMode(true);
            }
            if (args.size() > 1) {
                try {
                    Class<?> c = Class.forName(args.get(1));
                    gameBoard = (GameBoard) c.newInstance();
                } catch (ClassNotFoundException e) {
                    showAlert("Class Not Found. Program will exit");
                    System.exit(0);
                    return;
                } catch (IllegalAccessException e) {
                    showAlert("Illegal Access of Class. Program will exit");
                    System.exit(0);
                    return;
                } catch (InstantiationException e) {
                    showAlert("Class Cannot be Instantiated. Program will exit");
                    System.exit(0);
                    return;
                }
            }
        }

        if (gameBoard == null) {
            gameBoard = new GameBoardFull();
        }

        final GameBoard finalGameBoard = gameBoard;

        master.setGameBoard(finalGameBoard);
        int numPlayers = inputNumberOfPlayers(window);

        for (int i = 0; i < numPlayers; i++) {
            final int playerIndex = i;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Player Name");
            dialog.setHeaderText(null);
            dialog.setContentText("Please input name for Player " + (playerIndex + 1));

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(name ->
                    GameMaster.instance().getPlayer(playerIndex).setName(name));
        }

        window.setupGameBoard(finalGameBoard);
        window.show();
        master.setGUI(window);
        master.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}