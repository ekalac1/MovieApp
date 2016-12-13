package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.GalleryGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Image;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Gallery extends AppCompatActivity {

    int movieID, tvID;
    ApiInterface apiService;
    MovieApplication mApp;
    List<String> images_url;
    GalleryGridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        mApp = (MovieApplication) getApplicationContext();
        apiService = mApp.getApiService();

        images_url=new ArrayList<>();
        final GridView gallery=(GridView)findViewById(R.id.gallery);
        adapter = new GalleryGridAdapter(getApplicationContext(), R.layout.galery_image_element,images_url);
        gallery.setAdapter(adapter);

        movieID=getIntent().getIntExtra("movieID", 0);
        tvID=getIntent().getIntExtra("tvID", 0);

        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ImageView.class);
                intent.putExtra("position", position);
                if (movieID!=0)
                {
                    intent.putExtra("movieID", movieID);
                }
                else
                {
                    intent.putExtra("tvID", tvID);
                }

                startActivity(intent);
            }
        });

        if (movieID!=0)
        {
            Call<Movie> call = apiService.getMovieDetails(movieID, ApiClient.API_KEY, "images");
            call.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    for (Image i: response.body().getGallery().getBackdrops()) {
                        images_url.add(i.getSmallFullPosterPath(getApplicationContext()));
                    }
                    ((BaseAdapter)gallery.getAdapter()).notifyDataSetChanged();
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
                        images_url.add(i.getSmallFullPosterPath(getApplicationContext()));
                    }
                    ((BaseAdapter)gallery.getAdapter()).notifyDataSetChanged();
                }

                @Override
                public void onFailure(Call<TvShow> call, Throwable t) {

                }
            });
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
