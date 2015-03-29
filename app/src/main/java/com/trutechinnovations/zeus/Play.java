package com.trutechinnovations.zeus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Alston on 3/28/2015.
 */
public class Play extends Fragment {
    private static final Play instance = new Play();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.play, container, false);
        rootView.findViewById(R.id.follow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (User.getInstance().getSource() == User.RADIO) {
                    Toast.makeText(getActivity(), "Followed!", Toast.LENGTH_SHORT).show();
                    DAO dao = new DAO();
                    dao.addFollower(User.getInstance().getName(), User.getInstance().getRadio().getName());
                }
            }
        });
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
            if (User.getInstance().isPlaying()) {
                b.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pause_white));
            } else {
                b.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play_white));
            }

            if (User.getInstance().getCurrent() != null) {
                final MainActivity activity = (MainActivity) getActivity();
                final Song s = User.getInstance().getCurrent();
                artist.setText(s.getArtist());
                song.setText(s.getName());
                if (User.getInstance().getRadio() == null) {
                    user.setText(User.getInstance().getName());
                }else{
                    user.setText(User.getInstance().getRadio().getName());
                }
                s.setImageView(album);
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!User.getInstance().isPlaying()) {
                            User.getInstance().setPlaying(true);
                            if (User.getInstance().getSource() == User.RADIO){
                                activity.listenRadio(User.getInstance().getRadio());
                            } else if (User.getInstance().getSource() == User.PLAYLIST || User.getInstance().getSource() == User.FREE){
                                activity.unmuteSong();
                            }
                            ((ImageButton) v).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.pause_white));
                        } else {
                            User.getInstance().setPlaying(false);
                            activity.muteMusic();
                            ((ImageButton) v).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play_white));
                        }
                    }
                });
            } else {
                artist.setText(null);
                song.setText(null);
                user.setText(null);
            }
        }
    }

    public static Play getInstance() {
        return instance;
    }
}
