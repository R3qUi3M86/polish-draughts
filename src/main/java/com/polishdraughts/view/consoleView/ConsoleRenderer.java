package com.polishdraughts.view.consoleView;

import com.polishdraughts.controller.GameController;
import com.polishdraughts.model.GameRules.GameResults;
import com.polishdraughts.model.GameState;
import com.polishdraughts.model.Move;
import com.polishdraughts.view.Renderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleRenderer implements Renderer {
    public static void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }

    public void renderMainMenu(ArrayList<String> menuOptions, boolean invalidInput){
        try{
            clearScreen();
        } catch (Exception e){
            System.out.println("Cannot clear screen on this machine");
        }

        for (String option : menuOptions){
            System.out.println(option);
        }

        if (invalidInput){System.out.println("Invalid input!");}

        String userInput = getUserMenuInput();
        if (!userProvidedValidMenuInput(userInput, menuOptions)){
            renderMainMenu(menuOptions, true);
        } else {
            GameController.getInstance().initSelectedMenuOption(Integer.parseInt(userInput));
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
        return (0 < selectedOption && selectedOption < menuOptions.size()+1);
    }

    public void renderGameState(GameState gameState, boolean invalidInput){

    }

    public void askForMoveInput(Move previousMove){

    }

    public void renderFinalScore(GameResults gameResult){

    }
}
