package Controllers;

import Drivers.Playlist;
import Drivers.PlaylistController;

public class testMain {
    public static void main(String[] args) {

        PlaylistController.updateRootPlaylist();
        Playlist p1 = PlaylistController.readSinglePlaylist("Playlist4");
        System.out.println(p1.getSongName());
        p1.changePlaylistIndex(1);
        System.out.println(p1.getSongName());



    }
}
