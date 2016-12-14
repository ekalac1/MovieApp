package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.User;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.MovieRealm;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchListMovie extends Fragment {

    List<Movie> movies;
    MovieApplication mApp;
    User a;
    GridView favoriteMovies;
    MovieGridViewAdapter adapter;
    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         final View newView = inflater.inflate(R.layout.fragment_watch_list_movie, container, false);

        mApp = (MovieApplication)getActivity().getApplication();
        a=mApp.getAccount();

        Realm.init(getActivity().getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        movies=new ArrayList<>();
        favoriteMovies = (GridView)newView.findViewById(R.id.watch_list_movies);
        adapter = new MovieGridViewAdapter(newView.getContext(), R.layout.movie_element, movies, mApp);
        favoriteMovies.setAdapter(adapter);

        realm = Realm.getDefaultInstance();

        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("favorite", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("favorite", true).findAll();
            for (MovieRealm m : rows) {
                movies.add((new Movie()).getMovie(m));
            }
            ((BaseAdapter) favoriteMovies.getAdapter()).notifyDataSetChanged();
        }

        Call<MoviesListResponse> call = mApp.getApiService().getMoviesWatchList(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), mApp.order);
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response)
            {
                movies.addAll(response.body().getResults());
                ((BaseAdapter) favoriteMovies.getAdapter()).notifyDataSetChanged();
                realm.beginTransaction();
                for (Movie m : response.body().getResults())
                {
                    MovieRealm movie= m.getMovieRealm(m);
                    movie.setWatchlist(true);
                    realm.copyToRealm(movie);
                }
                realm.commitTransaction();
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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
