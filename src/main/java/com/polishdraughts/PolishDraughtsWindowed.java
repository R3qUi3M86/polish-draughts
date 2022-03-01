package com.polishdraughts;

import com.polishdraughts.controller.GameController;
import com.polishdraughts.controller.ViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class PolishDraughtsWindowed extends Application {
    private static Stage window;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        window = stage;
        window.setTitle("Polish Draughts");
        window.setResizable(false);

        final ViewController viewController = ViewController.getInstance();
        final GameController gameController = GameController.getInstance();

        viewController.setToWindowedDisplayMode();
        gameController.initMainMenuLabels();
        viewController.displayMainMenu();
        window.show();
    }

    public static Stage getWindow() {
        return window;
    }
}