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
    private Object obj = new Object();

    public createMusicPlayer(File song){
        media = new Media(song.toURI().toString());
        player = new MediaPlayer(media);
        readMetaData(player);

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


    }

    private void readMetaData(MediaPlayer player) {
        final MediaPlayer mp = player;
        mp.setOnReady(new Runnable() {
            @Override
            public void run() {
                artist = (String) mp.getMedia().getMetadata().get("artist");
                title = (String) mp.getMedia().getMetadata().get("title");
                album = (String) mp.getMedia().getMetadata().get("album");
                synchronized (obj) {//this is required since mp.setOnReady creates a new thread and our loopp  in the main thread
                    obj.notify();// the loop has to wait unitl we are able to get the media metadata thats why use .wait() and .notify() to synce the two threads(main thread and MediaPlayer thread)
                }
            }
        });
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
