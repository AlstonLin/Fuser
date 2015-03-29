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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alston on 3/28/2015.
 */
public class Home extends Fragment {

    private static final Home instance = new Home();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home, container, false);
        setupList(rootView);
        return rootView;
    }

    /**
     * Sets up the ListView.
     */
    private void setupList(View v) {
        ListView lv = (ListView) v.findViewById(R.id.list);
        List<Radio> radios = generateTestRadios(new ArrayList<Radio>());
        lv.setAdapter(new ListAdapter(getActivity(), R.layout.item, radios));
    }



    /**
     * FOR TESTING ONLY
     */
    private ArrayList<Radio> generateTestRadios(ArrayList<Radio> list) {
        DAO mydao = new DAO();
        List<Song> songs = mydao.getSong("drake");
        System.out.print("");
        list.add(new Radio("test", new Song("0 to 100", 0, "Drake", "https://dl.dropbox.com/s/mu28po1o9pila6n/01%200%20to%20100.mp3?dl=0", "http://hw-img.datpiff.com/mee6c8cb/Drake_Rookie_And_The_Veteran-front.jpg")));
        return list;
    }

    private class ListAdapter extends ArrayAdapter<Radio> {
        private ImageButton last = null;

        public ListAdapter(Context context, int resource, List<Radio> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            final Radio r = getItem(position);

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.item, null);
                ImageButton b = (ImageButton) v.findViewById(R.id.play);
                if (User.getInstance().isPlaying() && User.getInstance().getRadio() == r) {
                    b.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                } else {
                    b.setImageDrawable(getResources().getDrawable(R.drawable.play));
                }
            }

            if (r != null) {

                TextView title = (TextView) v.findViewById(R.id.title);
                TextView artist = (TextView) v.findViewById(R.id.artist);
                TextView dj = (TextView) v.findViewById(R.id.dj);
                final ImageButton b = (ImageButton) v.findViewById(R.id.play);
                r.getSong().setImageView((ImageView) v.findViewById(R.id.image));

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (r == User.getInstance().getRadio()) {
                            if (!User.getInstance().isPlaying()) {
                                User.getInstance().setPlaying(true);
                                b.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                                ((MainActivity) getActivity()).listenRadio(r);
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
                            ((MainActivity) getActivity()).listenRadio(r);
                            User.getInstance().setPlaying(true);
                        }
                        last = b;
                    }
                });
                title.setText(r.getSong().getName());
                artist.setText(r.getSong().getArtist());
                dj.setText(r.getName());
            }
            return v;
        }
    }

    public static Home getInstance() {
        return instance;
    }
}
