package com.polishdraughts.controller;

import com.polishdraughts.model.*;

import java.util.HashMap;
import java.util.Map;

public final class GameController {
    private static GameController gameController;
    private GameRules gameRules;
    private AICore aiCore;
    private final Map<Color, PlayerType> players = new HashMap<>();
    private GameState gameState;

    private GameController(){}

    public static GameController getInstance(){
        if (gameController == null){
            gameController = new GameController();
        }
        return gameController;
    }

    public void initMainMenuLabels(){
        MainMenu.setMenuOptions();
    }

    public void initSelectedMenuOption(int number){
        MainMenu.MenuOptions menuOption = MainMenu.MenuOptions.values()[number-1];
        switch (menuOption){
            case VS_HUMAN -> startPlayerVsPlayerGame();
            case VS_COMPUTER -> startPlayerVsAIGame();
            case QUIT -> quitProgram();
        }
    }

    private void startPlayerVsPlayerGame(){
        players.put(Color.WHITE, PlayerType.HUMAN);
        players.put(Color.BLACK, PlayerType.HUMAN);
        initCheckersGame();
    }

    private void startPlayerVsAIGame(){
        if ((int)((Math.random() * (3 - 1)) + 1) == 1){
            players.put(Color.WHITE, PlayerType.HUMAN);
            players.put(Color.BLACK, PlayerType.COMPUTER);
            aiCore = new AICore(Color.BLACK);
        } else {
            players.put(Color.WHITE, PlayerType.COMPUTER);
            players.put(Color.BLACK, PlayerType.HUMAN);
            aiCore = new AICore(Color.WHITE);
        }
        initCheckersGame();
    }

    private void initCheckersGame(){
        gameState = new GameState();
        gameRules = new GameRules();
        play(new Move());
    }

    private void play(Move previousMove){
        ViewController.getInstance().displayGameState(gameState);
        askCurrentPlayerToMove(previousMove);
    }

    private void askCurrentPlayerToMove(Move lastMove){
        if(playerIsHuman()){
            ViewController.getInstance().askHumanMove(lastMove);
        } else {
            aiCore.askAiMove(gameRules, gameState);
        }
    }

    private boolean playerIsHuman(){
        return gameRules.itsWhiteTurn() && players.get(Color.WHITE) == PlayerType.HUMAN ||
               gameRules.itsBlackTurn() && players.get(Color.BLACK) == PlayerType.HUMAN;
    }

    public void tryToPlayMove(Move move){
        gameRules.setMoveLegality(move);
        if (move.isValid()){
            gameState.makeMove(move);
            if (gameRules.gameHasFinished(gameState)){
                ViewController.getInstance().displayEndGameStatus(gameRules.getGameResult(), gameState);
            } else {
                if (gameRules.playerCanTakeNextPawn(move)){
                    play(move);
                } else {
                    gameRules.switchCurrentPlayer();
                    play(new Move());
                }
            }
        } else {
            play(move);
        }
    }

    private void quitProgram(){
        System.out.println("Goodbye!");
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.exit(0);
        }
        System.exit(0);
    }
}
