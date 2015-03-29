package com.trutechinnovations.zeus;

import java.util.ArrayList;

/**
 * Created by Alston on 3/28/2015.
 */
public class User {
    public static final int NONE = 0, RADIO = 1, PLAYLIST = 2, FREE = 3;
    private ArrayList<Radio> favs;
    private ArrayList<Song> playlist = new ArrayList<>();
    private Song current;
    private Radio radio;
    private String name;
    private boolean playing;
    private int source;
    private static final User instance = new User();

    private User(){ //SINGLETON
        favs = new ArrayList<>();
    }

    public static User getInstance(){
        return instance;
    }

    public Song getCurrent(){
        return current;
    }

    public void setCurrent(Song current){
        this.current = current;
    }

    public Radio getRadio() {
        return radio;
    }

    public ArrayList<Song> getPlaylist(){return playlist;}

    public void setRadio(Radio radio) {
        this.radio = radio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }


}
