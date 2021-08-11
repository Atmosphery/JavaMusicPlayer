package Drivers;

import java.io.File;
import java.util.ArrayList;

public class PlaylistController {
    static ArrayList<Playlist> rootPlaylist = new ArrayList<>();

    //updates the rootPlaylist to account for the addition of any new playlists
    public static void updatePlaylistList() {
        File[] rootPlaylist = new File("Playlists").listFiles();
        for (File f: rootPlaylist){
            PlaylistController.rootPlaylist.add(new Playlist(f));
        }
    }

    //Creates a playlist and the required directory as well as returning the new Playlist Object
    public static Playlist createNewPlaylist(String playlistName) {
        File newFile = new File("Playlists\\" + playlistName);
        newFile.mkdirs();
        updatePlaylistList();
        return new Playlist(newFile);
    }

    //Lists all of the playlists in the rootPlaylist directory
    public static File[] readAndListPlayLists() {
        File rootPlaylist = new File("Playlists");
        return rootPlaylist.listFiles();
    }

    //returns a single Playlist by passing in a playlist name
    public static Playlist readSinglePlaylist(String nameOfPlayList){
        File[] playlists = new File("Playlists").listFiles();
        for (File f: playlists){
            if (f.getName().equals(nameOfPlayList)){
                return new Playlist(f);
            }
        }
        return null;
    }

    //deletes a playlist, no matter how many levels of sub-folders it contains
    public static boolean deletePlaylist(File playlistDelete){
        File[] playlistContents = playlistDelete.listFiles();
        if (playlistContents != null) {
            for (File f: playlistContents){
                deletePlaylist(f);
            }
        }
        return playlistDelete.delete();
    }


}
