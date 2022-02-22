package com.polishdraughts.model;

public class Pawn {
    private final Color pawnColor;
    private boolean isPromoted;
    private int[] boardIndex;

    public Pawn(int pawnPos, int[] boardIndex) {
        if (pawnPos > 30) {
            pawnColor = Color.WHITE;
        } else {
            pawnColor = Color.BLACK;
        }
        this.boardIndex = boardIndex;
    }

    public Color getPawnColor() {
        return pawnColor;
    }

    public int[] getBoardIndex() {
        return boardIndex;
    }

    public void setBoardIndex(int[] boardIndex) {
        this.boardIndex = boardIndex;
    }
}
