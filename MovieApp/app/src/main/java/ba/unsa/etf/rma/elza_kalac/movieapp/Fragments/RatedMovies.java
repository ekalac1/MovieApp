package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatedMovies extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       final View newView = inflater.inflate(R.layout.fragment_rated_movies, container, false);

        final MovieApplication mApp = (MovieApplication)getActivity().getApplication();
        Account a;
        a=mApp.getAccount();

        final GridView favoriteMovies = (GridView)newView.findViewById(R.id.rated_movies);

        Call<MoviesListResponse> call = mApp.getApiService().getMoviesRatings(a.getAccountId(), ApiClient.API_KEY, a.getSessionId());
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {

                MovieGridViewAdapter adapter = new MovieGridViewAdapter(newView.getContext(), R.layout.movie_element, response.body().getResults(), mApp);
                favoriteMovies.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MoviesListResponse> call, Throwable t) {

            }
        });



        return newView;
    }

}
