package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDetailsActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public Movie movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        final TextView movieId=(TextView)findViewById(R.id.movies_detalis_title);
        final TextView date = (TextView)findViewById(R.id.movies_detalis_release_date);
        final TextView temp = (TextView)findViewById(R.id.movies_detalis_genres);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(getIntent().getIntExtra("id", 0), ApiClient.API_KEY);

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movies = response.body();
                movieId.setText(movies.getOriginalTitle());
                date.setText(movies.getReleaseDate());
                temp.setText(movies.getGenres());
                Glide.with(getApplicationContext())
                        .load(movies.getFullPosterPath())
                        .into((ImageView) findViewById(R.id.movies_detalis_image));
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });




    }
}
