package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Login;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Rating;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.CastGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.GaleryAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Image;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.Action;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmCast;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmTvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
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
    CallbackManager callbackManager;
    ShareDialog shareDialog;
    String link;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);

        mApp = (MovieApplication) getApplicationContext();
        apiService = mApp.getApiService();
        images_url = new ArrayList<>();

        getSupportActionBar().setTitle(R.string.tv_show);

        tvshowID = getIntent().getIntExtra("id", 0);

        if (tvshowID == 0) {
            Uri data = getIntent().getData();
            String a = String.valueOf(data);
            a = a.replaceAll("\\D+", "");
            tvshowID = Integer.valueOf(a);
        }
        final Intent intent = new Intent(getApplicationContext(), Seasons.class);
        intent.putExtra("id", tvshowID);


        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        final TextView movieId = (TextView) findViewById(R.id.tv_show_detalis_title);
        final TextView date = (TextView) findViewById(R.id.tv_show_detalis_release_date);
        final TextView temp = (TextView) findViewById(R.id.tv_show_detalis_genres);
        final TextView directors = (TextView) findViewById(R.id.tv_show_directors);
        final TextView about = (TextView) findViewById(R.id.tv_show_about);
        final TextView writers = (TextView) findViewById(R.id.tv_show_writers);
        final TextView stars = (TextView) findViewById(R.id.tv_show_stars);
        final RecyclerView cast = (RecyclerView) findViewById(R.id.tv_show_cast);
        final RecyclerView gallery = (RecyclerView) findViewById(R.id.tv_gallery);
        final TextView votes = (TextView) findViewById(R.id.tv_show_votes);
        final TextView seasonsList = (TextView) findViewById(R.id.seasons);
        final TextView yearsList = (TextView) findViewById(R.id.years_list);
        final LinearLayout seeAll = (LinearLayout) findViewById(R.id.see_all);
        final TextView rate = (TextView) findViewById(R.id.rate_this_label);
        final TextView gallerySeeAll = (TextView) findViewById(R.id.see_all_galery);
        final ImageView Watchlist = (ImageView) findViewById(R.id.watchlist);
        final ImageView Favorite = (ImageView) findViewById(R.id.favorite);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);

        yearsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable())
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
            }
        });

        seasonsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable())
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable())
                    startActivity(intent);
                else
                    Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
            }
        });

        if (mApp.getAccount() != null) {
            List<TvShow> movie = mApp.getAccount().getFavoriteTvShows();
            if (movie != null)
                for (TvShow m : mApp.getAccount().getFavoriteTvShows()) {
                    if (m.getId() == tvshowID) {
                        Favorite.setImageResource(R.drawable.favorite_active);
                    }
                }

            movie = mApp.getAccount().getWatchListTvShow();
            if (movie != null)
                for (TvShow m : movie)
                    if (m.getId() == tvshowID) {
                        Watchlist.setImageResource(R.drawable.watchlist_active);
                    }

        }

        Call<TvShow> call = apiService.getTvShowDetails(tvshowID, ApiClient.API_KEY, "credits,images");

        if (!isNetworkAvailable()) {
            realm.beginTransaction();
            final RealmTvShow tvshow = realm.where(RealmTvShow.class).equalTo("id", tvshowID).findFirst();
            realm.commitTransaction();
            movieId.setText(tvshow.getName());
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
                if (tvshow.getStatus() != null) {
                    if (tvshow.getStatus().equals("Returning Series"))
                        date.setText("Tv Series (" + tempe + m + " )");
                } else date.setText("Tv Series (" + tempe + m + temp2 + ")");
            } else {
                date.setText("");
                {
                    TextView temp1 = (TextView) findViewById(R.id.tv_show_seasons_label);
                    temp1.setVisibility(View.GONE);
                }
                {
                    TextView temp2 = (TextView) findViewById(R.id.tv_shows_years_label);
                    temp2.setVisibility(View.GONE);
                }
                {
                    LinearLayout temp2 = (LinearLayout) findViewById(R.id.see_all);
                    temp2.setVisibility(View.GONE);
                }
                {
                    View temp2 = findViewById(R.id.view_);
                    temp2.setVisibility(View.GONE);
                }
            }
            View tempView = (View) findViewById(R.id.view_);
            tempView.setVisibility(View.INVISIBLE);
            gallerySeeAll.setVisibility(View.INVISIBLE);
            seeAll.setVisibility(View.INVISIBLE);
            about.setText(tvshow.getOverview());
            votes.setText(String.valueOf(tvshow.getVoteAverage()));
            seasonsList.setText(tvshow.getSeasons());
            temp.setText(tvshow.getGenres());
            if (tvshow.getStars() != null && tvshow.getStars() != "") {
                TextView temp1 = (TextView) findViewById(R.id.tv_show_stars_label);
                temp1.setText(R.string.stars);
            }
            stars.setText(tvshow.getStars());
            about.setText(tvshow.getOverview());

            List<Cast> castTemp = new ArrayList<>();

            if (tvshow.getCast() != null) {
                for (RealmCast c : tvshow.getCast())
                    castTemp.add(new Cast().getCast(c));
            }

            CastGridAdapter mAdapter = new CastGridAdapter(getApplicationContext(), castTemp, "tv");
            LinearLayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            cast.setLayoutManager(LayoutManager);
            cast.setAdapter(mAdapter);

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


        }

        Watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getAccount() == null) Alert();
                else {
                    if (isNetworkAvailable()) {
                        PostBody postMovie;
                        if (Watchlist.getDrawable().getConstantState().equals(getDrawable(R.drawable.watchlist).getConstantState()))
                            postMovie = new PostBody(mApp.tvShow, tvshow.getId(), mApp.watchlist, mApp);
                        else
                            postMovie = new PostBody(mApp.tvShow, tvshow.getId(), mApp.favorite, mApp);
                        Call<PostResponse> call = mApp.getApiService().MarkWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                if (response.body().getStatusCode() == 1)
                                    Watchlist.setImageResource(R.drawable.watchlist_active);
                                else if (response.body().getStatusCode() == 13)
                                    Watchlist.setImageResource(R.drawable.watchlist);
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
                    } else {
                        realm.beginTransaction();
                        RealmTvShow tvshow = realm.where(RealmTvShow.class).equalTo("id", tvshowID).findFirst();
                        Action post = new Action();
                        post.setId(tvshow.getId());
                        post.setMediaType("tv");

                        if (Watchlist.getDrawable().getConstantState().equals(getDrawable(R.drawable.watchlist).getConstantState())) {
                            Watchlist.setImageResource(R.drawable.watchlist_active);
                            post.setActionType("watchlist");
                            tvshow.setWatchlist(true);
                            mApp.getAccount().getFavoriteTvShows().add(new TvShow().getTvShow(tvshow));
                        } else {

                            Watchlist.setImageResource(R.drawable.watchlist);
                            post.setActionType("removewatchlist");
                            RealmResults<RealmTvShow> movies = realm.where(RealmTvShow.class).equalTo("id", tvshowID).findAll();
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


        Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getAccount() == null) Alert();
                else {
                    if (isNetworkAvailable()) {
                        PostBody postMovie;
                        if (Favorite.getDrawable().getConstantState().equals(getDrawable(R.drawable.favorite).getConstantState()))
                            postMovie = new PostBody(mApp.tvShow, tvshow.getId(), mApp.favorite, mApp);
                        else
                            postMovie = new PostBody(mApp.tvShow, tvshow.getId(), mApp.watchlist, mApp);
                        Call<PostResponse> call = mApp.getApiService().PostFavorite(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                if (response.body().getStatusCode() == 1)
                                    Favorite.setImageResource(R.drawable.favorite_active);
                                else if (response.body().getStatusCode() == 13)
                                    Favorite.setImageResource(R.drawable.favorite);
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
                        RealmTvShow tvshow = realm.where(RealmTvShow.class).equalTo("id", tvshowID).findFirst();
                        Action post = new Action();
                        post.setId(tvshow.getId());
                        post.setMediaType("tv");

                        if (Favorite.getDrawable().getConstantState().equals(getDrawable(R.drawable.favorite).getConstantState())) {
                            Favorite.setImageResource(R.drawable.favorite_active);
                            post.setActionType("favorite");
                            tvshow.setFavorite(true);
                            mApp.getAccount().getFavoriteTvShows().add(new TvShow().getTvShow(tvshow));
                        } else {

                            Favorite.setImageResource(R.drawable.favorite);
                            post.setActionType("removefavorite");
                            RealmResults<RealmTvShow> movies = realm.where(RealmTvShow.class).equalTo("id", tvshowID).findAll();
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

        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                tvshow = response.body();
                movieId.setText(tvshow.getName());
                link = "https://www.themoviedb.org/tv/" + String.valueOf(tvshowID) + "-" + tvshow.getName().replace(" ", "-");
                realm.beginTransaction();
                RealmTvShow rtvshow = realm.where(RealmTvShow.class).equalTo("id", tvshowID).findFirst();
                if (rtvshow == null) {
                    realm.copyToRealm(tvshow.getRealmTvShow(tvshow));
                    rtvshow = realm.where(RealmTvShow.class).equalTo("id", tvshowID).findFirst();
                }


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

                for (Image i : response.body().getGallery().getBackdrops()) {
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
                    for (int i = tvshow.getSeasons().size(); i > 0; i--) {
                        seasonList = seasonList + String.valueOf(i) + " ";
                    }
                seasonsList.setText(seasonList);
                rtvshow.setSeasons(seasonList);
                rtvshow.setFirstAirDate(tvshow.getFirstAirDate());
                rtvshow.setLastAirDate(tvshow.getLastAirDate());
                rtvshow.setStatus(tvshow.getStatus());
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
                rtvshow.setGenres(tvshow.getGenres());
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
                rtvshow.setOverview(tvshow.getOverview());
                if (tvshow.getCredits().getStars() != "") {
                    TextView temp = (TextView) findViewById(R.id.tv_show_stars_label);
                    temp.setText(R.string.stars);
                    stars.setText(tvshow.getCredits().getStars());
                    rtvshow.setStars(tvshow.getCredits().getStars());

                    if (tvshow.getCredits().getCast() != null) {
                        for (Cast c : tvshow.getCredits().getCast())
                            rtvshow.getCast().add(c.getRealCast());
                    }
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
                realm.copyToRealm(rtvshow);
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
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
                if (isNetworkAvailable()) {
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        final ShareLinkContent content = new ShareLinkContent.Builder()
                                .setContentUrl(Uri.parse(link))
                                .build();
                        shareDialog.show(content);
                    }
                } else
                    Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
                break;
            case R.id.twitter:
                if (isNetworkAvailable()) {
                    TwitterAuthConfig authConfig = new TwitterAuthConfig(ApiClient.TWITTER_KEY, ApiClient.TWITTER_SECRET_KEY);
                    Fabric.with(getApplicationContext(), new TwitterCore(authConfig), new TweetComposer());
                    Intent intent = new TweetComposer.Builder(getApplicationContext())
                            .text(link)
                            .createIntent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else
                    Toast.makeText(getApplicationContext(), R.string.no_network, Toast.LENGTH_LONG).show();
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
