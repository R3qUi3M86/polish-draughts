package com.polishdraughts.polishdraughts.model;

import java.util.ArrayList;

public class MainMenu {
    private static final ArrayList<String> menuOptions = new ArrayList<String>();

    public static void setMenuOptions(){
        for (MenuOptions menuOption : MenuOptions.values()){
            switch (menuOption) {
                case VS_HUMAN -> MainMenu.menuOptions.add("1. Play Human vs Human");
                case VS_COMPUTER -> MainMenu.menuOptions.add("2. Play Human vs Computer");
                case QUIT -> MainMenu.menuOptions.add("3. Quit");
            }
        }
    }

    public static ArrayList<String> getMenuOptions(){
        return MainMenu.menuOptions;
    }
}
