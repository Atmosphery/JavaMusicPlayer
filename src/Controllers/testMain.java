package Controllers;

import Drivers.Playlist;
import Drivers.PlaylistController;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class testMain {
    public static void main(String[] args) {

        PlaylistController.updatePlaylistList();
        Playlist p1 = PlaylistController.readSinglePlaylist("Playlist1");
        System.out.println(p1.getSongName());
        p1.changePlaylistIndex(1);
        System.out.println(p1.getSongName());



    }
}
