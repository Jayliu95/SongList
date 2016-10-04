package app.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Comparator;

/**
 * Created by xliu189 on 10/1/2016.
 */
public class Song implements Comparable<Song> {
    private StringProperty title;
    private StringProperty artist;
    private StringProperty album;
    private IntegerProperty year;

    /**
     * Default constructor.
     */
    public Song() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     *
     * @param title : title of the song.
     * @param artist : artist of the song.
     */
    public Song(String title, String artist) {
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);

        // Dummy initialization.
        this.album = new SimpleStringProperty();
        this.year = new SimpleIntegerProperty();
    }
    /**
     * Constructor with some initial data.
     *
     * @param title : title of the song.
     * @param artist : artist of the song.
     * @param album : album of the song.
     * @param year : year the song was made.
     */
    public Song(String title, String artist, String album, int year) {
        this.title = new SimpleStringProperty(title);
        this.artist = new SimpleStringProperty(artist);
        this.album = new SimpleStringProperty(album);
        //  Ignore 0 as a valid year for song.
        if(year == 0){
            this.year = new SimpleIntegerProperty();
        }else {
            this.year = new SimpleIntegerProperty(year);
        }
    }


    public String getTitle(){
        return title.get();
    }

    public void setTitle(String newTitle){
        this.title.set(newTitle);
    }

    public String getArtist(){
        return artist.get();
    }

    public void setArtist(String newArtist){
        this.artist.set(newArtist);
    }

    public String getAlbum(){
        return album.get();
    }

    public void setAlbum(String newAlbum){
        this.album.set(newAlbum);
    }

    public int getYear(){
        return year.get();
    }

    public void setYear(int newYear){
        this.year.set(newYear);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Song))
            return false;

        return (this.getTitle().equalsIgnoreCase(((Song) obj).getTitle()) &&
                this.getArtist().equalsIgnoreCase(((Song) obj).getArtist()));
    }

    @Override
    public int compareTo(Song otherSong) {
        return this.getTitle().compareToIgnoreCase(otherSong.getTitle());
    }
    public String toString(){
        return  getTitle();
    }

}
