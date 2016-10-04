package app.Models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by xliu189 on 10/1/2016.
 */
public class Song implements Comparable<Song>, Serializable {
    private String title;
    private String artist;
    private String album;
    private int year;

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
        this.title = title;
        this.artist = artist;
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
        this.title = title;
        this.artist = artist;
        this.album = album;
        //  Ignore 0 as a valid year for song.
        if(year != 0){
            this.year = year;
        }
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }

    public String getArtist(){
        return artist;
    }

    public void setArtist(String newArtist){
        this.artist = newArtist;
    }

    public String getAlbum(){
        return album;
    }

    public void setAlbum(String newAlbum){
        this.album = newAlbum;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int newYear){
        this.year = newYear;
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
