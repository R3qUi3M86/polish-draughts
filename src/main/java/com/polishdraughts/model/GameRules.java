package com.polishdraughts.model;

public class GameRules {
    public enum GameResults {
        TIE, PLAYER1_WON, PLAYER2_WON
    }

    private Color currentPlayerColor;
    private GameResults gameResult;

    public GameRules(){
        currentPlayerColor = Color.WHITE;
    }

    public boolean itsWhiteTurn(){
        return currentPlayerColor == Color.WHITE;
    }

    public boolean itsBlackTurn(){
        return currentPlayerColor == Color.BLACK;
    }

    public void switchCurrentPlayer(){
        currentPlayerColor = Color.getOppositeColor(currentPlayerColor);
    }

    public void setMoveLegality(Move move){
        move.setValid(true);
    }

    public boolean playerCanTakeNextPawn(Move move){
        return true;
    }

    public boolean gameHasFinished(GameState gameState){
        return false;
    }

    public GameResults getGameResult(){
        return gameResult;
    }
}
