package ba.unsa.etf.rma.elza_kalac.movieapp;

import android.app.Activity;
import android.os.Bundle;
import android.test.mock.MockDialogInterface;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.uwetrottmann.tmdb2.Tmdb;
import com.uwetrottmann.tmdb2.entities.*;
import com.uwetrottmann.tmdb2.services.MoviesService;

import java.io.IOException;

import ba.unsa.etf.rma.elza_kalac.movieapp.MoviesDetailsPackage.MovieDetalis;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDetailsActivity extends Activity {
    public int movieID;
    private final static String API_KEY = "14f1ed2b33d9c95e73a70d058755a031";
    private static final String TAG = MainActivity.class.getSimpleName();
    public MovieDetalis movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        TextView movieId=(TextView)findViewById(R.id.textView);
        movieID=(getIntent().getIntExtra("id", 0));


        /*if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
        }

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        final Call<Movie> movieCall=apiService.getMovieDetails(movieID, API_KEY);
        movieCall.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
               Movie movie = response.body();
                Log.d("overview ",":"+movie.getOverview());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {

            }
        }); */
       // movieId.setText(movie.getOriginalTitle());

        // Create an instance of the service you wish to use
// you can keep this around
        Tmdb tmdb = new Tmdb(API_KEY);
        MoviesService movieService = tmdb.moviesService();
        AppendToResponse novi=new AppendToResponse();
//
// Call any of the available endpoints
        Call<com.uwetrottmann.tmdb2.entities.Movie> call = movieService.summary(movieID, null, null);
        try {
            com.uwetrottmann.tmdb2.entities.Movie movie = call.execute().body();
            movieId.setText(movie.title);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
