module JavaMusciPlayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires javafx.base;


    opens Controllers to javafx.fxml;
    opens Drivers to javafx.media, java.base;
    exports Controllers;
    exports Drivers;

}