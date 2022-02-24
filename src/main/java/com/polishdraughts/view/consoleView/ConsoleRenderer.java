package com.polishdraughts.view.consoleView;

import com.polishdraughts.controller.GameController;
import com.polishdraughts.controller.ViewController;
import com.polishdraughts.model.Color;
import com.polishdraughts.controller.GameRules.GameResults;
import com.polishdraughts.model.GameState;
import com.polishdraughts.model.Move;
import com.polishdraughts.view.Renderer;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleRenderer implements Renderer {
    public static void clearScreen(){
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e){
            System.out.println("Cannot clear screen on this machine");
        }
    }

    public void renderMainMenu(ArrayList<String> menuOptions, boolean invalidInput){
        clearScreen();
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
        clearScreen();
        System.out.println("           1         2         3         4         5");
        renderLabeledBoardRows(gameState);
        System.out.println("     46        47        48        49        50\n");
    }

    private void renderLabeledBoardRows(GameState gameState){
        String blackPieceRowFrag = "|:BB:";
        String blackPromPieceRowFrag = "|:BK:";
        String whitePieceRowFrag = "|:WW:";
        String whitePromPieceRowFrag = "|:WK:";
        String whiteSquareRowFrag = "|    ";
        String blackSquareRowFrag = "|::::";
        String hLineTopBot = "    =================================================";
        String hLineMid =    "   |----|----|----|----|----|----|----|----|----|----|";

        System.out.println(hLineTopBot);
        String[][] boardWithPieces = gameState.getGameBoard().getBoardWithPieces();
        for (int row = 0; row < 10; row++){
            String boardRowFrag = " ";
            for (int col = 0; col < 10; col++) {
                if (boardWithPieces[row][col].equals("B")) {
                    boardRowFrag = boardRowFrag.concat(blackPieceRowFrag);
                } else if (boardWithPieces[row][col].equals("BK")) {
                    boardRowFrag = boardRowFrag.concat(blackPromPieceRowFrag);
                } else if (boardWithPieces[row][col].equals("W")) {
                    boardRowFrag = boardRowFrag.concat(whitePieceRowFrag);
                } else if (boardWithPieces[row][col].equals("WK")) {
                    boardRowFrag = boardRowFrag.concat(whitePromPieceRowFrag);
                } else if (boardWithPieces[row][col].equals(" ")) {
                    boardRowFrag = boardRowFrag.concat(whiteSquareRowFrag);
                } else {
                    boardRowFrag = boardRowFrag.concat(blackSquareRowFrag);
                }
            }
            boardRowFrag = boardRowFrag.concat("| ");

            int firstDigit = (row / 2)*10;
            if (row % 2 == 0){
                if (firstDigit != 0) {
                    boardRowFrag = "  ".concat(boardRowFrag).concat(Integer.toString(firstDigit + 5));
                } else {
                    boardRowFrag = "  ".concat(boardRowFrag);
                }
            } else {
                if (firstDigit > 0) {
                    if (row != 9) {
                        boardRowFrag = Integer.toString(firstDigit + 6).concat(boardRowFrag);
                    } else {
                        boardRowFrag = "  " + boardRowFrag;
                    }
                } else {
                    boardRowFrag = " ".concat(Integer.toString(6)).concat(boardRowFrag);
                }
            }
            System.out.println(boardRowFrag);
            if (row != 9){
                System.out.println(hLineMid);
            } else {
                System.out.println(hLineTopBot);
            }
        }

    }

    public void askForMoveInput(Move move){
        if (move.isNewMove() && move.getOpponentMove() != null){
            displayPlayerMove(move.getOpponentMove());
        } else if (!move.isValid()){
            displayInvalidMoveInfo(move);
        } else if (move.isChainedMove()){
            displayPlayerMove(move);
        }
        if (move.getMovingPlayerColor() == Color.WHITE){
            System.out.println("White to move...");
        } else {
            System.out.println("Black to move....");
        }
        System.out.println("Please provide input in format FromFieldNo-ToFieldNo (example: 9-14)");
        System.out.print("Your move input > ");
        Scanner input = new Scanner(System.in);
        String inputString = input.nextLine();
        move.setCurrentMove(inputString);
        GameController.getInstance().takeGameInput(inputString, move);
    }

    private void displayPlayerMove(Move move){
        if (move.getMovingPlayerColor() == Color.WHITE){
            System.out.print("White ");
        } else {
            System.out.print("Black ");
        }
        if (move.moveTakenPiece()){
            System.out.print("takes: ");
        } else {
            System.out.print("moves: ");
        }
        System.out.println(move.getLastMove());
    }

    private void displayInvalidMoveInfo(Move move){
        switch (move.getInvalidType()){
            case WRONG_INPUT_FORMAT -> System.out.println("You have provided incorrect input format!");
            case NOT_SELECTED_OWN_PIECE -> System.out.println("You have not selected your piece to move!");
            case INVALID_TARGET_SQUARE -> System.out.println("Target square is invalid!");
            case NEEDS_TO_TAKE -> System.out.println("You must play a taking move!");
            case NOT_MOVED_WITH_SAME_PIECE -> System.out.println("You must move with the same piece! Last move to: "
                                                                  + move.getLastTargetFieldNo());
        }
    }

    public void renderFinalScore(GameResults gameResult){
        switch (gameResult){
            case TIE -> System.out.println("\nIt's a TIE!");
            case WHITE_WINS -> System.out.println("\nWHITE Player wins the game!");
            case BLACK_WINS -> System.out.println("\nBLACK Player wins the game!");
        }
    }
    public void pressAnyKeyPromptForBackToMenu(){
        Scanner input = new Scanner(System.in);
        input.nextLine();
        ViewController.getInstance().displayMainMenu();
    }
}
