package com.trutechinnovations.zeus;

import java.util.ArrayList;

public class Radio {

    private String name;
    private Song song;

    private ArrayList<Song> playlist;

    public Radio(String name, Song song){
        this.name = name;
        this.song = song;
    }


    public String getName(){
        return name;
    }

    public Song getSong(){
        return song;
    }

    public void setSong(Song song){
        this.song = song;
    }

}
