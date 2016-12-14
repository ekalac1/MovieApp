package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Login;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.Action;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmTvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.SignUpAlertListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication.order;

public class TvShowGridViewAdapter extends ArrayAdapter<TvShow> {

    private int resource;
    private Context context;
    private MovieApplication mApp;
    private LinearLayout newView;
    SignUpAlertListener listener;
    Realm realm;

    public TvShowGridViewAdapter(Context _context, int _resource, List<TvShow> items, MovieApplication mApp) {
        super(_context, _resource, items);
        resource = _resource;
        context = _context;
        this.mApp = mApp;
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public TvShowGridViewAdapter(Context _context, int _resource, List<TvShow> items, MovieApplication mApp, SignUpAlertListener listener) {
        super(_context, _resource, items);
        resource = _resource;
        context = _context;
        this.mApp = mApp;
        this.listener = listener;
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater) getContext().
                    getSystemService(inflater);
            li.inflate(resource, newView, true);
        } else {
            newView = (LinearLayout) convertView;
        }
        final TvShow tvShow = getItem(position);
        TextView tvShowName = (TextView) newView.findViewById(R.id.tvShowName);
        TextView date = (TextView) newView.findViewById(R.id.tvShowRelaseDate);
        TextView voteAverage = (TextView) newView.findViewById(R.id.tvShowAverage);
        final ImageView favorite = (ImageView) newView.findViewById(R.id.favorite_movie);
        final ImageView watchlist = (ImageView) newView.findViewById(R.id.watchlist_movie);
        if (mApp.getAccount() != null) {
            boolean ind = true;
            if (mApp.getAccount().getFavoriteTvShows() != null)
                for (TvShow m : mApp.getAccount().getFavoriteTvShows())
                    if (m.getId() == tvShow.getId()) {
                        ind = false;
                        favorite.setImageResource(R.drawable.favorite_active);
                    }
            if (ind) favorite.setImageResource(R.drawable.favorite);

            ind = true;
            if (mApp.getAccount().getWatchListTvShow() != null)
                for (TvShow m : mApp.getAccount().getWatchListTvShow())
                    if (m.getId() == tvShow.getId()) {
                        ind = false;
                        watchlist.setImageResource(R.drawable.watchlist_active);
                    }
            if (ind) watchlist.setImageResource(R.drawable.watchlist);

        }

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getAccount() == null) listener.Alert();
                else {
                    if (isNetworkAvailable()) {
                        PostBody postMovie;
                        if (favorite.getDrawable().getConstantState().equals(getContext().getResources().getDrawable(R.drawable.favorite).getConstantState()))
                            postMovie = new PostBody(mApp.tvShow, tvShow.getId(), mApp.favorite, mApp);
                        else
                            postMovie = new PostBody(mApp.tvShow, tvShow.getId(), mApp.watchlist, mApp);
                        Call<PostResponse> call = mApp.getApiService().PostFavorite(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                if (response.body().getStatusCode() == 1)
                                    favorite.setImageResource(R.drawable.favorite_active);
                                else if (response.body().getStatusCode() == 13)
                                    favorite.setImageResource(R.drawable.favorite);
                                Call<TvShowResponse> call1 = mApp.getApiService().getFavoritesTvShows(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
                                call1.enqueue(new Callback<TvShowResponse>() {
                                    @Override
                                    public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                        mApp.getAccount().setFavoriteTvShows(response.body().getResults());
                                    }

                                    @Override
                                    public void onFailure(Call<TvShowResponse> call, Throwable t) {

                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<PostResponse> call, Throwable t) {

                            }
                        });
                    } else {
                        realm.beginTransaction();
                        RealmTvShow tvshow = realm.where(RealmTvShow.class).equalTo("id", tvShow.getId()).findFirst();
                        Action post = new Action();
                        post.setId(tvshow.getId());
                        post.setMediaType("tv");

                        if (favorite.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.favorite).getConstantState())) {
                            favorite.setImageResource(R.drawable.favorite_active);
                            post.setActionType("favorite");
                            tvshow.setFavorite(true);
                            mApp.getAccount().getFavoriteTvShows().add(new TvShow().getTvShow(tvshow));
                        } else {

                            favorite.setImageResource(R.drawable.favorite);
                            post.setActionType("removefavorite");
                            RealmResults<RealmTvShow> movies = realm.where(RealmTvShow.class).equalTo("id", tvshow.getId()).findAll();
                            for (RealmTvShow r : movies)
                                r.setFavorite(false);
                            tvshow.setFavorite(false);
                        }
                        realm.copyToRealm(post);
                        realm.commitTransaction();
                    }
                }
            }
        });

        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getAccount() == null) listener.Alert();
                else {
                    if (isNetworkAvailable()) {
                        PostBody postMovie;
                        if (watchlist.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.watchlist).getConstantState()))
                            postMovie = new PostBody(mApp.tvShow, tvShow.getId(), mApp.watchlist, mApp);
                        else
                            postMovie = new PostBody(mApp.tvShow, tvShow.getId(), mApp.favorite, mApp);
                        Call<PostResponse> call = mApp.getApiService().MarkWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                if (response.body().getStatusCode() == 1)
                                    watchlist.setImageResource(R.drawable.watchlist_active);
                                else if (response.body().getStatusCode() == 13)
                                    watchlist.setImageResource(R.drawable.watchlist);
                                Call<TvShowResponse> call1 = mApp.getApiService().getTvShowWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
                                call1.enqueue(new Callback<TvShowResponse>() {
                                    @Override
                                    public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                        mApp.getAccount().setWatchListTvShow(response.body().getResults());
                                    }

                                    @Override
                                    public void onFailure(Call<TvShowResponse> call, Throwable t) {

                                    }
                                });


                            }

                            @Override
                            public void onFailure(Call<PostResponse> call, Throwable t) {
                            }
                        });
                    } else {
                        realm.beginTransaction();
                        RealmTvShow tvshow = realm.where(RealmTvShow.class).equalTo("id", tvShow.getId()).findFirst();
                        Action post = new Action();
                        post.setId(tvshow.getId());
                        post.setMediaType("tv");

                        if (watchlist.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.watchlist).getConstantState())) {
                            watchlist.setImageResource(R.drawable.watchlist_active);
                            post.setActionType("watchlist");
                            tvshow.setWatchlist(true);
                            mApp.getAccount().getFavoriteTvShows().add(new TvShow().getTvShow(tvshow));
                        } else {

                            watchlist.setImageResource(R.drawable.watchlist);
                            post.setActionType("removewatchlist");
                            RealmResults<RealmTvShow> movies = realm.where(RealmTvShow.class).equalTo("id", tvShow.getId()).findAll();
                            for (RealmTvShow r : movies)
                                r.setWatchlist(false);
                            tvshow.setWatchlist(false);
                        }
                        realm.copyToRealm(post);
                        realm.commitTransaction();
                    }
                }
            }
        });

        if (tvShow.getFirstAirDate() != ("")) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = new Date();
            try {
                startDate = df.parse(tvShow.getFirstAirDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String year = tvShow.getFirstAirDate();
            year = year.substring(0, 4);
            tvShowName.setText(tvShow.getName() + " (" + year + ")");
            date.setText((new SimpleDateFormat("d MMM yyyy")).format(startDate).toString());
        } else {
            date.setText("");
            tvShowName.setText(tvShow.getName());
        }

        voteAverage.setText(String.valueOf(tvShow.getVoteAverage()));

        if (tvShow.getPosterPath() != ("")) {

            Glide.with(getContext())
                    .load(tvShow.getSmallFullPosterPath(getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.movies)
                    .into((ImageView) newView.findViewById(R.id.tvShowImageView));
        }
        return newView;
    }


    private void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(newView.getRootView().getContext(), R.style.AppTheme));
        builder.setMessage(R.string.message)
                .setTitle(R.string.Sign_in)
                .setPositiveButton(R.string.sign, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(newView.getRootView().getContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
