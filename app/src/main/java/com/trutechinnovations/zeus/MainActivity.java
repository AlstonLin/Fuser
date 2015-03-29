package com.trutechinnovations.zeus;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener{
    public static final int DISCOVER = 0, PLAY = 1, ME = 2, RESULTS = 3;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 4;
    private ImageButton last;
    private MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void login(View v){
       String user = String.valueOf(((EditText)findViewById(R.id.username)).getText());
       String pass = String.valueOf(((EditText)findViewById(R.id.password)).getText());
       DAO dao = new DAO();
       if (dao.login(user, pass)){
           User.getInstance().setName(user);
           openHome();
       }else{
           Toast.makeText(this, "Invalid Login", Toast.LENGTH_LONG).show();
       }
    }

    public void signup(View v){
        setContentView(R.layout.sign_up);
    }


    public void register(View v){
        DAO dao = new DAO();
        String user = String.valueOf(((EditText)findViewById(R.id.user)).getText());
        String pass = String.valueOf(((EditText)findViewById(R.id.pass)).getText());
        boolean success = dao.createUser(user, pass);
        if (success) {
            Toast.makeText(this, "Registered!", Toast.LENGTH_LONG).show();
            setContentView(R.layout.login);
        } else{
            Toast.makeText(this, "Error Registering", Toast.LENGTH_LONG).show();
        }
    }


    public void openHome(){
        setContentView(R.layout.activity_main);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(4);
        mPager.setOnPageChangeListener(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        last = (ImageButton) findViewById(R.id.discover);
        last.setBackgroundColor(Color.parseColor("#E0E0E0"));
    }

    public void clickDiscover(View v){
        mPager.setCurrentItem(DISCOVER);
    }

    public void clickPlay(View v){
        mPager.setCurrentItem(PLAY);
    }

    public void clickMe(View v){
        mPager.setCurrentItem(ME);
    }

    public void clickResults(View v){
        mPager.setCurrentItem(RESULTS);
    }

    public void clickSearch(View v){
        EditText text = (EditText) findViewById(R.id.search);
        String str = String.valueOf(text.getText());
        DAO mydao = new DAO();
        List<Song> results = mydao.getSong(str);
        Results result = Results.getInstance();
        result.setResults(results);
        mPager.setCurrentItem(RESULTS);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        last.setBackgroundColor(Color.parseColor("#FFFFFF"));
        switch (position){
            case DISCOVER:
                last = (ImageButton) findViewById(R.id.discover);
                break;
            case PLAY:
                Play.getInstance().update();
                last = (ImageButton) findViewById(R.id.play_button);
                break;
            case ME:
                last = (ImageButton) findViewById(R.id.me);
                break;
            case RESULTS:
                last = (ImageButton) findViewById(R.id.results);
                break;
        }
        last.setBackgroundColor(Color.parseColor("#E0E0E0"));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position){
                case DISCOVER:
                    return Home.getInstance();
                case PLAY:
                    return Play.getInstance();
                case ME:
                    return Me.getInstance();
                case RESULTS:
                    return Results.getInstance();
                default:
                    throw new IllegalArgumentException();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }


    public void playSong(Song s){
        try {
            player.reset();
            player.setDataSource(s.getUrl());
            player.prepare();
            player.start();

            if (User.getInstance().getSource() == User.PLAYLIST){
                final DAO dao = new DAO();
                dao.createRadio(User.getInstance().getName(), s);
                player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (User.getInstance().getPlaylist().size() > 0){
                            playSong(User.getInstance().getPlaylist().remove(0));
                            Me.getInstance().updatePlaylistAdapter();
                        }else{
                            User.getInstance().setSource(User.NONE);
                            dao.createRadio(User.getInstance().getName(), null);
                        }
                    }
                });
            }else{
                player.setOnCompletionListener(null);
            }
            User.getInstance().setCurrent(s);
            User.getInstance().setPlaying(true);
        } catch (Exception e) {
        }
    }

    public void muteMusic(){
        try {
            player.pause();
            User.getInstance().setPlaying(false);
        } catch (Exception e) {
        }
    }


    public void unmuteSong(){
        try {
            player.start();
            User.getInstance().setPlaying(true);
        } catch (Exception e) {
        }
    }

    public void listenRadio(Radio r){
        try {
            User.getInstance().setCurrent(r.getSong());
            User.getInstance().setRadio(r);
            User.getInstance().setSource(User.RADIO);
            player.reset();
            player.setDataSource(r.getSong().getUrl());
            player.prepare();
            player.start();
            player.seekTo(r.getSong().getTime());
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {

                }
            });
        } catch (Exception e) {
        }
    }
}
