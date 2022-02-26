package com.polishdraughts.model;

import com.polishdraughts.controller.GameRules;

public class AICore {
    private PieceColor aiColor;

    public AICore(PieceColor aiColor){
        this.aiColor = aiColor;
    }

    public void askAiMove(GameRules gameRules, GameState gameState){
    }
}
