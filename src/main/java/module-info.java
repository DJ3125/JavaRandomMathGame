module com.example.randommathgame_8_27_23 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.randommathgame_8_27_23 to javafx.fxml;
    exports com.example.randommathgame_8_27_23;
}