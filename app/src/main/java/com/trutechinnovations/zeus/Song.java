package com.trutechinnovations.zeus;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Song {

    private String name;
    private float time;
    private String artist;
    private String url;
    private ImageView imageView;
    private String imageUrl;
    private DownloadImageTask task;

    public Song(String name, float time, String artist, String songUrl, String imageUrl){
        this.name = name;
        this.time = time;
        this.artist = artist;
        this.url = songUrl;
        this.imageUrl = imageUrl;
    }


    public void setImageView(ImageView imageView){
        this.imageView = imageView;
        if (task == null || task.getStatus() != AsyncTask.Status.RUNNING) {
            task = new DownloadImageTask();
            String[] params = new String[1];
            params[0] = imageUrl;
            task.execute(params);
        }
    }

    public String getName() {
        return name;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getArtist() {
        return artist;
    }


    private class DownloadImageTask extends AsyncTask <String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... param) {
            try {
                URL url = new URL(param[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }


    public String getUrl(){
        return url;
    }
}
