package com.polishdraughts;

import com.polishdraughts.controller.GameController;
import com.polishdraughts.controller.ViewController;

public class PolishDraughts {
    public static void main(String[] args) {
        final ViewController viewController = ViewController.getInstance();
        final GameController gameController = GameController.getInstance();

        viewController.setToConsoleDisplayMode(); //Change this line for different render mode
        gameController.initMainMenuLabels();
        viewController.displayMainMenu();
    }
}
