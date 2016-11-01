package ba.unsa.etf.rma.elza_kalac.movieapp.Fragments;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.MovieActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.TVShowDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.TvShowGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTvShowsFragment extends Fragment {

    List<TvShow> tvShow;
    ApiInterface apiService;
    MovieApplication mApp;


    private static final String TAG = MovieActivity.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_most_popular_tv_shows_fragment, container, false);

        final GridView grid = (GridView) view.findViewById(R.id.most_popular_tv_shows);

        mApp = (MovieApplication)getActivity().getApplicationContext();
        apiService = mApp.getApiService();

        Call<TvShowResponse> call = apiService.getPopularTvShows(ApiClient.API_KEY, 1);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {

                tvShow = response.body().getResults();
               final TvShowGridViewAdapter adapter = new TvShowGridViewAdapter(getActivity().getApplicationContext(), R.layout.tv_show_element, tvShow);
                grid.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {;
                Toast.makeText(getActivity().getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });
        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Call<TvShowResponse> call = apiService.getPopularTvShows(ApiClient.API_KEY, page);
                call.enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                        List<TvShow> temp = response.body().getResults();
                        tvShow.addAll(temp);
                        ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<TvShowResponse> call, Throwable t) {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
                    }
                });
                return true;
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(getActivity(), TVShowDetails.class);
                myIntent.putExtra("id", tvShow.get(position).getId());
                startActivity(myIntent);
            }
        });
        return view;
    }
}
