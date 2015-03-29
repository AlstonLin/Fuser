package com.trutechinnovations.zeus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Alston on 3/28/2015.
 */
public class Results extends Fragment {

    private static Results instance = new Results();
    private List<Song> results;
    private View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.results, container, false);
        v = rootView;
        return rootView;
    }

    private void setupList() {
        if (v != null) {
            ListView lv = (ListView) v.findViewById(R.id.list);
            lv.setAdapter(new ListAdapter(getActivity(), R.layout.item, results));
        }
    }

    public void setResults(List<Song> results) {
        this.results = results;
        setupList();
    }

    private class ListAdapter extends ArrayAdapter<Song> {
        private ImageButton last = null;

        public ListAdapter(Context context, int resource, List<Song> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            final Song s = getItem(position);

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.result_item, null);
            }

            final ImageButton b = (ImageButton) v.findViewById(R.id.play);
            if (!User.getInstance().isPlaying() && User.getInstance().getCurrent() == s) {
                b.setImageDrawable(getResources().getDrawable(R.drawable.pause));
            } else {
                b.setImageDrawable(getResources().getDrawable(R.drawable.play));
            }

            if (s != null) {

                TextView title = (TextView) v.findViewById(R.id.title);
                TextView artist = (TextView) v.findViewById(R.id.artist);
                s.setImageView((ImageView) v.findViewById(R.id.image));

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (s == User.getInstance().getCurrent()) {
                            if (!User.getInstance().isPlaying()) {
                                User.getInstance().setPlaying(true);
                                b.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                                ((MainActivity) getActivity()).unmuteSong();
                            } else {
                                User.getInstance().setPlaying(false);
                                b.setImageDrawable(getResources().getDrawable(R.drawable.play));
                                ((MainActivity) getActivity()).muteMusic();
                            }
                        } else {
                            if (last != null) {
                                last.setImageDrawable(getResources().getDrawable(R.drawable.play));
                            }
                            b.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                            ((MainActivity) getActivity()).playSong(s);
                            User.getInstance().setPlaying(false);
                            User.getInstance().setSource(User.FREE);
                        }
                        last = b;
                    }
                });
                title.setText(s.getName());
                artist.setText(s.getArtist());
            }
            return v;
        }
    }

    public static Results getInstance() {
        return instance;
    }

}
