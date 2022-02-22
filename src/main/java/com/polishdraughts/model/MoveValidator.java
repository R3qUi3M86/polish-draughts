package com.polishdraughts.model;

public class MoveValidator {
    public void setMoveLegality(Move move, Board board){
        move.setValid(true);
    }

    public boolean playerCanTakeNextPawn(Move move, Board board){
        return false;
    }
}
