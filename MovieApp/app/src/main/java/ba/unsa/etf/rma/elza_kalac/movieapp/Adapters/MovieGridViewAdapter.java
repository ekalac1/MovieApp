package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
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
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.Action;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.MovieRealm;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.SignUpAlertListener;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication.order;

public class MovieGridViewAdapter extends ArrayAdapter<Movie> {

    private int resource;
    private Context context;
    private MovieApplication mApp;
    private SignUpAlertListener listener;
    private LinearLayout newView;
    private List<Movie> movies;
    Realm realm;

    public MovieGridViewAdapter(Context _context, int _resource, List<Movie> items, MovieApplication mApp) {
        super(_context, _resource, items);
        resource = _resource;
        context = _context;
        this.mApp = mApp;
        this.movies = items;
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    public MovieGridViewAdapter(Context _context, int _resource, List<Movie> items, MovieApplication mApp, SignUpAlertListener listenet) {
        super(_context, _resource, items);
        resource = _resource;
        context = _context;
        this.mApp = mApp;
        this.movies = items;
        this.listener = listenet;
        Realm.init(context);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

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
        final Movie movie = getItem(position);
        TextView movieName = (TextView) newView.findViewById(R.id.movieName);
        TextView date = (TextView) newView.findViewById(R.id.relaseDate);
        TextView voteAverage = (TextView) newView.findViewById(R.id.vote_average);
        final ImageView favourite = (ImageView) newView.findViewById(R.id.favorite_movie);
        final ImageView watchlist = (ImageView) newView.findViewById(R.id.watchlist_movie);


        if (mApp.getAccount() != null) {
            List<Movie> favorite = mApp.getAccount().getFavoriteMovies();
            boolean ind = false;
            if (favorite != null) {
                for (Movie m : favorite)
                    if (m.getId() == movie.getId()) {
                        favourite.setImageResource(R.drawable.favorite_active);
                        ind = true;
                    }
                if (!ind) favourite.setImageResource(R.drawable.favorite);
                ind = false;
            }
            List<Movie> watchList = mApp.getAccount().getWatchListMovies();
            if (watchList != null) {
                for (Movie m : watchList)
                    if (m.getId() == movie.getId()) {
                        ind = true;
                        watchlist.setImageResource(R.drawable.watchlist_active);
                    }
                if (!ind) watchlist.setImageResource(R.drawable.watchlist);
            }

        }

        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getAccount() == null) listener.Alert();
                else { if (isNetworkAvailable()) {
                    PostBody postMovie;
                    if (watchlist.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.watchlist).getConstantState()))
                        postMovie = new PostBody(mApp.movie, movie.getId(), mApp.watchlist, mApp);
                    else postMovie = new PostBody(mApp.movie, movie.getId(), mApp.favorite, mApp);
                    Call<PostResponse> call = mApp.getApiService().MarkWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                    call.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if (response.body().getStatusCode() == 1)
                                watchlist.setImageResource(R.drawable.watchlist_active);
                            else if (response.body().getStatusCode() == 13)
                                watchlist.setImageResource(R.drawable.watchlist);
                            Call<MoviesListResponse> call1 = mApp.getApiService().getMoviesWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
                            call1.enqueue(new Callback<MoviesListResponse>() {
                                @Override
                                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                                    mApp.getAccount().setWatchListMovies(response.body().getResults());
                                }

                                @Override
                                public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<PostResponse> call, Throwable t) {

                        }
                    });
                } else
                {
                    realm.beginTransaction();
                    MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", getItem(position).getId()).findFirst();
                    Action post = new Action();
                    post.setId(movie.getId());

                    post.setMediaType("movie");
                    if (watchlist.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.watchlist).getConstantState())) {
                        watchlist.setImageResource(R.drawable.watchlist_active);
                        post.setActionType("watchlist");
                        movie.setWatchlist(true);
                        mApp.getAccount().getWatchListMovies().add(new Movie().getMovie(movie));
                    } else {
                        watchlist.setImageResource(R.drawable.watchlist);
                        post.setActionType("removewatchlist");
                        RealmResults<MovieRealm> movies = realm.where(MovieRealm.class).equalTo("id", movie.getId()).findAll();
                        for ( MovieRealm r : movies)
                            r.setWatchlist(false);
                    }
                    realm.copyToRealm(post);
                    realm.commitTransaction();
                }
                }
            }
        });

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getAccount() == null) listener.Alert();
                else if (isNetworkAvailable()) {
                    PostBody postMovie;
                    if (favourite.getDrawable().getConstantState().equals(getContext().getResources().getDrawable(R.drawable.favorite).getConstantState()))
                        postMovie = new PostBody(mApp.movie, movie.getId(), mApp.favorite, mApp);
                    else postMovie = new PostBody(mApp.movie, movie.getId(), mApp.watchlist, mApp);
                    Call<PostResponse> call = mApp.getApiService().PostFavorite(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                    call.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if (response.body().getStatusCode() == 1) {
                                favourite.setImageResource(R.drawable.favorite_active);
                            } else if (response.body().getStatusCode() == 13)
                                favourite.setImageResource(R.drawable.favorite);
                            Call<MoviesListResponse> call1 = mApp.getApiService().getFavoritesMovies(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
                            call1.enqueue(new Callback<MoviesListResponse>() {
                                @Override
                                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                                    mApp.getAccount().setFavoriteMovies(response.body().getResults());
                                }

                                @Override
                                public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<PostResponse> call, Throwable t) {
                        }
                    });
                } else {
                    realm.beginTransaction();
                    MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", getItem(position).getId()).findFirst();
                    Action post = new Action();
                    post.setId(movie.getId());
                    post.setMediaType("movie");
                    if (favourite.getDrawable().getConstantState().equals(context.getDrawable(R.drawable.favorite).getConstantState())) {
                        favourite.setImageResource(R.drawable.favorite_active);
                        post.setActionType("favorite");
                        movie.setFavorite(true);
                        mApp.getAccount().getFavoriteMovies().add(new Movie().getMovie(movie));
                    } else
                    {

                        favourite.setImageResource(R.drawable.favorite);
                        post.setActionType("removefavorite");
                        RealmResults<MovieRealm> movies = realm.where(MovieRealm.class).equalTo("id", getItem(position).getId()).findAll();
                        for ( MovieRealm r : movies)
                        r.setFavorite(false);
                        movie.setFavorite(false);
                    }
                    realm.copyToRealm(post);
                    realm.commitTransaction();
                }
            }
        });

        if (movie.getReleaseDate() != null && movie.getReleaseDate() != ("")) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = new Date();
            try {
                startDate = df.parse(movie.getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String year = movie.getReleaseDate();
            year = year.substring(0, 4);
            movieName.setText(movie.getTitle() + " (" + year + ")");
            date.setText((new SimpleDateFormat("d MMMM yyyy")).format(startDate).toString());
        } else {
            date.setText("");
            movieName.setText(movie.getOriginalTitle());
        }

        voteAverage.setText(String.valueOf(movie.getVoteAverage()));

        Glide.with(context)
                .load(movie.getSmallFullPosterPath(getContext()))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(R.drawable.movies)
                .into((ImageView) newView.findViewById(R.id.imageView));
        return newView;
    }

    private void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context.getApplicationContext(), android.R.style.Theme_Black);
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


