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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.TVShowDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.User;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.SignUpAlertListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication.order;


public class FavoritesMovies extends Fragment {
    List<Movie> movies;
    MovieApplication mApp;
    User a;
    GridView favoriteMovies;
    MovieGridViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View newView = inflater.inflate(R.layout.fragment_favorites_movies, container, false);

        mApp = (MovieApplication) getActivity().getApplication();
        a = mApp.getAccount();

        movies = new ArrayList<>();
        adapter = new MovieGridViewAdapter(newView.getContext(), R.layout.movie_element, movies, mApp);
        favoriteMovies = (GridView) newView.findViewById(R.id.favorite_movies);
        favoriteMovies.setAdapter(adapter);


        Call<MoviesListResponse> call = mApp.getApiService().getFavoritesMovies(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), order);
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                movies.addAll(response.body().getResults());
                ((BaseAdapter) favoriteMovies.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesListResponse> call, Throwable t) {
            }
        });

        favoriteMovies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), MoviesDetailsActivity.class);
                myIntent.putExtra("id", movies.get(position).getId());
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
