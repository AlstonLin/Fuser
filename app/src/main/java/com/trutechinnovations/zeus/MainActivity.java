package com.trutechinnovations.zeus;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.List;


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener{
    public static final int DISCOVER = 0, PLAY = 1, ME = 2, RESULTS = 3;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 4;
    private MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(4);
        mPager.setOnPageChangeListener(this);
        EditText myEditText = (EditText) findViewById(R.id.search);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
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
        switch (position){
            case PLAY:
                Play.getInstance().update();
        }
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
            User.getInstance().setCurrent(s);
        } catch (Exception e) {
        }
    }

    public void muteMusic(){
        try {
            player.pause();
        } catch (Exception e) {
        }
    }


    public void unmuteSong(){
        try {
            player.start();
        } catch (Exception e) {
        }
    }

    public void listenRadio(Radio r){
        try {
            User.getInstance().setCurrent(r.getSong());
            User.getInstance().setRadio(r);
            player.reset();
            player.setDataSource(r.getSong().getUrl());
            player.prepare();
            player.start();
            player.seekTo(r.getSong().getTime());
        } catch (Exception e) {
        }
    }
}
