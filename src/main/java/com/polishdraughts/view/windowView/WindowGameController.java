package com.polishdraughts.view.windowView;
import com.polishdraughts.PolishDraughtsWindowed;
import com.polishdraughts.controller.GameController;
import com.polishdraughts.model.Color;
import com.polishdraughts.model.Move;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WindowGameController {
    @FXML
    private Button tieButton;
    @FXML
    private Label infoPanel;

    @FXML
    public void endGameAsTie(ActionEvent actionEvent) {
        tieButton.setDisable(true);
        tieButton.setVisible(false);
        GameController.getInstance().takeGameInput("tie", new Move(Color.WHITE));
    }

    public void setInfoPanelText(String text){
        infoPanel.setText(text);
    }
}
