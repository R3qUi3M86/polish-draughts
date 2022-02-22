package com.polishdraughts.model;

public class GameRules {
    public enum GameResults {
        TIE, WHITE_WINS, BLACK_WINS
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

    public Color getCurrentPlayerColor(){
        return currentPlayerColor;
    }

    public void switchCurrentPlayer(){
        currentPlayerColor = Color.getOppositeColor(currentPlayerColor);
    }

    public boolean playerCanTakeNextPawn(Move move){
        return false;
    }

    public boolean gameHasFinished(GameState gameState){
        return false;
    }

    public GameResults getGameResult(){
        return gameResult;
    }
}
