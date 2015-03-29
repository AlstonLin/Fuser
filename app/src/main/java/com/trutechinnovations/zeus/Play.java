package com.trutechinnovations.zeus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Alston on 3/28/2015.
 */
public class Play extends Fragment {
    private static final Play instance = new Play();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.play, container, false);
        return rootView;
    }

    public void update() {
        if (getView() != null) {
            TextView artist = (TextView) getView().findViewById(R.id.artist);
            TextView song = (TextView) getView().findViewById(R.id.song);
            TextView user = (TextView) getView().findViewById(R.id.username);
            ImageButton playButton = (ImageButton) getView().findViewById(R.id.play);
            ImageView album = (ImageView) getView().findViewById(R.id.albumArt);

            ImageButton b = (ImageButton) getView().findViewById(R.id.play);
            if (User.getInstance().getCurrent() == null || User.getInstance().isMute()) {
                b.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play_white));
            } else {
                b.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pause_white));
            }

            if (User.getInstance().getRadio() != null) {
                final MainActivity activity = (MainActivity) getActivity();
                final Radio r = User.getInstance().getRadio();
                artist.setText("Artist - " + r.getSong().getArtist());
                song.setText("Song - " + r.getSong().getName());
                user.setText("User - " + r.getName());
                r.getSong().setImageView(album);
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (User.getInstance().isMute()) {
                            User.getInstance().setMute(false);
                            activity.listenRadio(r);
                            ((ImageButton) v).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pause_white));
                        } else {
                            User.getInstance().setMute(true);
                            activity.muteMusic();
                            ((ImageButton) v).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play_white));
                        }
                    }
                });
            } else if (User.getInstance().getCurrent() != null) {
                final MainActivity activity = (MainActivity) getActivity();
                final Song s = User.getInstance().getCurrent();
                artist.setText("Artist - " + s.getArtist());
                song.setText("Song - " + s.getName());
                user.setText("User - " + User.getInstance().getName());
                s.setImageView(album);
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (User.getInstance().isMute()) {
                            User.getInstance().setMute(false);
                            activity.unmuteSong();

                        } else {
                            User.getInstance().setMute(true);
                            activity.muteMusic();

                            ((ImageButton) v).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play_white));
                        }
                    }
                });
            } else {
                artist.setText("Artist - ");
                song.setText("Song - ");
                user.setText("User - ");
            }
        }
    }

    public static Play getInstance() {
        return instance;
    }
}
