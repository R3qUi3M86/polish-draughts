package com.polishdraughts.controller;

import com.polishdraughts.model.*;

import java.util.ArrayList;

public class GameRules {
    public enum GameResults {
        TIE, WHITE_WINS, BLACK_WINS
    }

    private final MoveValidator moveValidator = new MoveValidator();
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

    public MoveValidator getMoveValidator() {
        return moveValidator;
    }

    public Color getCurrentPlayerColor(){
        return currentPlayerColor;
    }

    public void switchCurrentPlayer(){
        currentPlayerColor = Color.getOppositeColor(currentPlayerColor);
    }

    public boolean playerCanTakeNextPawn(Integer moveFrom, GameState gameState){
        boolean pawnIsPromoted = false;
        try {
            pawnIsPromoted = gameState.getPawn(moveFrom).isPromoted();
        } catch (EmptyFieldException ignored) {}

        ArrayList<Integer> adjacentFields = gameState.getGameBoard().getAdjacentFields(moveFrom);
        for (Integer moveDir : adjacentFields){
            Integer[] fieldsInLine = gameState.getGameBoard().getFieldsLineInMoveDir(moveFrom, moveDir);
            for (int i = 0; i < 9; i++){
                if (fieldsInLine[i] != null && gameState.getGameBoard().fieldIsEmpty(fieldsInLine[i])) {
                    Move move = new Move(currentPlayerColor);
                    move.setMoveFrom(moveFrom);
                    move.setMoveTo(fieldsInLine[i]);
                    if ((gameState.moveJumpsOneFar(move, fieldsInLine) && pawnIsPromoted) ||
                            (gameState.moveJumpsOneNear(move, fieldsInLine) && !pawnIsPromoted)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean playerHasToTake(GameState gameState){
        if (currentPlayerColor == Color.WHITE){
            for (Integer playerPawnField : gameState.getWhitePieces().keySet()){
                if (playerCanTakeNextPawn(playerPawnField, gameState)){
                    return true;
                }
            }
        } else {
            for (Integer playerPawnField : gameState.getBlackPieces().keySet()){
                if (playerCanTakeNextPawn(playerPawnField, gameState)){
                    return true;
                }
            }
        }
        return false;
    }

    public void checkPawnPromotion(Move move, GameState gameState){
        Integer tileNo = move.getLastTargetFieldNo();
        if (move.getMovingPlayerColor() == Color.WHITE && tileNo < 6){
            gameState.getWhitePieces().get(tileNo).setPromoted(true);
            gameState.updateVisualModel();
        } else if (move.getMovingPlayerColor() == Color.BLACK && tileNo > 45){
            gameState.getBlackPieces().get(tileNo).setPromoted(true);
            gameState.updateVisualModel();
        }
    }

    public boolean gameHasFinished(GameState gameState){
        if (gameState.getWhitePieces().isEmpty()){
            gameResult = GameResults.BLACK_WINS;
            return true;
        } else if (gameState.getBlackPieces().isEmpty()){
            gameResult = GameResults.WHITE_WINS;
            return true;
        } else return gameResult == GameResults.TIE;
    }

    public GameResults getGameResult(){
        return gameResult;
    }

    public void setTie(){
        gameResult = GameResults.TIE;
    }
}
