package com.trutechinnovations.zeus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alston on 3/28/2015.
 */
public class Song {

    private String name;
    private int time;
    private String artist;
    private String url;
    private Bitmap image;

    public Song(String name, int time, String artist, String songUrl, String imageUrl){
        this.name = name;
        this.time = time;
        this.artist = artist;
        this.url = songUrl;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            image = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
        }
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
