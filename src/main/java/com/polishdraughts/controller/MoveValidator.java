package com.polishdraughts.controller;

import com.polishdraughts.model.*;
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
                GameRules gameRules = GameController.getInstance().getGameRules();
                if (move.isChainedMove() || gameRules.playerHasToTake(gameState)){
                    setMoveInvalid(move, Move.InvalidMoveType.NEEDS_TO_TAKE);
                } else {
                    setMoveValid(move);
                }
            }
        } else {
            setMoveInvalid(move, Move.InvalidMoveType.INVALID_TARGET_SQUARE);
        }
    }

    private boolean targetSquareIsValid(Move move, GameState gameState){
        Board gameBoard = gameState.getGameBoard();
        Integer moveFrom = move.getMoveFrom();
        Integer moveTo = move.getMoveTo();
        Integer[] moveDirFields = gameBoard.getFieldsLineInMoveDir(moveFrom, moveTo);
        if (gameBoard.fieldIsEmpty(moveTo) && arrayContains(moveDirFields, moveTo)) {
            if (movingPawn.isPromoted()) {
                if (gameState.moveJumpsOneFar(move, moveDirFields)) {
                    move.setMoveTakes(true);
                    return true;
                } else return gameState.moveDoesNotJumpOver(move, moveDirFields);
            } else if (gameState.moveJumpsOneNear(move, moveDirFields)){
                move.setMoveTakes(true);
                return true;
            } else return moveTo.equals(moveDirFields[0]) && moveIsForward(move);
        }
        return false;
    }

    private boolean moveIsForward(Move move){
        Color playerColor = move.getMovingPlayerColor();
        Integer moveFrom = move.getMoveFrom();
        Integer moveTo = move.getMoveTo();
        if (playerColor == Color.WHITE && moveFrom > moveTo){
            return true;
        } else return playerColor == Color.BLACK && moveFrom < moveTo;
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
    }
}
