package edu.sumdu.monopoly.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import edu.sumdu.monopoly.RespondDialog;
import edu.sumdu.monopoly.TradeDeal;

public class GUIRespondDialog extends Stage implements RespondDialog {
    private boolean response;
    private TextArea txtMessage = new TextArea();

    public GUIRespondDialog() {
        initModality(Modality.APPLICATION_MODAL);
        setTitle("Trade Response");

        Button btnYes = new Button("Yes");
        Button btnNo = new Button("No");
        txtMessage.setPrefSize(300, 200);
        txtMessage.setEditable(false);
        txtMessage.setWrapText(true);

        BorderPane root = new BorderPane();
        root.setCenter(txtMessage);

        HBox pnlButtons = new HBox(10, btnYes, btnNo);
        pnlButtons.setPadding(new Insets(10));
        root.setBottom(pnlButtons);

        btnYes.setOnAction(e -> {
            response = true;
            close();
        });

        btnNo.setOnAction(e -> {
            response = false;
            close();
        });

        Scene scene = new Scene(root);
        setScene(scene);
    }

    public boolean getResponse() {
        return response;
    }

    public void setDeal(TradeDeal deal) {
        txtMessage.setText(deal.makeMessage());
    }
}