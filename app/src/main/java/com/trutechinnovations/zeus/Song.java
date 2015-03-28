package com.trutechinnovations.zeus;

/**
 * Created by Alston on 3/28/2015.
 */
public class Song {

    private String name;
    private int time;
    private String artist;
    private String url;

    public Song(String name, int time, String artist){
        this.name = name;
        this.time = time;
        this.artist = artist;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getArtist() {
        return artist;
    }

}
