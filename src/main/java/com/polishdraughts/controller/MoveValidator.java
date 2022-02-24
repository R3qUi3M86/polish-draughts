package com.polishdraughts.controller;

import com.polishdraughts.model.Board;
import com.polishdraughts.model.GameState;
import com.polishdraughts.model.Move;
import com.polishdraughts.model.Pawn;
import com.polishdraughts.util.Utilities;

import java.util.Objects;

import static com.polishdraughts.util.Utilities.arrayContains;

public class MoveValidator {
    private Pawn movingPawn;

    public void validateMove(Move move, GameState gameState){
        if (inputIsValidFormat(move)){
            if (selectedOwnPiece(move, gameState)){
                if (move.getLastMove() != null){
                    if (movedWithSamePiece(move)){
                        checkMoveLegality(move, gameState);
                    } else {
                        setMoveInvalid(move, Move.InvalidMoveType.NOT_MOVED_WITH_SAME_PIECE);
                    }
                } else {
                    checkMoveLegality(move, gameState);
                }
            } else {
                setMoveInvalid(move, Move.InvalidMoveType.NOT_SELECTED_OWN_PIECE);
            }
        } else {
            setMoveInvalid(move, Move.InvalidMoveType.WRONG_INPUT_FORMAT);
        }
    }

    private boolean inputIsValidFormat(Move move){
        String userInput = move.getCurrentMove();
        if (userInput.contains("-")){
            String[] inputList = userInput.split("-");
            if (Utilities.isInteger(inputList[0]) && Utilities.isInteger(inputList[1])){
                int moveFrom = Integer.parseInt(inputList[0]);
                int moveTo = Integer.parseInt(inputList[1]);
                if(0 < moveFrom && moveFrom <= 50 && 0 < moveTo && moveTo <= 50){
                    move.setMoveFrom(moveFrom);
                    move.setMoveTo(moveTo);
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private boolean selectedOwnPiece(Move move, GameState gameState){
        try {
            movingPawn = gameState.getPawn(move.getMoveFrom());
            return movingPawn.getPawnColor() == move.getMovingPlayerColor();
        } catch (EmptyFieldException e) {
            return false;
        }
    }

    private boolean movedWithSamePiece(Move move){
        return Objects.equals(move.getMoveFrom(), move.getLastTargetFieldNo());
    }

    private void checkMoveLegality(Move move, GameState gameState){
        if (targetSquareIsValid(move, gameState)){
            if (move.moveTakes()){
                setMoveValid(move);
            } else {
                if (moveHasToTake(move, gameState)){
                    setMoveInvalid(move, Move.InvalidMoveType.NEEDS_TO_TAKE);
                } else {
                    setMoveValid(move);
                }
            }
        }
    }

    private boolean targetSquareIsValid(Move move, GameState gameState){
        Board gameBoard = gameState.getGameBoard();
        int moveFrom = move.getMoveFrom();
        int moveTo = move.getMoveTo();
        Integer[] straightLineFields = gameBoard.getStraightLineFields(moveFrom);
        if (gameBoard.fieldIsEmpty(moveTo) && arrayContains(straightLineFields, moveFrom)) {
            if (!gameBoard.fieldIsAdjacent(moveFrom, moveTo)) {
                if (movingPawn.isPromoted()) {
                    if (gameState.moveJumpsOneFar(moveFrom, moveTo, straightLineFields)) {
                        move.setMoveTakes(true);
                        return true;
                    } else return false;
                } else if (gameState.moveJumpsOneNear(moveFrom, moveTo, straightLineFields)){
                    move.setMoveTakes(true);
                    return true;
                } else return false;
            }
            return true;
        }
        return false;
    }

    private boolean moveHasToTake(Move move, GameState gameState){
        //Implement function
        return true;
    }

    private void setMoveInvalid(Move move, Move.InvalidMoveType invalidMoveType){
        move.setInvalidType(invalidMoveType);
        move.setValid(false);
        move.setMoveFrom(null);
        move.setMoveTo(null);
        move.setCurrentMove(null);
    }

    private void setMoveValid(Move move){
        move.setValid(true);
        // Implement rest of function
    }
}
