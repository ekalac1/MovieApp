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
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.TVShowDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.TvShowGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.EndlessScrollListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmTvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.SignUpAlertListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LatestTvShowsFragment extends Fragment {

    List<TvShow> tvShow;
    ApiInterface apiService;
    MovieApplication mApp;
    GridView grid;
    TvShowGridViewAdapter adapter;
    Realm realm;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_latest_tv_shows_fragment, container, false);

        Realm.init(getActivity().getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        mApp = (MovieApplication)getActivity().getApplicationContext();
        apiService = mApp.getApiService();

        tvShow=new ArrayList<>();
        grid = (GridView) view.findViewById(R.id.latets_tv_shows);
        adapter = new TvShowGridViewAdapter(getActivity().getApplicationContext(), R.layout.tv_show_element, tvShow, mApp, (SignUpAlertListener) getContext());
        grid.setAdapter(adapter);

        realm = Realm.getDefaultInstance();
        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<RealmTvShow> rows = realm.where(RealmTvShow.class).equalTo("latest", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        }
        else
        {
            realm.beginTransaction();
            RealmResults<RealmTvShow> rows = realm.where(RealmTvShow.class).equalTo("latest", true).findAll();
            realm.commitTransaction();
            for (RealmTvShow t : rows)
            {
                tvShow.add(new TvShow().getTvShow(t));
            }
            ((BaseAdapter)grid.getAdapter()).notifyDataSetChanged();
        }



        Call<TvShowResponse> call = apiService.getLatestTvShows(ApiClient.API_KEY, 1);
        call.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                tvShow.addAll(response.body().getResults());
                ((BaseAdapter)grid.getAdapter()).notifyDataSetChanged();
                realm.beginTransaction();
                for (TvShow t : response.body().getResults())
                {
                    RealmTvShow tvshow=t.getRealmTvShow(t);
                    tvshow.setLatest(true);
                    realm.copyToRealm(tvshow);
                }
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
            }
        });
        grid.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                Call<TvShowResponse> call = apiService.getLatestTvShows(ApiClient.API_KEY, page);
                call.enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                        tvShow.addAll(response.body().getResults());
                        ((BaseAdapter)grid.getAdapter()).notifyDataSetChanged();
                        realm.beginTransaction();
                        for (TvShow t : response.body().getResults())
                        {
                            RealmTvShow tvshow=t.getRealmTvShow(t);
                            realm.copyToRealm(tvshow);
                        }
                        realm.commitTransaction();
                    }

                    @Override
                    public void onFailure(Call<TvShowResponse> call, Throwable t) {
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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
