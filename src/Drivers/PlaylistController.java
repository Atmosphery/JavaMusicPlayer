package Drivers;

import java.io.File;
import java.util.ArrayList;

public class PlaylistController {
    static ArrayList<Playlist> playlistList = new ArrayList<>();

    public static void updatePlaylistList() {
        File[] rootPlaylist = new File("Playlists").listFiles();
        for (File f: rootPlaylist){
            playlistList.add(new Playlist(f));
        }
    }


    public static Playlist createNewPlaylist(String playlistName) {
        File newFile = new File("Playlists\\" + playlistName);
        newFile.mkdirs();
        return new Playlist(newFile);
    }
    

    public static File[] readAndListPlayLists() {
        File rootPlaylist = new File("Playlists");
        return rootPlaylist.listFiles();
    }

    public static Playlist readSinglePlaylist(String nameOfPlayList){
        File[] playlists = new File("Playlists").listFiles();
        for (File f: playlists){
            if (f.getName().equals(nameOfPlayList)){
                return new Playlist(f);
            }
        }
        return null;
    }

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
