package com.polishdraughts.polishdraughts;

import com.polishdraughts.polishdraughts.controller.GameController;
import com.polishdraughts.polishdraughts.controller.ViewController;

public class PolishDraughts {
    public static void main(String[] args) {
        ViewController.setToConsoleDisplayMode(); //Change this line for different render mode
        GameController.initMainMenuLabels();
        ViewController.displayMainMenu();
    }
}
