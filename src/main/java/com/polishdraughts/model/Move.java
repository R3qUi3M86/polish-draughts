package com.polishdraughts.model;

public class Move {
    private boolean validMove;

    public void setValid(boolean validMove) {
        this.validMove = validMove;
    }

    public boolean isValid(){
        return validMove;
    }
}
