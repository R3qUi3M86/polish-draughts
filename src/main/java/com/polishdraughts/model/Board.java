package com.polishdraughts.model;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    private final int[][] board = new int[10][10];
    private final String[][] boardWithPieces = new String[10][10];

    public Board(){
        setEmptyBoards();
    }
    
    private void setEmptyBoards(){
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

    public void updateBoardPieces(ArrayList<HashMap<Integer, Pawn>> BlackAndWhitePieces){
        setEmptyBoards();
        for (HashMap<Integer, Pawn> piecesSet : BlackAndWhitePieces) {
            piecesSet.forEach((fieldNo, pawn) -> {
                int[] fieldIndex = getTileArrIndex(fieldNo);
                Color pawnColor = pawn.getPawnColor();
                if (pawnColor == Color.WHITE){
                    if (pawn.isPromoted()){
                        boardWithPieces[fieldIndex[0]][fieldIndex[1]] = "WK";
                    } else {
                        boardWithPieces[fieldIndex[0]][fieldIndex[1]] = "W";
                    }
                } else {
                    if (pawn.isPromoted()) {
                        boardWithPieces[fieldIndex[0]][fieldIndex[1]] = "BK";
                    } else {
                        boardWithPieces[fieldIndex[0]][fieldIndex[1]] = "B";
                    }
                }
            });
        }
    }

    public boolean fieldIsEmpty(Integer fieldNo){
        int[] fieldIndex = getTileArrIndex(fieldNo);
        return boardWithPieces[fieldIndex[0]][fieldIndex[1]].equals("-");
    }

    public Integer[] getStraightLineFields(int moveFrom) {
        return new Integer[2];
    }

    public boolean fieldIsAdjacent(int moveFrom, int moveTo) {
        return true;
    }


}
