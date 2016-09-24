package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.MovieDeta;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDetailsActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public MovieDeta movies;
    public static final String BASE_URL = "http://image.tmdb.org/t/p/";
    String URLA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        final TextView movieId=(TextView)findViewById(R.id.movies_detalis_title);
        final TextView date = (TextView)findViewById(R.id.movies_detalis_release_date);



        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieDeta> call = apiService.getMovieDetails(getIntent().getIntExtra("id", 0), ApiClient.API_KEY);

        call.enqueue(new Callback<MovieDeta>() {
            @Override
            public void onResponse(Call<MovieDeta> call, Response<MovieDeta> response) {
                movies = response.body();
                movieId.setText(movies.getOriginalTitle());
                date.setText(movies.getReleaseDate());
                URLA=BASE_URL+"w500"+movies.getPosterPath();
                Glide.with(getApplicationContext())
                        .load(URLA)
                        .into((ImageView) findViewById(R.id.movies_detalis_image));
            }

            @Override
            public void onFailure(Call<MovieDeta> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });




    }
}
