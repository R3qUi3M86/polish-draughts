module com.polishdraughts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.polishdraughts to javafx.fxml;
    exports com.polishdraughts;
    opens com.polishdraughts.view.windowView to javafx.fxml;
}