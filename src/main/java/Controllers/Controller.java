package Controllers;

import Drivers.Playlist;
import Drivers.PlaylistController;
import Drivers.Song;
import Drivers.createMusicPlayer;
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
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.DirectoryChooser;
import javafx.util.Duration;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    createMusicPlayer metaData;

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
    @FXML
    private TextArea metaDisplay;
    @FXML
    private Slider sliderSeeker;
    @FXML
    private Button prevButton;
    @FXML
    private Button nextButton;

    int repeatAmt = 0;
    boolean isPaused = false;
    File currentSong;
    int playListIndex = 0;
    int songIndex = 0;
    boolean changing = false;
    int changedSlider = 0;
    String saveSerialized = "playlists.txt";
    Playlist currentPlaylist;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Starting up!");
        pausePlay.setText("||");
        //PlaylistController.importAllPlaylists("Playlists");
        //PlaylistController.saveRootPlaylist(savePath);
        PlaylistController.loadRootPlaylist(saveSerialized);

        loadPlaylistItems();

        playlistView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onClickTableItem(mouseEvent);
            }
        });

        songView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                onClickSongItem(mouseEvent);
            }
        });

        ArrayList<Playlist> p = PlaylistController.getRootPlaylist();
        ArrayList<Song> s = p.get(playListIndex).getSongs();
        currentSong = s.get(songIndex).getSong();
        metaData = new createMusicPlayer(currentSong);
        String metaString = "Title: " + metaData.getTitle() + "\n" +
                "Artist: " + metaData.getArtist() + "\n" +
                "Album: " + metaData.getAlbum() + "\n";
        metaDisplay.setText(metaString);

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
                    //Maybe consider this being separate from the end of the song.
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
        loadMusicSeeker();
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
                    currentPlaylist = p;
                    songs = p.getSongs();
                    for (Song s: songs){
                        songView.getItems().add(s.getTitle());
                    }
                }
            }
        }
    }

    @FXML
    public void onClickSongItem(MouseEvent mouse) {
        isPaused = true;
        pausePlay.setText("||");
        mediaPlayer.pause();
        String name = songView.getSelectionModel().getSelectedItem();
        if(name != null) {
            ArrayList<Song> songs = currentPlaylist.getSongs();
            for (Song s: songs){
                if(s.getTitle().equals(name)) {
                    currentSong = s.getSong();
                    media = new Media(currentSong.toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaView.setMediaPlayer(mediaPlayer);
                    loadMusicSeeker();
                }
            }
        }
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
        File playlistPath = new File(PlaylistController.getRootPlaylistPath());
        File choiceFile = null;
        DirectoryChooser dc = new DirectoryChooser();
        boolean loop = true;
        while (loop) {
            if (playlistPath != null) {
                loop = false;
                dc.setInitialDirectory(playlistPath);
            }else {
                playlistPath.mkdirs();
            }

        }
        dc.setTitle("Create or Select Playlist");
        try {
            choiceFile = dc.showDialog(null);
            if (choiceFile != null){
                PlaylistController.importPlaylist(choiceFile);
                PlaylistController.saveRootPlaylist(saveSerialized);
            }
            loadPlaylistItems();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No File was Selected");
        }
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
    void changeToNextSong(ActionEvent event) {
        if(!((playListIndex + 1) > (currentPlaylist.getSongs().size() - 1))){
            playListIndex ++;
            currentSong = currentPlaylist.getSongs().get(playListIndex).getSong();
            if(currentSong.getPath().contains(" ")){
                String formattedPath = currentSong.toURI().getPath();
                formattedPath = formattedPath.replaceAll(" ", "%20");

                //Do NOT remove file:// from the beginning of this - Media expects a url to a resource, not a direct file path. The format here is required for it to
                //function.
                media = new Media("file://" + formattedPath);
            }else{
                media = new Media("file://" + currentSong.toURI().getPath());
            }
        }

        mediaPlayer.stop();
        mediaPlayer.dispose();
        mediaPlayer = new MediaPlayer(media);
        loadMusicSeeker();
        pausePlay.setText(">");
        mediaPlayer.play();
    }

    @FXML
    void changeToPrevSong(ActionEvent event) {

        if(!((playListIndex - 1) < 0)){
            playListIndex--;
            currentSong = currentPlaylist.getSongs().get(playListIndex).getSong();
            if(currentSong.getPath().contains(" ")){
                String formattedPath = currentSong.toURI().getPath();
                formattedPath = formattedPath.replaceAll(" ", "%20");

                //Do NOT remove file:// from the beginning of this - Media expects a url to a resource, not a direct file path. The format here is required for it to
                //function.
                media = new Media("file://" + formattedPath);
            }else{
                media = new Media("file://" + currentSong.toURI().getPath());
            }
        }
        mediaPlayer.stop();

        mediaPlayer.dispose();

        //Do NOT remove file:// from the beginning of this - Media expects a url to a resource, not a direct file path. The format here is required for it to
        //function.

        mediaPlayer = new MediaPlayer(media);
        loadMusicSeeker();
        pausePlay.setText(">");
        mediaPlayer.play();

    }

    public void loadPlaylistItems() {
        ArrayList<Playlist> playlists = PlaylistController.getRootPlaylist();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Playlist p : playlists){
            list.add(p.getPlaylistName());
        }
        playlistView.setItems(list);
    }

    public void loadMusicSeeker() {
        mediaPlayer.setOnReady(() -> {
            sliderSeeker.setMax(0.0);
            sliderSeeker.setMax(mediaPlayer.getTotalDuration().toSeconds());
            // know when the slider value is actually changing
            // and only seek when it has stopped changing
            sliderSeeker.valueChangingProperty().addListener(
                    (observableValue, aBoolean, t1) -> {
                        changing = t1;
                        if (!changing) {
                            mediaPlayer.seek(Duration.seconds(changedSlider));
                        }
                    });
            // Save the slider value when changing, but don't seek yet
            // When slider not changing check the difference between
            // prev and current values.
            // For player initiated changes the delta will be small,
            // but for mouse clicks on the slider the delta will be much
            // larger, and in this case do a seek.
            sliderSeeker.valueProperty().addListener(
                    (observableValue, number, t1) -> {
                        if (changing) {
                            changedSlider = t1.intValue();
                        } else {
                            int change = Math.abs(t1.intValue() - number.intValue());
                            if (change > 1) {
                                mediaPlayer.seek(Duration.seconds(t1.intValue()));
                            }
                        }
                    });
            // set slider on player media progress,
            // but only when slider is not changing
            mediaPlayer.currentTimeProperty().addListener(
                    (observableValue, duration, t1) -> {
                        if (!changing) {
                            sliderSeeker.setValue(t1.toSeconds());
                        }
                    });
        });
    }

}