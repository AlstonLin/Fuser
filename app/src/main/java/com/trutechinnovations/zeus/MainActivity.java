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


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener{
    public static final int DISCOVER = 0, PLAY = 1, ME = 2;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;
    private static final int NUM_PAGES = 3;
    private MediaPlayer player = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(3);
        mPager.setOnPageChangeListener(this);
        EditText myEditText = (EditText) findViewById(R.id.search);
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myEditText.getWindowToken(), 0);
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
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
            player.reset();
            player.setDataSource(r.getSong().getUrl());
            player.prepare();
            player.start();
            player.seekTo(r.getSong().getTime());
            User.getInstance().setCurrent(r.getSong());
            User.getInstance().setRadio(r);
        } catch (Exception e) {
        }
    }
}
