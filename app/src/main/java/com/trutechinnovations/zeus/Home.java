package com.trutechinnovations.zeus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alston on 3/28/2015.
 */
public class Home extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home, container, false);
        setupList(rootView);
        return rootView;
    }

    /**
     * Sets up the ListView.
     */
    private void setupList(View v){
        ListView lv = (ListView) v.findViewById(R.id.list);
        ArrayList<Radio> radios = new ArrayList<>();
        radios = generateTestRadios(radios);
        //radios = getRadios(radios);
        lv.setAdapter(new ListAdapter(getActivity(), R.layout.item, radios));
    }


    private ArrayList<Radio> getRadios(ArrayList<Radio> list){
        return list;
    }

    /**
     * FOR TESTING ONLY
     */
    private ArrayList<Radio> generateTestRadios(ArrayList<Radio> list){
        DAO mydao = new DAO();
        List<Song> songs = mydao.getSong("drake");
        System.out.print("");
        for(Song s : songs)
        {
            list.add(new Radio("dj", s));
        }
//        list.add(new Radio("DJ 1", new Song("SONG 1", 0, "ARTIST 1")));
//        list.add(new Radio("DJ 2", new Song("SONG 2", 0, "ARTIST 2")));
//        list.add(new Radio("DJ 3", new Song("SONG 3", 0, "ARTIST 3")));
//        list.add(new Radio("DJ 4", new Song("SONG 4", 0, "ARTIST 4")));
//        list.add(new Radio("DJ 5", new Song("SONG 5", 0, "ARTIST 5")));
//        list.add(new Radio("DJ 1", new Song("SONG 1", 0, "ARTIST 1")));
//        list.add(new Radio("DJ 2", new Song("SONG 2", 0, "ARTIST 2")));
//        list.add(new Radio("DJ 3", new Song("SONG 3", 0, "ARTIST 3")));
//        list.add(new Radio("DJ 4", new Song("SONG 4", 0, "ARTIST 4")));
//        list.add(new Radio("DJ 5", new Song("SONG 5", 0, "ARTIST 5")));
        return list;
    }

    private class ListAdapter extends ArrayAdapter<Radio> {

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<Radio> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.item, null);
            }

            Radio r = getItem(position);

            if (r != null) {

                TextView title = (TextView) v.findViewById(R.id.title);
                TextView artist = (TextView) v.findViewById(R.id.artist);
                TextView dj = (TextView) v.findViewById(R.id.dj);

                title.setText(r.getSong().getName());
                artist.setText(r.getSong().getArtist());
                dj.setText(r.getName());
            }
            return v;
        }
    }
}
