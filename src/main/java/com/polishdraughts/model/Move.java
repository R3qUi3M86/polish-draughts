package com.polishdraughts.model;

public class Move {
    public enum InvalidMoveType {
        WRONG_INPUT_FORMAT, NOT_SELECTED_OWN_PIECE, INVALID_TARGET_SQUARE, NEEDS_TO_TAKE, NOT_MOVED_WITH_SAME_PIECE
    }

    private boolean newMove = true;
    private final Color movingPlayerColor;
    private boolean validMove = true;
    private boolean takingMove = false;
    private boolean chainedMove = false;
    private InvalidMoveType invalidType;
    private Move opponentMove;
    private String lastMove;
    private String currentMove;

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

    public boolean isTakingMove() {
        return takingMove;
    }

    public void setTakingMove(boolean takingMove) {
        this.takingMove = takingMove;
    }

    public boolean isChainedMove() {
        return chainedMove;
    }

    public void setChainedMove(boolean chainedMove) {
        this.chainedMove = chainedMove;
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
}
