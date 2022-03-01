package com.polishdraughts.model;

import com.polishdraughts.controller.GameController;
import com.polishdraughts.controller.GameRules;

import java.util.*;

public class AICore implements Runnable{
    private final PieceColor aiColor;
    private Move bestMove;
    private final Integer maxDepth = 4;
    Integer bestScore = 0;
    private GameRules gameRules;
    private GameState gameState;
    private Move lastMove;

    public AICore(PieceColor aiColor){
        this.aiColor = aiColor;
    }

    public Move askAiMove(GameRules gameRules, GameState gameState, Move lastMove){
        playPotentialMoves(gameRules, gameState, 1, lastMove);
        decideBestMove(gameState, true);
        gameState.getMinMaxMoves().clear();
        lastMove.setCurrentMove(bestMove.getCurrentMove());
        return lastMove;
    }

    private void playPotentialMoves(GameRules gameRules, GameState gameState, Integer currDepth, Move lastMove){
        if (currDepth <= maxDepth) {
            ArrayList<Move> potentialMoves = getPotentialMovesList(gameRules, gameState, lastMove);
            for (Move potentialMove : potentialMoves) {
                try {
                    GameState iterGameState = (GameState) gameState.clone();
                    iterGameState.makeMove(potentialMove);
                    updateMove(potentialMove, gameRules, iterGameState);
                    if (potentialMove.isChainedMove()) {
                        playPotentialMoves(gameRules, iterGameState, currDepth+1, potentialMove);
                    } else {
                        playPotentialMoves(gameRules, iterGameState, currDepth+1,
                                new Move(PieceColor.getOppositeColor(potentialMove.getMovingPlayerColor()), potentialMove));
                    }
                    scoreGameState(potentialMove, gameState, iterGameState);
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void decideBestMove(GameState gameState, boolean decideForAI){
        if (decideForAI){
            bestScore = -100;
        } else {
            bestScore = 100;
        }
        for (Map.Entry<Move, Integer> entry : gameState.getMinMaxMoves().entrySet()) {
            Move move = entry.getKey();
            Integer score = entry.getValue();
            if (decideForAI) {
                if (score >= bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            } else {
                if (score <= bestScore) {
                    bestScore = score;
                    bestMove = move;
                }
            }
        }
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
        Set<Integer> emptyFields = gameState.getEmptyFields();
        Set<Integer> pawnFields = pieces.keySet();
        for (Integer pawnField : pawnFields){
            for (Integer emptyField : emptyFields){
                try{
                    Move potMove = (Move) lastMove.clone();
                    potMove.setCurrentMove(pawnField.toString() + "-" + emptyField.toString());
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



    private void scoreGameState(Move potentialMove, GameState origGameState, GameState iterGameState){
        if (iterGameState.getMinMaxMoves().size() == 0) {
            Integer blackPiecesCount = iterGameState.getBlackPieces().size();
            Integer whitePiecesCount = iterGameState.getWhitePieces().size();
            int score;
            if (aiColor == PieceColor.BLACK) {
                score = blackPiecesCount + getPromotedScore(iterGameState.getBlackPieces()) -
                        (whitePiecesCount + getPromotedScore(iterGameState.getWhitePieces()));
            } else {
                score = whitePiecesCount + getPromotedScore(iterGameState.getWhitePieces()) -
                        (blackPiecesCount + getPromotedScore(iterGameState.getBlackPieces()));
            }
            origGameState.getMinMaxMoves().put(potentialMove, score);
        } else {
            if (potentialMove.getMovingPlayerColor() == aiColor) {
                decideBestMove(iterGameState, true);
            } else {
                decideBestMove(iterGameState, false);
            }
            origGameState.getMinMaxMoves().put(potentialMove, bestScore);
        }
    }

    private Integer getPromotedScore(HashMap<Integer, Pawn> pieces){
        int promScore = 0;
        for (Integer pawnField : pieces.keySet()){
            if (pieces.get(pawnField).isPromoted()){
                promScore += 4;
            }
        }
        return promScore;
    }

    private void updateMove(Move potMove, GameRules gameRules, GameState gameState){
        if (potMove.moveTakenPiece() && gameRules.playerCanTakeNextPawn(potMove.getLastTargetFieldNo(), gameState, potMove.getMovingPlayerColor())){
            potMove.setChainedMove(true);
            potMove.setMoveTakenPiece(true);
        } else {
            gameRules.setPawnPromotion(potMove, gameState, true);
        }
    }

    public void setAiCore(GameRules gameRules, GameState gameState, Move lastMove){
        this.gameRules = gameRules;
        this.gameState = gameState;
        this.lastMove = lastMove;
    }

    @Override
    public void run() {
        askAiMove(gameRules, gameState, lastMove);
    }
}
