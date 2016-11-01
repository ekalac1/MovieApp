package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class RatedTvShows extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rated_tv_shows, container, false);
    }

}
