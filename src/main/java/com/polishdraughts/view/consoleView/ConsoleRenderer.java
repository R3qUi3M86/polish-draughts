package com.polishdraughts.polishdraughts.view.consoleView;

import com.polishdraughts.polishdraughts.controller.GameController;
import com.polishdraughts.polishdraughts.controller.RenderModes;
import com.polishdraughts.polishdraughts.model.MainMenu;
import com.polishdraughts.polishdraughts.view.Renderer;

import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleRenderer implements Renderer {
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void renderMainMenu(ArrayList<String> menuOptions, boolean invalidInput){
        clearScreen();

        for (String option : menuOptions){
            System.out.println(option);
        }
        String userInput = getUserMenuInput();
        if (!userProvidedValidMenuInput(userInput, menuOptions)){
            renderMainMenu(menuOptions, true);
        } else {
            GameController.initSelectedMenuOption(Integer.parseInt(userInput));
        }
    }

    private String getUserMenuInput(){
        System.out.print("Select menu option > ");
        Scanner input = new Scanner(System.in);
        return input.nextLine();
    }

    private boolean userProvidedValidMenuInput(String userInput, ArrayList<String> menuOptions){
        int selectedOption;
        try {
            selectedOption = Integer.parseInt(userInput);
        } catch (NumberFormatException e) {
            return false;
        }
        return selectedOption < menuOptions.size();
    }


}
