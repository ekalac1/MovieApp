package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.GalleryGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.PagerAdapters.CustomPagerAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Image;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageView extends AppCompatActivity {

    int movieID, tvID;
    ApiInterface apiService;
    MovieApplication mApp;
    List<String> images_url;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        mApp = (MovieApplication) getApplicationContext();
        apiService = mApp.getApiService();

        images_url=new ArrayList<>();
        mViewPager = (ViewPager) findViewById(R.id.pager);

        movieID=getIntent().getIntExtra("movieID", 0);
        tvID=getIntent().getIntExtra("tvID", 0);

        getSupportActionBar().hide();

        if (movieID!=0)
        {
            Call<Movie> call = apiService.getMovieDetails(movieID, ApiClient.API_KEY, "images");
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    for (Image i: response.body().getGallery().getBackdrops()) {
                        images_url.add(i.getFullPosterPath(getApplicationContext()));
                    }

                    CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getApplicationContext(), images_url);
                    mViewPager.setAdapter(mCustomPagerAdapter);
                    mViewPager.setCurrentItem(getIntent().getIntExtra("position", 0));
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {

                }
            });
        }
        else if (tvID!=0)
        {
            Call<TvShow> call = apiService.getTvShowDetails(tvID, ApiClient.API_KEY, "images");
            call.enqueue(new Callback<TvShow>() {
                @Override
                public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                    for (Image i: response.body().getGallery().getBackdrops()) {
                        images_url.add(i.getFullPosterPath(getApplicationContext()));
                    }
                    CustomPagerAdapter mCustomPagerAdapter = new CustomPagerAdapter(getApplicationContext(), images_url);
                    mViewPager.setAdapter(mCustomPagerAdapter);
                    mViewPager.setCurrentItem(getIntent().getIntExtra("position", 0));

                }

                @Override
                public void onFailure(Call<TvShow> call, Throwable t) {

                }
            });
        }
    }





}
