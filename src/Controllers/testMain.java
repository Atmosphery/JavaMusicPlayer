package Controllers;


public class testMain {
    public static void main(String[] args) {
        PlaylistController plc = new PlaylistController();

        plc.importPlaylist("Playlists\\Playlist1");
        System.out.println(plc.readAndListPlayLists());

        String name = plc.readSinglePlaylist("Playlist1").listAllSongs();
        System.out.println(name);

        plc.createNewPlaylist("MyPlaylist");
        System.out.println(plc.readAndListPlayLists());
//
//        plc.deletePlaylist("MyPlaylist");
//        System.out.println(plc.readAndListPlayLists());
//
        plc.saveRootPlaylist("Playlists\\playlists.txt");

        plc.loadRootPlaylist("Playlists\\playlists.txt");
        System.out.println(plc.readAndListPlayLists());


    }
}
