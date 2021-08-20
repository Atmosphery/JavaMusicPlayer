module com.example.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires de.jensd.fx.glyphs.fontawesome;


    opens Controllers to javafx.fxml;
    opens Drivers to java.base;
    exports Controllers;
}