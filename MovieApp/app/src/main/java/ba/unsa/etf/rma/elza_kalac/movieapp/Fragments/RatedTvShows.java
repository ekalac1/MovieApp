package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.TVShowDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.TvShowGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.User;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatedTvShows extends Fragment {

    List<TvShow> tvShow;
    MovieApplication mApp;
    User a;
    GridView favoriteMovies;
    TvShowGridViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View newView = inflater.inflate(R.layout.fragment_rated_tv_shows, container, false);
        tvShow=new ArrayList<>();

        mApp = (MovieApplication)getActivity().getApplication();
        a=mApp.getAccount();

        favoriteMovies = (GridView)newView.findViewById(R.id.rated_tv_shows);
        adapter = new TvShowGridViewAdapter(newView.getContext(), R.layout.tv_show_element, tvShow, mApp);
        favoriteMovies.setAdapter(adapter);

        Call<TvShowResponse> call = mApp.getApiService().getTvShowRatings(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), mApp.order);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                tvShow.addAll(response.body().getResults());
                ((BaseAdapter)favoriteMovies.getAdapter()).notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });

        favoriteMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), TVShowDetails.class);
                myIntent.putExtra("id", tvShow.get(position).getId());
                startActivity(myIntent);
            }
        });
        return newView;
    }

    @Override
    public void onResume() {
        ((BaseAdapter) favoriteMovies.getAdapter()).notifyDataSetChanged();
        super.onResume();
    }

}
