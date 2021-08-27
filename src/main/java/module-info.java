module com.example.musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires de.jensd.fx.glyphs.fontawesome;
    requires javafx.swing;
    requires jaudiotagger;


    opens Controllers to javafx.fxml;
    opens Drivers to java.base;
    exports Controllers;
}