package com.polishdraughts.controller;

import com.polishdraughts.model.*;

import java.util.ArrayList;

public class GameRules {
    public enum GameResults {
        TIE, WHITE_WINS, BLACK_WINS
    }

    private final MoveValidator moveValidator = new MoveValidator();
    private PieceColor currentPlayerColor;
    private GameResults gameResult;

    public GameRules(){
        currentPlayerColor = PieceColor.WHITE;
    }

    public boolean itsWhiteTurn(){
        return currentPlayerColor == PieceColor.WHITE;
    }

    public boolean itsBlackTurn(){
        return currentPlayerColor == PieceColor.BLACK;
    }

    public MoveValidator getMoveValidator() {
        return moveValidator;
    }

    public PieceColor getCurrentPlayerColor(){
        return currentPlayerColor;
    }

    public void switchCurrentPlayer(){
        currentPlayerColor = PieceColor.getOppositeColor(currentPlayerColor);
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
        if (currentPlayerColor == PieceColor.WHITE){
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

    public void setPawnPromotion(Move move, GameState gameState, boolean clone){
        Integer tileNo = move.getLastTargetFieldNo();
        if (move.getMovingPlayerColor() == PieceColor.WHITE && tileNo < 6){
            if (clone){
                Pawn clonedPawn = new Pawn(PieceColor.WHITE, true);
                gameState.getWhitePieces().put(tileNo, clonedPawn);
            } else {
                gameState.getWhitePieces().get(tileNo).setPromoted(true);
            }
        } else if (move.getMovingPlayerColor() == PieceColor.BLACK && tileNo > 45){
            if (clone){
                Pawn clonedPawn = new Pawn(PieceColor.BLACK, true);
                gameState.getWhitePieces().put(tileNo, clonedPawn);
            } else {
                gameState.getBlackPieces().get(tileNo).setPromoted(true);
            }
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
        currentPlayerColor = PieceColor.NONE;
        return gameResult;
    }

    public void setTie(){
        gameResult = GameResults.TIE;
    }
}
