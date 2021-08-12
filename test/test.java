import Drivers.Song;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class test {
    @Test
    public void testSetSong() {
        File f = new File("C:\\Users\\abecc\\Documents\\GitHub\\JavaMusicPlayer\\Playlists\\Playlist1\\On Melancholy Hill.mp3");
        Song s = new Song(f);
        String title = s.getTitle();
        System.out.println(title);

    }

}
