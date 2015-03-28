package com.trutechinnovations.zeus;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Alston on 3/28/2015.
 */
public class Me extends Fragment {
    private static final Me instance = new Me();
    public static Me getInstance(){
        return instance;
    }
    private List<Song> results;
    private PopupWindow playlistSelect;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.me, container, false);
        setupList(rootView);
        Button b = (Button) rootView.findViewById(R.id.playlist);
        Button c = (Button) rootView.findViewById(R.id.play);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPlaylist();
            }
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPlaylist();
            }
        });
        return rootView;
    }


    public void playPlaylist() {
        if (User.getInstance().isMute()){
            if (User.getInstance().getPlaylist().size() > 0) {
                Song s = User.getInstance().getPlaylist().remove(0);
                ((MainActivity) getActivity()).playSong(s);
                User.getInstance().setMute(false);
                ((Button)getView().findViewById(R.id.play)).setText("Play");
            }
        }else {
            if (User.getInstance().isPlayPlaylist()){
                User.getInstance().setMute(true);
                ((Button)getView().findViewById(R.id.play)).setText("Pause");
            }else {
                if (User.getInstance().getPlaylist().size() > 0) {
                    User.getInstance().setPlayPlaylist(true);
                    Song s = User.getInstance().getPlaylist().remove(0);
                    ((MainActivity) getActivity()).playSong(s);
                    ((ImageButton)getView().findViewById(R.id.play)).setImageDrawable(getActivity().getResources().getDrawable(R.drawable.play_white));
                    ((Button)getView().findViewById(R.id.play)).setText("Play");
                }
            }
        }

    }

    /**
     * Sets up the ListView.
     */
    private void setupList(View v){
        ListView lv = (ListView) v.findViewById(R.id.list);
        lv.setAdapter(new ListAdapter(getActivity(), R.layout.item, User.getInstance().getPlaylist()));
    }


    private class ListAdapter extends ArrayAdapter<Song> {
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
                v = vi.inflate(R.layout.playlist_remove, null);
            }

            if (s != null) {

                TextView title = (TextView) v.findViewById(R.id.title);
                TextView artist = (TextView) v.findViewById(R.id.artist);
                final ImageButton b = (ImageButton) v.findViewById(R.id.remove);
                s.setImageView((ImageView) v.findViewById(R.id.image));

                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User.getInstance().getPlaylist().remove(s);
                        notifyDataSetChanged();
                    }
                });
                title.setText(s.getName());
                artist.setText(s.getArtist());
            }

            return v;
        }
    }


    public void finish(){
        playlistSelect.dismiss();
        ListView list = (ListView) getView().findViewById(R.id.list);
        ((ListAdapter)list.getAdapter()).notifyDataSetChanged();
    }

    public void clickPlaylist(){
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.playlist_select, null, false);
        layout.findViewById(R.id.playlistSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchPlaylist();
            }
        });
        playlistSelect = new PopupWindow(layout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        playlistSelect.showAtLocation(getActivity().findViewById(R.id.frame), Gravity.CENTER, 0, 0);

        Button finish = (Button) layout.findViewById(R.id.done);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void searchPlaylist(){
        EditText text = (EditText) playlistSelect.getContentView().findViewById(R.id.playlistText);
        String str = String.valueOf(text.getText());
        DAO mydao = new DAO();
        List<Song> results = mydao.getSong(str);
        this.results = results;
        ListView list = (ListView) playlistSelect.getContentView().findViewById(R.id.playlistList);
        list.setAdapter(new PlaylistAdapter(getActivity(), R.layout.playlist_add, results));
    }

    private class PlaylistAdapter extends ArrayAdapter<Song> {
        public PlaylistAdapter(Context context, int resource, List<Song> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            final Song s = getItem(position);

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.playlist_add, null);
            }

            if (s != null) {

                TextView title = (TextView) v.findViewById(R.id.title);
                TextView artist = (TextView) v.findViewById(R.id.artist);
                final ImageButton b = (ImageButton) v.findViewById(R.id.add);
                s.setImageView((ImageView) v.findViewById(R.id.image));
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        User.getInstance().getPlaylist().add(s);
                        results.remove(s);
                        notifyDataSetChanged();
                    }
                });
                title.setText(s.getName());
                artist.setText(s.getArtist());
            }
            return v;
        }
    }
}
