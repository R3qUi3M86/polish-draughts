package com.polishdraughts.controller;

import com.polishdraughts.model.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class GameController {
    private static GameController gameController;
    private GameRules gameRules;
    private AICore aiCore;
    private final Map<PieceColor, PlayerType> players = new HashMap<>();
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
        players.put(PieceColor.WHITE, PlayerType.HUMAN);
        players.put(PieceColor.BLACK, PlayerType.HUMAN);
        initCheckersGame();
    }

    private void startPlayerVsAIGame(){
        if ((int)((Math.random() * (3 - 1)) + 1) == 1){
            players.put(PieceColor.WHITE, PlayerType.HUMAN);
            players.put(PieceColor.BLACK, PlayerType.COMPUTER);
            aiCore = new AICore(PieceColor.BLACK);
        } else {
            players.put(PieceColor.WHITE, PlayerType.COMPUTER);
            players.put(PieceColor.BLACK, PlayerType.HUMAN);
            aiCore = new AICore(PieceColor.WHITE);
        }
        initCheckersGame();
    }

    public GameRules getGameRules() {
        return gameRules;
    }

    private void initCheckersGame(){
        gameState = new GameState();
        gameRules = new GameRules();
        play(new Move(PieceColor.WHITE));
    }

    private void play(Move previousMove){
        ViewController.getInstance().displayGameState(gameState);
        askCurrentPlayerToMove(previousMove);
    }

    private void askCurrentPlayerToMove(Move lastMove){
        if(playerIsHuman()){
            ViewController.getInstance().askHumanMove(lastMove);
        } else {
            aiCore.askAiMove(gameRules, gameState, lastMove);
        }
    }

    private boolean playerIsHuman(){
        return gameRules.itsWhiteTurn() && players.get(PieceColor.WHITE) == PlayerType.HUMAN ||
               gameRules.itsBlackTurn() && players.get(PieceColor.BLACK) == PlayerType.HUMAN;
    }

    public void takeGameInput(String inputString, Move move) {
        if (Objects.equals(inputString, "tie")) {
            GameController.getInstance().tieGame();
        } else {
            move.setCurrentMove(inputString);
            GameController.getInstance().tryToPlayMove(move);
        }
    }

    public void tieGame(){
        gameRules.setTie();
        ViewController.getInstance().displayEndGameStatus(gameRules.getGameResult(), gameState);
    }

    public void tryToPlayMove(Move move){
        gameRules.getMoveValidator().validateMove(move, gameState);
        if (move.isValid()){
            gameState.makeMove(move);
            gameState.updateVisualModel();
            if (gameRules.gameHasFinished(gameState)){
                ViewController.getInstance().displayEndGameStatus(gameRules.getGameResult(), gameState);
            } else {
                if (move.moveTakenPiece() && gameRules.playerCanTakeNextPawn(move.getLastTargetFieldNo(), gameState)){
                    move.setChainedMove(true);
                    move.setMoveTakenPiece(true);
                    play(move);
                } else {
                    gameRules.setPawnPromotion(move, gameState, false);
                    gameRules.switchCurrentPlayer();
                    gameState.updateVisualModel();
                    play(new Move(gameRules.getCurrentPlayerColor(), move));
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
