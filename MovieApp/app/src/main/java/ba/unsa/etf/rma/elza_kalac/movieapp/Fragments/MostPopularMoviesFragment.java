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

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularMoviesFragment extends Fragment {
    List<Movie> movies;
    ApiInterface apiService;
    MovieApplication mApp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.most_popular_movies_fragment, container, false);
        final GridView grid = (GridView) view.findViewById(R.id.gridView1);

        mApp = (MovieApplication)getActivity().getApplicationContext();
        apiService = mApp.getApiService();

        Call<MoviesListResponse> call = apiService.getMostPopularMovies(ApiClient.API_KEY, 1);
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                movies = response.body().getResults();
                final MovieGridViewAdapter adapter = new MovieGridViewAdapter(getActivity().getApplicationContext(), R.layout.movie_element, movies, mApp);
                grid.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(),R.string.on_failure, Toast.LENGTH_LONG).show();
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
                //  ApiInterface apiService =
                //   ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesListResponse> call = apiService.getMostPopularMovies(ApiClient.API_KEY, page);
                call.enqueue(new Callback<MoviesListResponse>() {
                    @Override
                    public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {

                        List<Movie> temp = response.body().getResults();
                        movies.addAll(temp);
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
}
