package Controllers;

import Drivers.Playlist;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;

    @FXML
    private Button pausePlay;
    @FXML
    private Button repeat;
    @FXML
    private Slider sliderVolume;

    int repeatAmt = 0;
    boolean isPaused = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Starting up!");
        media = new Media(new File("C:\\Users\\Sina\\IdeaProjects\\untitled\\CalculatorUnitTests\\MusicPlayer\\Playlists\\Test\\Powersurge15.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaView.setMediaPlayer(mediaPlayer);
        mediaPlayer.setOnEndOfMedia(() -> {
            switch (repeatAmt) {
                case 1:
                    //Repeat Song
                    mediaPlayer.seek(Duration.ZERO);
                    break;
                case 2:
                    //Repeat Arraylist
                    System.out.println("Playlist Check");
                    break;
                default:
                    //No repeat

            }
        });
        sliderVolume.setValue(mediaPlayer.getVolume() * 100);
        sliderVolume.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                mediaPlayer.setVolume(sliderVolume.getValue() / 100);
            }
        });
    }

    @FXML
    protected void onPauseButtonPressed() {
        isPaused = !isPaused;
        if (isPaused) {
            mediaPlayer.pause();
            pausePlay.setText("||");
        }
        else {
            mediaPlayer.play();
            pausePlay.setText(">");
        }
    }

    @FXML
    protected void onFileButtonPressed() {
        FileChooser fc = new FileChooser();
        fc.setTitle("File Choosing");
        File f = fc.showOpenDialog(null);
        System.out.println(f.getPath());
    }

    @FXML
    public void onRepeatButtonPressed(ActionEvent actionEvent) {
        repeatAmt++;
        switch (repeatAmt) {
            case 1:
                //Repeat Song
                repeat.setText("R1");
                break;
            case 2:
                //Repeat Arraylist
                repeat.setText("R2");
                break;
            default:
                //No repeat
                repeat.setText("R3");
                repeatAmt = 0;
        }
    }
}