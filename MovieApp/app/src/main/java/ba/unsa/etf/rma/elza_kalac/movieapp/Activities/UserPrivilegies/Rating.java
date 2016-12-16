package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.Action;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.MovieRealm;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmTvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Rating extends AppCompatActivity {
    int starNum, movieID, tvShowID;
    ApiInterface apiService;
    MovieApplication mApp;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        movieID = getIntent().getIntExtra("movieID", 0);
        tvShowID = getIntent().getIntExtra("tvID", 0);
        mApp = (MovieApplication) getApplicationContext();
        apiService = mApp.getApiService();

        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();


        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.rate_label);
        actionBar.setDisplayShowTitleEnabled(true);

        final ImageView rating1 = (ImageView) findViewById(R.id.rating1);
        final ImageView rating2 = (ImageView) findViewById(R.id.rating2);
        final ImageView rating3 = (ImageView) findViewById(R.id.rating3);
        final ImageView rating4 = (ImageView) findViewById(R.id.rating4);
        final ImageView rating5 = (ImageView) findViewById(R.id.rating5);
        final ImageView rating6 = (ImageView) findViewById(R.id.rating6);
        final ImageView rating7 = (ImageView) findViewById(R.id.rating7);
        final ImageView rating8 = (ImageView) findViewById(R.id.rating8);
        final ImageView rating9 = (ImageView) findViewById(R.id.rating9);
        final ImageView rating10 = (ImageView) findViewById(R.id.rating10);


        rating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 1;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_empty);
                rating3.setImageResource(R.drawable.star_empty);
                rating4.setImageResource(R.drawable.star_empty);
                rating5.setImageResource(R.drawable.star_empty);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 2;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_empty);
                rating4.setImageResource(R.drawable.star_empty);
                rating5.setImageResource(R.drawable.star_empty);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 3;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_empty);
                rating5.setImageResource(R.drawable.star_empty);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 4;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_empty);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 5;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 6;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 7;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_filled);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 8;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_filled);
                rating8.setImageResource(R.drawable.star_filled);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 9;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_filled);
                rating8.setImageResource(R.drawable.star_filled);
                rating9.setImageResource(R.drawable.star_filled);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum = 10;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_filled);
                rating8.setImageResource(R.drawable.star_filled);
                rating9.setImageResource(R.drawable.star_filled);
                rating10.setImageResource(R.drawable.star_filled);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rate_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        if (item.getItemId() == R.id.rate) {
            if (starNum == 0) {
                Toast.makeText(getApplicationContext(), R.string.empty_rate, Toast.LENGTH_LONG).show();
            } else {
                if (isNetworkAvailable()) {
                    PostBody rate = new PostBody(starNum);
                    if (movieID != 0) {
                        Call<PostResponse> call = apiService.RateMovie(movieID, ApiClient.API_KEY, mApp.getAccount().getSessionId(), rate);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                if (response.body().getStatusCode() == 1 || response.body().getStatusCode() == 12) {
                                    Toast.makeText(getApplicationContext(), R.string.sucess_rate, Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                }
                            }

                            @Override
                            public void onFailure(Call<PostResponse> call, Throwable t) {
                            }
                        });
                    } else if (tvShowID != 0) {
                        Call<PostResponse> call = apiService.RateTvshow(tvShowID, ApiClient.API_KEY, mApp.getAccount().getSessionId(), rate);
                        call.enqueue(new Callback<PostResponse>() {
                            @Override
                            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                if (response.body().getStatusCode() == 1 || response.body().getStatusCode() == 12) {
                                    Toast.makeText(getApplicationContext(), R.string.sucess_rate, Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                }
                            }

                            @Override
                            public void onFailure(Call<PostResponse> call, Throwable t) {
                            }
                        });
                    }
                } else {
                    realm.beginTransaction();
                    if (movieID!=0)
                    {
                        MovieRealm movie = realm.where(MovieRealm.class).equalTo("id", movieID).findFirst();
                        Action post = new Action();
                        post.setId(movie.getId());
                        post.setMediaType("movie");
                        post.setActionType("rate");
                        post.setVote(starNum);
                        movie.setRated(true);

                        mApp.getAccount().getRatedMovies().add(new Movie().getMovie(movie));
                        realm.copyToRealm(post);
                    }
                    else if (tvShowID!=0)
                    {
                        RealmTvShow movie = realm.where(RealmTvShow.class).equalTo("id", tvShowID).findFirst();
                        Action post = new Action();
                        post.setId(movie.getId());
                        post.setMediaType("tv");
                        post.setActionType("rate");
                        post.setVote(starNum);
                        movie.setRated(true);
                        mApp.getAccount().getRatedTvShow().add(new TvShow().getTvShow(movie));
                        realm.copyToRealm(post);
                    }
                    Toast.makeText(getApplicationContext(), R.string.sucess_rate, Toast.LENGTH_LONG).show();
                    realm.commitTransaction();
                    onBackPressed();

                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
