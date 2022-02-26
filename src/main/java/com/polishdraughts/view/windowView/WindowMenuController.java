package com.polishdraughts.view.windowView;

import com.polishdraughts.controller.GameController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WindowMenuController {
    public void onMenuBtnClick(String option){
        GameController.getInstance().initSelectedMenuOption(Integer.parseInt(option));
    }
}