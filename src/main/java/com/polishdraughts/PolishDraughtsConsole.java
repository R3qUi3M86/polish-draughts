package com.polishdraughts;

import com.polishdraughts.controller.GameController;
import com.polishdraughts.controller.ViewController;

public class PolishDraughtsConsole {
    public static void main(String[] args) {
        final ViewController viewController = ViewController.getInstance();
        final GameController gameController = GameController.getInstance();

        viewController.setToConsoleDisplayMode();
        gameController.initMainMenuLabels();
        viewController.displayMainMenu();
    }
}
