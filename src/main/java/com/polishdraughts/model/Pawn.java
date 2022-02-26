package com.polishdraughts.model;

public class Pawn {
    private final PieceColor pawnColor;
    private boolean isPromoted = false;

    public Pawn(int pawnPos) {
        if (pawnPos > 30) {
            pawnColor = PieceColor.WHITE;
        } else {
            pawnColor = PieceColor.BLACK;
        }
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
}
