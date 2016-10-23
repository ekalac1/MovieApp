package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionValues;
import android.util.Log;
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

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.CastGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.ReviewListAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDetailsActivity extends AppCompatActivity {
    public Movie movie;
    public int movieID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);

        getSupportActionBar().setTitle(R.string.movie);

        final TextView movieId = (TextView) findViewById(R.id.movies_detalis_title);
        final TextView date = (TextView) findViewById(R.id.movies_detalis_release_date);
        final TextView temp = (TextView) findViewById(R.id.movies_detalis_genres);
        final TextView directors = (TextView)findViewById(R.id.directors_name);
        final TextView about = (TextView)findViewById(R.id.about);
        final TextView writers = (TextView)findViewById(R.id.writers_list);
        final TextView stars= (TextView)findViewById(R.id.stars_list);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView review = (RecyclerView)findViewById(R.id.reviews);
        final TextView votes = (TextView)findViewById(R.id.votes);
        final ImageView play = (ImageView)findViewById(R.id.star_vote);




        movieID = getIntent().getIntExtra("id", 0);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(movieID, ApiClient.API_KEY, "credits,reviews,videos");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                movieId.setText(movie.getTitle());
                if (movie.getReleaseDate()!=null)
                {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate=new Date();
                    try {
                        startDate = df.parse(movie.getReleaseDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    date.setText((new SimpleDateFormat("dd. MMM yyyy.")).format(startDate).toString());
                }
                else {
                    date.setText("");

                }

                if (movie.getReviews().getResults().size()==0)
                {
                    TextView r = (TextView)findViewById(R.id.reviewtext);
                    r.setVisibility(View.INVISIBLE);
                    View v = (View)findViewById(R.id.view___);
                    v.setVisibility(View.INVISIBLE);
                }
                if (movie.getVideos().getResults().size()==0)
                    play.setVisibility(View.INVISIBLE);

                temp.setText(movie.getGenres());
                Glide.with(getApplicationContext())
                        .load(movie.getFullPosterPath(getApplicationContext()))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into((ImageView) findViewById(R.id.movies_detalis_image));

                directors.setText(movie.getCredits().getDirectors());
                writers.setText(movie.getCredits().getWriters());
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


            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });

        ImageView moviesImage = (ImageView)findViewById(R.id.movies_detalis_image);
        moviesImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), TrailerActivity.class);
                i.putExtra("id", movieID);
                if (movie.getVideos().getResults().size() != 0)
                    startActivity(i);
                else
                    Toast.makeText(getApplicationContext(), R.string.trailer_error, Toast.LENGTH_LONG).show();
            }
        });
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
