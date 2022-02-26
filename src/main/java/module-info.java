module com.polishdraughts.polishdraughts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.polishdraughts to javafx.fxml;
    exports com.polishdraughts;
    exports com.polishdraughts.model;
    exports com.polishdraughts.controller;
    exports com.polishdraughts.view.windowView;
    opens com.polishdraughts.view.windowView to javafx.fxml;
}