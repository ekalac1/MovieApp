package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.MainActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.GridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestMoviesFragment extends Fragment {
    private static final String TAG = MainActivity.class.getSimpleName();
    public List<Movie> movies;
    private int pageNum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.latest_movies_fragment, container, false);
        final GridView grid = (GridView) view.findViewById(R.id.gridView2);
        pageNum=1;

        if (ApiClient.API_KEY.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), R.string.api_key_missing, Toast.LENGTH_LONG).show();
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MoviesListResponse> call = apiService.getLatestMovies(ApiClient.API_KEY, pageNum);
        call.enqueue(new Callback<MoviesListResponse>() {
           @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                movies = response.body().getResults();
                final GridViewAdapter adapter = new GridViewAdapter(getActivity().getApplicationContext(), R.layout.grid_view_element, movies);

                 grid.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity().getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();

            }
        });

        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<MoviesListResponse> call = apiService.getMostPopularMovies(ApiClient.API_KEY, pageNum);
                call.enqueue(new Callback<MoviesListResponse>() {
                    @Override
                    public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {

                        List<Movie> temp = response.body().getResults();
                        movies.addAll(temp);
                        ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                        Toast.makeText(getActivity().getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                        return;
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
