package com.polishdraughts.view.windowView;

import com.polishdraughts.controller.GameController;

public class WindowMenuController {
    public void onMenuBtnClick(String option){
        GameController.getInstance().initSelectedMenuOption(Integer.parseInt(option));
    }
}