package com.polishdraughts.model;

public class Pawn implements Cloneable{
    private final PieceColor pawnColor;
    private boolean isPromoted = false;

    public Pawn(int pawnPos) {
        if (pawnPos > 30) {
            pawnColor = PieceColor.WHITE;
        } else {
            pawnColor = PieceColor.BLACK;
        }
    }

    public Pawn(PieceColor pawnColor, boolean isPromoted){
        this.pawnColor = pawnColor;
        this.isPromoted = isPromoted;
    }

    public PieceColor getPawnColor() {
        return pawnColor;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public void setPromoted(boolean promoted) {
        isPromoted = promoted;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
