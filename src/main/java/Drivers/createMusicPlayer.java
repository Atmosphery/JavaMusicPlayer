package Drivers;

import javafx.collections.MapChangeListener;
import javafx.scene.image.Image;
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
    private Image albumArt;


    public createMusicPlayer(File song){

        if(song.getPath().contains(" ")){
            String formattedPath = song.toURI().getPath();
            formattedPath = formattedPath.replaceAll(" ", "%20");

            //Do NOT remove file:// from the beginning of this - Media expects a url to a resource, not a direct file path. The format here is required for it to
            //function.
            currentSong = new File(formattedPath);
            media = new Media("file://" + formattedPath);
        }else{
            currentSong = song;
            media = new Media("file://" + song.toURI().getPath());
        }

        media = new Media(song.toURI().toString());

        media.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object > change) -> {
            if(change.wasAdded()){
                if("artist".equals(change.getKey())){
                    artist = change.getValueAdded().toString();
                }else if("title".equals(change.getKey())){
                    title = change.getValueAdded().toString();
                }else if("album".equals(change.getKey())){
                    album = change.getValueAdded().toString();
                }else if("image".equals(change.getKey())){
                    albumArt = (Image) change.getValueAdded();
                }
            }
        });
        player = new MediaPlayer(media);
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

    public void changeMedia(Media media){
        player.stop();
        player.dispose();
        this.media = media;

        this.media.getMetadata().addListener((MapChangeListener.Change<? extends String, ? extends Object > change) -> {
            if(change.wasAdded()){
                if("artist".equals(change.getKey())){
                    artist = change.getValueAdded().toString();
                }else if("title".equals(change.getKey())){
                    title = change.getValueAdded().toString();
                }else if("album".equals(change.getKey())){
                    album = change.getValueAdded().toString();
                }else if("image".equals(change.getKey())){
                    albumArt = (Image) change.getValueAdded();
                }
            }

        });

        player = new MediaPlayer(this.media);
    }



    public MediaPlayer getPlayer() {
        return player;
    }

    public void setPlayer(MediaPlayer player) {
        this.player = player;
    }

    public Image getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(Image albumArt) {
        this.albumArt = albumArt;
    }
}
