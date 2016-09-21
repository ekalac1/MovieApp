package ba.unsa.etf.rma.elza_kalac.movieapp.Fragmenti;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.MainActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.MyAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragment3 extends Fragment {
    private final static String API_KEY = "14f1ed2b33d9c95e73a70d058755a031";
    private static final String TAG = MainActivity.class.getSimpleName();
    public List<Movie> movies;
    private int pageNum;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.tab_fragment_3, container, false);
        final GridView grid = (GridView) view.findViewById(R.id.gridView3);
        pageNum =1;

       if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY, pageNum);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                movies = response.body().getResults();
                final MyAdapter adapter = new MyAdapter(getActivity().getApplicationContext(), R.layout.element_liste, movies);
                grid.setAdapter(adapter);
                pageNum++;
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity().getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                return;
            }
        });

        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY, pageNum);
                call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                        List<Movie> temp = response.body().getResults();
                        movies.addAll(temp);
                        ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                        pageNum++;
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                        Toast.makeText(getActivity().getApplicationContext(), "Failed", Toast.LENGTH_LONG).show();
                        return;
                    }
                });


                return true;
            }
        });

        return view;

    }
}
