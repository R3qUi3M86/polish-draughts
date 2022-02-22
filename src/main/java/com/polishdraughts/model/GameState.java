package com.polishdraughts.model;

import java.util.ArrayList;

public class GameState {
    private final Board gameBoard = new Board();
    private final ArrayList<Pawn> whitePieces = gameBoard.setWhitePiecesOnBoard();
    private final ArrayList<Pawn> blackPieces = gameBoard.setBlackPiecesOnBoard();
    private final ArrayList<Pawn> takenPieces = new ArrayList<>();

    public void makeMove(Move move){

    }

    public Board getGameBoard() {
        return gameBoard;
    }
}
