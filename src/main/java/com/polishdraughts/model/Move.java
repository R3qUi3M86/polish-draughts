package com.polishdraughts.model;

import java.util.ArrayList;

public class Move {
    public enum InvalidMoveType {
        WRONG_INPUT_FORMAT, NOT_SELECTED_OWN_PIECE, INVALID_TARGET_SQUARE, NEEDS_TO_TAKE, NOT_MOVED_WITH_SAME_PIECE
    }

    private final Color movingPlayerColor;
    private InvalidMoveType invalidType;
    private boolean newMove = true;
    private boolean validMove = true;
    private boolean moveTakes = false;
    private boolean chainedMove = false;
    private Integer moveFrom;
    private Integer moveTo;
    private Move opponentMove;
    private String lastMove;
    private String currentMove;
    private final ArrayList<Integer> targetFieldMoves = new ArrayList<>();

    public Move(Color movingPlayerColor){
        this.movingPlayerColor = movingPlayerColor;
    }
    public Move(Move opponentMove, Color movingPlayerColor){
        this.movingPlayerColor = movingPlayerColor;
        this.opponentMove = opponentMove;
    }

    public boolean isNewMove() {
        return newMove;
    }

    public void setValid(boolean validMove) {
        this.validMove = validMove;
        newMove = false;
    }

    public Color getMovingPlayerColor() {
        return movingPlayerColor;
    }

    public boolean moveTakes() {
        return moveTakes;
    }

    public void setMoveTakes(boolean moveTakes) {
        this.moveTakes = moveTakes;
    }

    public boolean isChainedMove() {
        return chainedMove;
    }

    public void setChainedMove(boolean chainedMove) {
        this.chainedMove = chainedMove;
    }

    public Integer getMoveFrom() {
        return moveFrom;
    }

    public void setMoveFrom(Integer moveFrom) {
        this.moveFrom = moveFrom;
    }

    public Integer getMoveTo() {
        return moveTo;
    }

    public void setMoveTo(Integer moveTo) {
        this.moveTo = moveTo;
    }

    public boolean isValid(){
        return validMove;
    }

    public String getCurrentMove() {
        return currentMove;
    }

    public InvalidMoveType getInvalidType() {
        return invalidType;
    }

    public void setInvalidType(InvalidMoveType invalidType) {
        this.invalidType = invalidType;
    }

    public Move getOpponentMove() {
        return opponentMove;
    }

    public String getLastMove() {
        return lastMove;
    }

    public void setLastMove(String lastMove) {
        this.lastMove = lastMove;
    }

    public void setCurrentMove(String currentMove) {
        this.currentMove = currentMove;
    }

    public Integer getLastTargetFieldNo(){
        return targetFieldMoves.get(targetFieldMoves.size()-1);
    }

    public void addMoveToTargetFieldMoves(Integer targetField){
        targetFieldMoves.add(targetField);
    }
}
