package com.trutechinnovations.zeus;

/**
 * Created by Cale Gibson on 3/28/2015.
 */
public class Playlist {
    private Song _song;
    private String _userID;


    public String get_userID() {
        return _userID;
    }

    public void set_userID(String _userID) {
        this._userID = _userID;
    }

    public Song get_song() {
        return _song;
    }

    public void set_song(Song _song) {
        this._song = _song;
    }

    public void Playlist(Song s, String u)
    {
        this._song = s;
        this._userID = u;
    }

}
