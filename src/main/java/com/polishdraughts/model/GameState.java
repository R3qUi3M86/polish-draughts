package com.polishdraughts.model;

import com.polishdraughts.controller.EmptyFieldException;

import java.util.ArrayList;
import java.util.HashMap;

public class GameState {
    private final Board gameBoard = new Board();
    private final HashMap<Integer, Pawn> whitePieces = new HashMap<>();
    private final HashMap<Integer, Pawn> blackPieces = new HashMap<>();
    private final ArrayList<HashMap<Integer, Pawn>> allPieces = new ArrayList<>();
    private final ArrayList<Pawn> takenPieces = new ArrayList<>();

    public GameState(){
        setPawnPieces();
        allPieces.add(whitePieces);
        allPieces.add(blackPieces);
        gameBoard.updateBoardPieces(allPieces);
    }

    public void makeMove(Move move){
        gameBoard.updateBoardPieces(allPieces);
    }

    public void setPawnPieces() {
        for (int pos = 1; pos <= 50; pos++) {
            if (pos < 21){
                blackPieces.put(pos, new Pawn(pos));
            } else if (pos > 30){
                whitePieces.put(pos, new Pawn(pos));
            }
        }
    }

    public Board getGameBoard() {
        return gameBoard;
    }

    public Pawn getPawn(int fieldNo) throws EmptyFieldException {
        if (whitePieces.get(fieldNo) != null) {
            return whitePieces.get(fieldNo);
        } else  if (blackPieces.get(fieldNo) != null) {
            return blackPieces.get(fieldNo);
        } else {
            throw new EmptyFieldException(fieldNo);
        }
    }

    public boolean moveJumpsOneFar(Integer moveFrom, Integer moveTo, Integer[] straightLineFields) {
        return true;
    }

    public boolean moveJumpsOneNear(Integer moveFrom, Integer moveTo, Integer[] straightLineFields) {
        return true;
    }
}
