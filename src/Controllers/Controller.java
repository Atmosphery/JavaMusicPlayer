package Controllers;

import Drivers.Playlist;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Pane rootpane;

    private MediaPlayer player;
    private Media media;
    private Playlist playlist;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO put something here
    }

    public void play() {

    }
}
