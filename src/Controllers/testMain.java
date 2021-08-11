package Controllers;

import Drivers.Playlist;
import Drivers.PlaylistController;

import java.util.ArrayList;

public class testMain {
    public static void main(String[] args) {

        PlaylistController.updateRootPlaylist();
        Playlist p1 = PlaylistController.readSinglePlaylist("Playlist2");
        Playlist p2 = PlaylistController.createNewPlaylist("TheBestList");
        ArrayList<Playlist> f = PlaylistController.getRootPlaylist();
        for(Playlist p: f) {
            System.out.println(p.getPlayListPath());
        }
    }
}
