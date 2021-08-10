package Drivers;

import java.io.File;
import java.util.ArrayList;

public class Playlist {
    readWrite rw = new readWrite();
    private ArrayList<File> playList = null;
    private int playlistIndex = 0;
    private String songName;


    public Playlist(File fileDirectory){
        try{
            playList
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void createNewPlaylist(String playlistName, File fileDirectory) {
       File newPlayList = new File("Playlists\\" + playlistName);
       newPlayList.mkdirs();
       File[] files = fileDirectory.listFiles();

    }
}
