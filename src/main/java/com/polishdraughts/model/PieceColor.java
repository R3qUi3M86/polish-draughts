package com.polishdraughts.model;

public enum PieceColor {
    WHITE, BLACK, NONE;

    public static PieceColor getOppositeColor(PieceColor color){
        if (color == PieceColor.WHITE){
            return PieceColor.BLACK;
        } else {
            return PieceColor.WHITE;
        }
    }
}
