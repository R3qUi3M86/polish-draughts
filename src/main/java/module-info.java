module com.polishdraughts.polishdraughts {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.polishdraughts to javafx.fxml;
    exports com.polishdraughts;
}