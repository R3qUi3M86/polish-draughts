package com.polishdraughts.model;

public enum PieceColor {
    WHITE, BLACK;

    public static PieceColor getOppositeColor(PieceColor color){
        if (color == PieceColor.WHITE){
            return PieceColor.BLACK;
        } else {
            return PieceColor.WHITE;
        }
    }
}
