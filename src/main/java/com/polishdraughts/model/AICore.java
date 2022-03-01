package com.polishdraughts.model;

import com.polishdraughts.controller.GameController;
import com.polishdraughts.controller.GameRules;

import java.util.*;

public class AICore {
    private final PieceColor aiColor;
    private Move bestMove;
    private Integer bestScore = -100;
    public AICore(PieceColor aiColor){
        this.aiColor = aiColor;
    }

    public void askAiMove(GameRules gameRules, GameState gameState, Move lastMove){
        playPotentialMoves(gameRules, gameState, lastMove);
        decideBestMove(gameState);
        gameState.getMinMaxMoves().clear();
        GameController.getInstance().tryToPlayMove(bestMove);
    }

    private void playPotentialMoves(GameRules gameRules, GameState gameState, Move lastMove){
        ArrayList<Move> potentialMoves = getPotentialMovesList(gameRules, gameState, lastMove);
        for (Move potentialMove : potentialMoves){
            try {
                GameState iterGameState = (GameState) gameState.clone();
                iterGameState.makeMove(potentialMove);
                scoreGameState(gameState, iterGameState, potentialMove);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
    }

    private void decideBestMove(GameState gameState){
        for (Map.Entry<Move, Integer> entry : gameState.getMinMaxMoves().entrySet()) {
            Move move = entry.getKey();
            Integer score = entry.getValue();
            if (score >= bestScore){
                bestScore = score;
                bestMove = move;
            }
        }
        bestScore = -100;
    }

    private ArrayList<Move> getPotentialMovesList(GameRules gameRules, GameState gameState, Move lastMove){
        PieceColor movingPlayerColor = lastMove.getMovingPlayerColor();
        ArrayList<Move> potMoveList = new ArrayList<>();
        HashMap<Integer, Pawn> pieces;
        if (movingPlayerColor == PieceColor.WHITE) {
            pieces = gameState.getWhitePieces();
        } else {
            pieces = gameState.getBlackPieces();
        }
        Set<Integer> emptyFields = getEmptyFields(gameState);
        Set<Integer> pawnFields = pieces.keySet();
        for (Integer pawnField : pawnFields){
            for (Integer emptyField : emptyFields){
                try{
                    Move potMove = (Move) lastMove.clone();
                    potMove.setCurrentMove(pawnField + "-" + emptyField);
                    gameRules.getMoveValidator().validateMove(potMove, gameState);
                    if (potMove.isValid()){
                        potMoveList.add(potMove);
                    }
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
        return potMoveList;
    }

    private Set<Integer> getEmptyFields(GameState gameState){
        Set<Integer> emptyFields = new HashSet<>();
        for (int i = 1; i <= 50; i++){
            if (!gameState.getBlackPieces().containsKey(i) && !gameState.getWhitePieces().containsKey(i)){
                emptyFields.add(i);
            }
        }
        return emptyFields;
    }

    private void scoreGameState(GameState previousGameState, GameState iterGameState, Move potentialMove){
        Integer blackPiecesCount = iterGameState.getBlackPieces().size();
        Integer whitePiecesCount = iterGameState.getWhitePieces().size();
        int score;
        if (aiColor == PieceColor.BLACK){
            score = blackPiecesCount - whitePiecesCount;
        } else {
            score = whitePiecesCount - blackPiecesCount;
        }
        previousGameState.getMinMaxMoves().put(potentialMove, score);
    }

//    private void (Move potMove, GameRules gameRules, GameState gameState){
//            if (potMove.moveTakenPiece() && gameRules.playerCanTakeNextPawn(potMove.getLastTargetFieldNo(), gameState)){
//                potMove.setChainedMove(true);
//                potMove.setMoveTakenPiece(true);
//                play(move);
//            } else {
////                gameRules.checkPawnPromotion(potMove, gameState);
////                gameRules.switchCurrentPlayer();
////                play(new Move(gameRules.getCurrentPlayerColor(), potMove));
//            }
//        }
//    }
}
