package com.trutechinnovations.zeus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
        list.add(new Radio("DJ 1", new Song("0 to 100", 0, "Drake", "https://dl.dropbox.com/s/mu28po1o9pila6n/01%200%20to%20100.mp3?dl=0", "http://hw-img.datpiff.com/mee6c8cb/Drake_Rookie_And_The_Veteran-front.jpg")));
        list.add(new Radio("DJ 2", new Song("Bottoms Up", 0, "Trey Songz", "https://www.google.com/url?q=https%3A%2F%2Fdl.dropbox.com%2Fs%2F7enirg2j2faqza8%2F01%2520Bottoms%2520Up.mp3%3Fdl%3D0&sa=D&sntz=1&usg=AFQjCNE5iodO-5pSvy5EaZcO1UkO5UK8Iw", "https://assets.hiphopvip.com/asset_uploads/pitpanther01/trey-songz-check-me-out-feat-diddy-meek-mill-tags/4abe0f76d8501f9138c9ff302165dd3f.jpg")));
        list.add(new Radio("DJ 3", new Song("Just A Dream", 0, "Nelly", "https://www.google.com/url?q=https%3A%2F%2Fdl.dropbox.com%2Fs%2Fdfkiaa219e0zlz4%2F01%2520Just%2520A%2520Dream.mp3%3Fdl%3D0&sa=D&sntz=1&usg=AFQjCNGKeVBw1QgMOHMh46dRHHJ8NmGk2g", "http://s1.evcdn.com/images/block/I0-001/000/909/252-6.jpeg_/nelly-52.jpeg")));
        return list;
    }

    private class ListAdapter extends ArrayAdapter<Radio> {
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
                if (!User.getInstance().isMute() && User.getInstance().getRadio() == r){
                    b.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                }else{
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
                        if (r == User.getInstance().getRadio()){
                            if (User.getInstance().isMute()){
                                User.getInstance().setMute(false);
                                b.setImageDrawable(getResources().getDrawable(R.drawable.pause));
                                ((MainActivity)getActivity()).listenRadio(r);
                            }else{
                                User.getInstance().setMute(true);
                                b.setImageDrawable(getResources().getDrawable(R.drawable.play));
                                ((MainActivity)getActivity()).muteMusic();
                            }
                        }else{
                            ((MainActivity)getActivity()).listenRadio(r);
                            User.getInstance().setMute(false);
                        }
                    }
                });
                title.setText(r.getSong().getName());
                artist.setText(r.getSong().getArtist());
                dj.setText(r.getName());
            }
            return v;
        }
    }

    public static Home getInstance(){
        return instance;
    }
}
