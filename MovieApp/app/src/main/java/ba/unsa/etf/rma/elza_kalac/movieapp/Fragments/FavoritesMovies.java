package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.TVShowDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoritesMovies extends Fragment {
    List<Movie> movies;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View newView= inflater.inflate(R.layout.fragment_favorites_movies, container, false);

        final MovieApplication mApp = (MovieApplication)getActivity().getApplication();
        Account a;
        a=mApp.getAccount();

        final GridView favoriteMovies = (GridView)newView.findViewById(R.id.favorite_movies);

        Call<MoviesListResponse> call = mApp.getApiService().getFavoritesMovies(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), mApp.order);
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                movies=response.body().getResults();
                MovieGridViewAdapter adapter = new MovieGridViewAdapter(newView.getContext(), R.layout.movie_element, response.body().getResults(), mApp);
                favoriteMovies.setAdapter(adapter);
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

        return  newView;
    }




}
