package com.polishdraughts.model;

public class Pawn {
    private final Color pawnColor;
    private boolean isPromoted = false;

    public Pawn(int pawnPos) {
        if (pawnPos > 30) {
            pawnColor = Color.WHITE;
        } else {
            pawnColor = Color.BLACK;
        }
    }

    public Color getPawnColor() {
        return pawnColor;
    }

    public boolean isPromoted() {
        return isPromoted;
    }

    public void setPromoted(boolean promoted) {
        isPromoted = promoted;
    }
}
