package com.polishdraughts.controller;

import com.polishdraughts.controller.GameRules.GameResults;
import com.polishdraughts.model.GameState;
import com.polishdraughts.model.MainMenu;
import com.polishdraughts.model.Move;
import com.polishdraughts.view.Renderer;
import com.polishdraughts.view.Renderer.RenderModes;
import com.polishdraughts.view.consoleView.ConsoleRenderer;
import com.polishdraughts.view.windowView.WindowRenderer;

import java.util.ArrayList;

public final class ViewController {
    private static ViewController viewController;
    private RenderModes renderMode;
    private Renderer renderer;

    private ViewController(){}

    public static ViewController getInstance(){
        if (viewController == null){
            viewController = new ViewController();
        }
        return viewController;
    }

    public RenderModes getRenderMode(){
        return renderMode;
    }

    public void setToConsoleDisplayMode(){
        renderMode = RenderModes.CONSOLE;
        renderer = new ConsoleRenderer();
    }
    public void setToWindowedDisplayMode(){
        renderMode = RenderModes.WINDOWED;
        renderer = new WindowRenderer();
    }

    public void displayMainMenu(){
        ArrayList<String> menuOptions = MainMenu.getMenuOptions();
        renderer.renderMainMenu(menuOptions, false);
    }

    public void displayGameState(GameState gameState){
        renderer.renderGameState(gameState, false);
    }

    public void askForMove(Move previousMove){
        renderer.askForMoveInput(previousMove);
    }

    public void displayEndGameStatus(GameResults gameResult, GameState gameState){
        renderer.renderGameState(gameState, false);
        renderer.renderFinalScore(gameResult);
        renderer.pressAnyKeyPromptForBackToMenu();
    }
}
