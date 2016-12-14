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
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.MovieRealm;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.SignUpAlertListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularMoviesFragment extends Fragment {
    List<Movie> movies;

    ApiInterface apiService;
    MovieApplication mApp;

    MovieGridViewAdapter adapter;
    GridView grid;

    Realm realm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.most_popular_movies_fragment, container, false);

        Realm.init(getActivity().getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        mApp = (MovieApplication) getActivity().getApplicationContext();
        apiService = mApp.getApiService();

        movies = new ArrayList<>();
        adapter = new MovieGridViewAdapter(getActivity().getApplicationContext(), R.layout.movie_element, movies, mApp, (SignUpAlertListener) getContext());
        grid = (GridView) view.findViewById(R.id.gridView1);
        grid.setAdapter(adapter);

        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("mostPopular", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("mostPopular", true).findAll();
            for (MovieRealm m : rows) {
                movies.add(new Movie().getMovie(m));
            }
            ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();

        }


        Call<MoviesListResponse> call = apiService.getMostPopularMovies(ApiClient.API_KEY, 1);
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                movies.addAll(response.body().getResults());
                realm.beginTransaction();
                for (Movie m : response.body().getResults()) {
                    MovieRealm movie= m.getMovieRealm(m);
                    movie.setMostPopular(true);
                    realm.copyToRealm(movie);
                }
                realm.commitTransaction();

                ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesListResponse> call, Throwable t) {
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), MoviesDetailsActivity.class);
                myIntent.putExtra("id", movies.get(position).getId());
                startActivity(myIntent);

            }
        });
        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Call<MoviesListResponse> call = apiService.getMostPopularMovies(ApiClient.API_KEY, page);
                call.enqueue(new Callback<MoviesListResponse>() {
                    @Override
                    public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                        movies.addAll(response.body().getResults());
                        ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                        realm.beginTransaction();
                        for (Movie m : response.body().getResults()) {
                            MovieRealm movie= m.getMovieRealm(m);
                            movie.setMostPopular(true);
                            realm.copyToRealm(movie);
                        }
                        realm.commitTransaction();
                    }

                    @Override
                    public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                    }
                });
                return true;
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), MoviesDetailsActivity.class);
                myIntent.putExtra("id", movies.get(position).getId());
                startActivity(myIntent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
        super.onResume();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
