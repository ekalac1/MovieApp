package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Login;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Rating;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.CastGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.GaleryAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.ReviewListAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Image;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Review;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.Action;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.MovieRealm;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmReview;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication.order;

public class MoviesDetailsActivity extends AppCompatActivity {
    public Movie movie;
    public int movieID;
    ApiInterface apiService;
    MovieApplication mApp;
    List<String> images_url;
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String link;


    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        mApp = (MovieApplication) getApplicationContext();
        apiService = mApp.getApiService();
        images_url = new ArrayList<>();

        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.movies);

        final TextView movieId = (TextView) findViewById(R.id.movies_detalis_title);
        final TextView date = (TextView) findViewById(R.id.movies_detalis_release_date);
        final TextView temp = (TextView) findViewById(R.id.movies_detalis_genres);
        final TextView directors = (TextView) findViewById(R.id.directors);
        final TextView about = (TextView) findViewById(R.id.about);
        final TextView writers = (TextView) findViewById(R.id.writers);
        final TextView stars = (TextView) findViewById(R.id.stars);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView review = (RecyclerView) findViewById(R.id.reviews);
        final TextView votes = (TextView) findViewById(R.id.votes);
        final ImageView play = (ImageView) findViewById(R.id.play);
        final TextView rate = (TextView) findViewById(R.id.rate_this_label);
        final RecyclerView gallery = (RecyclerView) findViewById(R.id.tv_gallery);
        final TextView gallerySeeAll = (TextView) findViewById(R.id.see_all_galery);
        final ImageView Watchlist = (ImageView) findViewById(R.id.watchlist);
        final ImageView Favorite = (ImageView) findViewById(R.id.favorite);

        movieID = getIntent().getIntExtra("id", 0);

        if (mApp.getAccount() != null) {
            List<Movie> movie = mApp.getAccount().getFavoriteMovies();
            if (movie != null)
                for (Movie m : mApp.getAccount().getFavoriteMovies()) {
                    if (m.getId() == movieID) {
                        Favorite.setImageResource(R.drawable.favorite_active);
                    }
                }

            movie = mApp.getAccount().getWatchListMovies();
            if (movie != null)
                for (Movie m : movie)
                    if (m.getId() == movieID) {
                        Watchlist.setImageResource(R.drawable.watchlist_active);
                    }

        }

        Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    if (mApp.getAccount() == null) Alert();
                    else {
                        PostBody post;
                        if (Favorite.getDrawable().getConstantState().equals(getDrawable(R.drawable.favorite).getConstantState()))
                            post = new PostBody(mApp.movie, movieID, mApp.favorite, mApp);
                        else post = new PostBody(mApp.movie, movieID, mApp.watchlist, mApp);
                        Call<PostResponse> call = apiService.PostFavorite(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), post);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                if (response.body().getStatusCode() == 1) {
                                    Favorite.setImageResource(R.drawable.favorite_active);
                                } else if (response.body().getStatusCode() == 13) {
                                    Favorite.setImageResource(R.drawable.favorite);
                                }
                                Call<MoviesListResponse> call1 = apiService.getFavoritesMovies(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
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
                    }
                } else {
                    if (mApp.getAccount()!=null)
                    {
                        realm.beginTransaction();
                        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieID).findFirst();
                        Action post = new Action();
                        post.setId(movie.getId());

                        post.setMediaType("movie");
                        if (Favorite.getDrawable().getConstantState().equals(getDrawable(R.drawable.favorite).getConstantState())) {
                            Favorite.setImageResource(R.drawable.favorite_active);
                            post.setActionType("favorite");
                            movie.setFavorite(true);
                            mApp.getAccount().getFavoriteMovies().add(new Movie().getMovie(movie));

                        } else {
                            Favorite.setImageResource(R.drawable.favorite);
                            post.setActionType("removefavorite");
                            movie.setFavorite(false);
                            RealmResults<MovieRealm> movies = realm.where(MovieRealm.class).equalTo("id", movie.getId()).findAll();
                            for ( MovieRealm r : movies)
                                r.setFavorite(false);
                        }
                        realm.copyToRealm(post);
                        realm.commitTransaction();
                    } else Alert();

                }
            }
        });

        Watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getAccount() == null) Alert();
                else {
                    if (isNetworkAvailable()) {
                        PostBody post;
                        if (Watchlist.getDrawable().getConstantState().equals(getDrawable(R.drawable.watchlist).getConstantState()))
                            post = new PostBody(mApp.movie, movieID, mApp.watchlist, mApp);
                        else post = new PostBody(mApp.movie, movieID, mApp.favorite, mApp);
                        Call<PostResponse> call = apiService.MarkWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), post);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                if (response.body().getStatusCode() == 1)
                                    Watchlist.setImageResource(R.drawable.watchlist_active);
                                else if (response.body().getStatusCode() == 13)
                                    Watchlist.setImageResource(R.drawable.watchlist);
                                Call<MoviesListResponse> call1 = apiService.getMoviesWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
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
                    } else {
                        realm.beginTransaction();
                        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieID).findFirst();
                        Action post = new Action();
                        post.setId(movie.getId());

                        post.setMediaType("movie");
                        if (Watchlist.getDrawable().getConstantState().equals(getDrawable(R.drawable.watchlist).getConstantState())) {
                            Watchlist.setImageResource(R.drawable.watchlist_active);
                            post.setActionType("watchlist");
                            movie.setWatchlist(true);
                            mApp.getAccount().getWatchListMovies().add(new Movie().getMovie(movie));
                        } else {
                            Watchlist.setImageResource(R.drawable.watchlist);
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


        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        if (movieID == 0) {
            Uri data = getIntent().getData();
            String a = String.valueOf(data);
            a = a.replaceAll("\\D+", "");
            movieID = Integer.valueOf(a);
        }


        if (!isNetworkAvailable()) {
            realm.beginTransaction();
            MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieID).findFirst();
            realm.commitTransaction();
            movieId.setText(movie.getTitle());
            if (movie.getReleaseDate() != null) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date startDate = new Date();
                try {
                    startDate = df.parse(movie.getReleaseDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                date.setText((new SimpleDateFormat("d. MMM yyyy.")).format(startDate).toString());
            } else {
                date.setText("");
            }
            votes.setText(String.valueOf(movie.getVoteAverage()));
            about.setText(movie.getOverview());
            List<Review> temp1 = new ArrayList<>();
            if (movie.getReviews() != null)
                for (RealmReview r : movie.getReviews())
                    temp1.add(new Review().getReview(r));

            ReviewListAdapter reviewListAdapter = new ReviewListAdapter(temp1);
            RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
            review.setLayoutManager(reviewLayoutManager);
            review.setAdapter(reviewListAdapter);


        }

        Call<Movie> call = apiService.getMovieDetails(movieID, ApiClient.API_KEY, "credits,reviews,videos,images");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                realm.beginTransaction();
                MovieRealm realmMovie = realm.where(MovieRealm.class).equalTo("id", movieID).findFirst();
                link = "https://www.themoviedb.org/movie/" + String.valueOf(movieID) + "-" + movie.getTitle().replace(" ", "-");
                movieId.setText(movie.getTitle());
                rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mApp.getAccount() != null) {
                            Intent intent = (new Intent(getApplicationContext(), Rating.class));
                            intent.putExtra("movieID", movieID);
                            intent.putExtra("movieName", movie.getTitle());
                            startActivity(intent);
                        } else {
                            Alert();
                        }
                    }
                });

                for (Image i : response.body().getGallery().getBackdrops()) {
                    images_url.add(i.getFullPosterPath(getApplicationContext()));
                }

                GaleryAdapter gAdapter = new GaleryAdapter(getApplicationContext(), images_url);
                LinearLayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                gallery.setLayoutManager(LayoutManager);
                gallery.setAdapter(gAdapter);

                gallerySeeAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Gallery.class);
                        intent.putExtra("movieID", movieID);
                        startActivity(intent);
                    }
                });

                if (movie.getReleaseDate() != null) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = new Date();
                    try {
                        startDate = df.parse(movie.getReleaseDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    date.setText((new SimpleDateFormat("d. MMM yyyy.")).format(startDate).toString());
                } else {
                    date.setText("");
                }

                if (movie.getReviews().getResults().size() == 0) {
                    TextView r = (TextView) findViewById(R.id.review_label);
                    r.setVisibility(View.INVISIBLE);
                    View v = (View) findViewById(R.id.view___);
                    v.setVisibility(View.INVISIBLE);
                }
                if (movie.getVideos().getResults().size() != 0) {
                    play.setVisibility(View.VISIBLE);
                }
                temp.setText(movie.getGenres());
                Glide.with(getApplicationContext())
                        .load(movie.getFullPosterPath(getApplicationContext()))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into((ImageView) findViewById(R.id.movies_detalis_image));

                if (movie.getCredits().getDirectors().equals("")) {
                    TextView temp = (TextView) findViewById(R.id.directors_label);
                    temp.setVisibility(View.INVISIBLE);
                } else {
                    directors.setText(movie.getCredits().getDirectors());
                    TextView temp = (TextView) findViewById(R.id.directors_label);
                    temp.setText(R.string.directors);
                }
                if (movie.getCredits().getWriters().equals("")) {
                    writers.setVisibility(View.GONE);
                    TextView temp = (TextView) findViewById(R.id.writers_label);
                    temp.setVisibility(View.GONE);
                } else {
                    writers.setText(movie.getCredits().getWriters());
                    TextView temp = (TextView) findViewById(R.id.writers_label);
                    temp.setText(R.string.writers);
                }

                about.setText(movie.getOverview());
                stars.setText(movie.getCredits().getStars());
                votes.setText(String.valueOf(movie.getVoteAverage()));
                CastGridAdapter mAdapter = new CastGridAdapter(getApplicationContext(), movie.getCredits().getCast(), "movie");
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                ReviewListAdapter reviewListAdapter = new ReviewListAdapter(movie.getReviews().getResults());
                RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
                review.setLayoutManager(reviewLayoutManager);
                review.setAdapter(reviewListAdapter);

                if (movie.getReviews() != null) {
                    for (Review r : movie.getReviews().getResults()) {
                        realmMovie.getReviews().add(r.getRealmReview());
                    }
                }
                realm.commitTransaction();
                ImageView moviesImage = (ImageView) findViewById(R.id.movies_detalis_image);
                if (movie.getVideos().getResults().size() != 0)
                    moviesImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(getApplicationContext(), TrailerActivity.class);
                            i.putExtra("id", movieID);
                            startActivity(i);
                        }
                    });
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.facebook:
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    final ShareLinkContent content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(link))
                            .build();
                    shareDialog.show(content);
                }
                break;
            case R.id.twitter:
                TwitterAuthConfig authConfig = new TwitterAuthConfig(ApiClient.TWITTER_KEY, ApiClient.TWITTER_SECRET_KEY);
                Fabric.with(getApplicationContext(), new TwitterCore(authConfig), new TweetComposer());
                Intent intent = new TweetComposer.Builder(getApplicationContext())
                        .text(link)
                        .createIntent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icons, menu);
        return true;
    }

    private void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MoviesDetailsActivity.this);
        builder.setMessage(R.string.message)
                .setTitle(R.string.Sign_in)
                .setPositiveButton(R.string.sign, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
