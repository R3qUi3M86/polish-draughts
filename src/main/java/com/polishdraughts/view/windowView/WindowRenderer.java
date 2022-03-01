package com.polishdraughts.view.windowView;

import com.polishdraughts.PolishDraughtsWindowed;
import com.polishdraughts.controller.GameController;
import com.polishdraughts.controller.GameRules.GameResults;
import com.polishdraughts.controller.ViewController;
import com.polishdraughts.model.*;
import com.polishdraughts.view.Renderer;
import javafx.scene.image.Image ;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class WindowRenderer implements Renderer {
    Scene menuScene;
    Scene gameScene;
    Image whitePawnImg;
    Image whiteQueenImg;
    Image blackPawnImg;
    Image blackQueenImg;
    WindowMenuController windowMenuController = new WindowMenuController();
    WindowGameController windowGameController;

    public WindowRenderer(){
        URL whitePawnURL = PolishDraughtsWindowed.class.getResource("pictures/white_pawn.png");
        URL whiteQueenURL = PolishDraughtsWindowed.class.getResource("pictures/white_queen.png");
        URL blackPawnURL = PolishDraughtsWindowed.class.getResource("pictures/black_pawn.png");
        URL blackQueenURL = PolishDraughtsWindowed.class.getResource("pictures/black_queen.png");

        assert whitePawnURL != null;
        whitePawnImg = new Image(whitePawnURL.toExternalForm());
        assert whiteQueenURL != null;
        whiteQueenImg = new Image(whiteQueenURL.toExternalForm());
        assert blackPawnURL != null;
        blackPawnImg = new Image(blackPawnURL.toExternalForm());
        assert blackQueenURL != null;
        blackQueenImg = new Image(blackQueenURL.toExternalForm());
    }

    public void renderMainMenu(ArrayList<String> menuOptions, boolean invalidInput) {
        Stage window = PolishDraughtsWindowed.getWindow();

        if (menuScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(PolishDraughtsWindowed.class.getResource("menu.fxml"));
            try {
                this.menuScene = new Scene(fxmlLoader.load());
            } catch (IOException e){
                System.err.println("Could not load resource!");
            }

            VBox menuContainer = (VBox) menuScene.lookup("#menuContainer");
            menuContainer.setSpacing(50);

            int i = 1;
            for (String menuOption : menuOptions) {
                Button button = new Button(menuOption.substring(3));
                button.setId(String.valueOf(i));
                button.setPrefWidth(400);
                button.setPrefHeight(50);
                menuContainer.getChildren().add(button);
                button.setOnAction(e -> windowMenuController.onMenuBtnClick(button.getId()));
                i++;
            }
        }
        window.setScene(menuScene);
    }

    public void renderGameState(GameState gameState, boolean validInput) {
        Stage window = PolishDraughtsWindowed.getWindow();
        if (gameScene == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(PolishDraughtsWindowed.class.getResource("game.fxml"));
            try {
                gameScene = new Scene(fxmlLoader.load());
                windowGameController = fxmlLoader.getController();
                window.setScene(gameScene);
            } catch (IOException e) {
                System.err.println("Could not load resource!");
            }
        }
        GridPane boardGrid = (GridPane) gameScene.lookup("#boardGrid");
        boardGrid.getChildren().clear();
        Board board = gameState.getGameBoard();
        String[][] boardWithPieces = board.getBoardWithPieces();
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                addFieldDropZone(boardGrid, row, col, board.getFieldNo(new int[]{row,col}));
                switch (boardWithPieces[row][col]) {
                    case "B" -> addPieceToBoard(boardGrid, row, col, board.getFieldNo(new int[]{row,col}), blackPawnImg);
                    case "BK" -> addPieceToBoard(boardGrid, row, col, board.getFieldNo(new int[]{row,col}), blackQueenImg);
                    case "W" -> addPieceToBoard(boardGrid, row, col, board.getFieldNo(new int[]{row,col}), whitePawnImg);
                    case "WK" -> addPieceToBoard(boardGrid, row, col, board.getFieldNo(new int[]{row,col}), whiteQueenImg);
                }
            }
        }
    }

    private void addPieceToBoard(GridPane boardGrid, int row, int col, int fieldNo, Image pieceImg) {
        PieceColor movingPlayerColor = GameController.getInstance().getGameRules().getCurrentPlayerColor();
        PlayerType playerType = GameController.getInstance().getPlayers().get(movingPlayerColor);
        ImageView piece = new ImageView();
        piece.setImage(pieceImg);
        piece.setFitWidth(70);
        piece.setFitHeight(70);
        piece.setId(String.valueOf(fieldNo));
        boardGrid.add(piece, col, row);
        if (playerType != PlayerType.COMPUTER) {
            if ((movingPlayerColor == PieceColor.WHITE && (pieceImg.equals(whitePawnImg) || pieceImg.equals(whiteQueenImg))) ||
                (movingPlayerColor == PieceColor.BLACK && (pieceImg.equals(blackPawnImg) || pieceImg.equals(blackQueenImg)))) {
                windowGameController.makeDraggable(piece);
            }
        }
    }

    private void addFieldDropZone(GridPane boardGrid, int row, int col, int fieldNo) {
        Region region = new Region();
        region.setMaxSize(70,70);
        region.setPrefSize(70,70);
        region.setId(String.valueOf(fieldNo));
        boardGrid.add(region, col, row);
        windowGameController.addDropzoneEnterListener(region);
    }

    public void askForMoveInput(Move move) {
        windowGameController.setPreviousMove(move);
        String info = "";
        if (move.isNewMove() && move.getOpponentMove() != null){
            info = getPreviousPlayerMove(move.getOpponentMove());
        } else if (!move.isValid()){
            info = getInvalidMoveInfo(move);
        } else if (move.isChainedMove()){
            info = getPreviousPlayerMove(move);
        }
        if (move.getMovingPlayerColor() == PieceColor.WHITE){
            info += "\nWhite to move...";
        } else {
            info +="\nBlack to move....";
        }
        windowGameController.setInfoPanelText(info);
    }

    private String getPreviousPlayerMove(Move move){
        String info;
        if (move.getMovingPlayerColor() == PieceColor.WHITE){
            info = "White ";
        } else {
            info = "Black ";
        }
        if (move.moveTakenPiece()){
            info += "takes: \n";
        } else {
            info += "moves: ";
        }
        info += move.getLastMove();
        return info;
    }

    private String getInvalidMoveInfo(Move move){
        String info = "";
        switch (move.getInvalidType()){
            case WRONG_INPUT_FORMAT -> info = "You have provided \nincorrect move!";
            case INVALID_TARGET_SQUARE -> info = "Target square is invalid!";
            case NEEDS_TO_TAKE -> info = "You must play \na taking move!";
            case NOT_MOVED_WITH_SAME_PIECE -> info= "You must move with \nthe same piece! \nLast move to: "
                    + move.getLastTargetFieldNo();
        }
        return info;
    }

    public void renderFinalScore(GameResults gameResult) {
        gameScene.lookup("#tieButton").setDisable(true);
        gameScene.lookup("#tieButton").setVisible(false);
        switch (gameResult){
            case TIE -> windowGameController.setInfoPanelText("It's a TIE!");
            case WHITE_WINS -> windowGameController.setInfoPanelText("WHITE Player wins!");
            case BLACK_WINS -> windowGameController.setInfoPanelText("BLACK Player wins!");
        }
    }

    public void pressAnyKeyPromptForBackToMenu() {
        Button backToMenuBtn = (Button) gameScene.lookup("#backToMenu");
        backToMenuBtn.setDisable(false);
        backToMenuBtn.setVisible(true);
        backToMenuBtn.setOnAction(e -> {
            gameScene = null;
            ViewController.getInstance().displayMainMenu();
        });
    }
}
