package com.polishdraughts.view;

import com.polishdraughts.controller.GameRules.GameResults;
import com.polishdraughts.model.GameState;
import com.polishdraughts.model.Move;

import java.util.ArrayList;

public interface Renderer {
    enum RenderModes {
        CONSOLE, WINDOWED
    }
    void renderMainMenu(ArrayList<String> menuOptions, boolean invalidInput);
    void renderGameState(GameState gameState, boolean invalidInput);
    void askForMoveInput(Move previousMove);
    void renderFinalScore(GameResults gameResult);
}
