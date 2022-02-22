package com.polishdraughts.model;

import java.util.ArrayList;

public class Board {
    private final int[][] board = new int[10][10];
    private final String[][] boardWithPieces = new String[10][10];

    public Board(){
        Color tileColor = Color.WHITE;
        int tileNumber = 1;
        for (int row = 0; row < 10; row++){
            for (int col = 0; col < 10; col++){
                if (tileColor == Color.BLACK){
                    board[row][col] = tileNumber;
                    boardWithPieces[row][col] = "-";
                    tileNumber++;
                } else {
                    board[row][col] = 0;
                    boardWithPieces[row][col] = " ";
                }
                if (col != 9) {
                    tileColor = Color.getOppositeColor(tileColor);
                }
            }
        }
    }

    public ArrayList<Pawn> setWhitePiecesOnBoard() {
        ArrayList<Pawn> pieces = new ArrayList<>();
        for (int pos = 31; pos <= 50; pos++) {
            int[] pawnIndex = getTileArrIndex(pos);
            boardWithPieces[pawnIndex[0]][pawnIndex[1]] = "W";
            pieces.add(new Pawn(pos, pawnIndex));
        }
        return pieces;
    }

    public ArrayList<Pawn> setBlackPiecesOnBoard() {
        ArrayList<Pawn> pieces = new ArrayList<>();
        for (int pos = 1; pos <= 20; pos++) {
            int[] pawnIndex = getTileArrIndex(pos);
            boardWithPieces[pawnIndex[0]][pawnIndex[1]] = "B";
            pieces.add(new Pawn(pos, pawnIndex));
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

    public int[][] getEmptyBoard() {
        return board;
    }

    public String[][] getBoardWithPieces() {
        return boardWithPieces;
    }

}
