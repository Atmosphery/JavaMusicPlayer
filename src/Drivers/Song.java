package Drivers;

import java.io.File;

public class Song {
    File song;
    String filePath;
    String title;

    public Song(File song) {
        setSong(song);
        setFilePath(song.getAbsolutePath());
        setTitle(song.getName());
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public File getSong() {
        return song;

    }

    public String getFilePath() {
        return filePath;
    }


    public void setSong(File song) {
        this.song = song;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
