package com.polishdraughts.view.windowView;

import com.polishdraughts.PolishDraughtsWindowed;
import com.polishdraughts.controller.GameRules.GameResults;
import com.polishdraughts.model.GameState;
import com.polishdraughts.model.Move;
import com.polishdraughts.view.Renderer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class WindowRenderer implements Renderer {
    Scene menuScene;
    Scene gameScene;
    WindowMenuController windowMenuController = new WindowMenuController();
    WindowGameController windowGameController;

    public void renderMainMenu(ArrayList<String> menuOptions, boolean invalidInput){
        Stage window = PolishDraughtsWindowed.getWindow();

        if (menuScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(PolishDraughtsWindowed.class.getResource("menu.fxml"));
            try {
                this.menuScene = new Scene(fxmlLoader.load());
            } catch (IOException e){
                System.err.println("Could not load resource!");
            }

            VBox menuContainer = (VBox) menuScene.lookup("#menuContainer");
            menuContainer.setSpacing(50);

            int i = 1;
            for (String menuOption : menuOptions) {
                Button button = new Button(menuOption.substring(3));
                button.setId(String.valueOf(i));
                button.setPrefWidth(400);
                button.setPrefHeight(50);
                menuContainer.getChildren().add(button);
                button.setOnAction(e -> windowMenuController.onMenuBtnClick(button.getId()));
                i++;
            }
        }
        window.setScene(menuScene);
    }

    public void renderGameState(GameState gameState, boolean validInput){
        Stage window = PolishDraughtsWindowed.getWindow();
        if (gameScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(PolishDraughtsWindowed.class.getResource("game.fxml"));
            try {
                gameScene = new Scene(fxmlLoader.load());
                windowGameController = fxmlLoader.getController();
                window.setScene(gameScene);
            } catch (IOException e) {
                System.err.println("Could not load resource!");
            }
        }
    }

    public void askForMoveInput(Move previousMove){

    }

    public void renderFinalScore(GameResults gameResult){
        switch (gameResult){
            case TIE -> windowGameController.setInfoPanelText("It's a TIE!");
            case WHITE_WINS -> windowGameController.setInfoPanelText("WHITE Player wins the game!");
            case BLACK_WINS -> windowGameController.setInfoPanelText("BLACK Player wins the game!");
        }
    }

    public void pressAnyKeyPromptForBackToMenu(){

    }
}
