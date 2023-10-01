module Yatzy {
    requires javafx.controls;
    requires javafx.fxml;


    opens yatzy to javafx.fxml;
    exports yatzy;
}

