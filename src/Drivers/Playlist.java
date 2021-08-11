package Drivers;

import java.io.File;
import java.util.ArrayList;

public class Playlist {
    private ArrayList<Song> playList;
    private int playlistIndex = 0;
    private String playListPath;
    private String playlistName;

// Constructor for when a playlist is null

    public Playlist(){

    }

    public Playlist(File playlistFile){
        try{
            playListPath = playlistFile.getAbsolutePath();
            playlistName = playlistFile.getName();
            playList = new ArrayList<Song>();
            File[] musicList = playlistFile.listFiles();

            for (File f: musicList){
                String path = f.getAbsolutePath();
                Song song = new Song(f);
                if (path.endsWith(".mp3") || path.endsWith(".mp4") || path.endsWith(".wav") || path.endsWith(".m4a")){
                    playList.add(song);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("An error occurred, file could not be read in!");
        }
    }

    public String getPlayListPath() {
        return playListPath;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public ArrayList<Song> getPlaylist() {
        return playList;
    }

    public String getSongName() {
        if (verifyIndex()){
            return playList.get(playlistIndex).getTitle();
        }else {
            return "";
        }

    }

    public String getSongPath() {
       if (verifyIndex()){
           return playList.get(playlistIndex).getFilePath();
       }else {
           return "";
       }
    }

    public void changePlaylistIndex(int change) {
        playlistIndex += change;
    }

    public boolean verifyIndex() {
        return (playlistIndex < playList.size() && playlistIndex >= 0);
    }
}
