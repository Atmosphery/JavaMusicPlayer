package Controllers;

import Drivers.Playlist;
import Drivers.PlaylistController;
import Drivers.Song;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class testMain {
    public static void main(String[] args) {


        ArrayList<Playlist> list = PlaylistController.getRootPlaylist();
        for (Playlist p: list){
            ArrayList<Song> song= p.getSongs();
            for (Song s: song) {
                System.out.println(s.getTitle());
            }
        }


    }
}
