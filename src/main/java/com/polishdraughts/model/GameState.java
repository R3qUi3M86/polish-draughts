package com.polishdraughts.model;

import com.polishdraughts.controller.EmptyFieldException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static com.polishdraughts.util.Utilities.getPawnFieldNoFromSet;

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
        gameBoard.updateVisualModel(allPieces);
    }

    public void makeMove(Move move){
        move.addMoveToTargetFieldMoves(move.getMoveTo());
        move.setLastMove();
        if (move.getMovingPlayerColor() == Color.WHITE){
            whitePieces.put(move.getMoveTo(), whitePieces.get(move.getMoveFrom()));
            whitePieces.remove(move.getMoveFrom());
        } else {
            blackPieces.put(move.getMoveTo(), blackPieces.get(move.getMoveFrom()));
            blackPieces.remove(move.getMoveFrom());
        }
        if (move.moveTakes()){
            removePawnFromTheGame(move.getTakenPawnFieldNo());
            move.setTakenPawnFieldNo(null);
            move.setMoveTakenPiece(true);
            move.setMoveTakes(false);
            move.setChainedMove(false);
        }
        move.setMoveFrom(null);
        move.setMoveTo(null);
        move.setCurrentMove(null);
        gameBoard.updateVisualModel(allPieces);
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

    public Pawn getPawn(Integer fieldNo) throws EmptyFieldException {
        if (whitePieces.get(fieldNo) != null) {
            return whitePieces.get(fieldNo);
        } else  if (blackPieces.get(fieldNo) != null) {
            return blackPieces.get(fieldNo);
        } else {
            throw new EmptyFieldException(fieldNo);
        }
    }

    public boolean moveJumpsOneNear(Move move, Integer[] straightLineFields) {
        Integer moveTo = move.getMoveTo();
        Color playerColor = move.getMovingPlayerColor();
        Pawn jumpedPawn;
        try{
            jumpedPawn = getPawn(straightLineFields[0]);
        } catch (EmptyFieldException e){
            return false;
        }

        if (straightLineFields[1].equals(moveTo)) {
            if (jumpedPawn.getPawnColor().equals(Color.getOppositeColor(playerColor))){
                setMoveTakenPawnFieldNo(move, jumpedPawn);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean moveJumpsOneFar(Move move, Integer[] straightLineFields) {
        Integer moveTo = move.getMoveTo();
        Color playerColor = move.getMovingPlayerColor();
        Pawn jumpedPawn = null;
        for (int i = 0; i < Arrays.asList(straightLineFields).indexOf(moveTo); i++) {
            try {
                if (jumpedPawn == null){
                    jumpedPawn = getPawn(straightLineFields[i]);
                } else if (getPawn(straightLineFields[i]) != null){
                    return false;
                }
            } catch (EmptyFieldException ignored) {}
        }
        if (jumpedPawn != null && jumpedPawn.getPawnColor().equals(Color.getOppositeColor(playerColor))){
            setMoveTakenPawnFieldNo(move, jumpedPawn);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveDoesNotJumpOver(Move move, Integer[] straightLineFields){
        Integer moveTo = move.getMoveTo();
        for (int i = 0; i < Arrays.asList(straightLineFields).indexOf(moveTo); i++) {
            try {
                if (getPawn(straightLineFields[i]) != null){
                    return false;
                }
            } catch (EmptyFieldException ignored) {}
        }
        return true;
    }

    private void setMoveTakenPawnFieldNo(Move move, Pawn takenPawn){
        Color takenPawnColor = takenPawn.getPawnColor();
        if (takenPawnColor == Color.WHITE){
            move.setTakenPawnFieldNo(getPawnFieldNoFromSet(whitePieces, takenPawn));
        } else {
            move.setTakenPawnFieldNo(getPawnFieldNoFromSet(blackPieces, takenPawn));
        }
    }

    private void removePawnFromTheGame(Integer fieldNo){
        if (whitePieces.containsKey(fieldNo)){
            takenPieces.add(whitePieces.get(fieldNo));
            whitePieces.remove(fieldNo);
        } else {
            takenPieces.add(blackPieces.get(fieldNo));
            blackPieces.remove(fieldNo);
        }
    }

    public HashMap<Integer, Pawn> getWhitePieces() {
        return whitePieces;
    }

    public HashMap<Integer, Pawn> getBlackPieces() {
        return blackPieces;
    }
}
