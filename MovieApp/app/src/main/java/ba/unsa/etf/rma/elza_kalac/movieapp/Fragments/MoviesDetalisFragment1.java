package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class MoviesDetalisFragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.highest_rated_movies_fragment, container, false);
        return view;
    }
}
