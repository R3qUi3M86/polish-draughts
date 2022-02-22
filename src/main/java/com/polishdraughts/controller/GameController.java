package com.polishdraughts.polishdraughts.controller;

import com.polishdraughts.polishdraughts.model.MainMenu;
import com.polishdraughts.polishdraughts.model.MenuOptions;

import java.util.concurrent.TimeUnit;

public abstract class GameController {
    public static void initMainMenuLabels(){
        MainMenu.setMenuOptions();
    }
    public static void initSelectedMenuOption(int number){
        MenuOptions menuOption = MenuOptions.values()[number];
        switch (menuOption){
            case VS_HUMAN -> startPlayerVsPlayerGame();
            case VS_COMPUTER -> startPlayerVsAIGame();
            case QUIT -> quitProgram();
        }
    }
    private static void startPlayerVsPlayerGame(){
        System.out.println("Starting pvp game");
    }
    private static void startPlayerVsAIGame(){
        System.out.println("Starting pve game");
    }
    private static void quitProgram(){
        System.out.println("Goodbye!");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.exit(0);
        }
        System.exit(0);
    }
}
