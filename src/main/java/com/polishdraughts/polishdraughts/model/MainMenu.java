package com.polishdraughts.polishdraughts.model;

import java.util.ArrayList;

public class MainMenu {
    private enum MenuOptions {
        VS_HUMAN, VS_COMPUTER, QUIT
    }

    private static ArrayList<String> menuOptions;

    public static void setMenuOptions(){
        for (MenuOptions menuOption : MenuOptions.values()){
            switch (menuOption) {
                case VS_HUMAN -> MainMenu.menuOptions.add("Play Human vs Human");
                case VS_COMPUTER -> MainMenu.menuOptions.add("Play Human vs Computer");
                case QUIT -> MainMenu.menuOptions.add("Quit");
            }
        }
    }

    public static ArrayList<String> getMenuOptions(){
        return MainMenu.menuOptions;
    }
}
