package Drivers;

import javafx.collections.MapChangeListener;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;


public class createMusicPlayer {
    private File currentSong;
    private String title;
    private String artist;
    private String album;
    private Media media;
    private MediaPlayer player;

    public createMusicPlayer(File song){
        media = new Media(song.toURI().toString());

//        media.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object > change) -> {
//            if(change.wasAdded()){
//                if("artist".equals(change.getKey())){
//                    artist = change.getValueAdded().toString();
//                }else if("title".equals(change.getKey())){
//                    title = change.getValueAdded().toString();
//                }else if("album".equals(change.getKey())){
//                    album = change.getValueAdded().toString();
//                }
//            }
//        });
        media.getMetadata().addListener(new MapChangeListener<String, Object>(){
            @Override
            public void onChanged(Change<? extends String, ? extends Object> change) {
                if(change.wasAdded()) {
                    handleMetadata(change.getKey(), change.getValueAdded());
                }
            }
        });

        player = new MediaPlayer(media);
    }

    public void handleMetadata(String key, Object value){
        if (key.equals("title")){
            title = value.toString();
        }
        if (key.equals("artist")){
            artist = value.toString();
        }
    }

    public File getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(File currentSong) {
        this.currentSong = currentSong;
    }

    public void changeSong(File song){
        player.dispose();

        media = new Media(song.toURI().toString());

        player = new MediaPlayer(media);
    }

    public void play(){
        player.play();
    }

    public void pause(){
        player.pause();
    }

    public void stop(){
        player.stop();
    }

    public void dropSong(){
        player.dispose();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }
}
