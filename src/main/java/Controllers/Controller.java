package Controllers;

import Drivers.Playlist;
import Drivers.PlaylistController;
import Drivers.Song;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
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


    @FXML
    private Button pausePlay;
    @FXML
    private Button repeat;
    @FXML
    private Slider sliderVolume;
    @FXML
    private ListView<String> playlistView;
    @FXML
    private ListView<String> songView;

    int repeatAmt = 0;
    boolean isPaused = false;
    File currentSong;
    int playListIndex = 0;
    int songIndex = 0;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Starting up!");
        //Playlist Column


        PlaylistController.loadRootPlaylist("playlists.txt");
        PlaylistController.importPlaylist("Playlists\\Playlist2");
        //PlaylistController.saveRootPlaylist("playlists.txt");

        ArrayList<Playlist> playlists = PlaylistController.getRootPlaylist();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Playlist p : playlists){
            list.add(p.getPlaylistName());
        }
        playlistView.setItems(list);
        playlistView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                onClickTableItem(mouseEvent);
            }
        });

        ArrayList<Playlist> p = PlaylistController.getRootPlaylist();
        ArrayList<Song> s = p.get(playListIndex).getSongs();
        currentSong = s.get(songIndex).getSong();

        media = new Media(currentSong.toURI().toString());
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

    @FXML
    public void onClickTableItem(MouseEvent mouse) {
        songView.getItems().clear();
        String name = playlistView.getSelectionModel().getSelectedItem();
            if (name != null){
                ArrayList<Playlist> playlist = PlaylistController.getRootPlaylist();
                ArrayList<Song> songs = new ArrayList<>();

                for (Playlist p: playlist){
                    if (p.getPlaylistName().equals(name)){

                        songs = p.getSongs();
                        for (Song s: songs){
                            songView.getItems().add(s.getTitle());
                        }
                    }
                }
            }

    }
}