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

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.TVShowDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.TvShowGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.SignUpAlertListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AiringTodayTvShowsFragment extends Fragment {

    List<TvShow> tvShow;
    ApiInterface apiService;
    MovieApplication mApp;
    TvShowGridViewAdapter adapter;
    GridView grid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_airing_today_tv_shows_fragment, container, false);

        mApp = (MovieApplication)getActivity().getApplicationContext();
        apiService = mApp.getApiService();

        tvShow=new ArrayList<>();
        grid = (GridView) view.findViewById(R.id.airing_today_tv_shows);
        adapter = new TvShowGridViewAdapter(getActivity().getApplicationContext(), R.layout.tv_show_element, tvShow, mApp, (SignUpAlertListener) getContext());
        grid.setAdapter(adapter);

        Call<TvShowResponse> call = apiService.getAiringTodayTvShows(ApiClient.API_KEY, 1);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                tvShow.addAll(response.body().getResults());
                ((BaseAdapter)grid.getAdapter()).notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                Toast.makeText(getActivity().getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });

        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Call<TvShowResponse> call = apiService.getAiringTodayTvShows(ApiClient.API_KEY, page);
                call.enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                        tvShow.addAll(response.body().getResults());
                        ((BaseAdapter)grid.getAdapter()).notifyDataSetChanged();
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

    @Override
    public void onResume() {
        ((BaseAdapter) grid.getAdapter()).notifyDataSetChanged();
        super.onResume();
    }
}
