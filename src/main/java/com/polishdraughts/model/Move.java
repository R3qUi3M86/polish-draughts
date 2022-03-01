package com.polishdraughts.model;

import java.util.ArrayList;

public class Move implements Cloneable{
    public enum InvalidMoveType {
        WRONG_INPUT_FORMAT, NOT_SELECTED_OWN_PIECE, INVALID_TARGET_SQUARE, NEEDS_TO_TAKE, NOT_MOVED_WITH_SAME_PIECE
    }

    private final PieceColor movingPlayerColor;
    private InvalidMoveType invalidType;
    private boolean newMove = true;
    private boolean validMove = true;
    private boolean chainedMove = false;
    private boolean moveTakes = false;
    private boolean moveTakenPiece = false;
    private Integer takenPawnFieldNo;
    private Integer moveFrom;
    private Integer moveTo;
    private Move opponentMove;
    private String lastMove;
    private String currentMove;
    private ArrayList<Integer> targetFieldMoves = new ArrayList<>();

    public Move(PieceColor movingPlayerColor){
        this.movingPlayerColor = movingPlayerColor;
    }
    public Move(PieceColor movingPlayerColor, Move opponentMove){
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

    public PieceColor getMovingPlayerColor() {
        return movingPlayerColor;
    }

    public boolean isChainedMove() {
        return chainedMove;
    }

    public void setChainedMove(boolean chainedMove) {
        this.chainedMove = chainedMove;
    }

    public boolean moveTakes() {
        return moveTakes;
    }

    public void setMoveTakes(boolean moveTakes) {
        this.moveTakes = moveTakes;
    }

    public boolean moveTakenPiece() {
        return moveTakenPiece;
    }

    public void setMoveTakenPiece(boolean moveTakenPiece) {
        this.moveTakenPiece = moveTakenPiece;
    }

    public Integer getTakenPawnFieldNo() {
        return takenPawnFieldNo;
    }

    public void setTakenPawnFieldNo(Integer takenPawnFieldNo) {
        this.takenPawnFieldNo = takenPawnFieldNo;
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

    public void setLastMove() {
        if (lastMove != null){
            if(moveTakes){
                lastMove = lastMove.concat("x").concat(moveTo.toString());
            } else {
                lastMove = lastMove.concat("-").concat(moveTo.toString());
            }
        } else {
            if (moveTakes){
                lastMove = moveFrom.toString() + "x" + moveTo.toString();
            } else {
                lastMove = currentMove;
            }
        }
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

    public Object clone() throws CloneNotSupportedException
    {
        Move clonedMove = (Move) super.clone();
        clonedMove.setTargetFieldMoves(new ArrayList<>(targetFieldMoves));
        return clonedMove;
    }

    public void setTargetFieldMoves(ArrayList<Integer> targetFieldMoves) {
        this.targetFieldMoves = targetFieldMoves;
    }
}
