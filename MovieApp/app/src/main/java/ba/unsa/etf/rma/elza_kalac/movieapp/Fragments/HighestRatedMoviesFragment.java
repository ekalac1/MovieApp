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
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.MovieActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.SignUpAlertListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HighestRatedMoviesFragment extends Fragment {

    List<Movie> movies;
    ApiInterface apiService;
    MovieApplication mApp;
    GridView grid;
    MovieGridViewAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.highest_rated_movies_fragment, container, false);

        mApp = (MovieApplication)getActivity().getApplicationContext();
        apiService = mApp.getApiService();

        movies=new ArrayList<>();
        grid = (GridView) view.findViewById(R.id.gridView3);
        adapter = new MovieGridViewAdapter(getActivity().getApplicationContext(), R.layout.movie_element, movies, mApp, (SignUpAlertListener) getContext());
        grid.setAdapter(adapter);



        Call<MoviesListResponse> call = apiService.getTopRatedMovies(ApiClient.API_KEY, 1);
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                movies.addAll(response.body().getResults());
                ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });

        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Call<MoviesListResponse> call = apiService.getTopRatedMovies(ApiClient.API_KEY, page);
                call.enqueue(new Callback<MoviesListResponse>() {
                    @Override
                    public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                        movies.addAll(response.body().getResults());
                        ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
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
    }}
