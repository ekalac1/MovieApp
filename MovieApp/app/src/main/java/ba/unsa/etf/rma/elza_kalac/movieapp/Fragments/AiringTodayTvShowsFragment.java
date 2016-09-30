package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class AiringTodayTvShowsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_airing_today_tv_shows_fragment, container, false);
        return view;
    }
}
