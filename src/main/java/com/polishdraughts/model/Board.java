package com.polishdraughts.model;

import java.util.ArrayList;

public class Board {
    private final int[][] board = new int[10][10];

    public Board(){
        Color tileColor = Color.WHITE;
        int tileNumber = 1;
        for (int row = 0; row < 10; row++){
            for (int col = 0; col < 10; col++){
                if (tileColor == Color.BLACK){
                    board[row][col] = tileNumber;
                    tileNumber++;
                } else {
                    board[row][col] = 0;
                }
                if (col != 9) {
                    tileColor = Color.getOppositeColor(tileColor);
                }
            }
        }
    }

    public ArrayList<Pawn> setWhitePiecesOnBoard() {
        ArrayList<Pawn> pieces = new ArrayList<>();
        for (int i = 31; i <= 50; i++) {
            pieces.add(new Pawn(i, getTileArrIndex(i)));
        }
        return pieces;
    }

    public ArrayList<Pawn> setBlackPiecesOnBoard() {
        ArrayList<Pawn> pieces = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            pieces.add(new Pawn(i, getTileArrIndex(i)));
        }
        return pieces;
    }

    public int[] getTileArrIndex(int fieldNo){
        int row = (fieldNo-1) / 5;
        int column;
        if (0 < fieldNo % 10 && fieldNo % 10 < 6){
            column = ((fieldNo-1) % 5)*2 + 1;
        } else {
            column = ((fieldNo-1) % 5)*2;
        }
        return new int[]{row, column};
    }

    public int getFieldNo(int[] arrIndex){
        return board[arrIndex[0]][arrIndex[1]];
    }

    public int[][] getBoard() {
        return board;
    }
}
