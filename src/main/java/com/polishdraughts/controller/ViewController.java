package com.polishdraughts.polishdraughts.controller;

import com.polishdraughts.polishdraughts.model.MainMenu;
import com.polishdraughts.polishdraughts.view.Renderer;
import com.polishdraughts.polishdraughts.view.consoleView.ConsoleRenderer;
import com.polishdraughts.polishdraughts.view.windowView.WindowRenderer;

import java.util.ArrayList;


public abstract class ViewController {
    private static RenderModes renderMode;
    private static Renderer renderer;

    private static void setRenderMode(RenderModes renderMode) {
        ViewController.renderMode = renderMode;
    }

    private static RenderModes getRenderMode(){
        return renderMode;
    }

    public static void setToConsoleDisplayMode(){
        ViewController.setRenderMode(RenderModes.CONSOLE);
        ViewController.renderer = new ConsoleRenderer();
    }
    public static void setToWindowedDisplayMode(){
        ViewController.setRenderMode(RenderModes.WINDOWED);
        ViewController.renderer = new WindowRenderer();
    }

    public static void displayMainMenu(){
        ArrayList<String> menuOptions = MainMenu.getMenuOptions();
        renderer.renderMainMenu(menuOptions, false);
    }
}
