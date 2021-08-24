package Drivers;

import java.io.File;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Playlist implements Serializable {
    @Serial
    private static final long serialVersionUID = 7618249259810837353L;
    private ArrayList<Song> playList;
    private int playlistIndex;
    private String playlistName;
    private File playlistFile;


    //Constructor for creating an empty Playlist object
    public Playlist(String playlistName){
        setPlayList(new ArrayList<>());
        setPlaylistName(playlistName);
        playlistFile = new File("Playlists\\" + playlistName);
    }

    //Constructor for importing a directory playlist into a Playlist object
    public Playlist(File playlistFile, int InIndex){
        try{
            this.playlistFile = playlistFile;
            this.playlistIndex = InIndex;
            this.playlistName = playlistFile.getName();
            playList = new ArrayList<Song>();
            File[] musicList = playlistFile.listFiles();
            int index = 0;
            for (File f: musicList){
                Song song = new Song(f, index);
                index++;
                String path = song.getFilePath();
                if (path.endsWith(".mp3") || path.endsWith(".mp4") || path.endsWith(".wav") || path.endsWith(".m4a")){
                    playList.add(song);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("An error occurred, file could not be read in!");
        }
    }

    //List all songs withing the playlist as a string
    public String listAllSongs() {
        StringBuilder sb = new StringBuilder();
        for(Song s: playList){
            sb.append(s.getTitle());
            sb.append("\n");
        }
        return sb.toString();
    }

    // Adds a single song object to playlist array
    public void addSongToPlaylist(Song song) {
        playList.add(song);
    }

    // Returns name of current song
    public String getSongName() {
        if (verifyIndex()){
            return playList.get(playlistIndex).getTitle();
        }else {
            return "Song not found";
        }

    }

    public int getPlaylistIndex() {
        return playlistIndex;
    }

    // Returns path of current song
    public String getSongPath() {
        if (verifyIndex()){
            return playList.get(playlistIndex).getFilePath();
        }else {
            return "";
        }
    }

    public File getPlaylistFile() {
        return playlistFile;
    }

    public void setPlayList(ArrayList<Song> playList) {
        this.playList = playList;
    }

    public void setPlaylistIndex(int playlistIndex) {
        this.playlistIndex = playlistIndex;
    }

    public void setPlaylistName(String playlistName) {

        this.playlistName = playlistName;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public ArrayList<Song> getPlayList() {
        return playList;
    }

    public ArrayList<Song> getSongs() {
        return playList;
    }

    public void setSongs(ArrayList<Song> input){
        this.playList = input;
    }

    public void changePlaylistIndex(int change) {
        playlistIndex += change;
    }

    public boolean verifyIndex() {
        return (playlistIndex < playList.size() && playlistIndex >= 0);
    }
}
