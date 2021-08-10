package Drivers;

import java.io.File;

public class Playlist {
    readWrite rw = new readWrite();
    File playList = null;

    public void createNewPlaylist(String playlistName) {
       File newPlayList = new File("Playlists\\" + playlistName);
       newPlayList.mkdirs();
       newPlayList = playList;
    }
}
