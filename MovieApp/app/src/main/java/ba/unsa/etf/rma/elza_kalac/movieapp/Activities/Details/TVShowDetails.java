package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Login;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Rating;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.CastGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.GaleryAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Image;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication.order;

public class TVShowDetails extends AppCompatActivity {

    TvShow tvshow;
    int tvshowID;
    ApiInterface apiService;
    MovieApplication mApp;
    List<String> images_url;

    Drawable favorite_active;
    Drawable favorite;
    Drawable watchlist_active;
    Drawable watchlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);

        mApp = (MovieApplication) getApplicationContext();
        apiService = mApp.getApiService();
        images_url=new ArrayList<>();

        getSupportActionBar().setTitle(R.string.tv_show);

        tvshowID = getIntent().getIntExtra("id", 0);

        if (tvshowID==0)
        {
            Uri data = getIntent().getData();
            String a = String.valueOf(data);
            a = a.replaceAll("\\D+","");
            tvshowID=Integer.valueOf(a);
        }
        final Intent intent = new Intent(getApplicationContext(), Seasons.class);
        intent.putExtra("id", tvshowID);

        setIcons();

        final TextView movieId = (TextView) findViewById(R.id.tv_show_detalis_title);
        final TextView date = (TextView) findViewById(R.id.tv_show_detalis_release_date);
        final TextView temp = (TextView) findViewById(R.id.tv_show_detalis_genres);
        final TextView directors = (TextView) findViewById(R.id.tv_show_directors);
        final TextView about = (TextView) findViewById(R.id.tv_show_about);
        final TextView writers = (TextView) findViewById(R.id.tv_show_writers);
        final TextView stars = (TextView) findViewById(R.id.tv_show_stars);
        final RecyclerView cast = (RecyclerView) findViewById(R.id.tv_show_cast);
        final RecyclerView gallery =(RecyclerView)findViewById(R.id.tv_gallery);
        final TextView votes = (TextView) findViewById(R.id.tv_show_votes);
        final TextView seasonsList = (TextView) findViewById(R.id.seasons);
        final TextView yearsList = (TextView) findViewById(R.id.years_list);
        final LinearLayout seeAll = (LinearLayout) findViewById(R.id.see_all);
        final TextView rate = (TextView) findViewById(R.id.rate_this_label);
        final TextView gallerySeeAll = (TextView)findViewById(R.id.see_all_galery);

        yearsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        seasonsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        Call<TvShow> call = apiService.getTvShowDetails(tvshowID, ApiClient.API_KEY, "credits,images");

        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                tvshow = response.body();
                movieId.setText(tvshow.getName());
                intent.putExtra("name", tvshow.getName());
                rate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mApp.getAccount() != null) {
                            Intent intent = (new Intent(getApplicationContext(), Rating.class));
                            intent.putExtra("tvID", tvshowID);
                            intent.putExtra("movieName", tvshow.getName());
                            startActivity(intent);
                        } else Alert();
                    }
                });

                for (Image i: response.body().getGallery().getBackdrops()) {
                    images_url.add(i.getFullPosterPath(getApplicationContext()));
                }

                GaleryAdapter gAdapter = new GaleryAdapter(getApplicationContext(), images_url);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                gallery.setLayoutManager(mLayoutManager);
                gallery.setAdapter(gAdapter);

                gallerySeeAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), Gallery.class);
                        intent.putExtra("tvID", tvshowID);
                        startActivity(intent);
                    }
                });


                String seasonList = "";
                if (tvshow.getSeasons().size() == 1) seasonList = String.valueOf(1);
                else
                    for (int i = tvshow.getSeasons().size(); i >0; i--) {
                        seasonList = seasonList + String.valueOf(i) + " ";
                    }
                seasonsList.setText(seasonList);
                if (tvshow.getFirstAirDate() != null) {
                    String tempe = tvshow.getFirstAirDate().substring(0, 4);
                    int startYear = Integer.parseInt(tempe);
                    String m = "-";
                    String temp2 = "";
                    if (tvshow.getLastAirDate() != null) {
                        temp2 = tvshow.getLastAirDate().substring(0, 4);
                        int endYear = Integer.parseInt(temp2);
                        List<Integer> yearList = new ArrayList<Integer>();
                        String list = "";
                        for (int i = endYear; i >= startYear; i--) {
                            yearList.add(i);
                            list = list + String.valueOf(i) + "  ";
                        }
                        yearsList.setText(list);
                    }
                    if (tvshow.getStatus().equals("Returning Series"))
                        date.setText("Tv Series (" + tempe + m + " )");
                    else date.setText("Tv Series (" + tempe + m + temp2 + ")");
                } else {
                    date.setText("");
                    {
                        TextView temp = (TextView) findViewById(R.id.tv_show_seasons_label);
                        temp.setVisibility(View.GONE);
                    }
                    {
                        TextView temp = (TextView) findViewById(R.id.tv_shows_years_label);
                        temp.setVisibility(View.GONE);
                    }
                    {
                        LinearLayout temp = (LinearLayout) findViewById(R.id.see_all);
                        temp.setVisibility(View.GONE);
                    }
                    {
                        View temp = findViewById(R.id.view_);
                        temp.setVisibility(View.GONE);
                    }
                }
                temp.setText(tvshow.getGenres());
                Glide.with(getApplicationContext())
                        .load(tvshow.getFullPosterPath(getApplicationContext()))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into((ImageView) findViewById(R.id.tv_show_image));

                if (tvshow.getCredits().getDirectors() != "") {
                    TextView temp = (TextView) findViewById(R.id.tv_show_directors_label);
                    temp.setText(R.string.directors);
                    directors.setText(tvshow.getCredits().getDirectors());
                } else {
                    directors.setVisibility(View.GONE);
                    TextView temp = (TextView) findViewById(R.id.tv_show_directors_label);
                    temp.setVisibility(View.GONE);
                }
                if ((tvshow.getCredits().getWriters() != "")) {
                    TextView temp = (TextView) findViewById(R.id.tv_show_writers_label);
                    temp.setText(R.string.writers);
                    writers.setText(tvshow.getCredits().getWriters());
                } else {
                    writers.setVisibility(View.GONE);
                    TextView temp = (TextView) findViewById(R.id.tv_show_writers_label);
                    temp.setVisibility(View.GONE);
                }
                about.setText(tvshow.getOverview());
                if (tvshow.getCredits().getStars() != "") {
                    TextView temp = (TextView) findViewById(R.id.tv_show_stars_label);
                    temp.setText(R.string.stars);
                    stars.setText(tvshow.getCredits().getStars());
                    CastGridAdapter mAdapter = new CastGridAdapter(getApplicationContext(), tvshow.getCredits().getCast(), "tv");
                    LinearLayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    cast.setLayoutManager(LayoutManager);
                    cast.setAdapter(mAdapter);
                } else {
                    stars.setVisibility(View.GONE);
                    TextView temp = (TextView) findViewById(R.id.tv_show_stars_label);
                    temp.setVisibility(View.GONE);
                    cast.setVisibility(View.GONE);
                    ImageView tempImage = (ImageView) findViewById(R.id.cast_navigation);
                    tempImage.setVisibility(View.GONE);
                }
                votes.setText(String.valueOf(tvshow.getVoteAverage()));
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
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
                    PostBody postMovie;
                    if (item.getIcon().getConstantState().equals(watchlist.getConstantState()))
                        postMovie = new PostBody(mApp.tvShow, tvshow.getId(), mApp.watchlist, mApp);
                    else postMovie = new PostBody(mApp.tvShow, tvshow.getId(), mApp.favorite, mApp);
                    Call<PostResponse> call = mApp.getApiService().MarkWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                    call.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if (response.body().getStatusCode() == 1)
                                item.setIcon(watchlist_active);
                            else if (response.body().getStatusCode() == 13)
                                item.setIcon(watchlist);
                            Call<TvShowResponse> call1 = apiService.getTvShowWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
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
                }
                break;
            case R.id.favorite:
                if (mApp.getAccount() == null) Alert();
                else {
                    PostBody postMovie;
                    if (item.getIcon().getConstantState().equals(favorite.getConstantState()))
                    postMovie = new PostBody(mApp.tvShow, tvshow.getId(), mApp.favorite, mApp);
                    else postMovie = new PostBody(mApp.tvShow, tvshow.getId(), mApp.watchlist, mApp);
                    Call<PostResponse> call = mApp.getApiService().PostFavorite(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                    call.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if (response.body().getStatusCode() == 1)
                                item.setIcon(favorite_active);
                            else  if (response.body().getStatusCode() == 13)
                                item.setIcon(favorite);
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
            for (TvShow m : mApp.getAccount().getFavoriteTvShows())
                if (m.getId() == tvshowID) {
                    ma1.setIcon(favorite_active);
                }
            for (TvShow m : mApp.getAccount().getWatchListTvShow())
                if (m.getId() == tvshowID) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(TVShowDetails.this);
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
