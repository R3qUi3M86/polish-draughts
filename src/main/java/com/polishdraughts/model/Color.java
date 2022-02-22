package com.polishdraughts.model;

public enum Color {
    WHITE, BLACK;

    public static Color getOppositeColor(Color color){
        if (color == Color.WHITE){
            return Color.BLACK;
        } else {
            return Color.WHITE;
        }
    }
}
