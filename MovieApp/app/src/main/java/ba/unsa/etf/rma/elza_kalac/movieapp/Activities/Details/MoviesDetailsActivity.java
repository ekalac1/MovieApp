package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Login;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Rating;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.CastGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.ReviewListAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication.order;

public class MoviesDetailsActivity extends AppCompatActivity {
    public Movie movie;
    public int movieID;
    ApiInterface apiService;
    MovieApplication mApp;

    Drawable favorite_active;
    Drawable favorite;
    Drawable watchlist_active;
    Drawable watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        mApp = (MovieApplication) getApplicationContext();
        apiService = mApp.getApiService();

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


        movieID = getIntent().getIntExtra("id", 0);

        if (movieID==0)
        {
            Uri data = getIntent().getData();
            String a = String.valueOf(data);
            a = a.replaceAll("\\D+","");
            movieID=Integer.valueOf(a);
        }

        setIcons();

        Call<Movie> call = apiService.getMovieDetails(movieID, ApiClient.API_KEY, "credits,reviews,videos");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
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

                if (movie.getReviews().getResults().size() == 0)
                {
                    TextView r = (TextView) findViewById(R.id.review_label);
                    r.setVisibility(View.INVISIBLE);
                    View v = (View) findViewById(R.id.view___);
                    v.setVisibility(View.INVISIBLE);
                }
                if (movie.getVideos().getResults().size() != 0)
                {
                    play.setVisibility(View.VISIBLE);
                }
                temp.setText(movie.getGenres());
                Glide.with(getApplicationContext())
                        .load(movie.getFullPosterPath(getApplicationContext()))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into((ImageView) findViewById(R.id.movies_detalis_image));

                if (movie.getCredits().getDirectors().equals(""))
                {
                    TextView temp = (TextView) findViewById(R.id.directors_label);
                    temp.setVisibility(View.INVISIBLE);
                }
                else
                {
                    directors.setText(movie.getCredits().getDirectors());
                    TextView temp = (TextView) findViewById(R.id.directors_label);
                    temp.setText(R.string.directors);
                }
                if (movie.getCredits().getWriters().equals(""))
                {
                    writers.setVisibility(View.GONE);
                    TextView temp = (TextView) findViewById(R.id.writers_label);
                    temp.setVisibility(View.GONE);
                }
                else
                {
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
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.watchlist:
                if (mApp.getAccount() == null) Alert();
                else {
                    PostBody post;
                    if (item.getIcon().getConstantState().equals(watchlist.getConstantState()))
                        post = new PostBody(mApp.movie, movieID, mApp.watchlist, mApp);
                    else post = new PostBody(mApp.movie, movieID, mApp.favorite, mApp);
                    Call<PostResponse> call = apiService.MarkWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), post);
                    call.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if (response.body().getStatusCode() == 1)
                                item.setIcon(watchlist_active);
                            else if (response.body().getStatusCode() == 13)
                                item.setIcon(watchlist);
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
                }
                break;
            case R.id.favorite:
                if (mApp.getAccount() == null) Alert();
                else {
                    PostBody post;
                    if (item.getIcon().getConstantState().equals(favorite.getConstantState()))
                        post = new PostBody(mApp.movie, movieID, mApp.favorite, mApp);
                    else post = new PostBody(mApp.movie, movieID, mApp.watchlist, mApp);
                    Call<PostResponse> call = apiService.PostFavorite(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), post);
                    call.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if (response.body().getStatusCode() == 1) {
                                item.setIcon(favorite_active);
                            } else if (response.body().getStatusCode() == 13) {
                                item.setIcon(favorite);
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.icons, menu);
        MenuItem ma0 = menu.getItem(0);
        MenuItem ma1 = menu.getItem(1);

        ma0.setIcon(watchlist);
        ma1.setIcon(favorite);
        if (mApp.getAccount() != null) {
            List<Movie> movie = mApp.getAccount().getFavoriteMovies();
            if (movie != null)
                for (Movie m : mApp.getAccount().getFavoriteMovies())
                    if (m.getId() == movieID) {
                        ma1.setIcon(favorite_active);
                    }
            movie = mApp.getAccount().getWatchListMovies();
            if (movie != null)
                for (Movie m : movie)
                    if (m.getId() == movieID) {
                        ma0.setIcon(watchlist_active);
                    }

        }
        return true;
    }
    public void setIcons() {
        // Old icons
        Drawable dr_favorite_active = getDrawable(R.drawable.favorite_active);
        Drawable dr_favorite = getDrawable(R.drawable.favorite);
        Drawable dr_watchlist_active = getDrawable(R.drawable.watchlist_active);
        Drawable dr_watchlist = getDrawable(R.drawable.watchlist);
        // Bitmaps
        Bitmap bitmap_favorite_active = ((BitmapDrawable) dr_favorite_active).getBitmap();
        Bitmap bitmap_favorite = ((BitmapDrawable) dr_favorite).getBitmap();
        Bitmap bitmap_watchlist_active = ((BitmapDrawable) dr_watchlist_active).getBitmap();
        Bitmap bitmap_watchlist = ((BitmapDrawable) dr_watchlist).getBitmap();
        // Final drawables
        favorite_active = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap_favorite_active, 70, 70, true));
        favorite = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap_favorite, 70, 70, true));
        watchlist_active = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap_watchlist_active, 70, 70, true));
        watchlist = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap_watchlist, 70, 70, true));
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
}
