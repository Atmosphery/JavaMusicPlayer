package Drivers;

import java.io.File;
import java.util.ArrayList;

public class PlaylistController {
    static ArrayList<Playlist> rootPlaylist = new ArrayList<>();

    public static ArrayList<Playlist> getRootPlaylist() {
        return rootPlaylist;
    }

    public static void setRootPlaylist(ArrayList<Playlist> rootPlaylist) {
        PlaylistController.rootPlaylist = rootPlaylist;
    }

    public static void saveRootPlaylist(ArrayList<Playlist> playlists) {
        readWrite.writeToDirectory(playlists, "Playlists\\playlists.txt");
    }

    public static void loadPlaylistObject() {
        readWrite.deserializePlaylist("Playlists\\playlists.txt");
    }

    //updates the rootPlaylist to account for the addition of any new playlists
    public static void updateRootPlaylist() {
        File[] rootPlaylist = new File("Playlists").listFiles();
        ArrayList<Playlist> temp = new ArrayList<>();
        for (File f: rootPlaylist){
           temp.add(new Playlist(f));
        }
        setRootPlaylist(temp);
    }

    //Creates a playlist and the required directory as well as returning the new Playlist Object
    public static Playlist createNewPlaylist(String playlistName) {
        File newFile = new File("Playlists\\" + playlistName);
        newFile.mkdirs();
        updateRootPlaylist();
        return new Playlist(newFile);
    }

    //Lists all of the playlists in the rootPlaylist directory
    public static File[] readAndListPlayLists() {
        File rootPlaylist = new File("Playlists");
        updateRootPlaylist();
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
