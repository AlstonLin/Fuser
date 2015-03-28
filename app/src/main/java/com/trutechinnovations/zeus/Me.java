package com.trutechinnovations.zeus;

import android.support.v4.app.Fragment;

/**
 * Created by Alston on 3/28/2015.
 */
public class Me extends Fragment {
    private static final Me instance = new Me();

    public static Me getInstance(){
        return instance;
    }
}
