package com.trutechinnovations.zeus;

import java.util.ArrayList;

/**
 * Created by Alston on 3/28/2015.
 */
public class User {
    private ArrayList<Radio> favs;
    private Song current;
    private Radio radio;
    private boolean mute;
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

    public void setRadio(Radio radio) {
        this.radio = radio;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

}
