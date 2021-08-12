package Controllers;

import Drivers.Playlist;
import Drivers.PlaylistController;
import Drivers.Song;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private MediaView mediaView;
    private MediaPlayer mediaPlayer;
    private Media media;

    Stage window;
    @FXML
    private Button pausePlay;
    @FXML
    private Button repeat;
    @FXML
    private Slider sliderVolume;
    @FXML
    private TableView<Playlist> playlistTable;
    @FXML
    private TableView<Song> songTable;




    int repeatAmt = 0;
    boolean isPaused = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Starting up!");
        //Playlist Column
        TableColumn<Playlist, String> playlists = new TableColumn("Playlists");
        playlistTable.getColumns().add(playlists);
        ObservableList<Playlist> playlistData = getPlaylist();
        playlists.setCellValueFactory(new PropertyValueFactory<Playlist, String>("playlistName"));
        playlistTable.setItems(playlistData);









        media = new Media(new File("C:\\Users\\abecc\\Documents\\GitHub\\JavaMusicPlayer\\Playlists\\Playlist1\\Powersurge15.mp3").toURI().toString());
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

    public ObservableList<Playlist> getPlaylist() {
        ObservableList<Playlist> playlistsOBL = FXCollections.observableArrayList();
        ArrayList<Playlist> playlists = PlaylistController.getRootPlaylist();
        for (Playlist p : playlists){
            playlistsOBL.add(p);
        }
        return playlistsOBL;
    }

    public ObservableList<Song> getSongs(Playlist targetPlaylist) {
        ObservableList<Song> songsOBL = FXCollections.observableArrayList();
        ArrayList<Playlist> playlists = PlaylistController.getRootPlaylist();
        int index = playlists.indexOf(targetPlaylist);
        ArrayList<Song> songs = playlists.get(index).getSongs();
        songsOBL.addAll(songs);
        return songsOBL;
    }

    @FXML
    protected void onClickTableItem() {

//        TableColumn<Song, String> songs = new TableColumn("Songs");
//        songTable.getColumns().add(songs);

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