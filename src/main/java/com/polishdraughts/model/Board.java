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
        PieceColor tileColor = PieceColor.WHITE;
        int tileNumber = 1;
        for (int row = 0; row < 10; row++){
            for (int col = 0; col < 10; col++){
                if (tileColor == PieceColor.BLACK){
                    board[row][col] = tileNumber;
                    boardWithPieces[row][col] = "-";
                    tileNumber++;
                } else {
                    board[row][col] = 0;
                    boardWithPieces[row][col] = " ";
                }
                if (col != 9) {
                    tileColor = PieceColor.getOppositeColor(tileColor);
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

    public String[][] getBoardWithPieces() {
        return boardWithPieces;
    }

    public void updateVisualModel(ArrayList<HashMap<Integer, Pawn>> BlackAndWhitePieces){
        setEmptyBoards();
        for (HashMap<Integer, Pawn> piecesSet : BlackAndWhitePieces) {
            piecesSet.forEach((fieldNo, pawn) -> {
                int[] fieldIndex = getTileArrIndex(fieldNo);
                PieceColor pawnColor = pawn.getPawnColor();
                if (pawnColor == PieceColor.WHITE){
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

    public Integer[] getFieldsLineInMoveDir(Integer moveFrom, Integer moveTo){
        int[] moveFromIndex = getTileArrIndex(moveFrom);
        int[] moveToIndex = getTileArrIndex(moveTo);
        Integer[] moveDirFields = new Integer[9];

        int intRowDir = 1;
        int intColDir = 1;

        if (moveToIndex[0] < moveFromIndex[0]){
            intRowDir = -1;
        } else if (moveToIndex[0] == moveFromIndex[0]){
            return moveDirFields;
        }
        if (moveToIndex[1] < moveFromIndex[1]){
            intColDir = -1;
        } else if (moveToIndex[1] == moveFromIndex[1]){
            return moveDirFields;
        }

        for (int i = 1; i < 10; i++){
            if(moveFromIndex[0]+(i*intRowDir) < 10 && moveFromIndex[1]+(i*intColDir) < 10 &&
                    moveFromIndex[0]+(i*intRowDir) >= 0 && moveFromIndex[1]+(i*intColDir) >= 0 ){
                moveDirFields[i-1] = board[moveFromIndex[0]+(i*intRowDir)][moveFromIndex[1]+(i*intColDir)];
            } else {
                return moveDirFields;
            }
        }
        return moveDirFields;
    }

    public ArrayList<Integer> getAdjacentFields(Integer fieldNo){
        ArrayList<Integer> adjacentFields = new ArrayList<>();
        int[] fieldIndex = getTileArrIndex(fieldNo);
        if (fieldIndex[0]+1 < 10){
            if (fieldIndex[1]+1 < 10){
                adjacentFields.add(getFieldNo(new int[]{fieldIndex[0]+1, fieldIndex[1]+1}));
            }
            if (fieldIndex[1]-1 >= 0){
                adjacentFields.add(getFieldNo(new int[]{fieldIndex[0]+1, fieldIndex[1]-1}));
            }
        }
        if (fieldIndex[0]-1 >= 0){
            if (fieldIndex[1]+1 < 10){
                adjacentFields.add(getFieldNo(new int[]{fieldIndex[0]-1, fieldIndex[1]+1}));
            }
            if (fieldIndex[1]-1 >= 0){
                adjacentFields.add(getFieldNo(new int[]{fieldIndex[0]-1, fieldIndex[1]-1}));
            }
        }
        return adjacentFields;
    }
}
