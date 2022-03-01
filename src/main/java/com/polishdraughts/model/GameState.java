package com.polishdraughts.model;

import com.polishdraughts.controller.EmptyFieldException;

import java.util.*;

import static com.polishdraughts.util.Utilities.getPawnFieldNoFromSet;

public class GameState implements Cloneable {
    private final Board gameBoard = new Board();
    private HashMap<Integer, Pawn> whitePieces = new HashMap<>();
    private HashMap<Integer, Pawn> blackPieces = new HashMap<>();
    private ArrayList<HashMap<Integer, Pawn>> allPieces = new ArrayList<>();
    private final ArrayList<Pawn> takenPieces = new ArrayList<>();
    private HashMap<Move, Integer> minMaxMoves = new HashMap<>();

    public GameState(){
        setPawnPieces();
        allPieces.add(whitePieces);
        allPieces.add(blackPieces);
        updateVisualModel();
    }

    public void makeMove(Move move){
        move.addMoveToTargetFieldMoves(move.getMoveTo());
        move.setLastMove();
        if (move.getMovingPlayerColor() == PieceColor.WHITE){
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
            move.setChainedMove(false);
            move.setMoveTakes(false);
        }
        move.setMoveFrom(null);
        move.setMoveTo(null);
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
        PieceColor playerColor = move.getMovingPlayerColor();
        Pawn jumpedPawn;
        try{
            jumpedPawn = getPawn(straightLineFields[0]);
        } catch (EmptyFieldException e){
            return false;
        }

        if (straightLineFields[1].equals(moveTo)) {
            if (jumpedPawn.getPawnColor().equals(PieceColor.getOppositeColor(playerColor))){
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
        PieceColor playerColor = move.getMovingPlayerColor();
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
        if (jumpedPawn != null && jumpedPawn.getPawnColor().equals(PieceColor.getOppositeColor(playerColor))){
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
        PieceColor takenPawnColor = takenPawn.getPawnColor();
        if (takenPawnColor == PieceColor.WHITE){
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

    public void updateVisualModel(){
        gameBoard.updateVisualModel(allPieces);
    }

    public Set<Integer> getEmptyFields(){
        Set<Integer> emptyFields = new HashSet<>();
        for (int i = 1; i <= 50; i++){
            if (!getBlackPieces().containsKey(i) && !getWhitePieces().containsKey(i)){
                emptyFields.add(i);
            }
        }
        return emptyFields;
    }

    public HashMap<Integer, Pawn> getWhitePieces() {
        return whitePieces;
    }

    public HashMap<Integer, Pawn> getBlackPieces() {
        return blackPieces;
    }

    public Object clone() throws CloneNotSupportedException
    {
        GameState clonedGameState = (GameState) super.clone();
        clonedGameState.setWhitePieces(new HashMap<>(whitePieces));
        clonedGameState.setBlackPieces(new HashMap<>(blackPieces));
        clonedGameState.setAllPieces(new ArrayList<>(allPieces));
        clonedGameState.setMinMaxMoves(new HashMap<>());
        return clonedGameState;
    }

    public HashMap<Move, Integer> getMinMaxMoves() {
        return minMaxMoves;
    }

    public void setMinMaxMoves(HashMap<Move, Integer> minMaxMoves) {
        this.minMaxMoves = minMaxMoves;
    }

    public void setWhitePieces(HashMap<Integer, Pawn> whitePieces) {
        this.whitePieces = whitePieces;
    }

    public void setBlackPieces(HashMap<Integer, Pawn> blackPieces) {
        this.blackPieces = blackPieces;
    }

    public void setAllPieces(ArrayList<HashMap<Integer, Pawn>> allPieces) {
        this.allPieces = allPieces;
    }
}
