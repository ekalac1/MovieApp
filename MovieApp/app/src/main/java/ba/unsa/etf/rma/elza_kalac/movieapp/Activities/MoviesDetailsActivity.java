package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.CreditsResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ReviewResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.CastGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.ClickListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.RecyclerTouchListener;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.ReviewListAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDetailsActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public Movie movie;
    public int movieID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        final TextView movieId = (TextView) findViewById(R.id.movies_detalis_title);
        final TextView date = (TextView) findViewById(R.id.movies_detalis_release_date);
        final TextView temp = (TextView) findViewById(R.id.movies_detalis_genres);
        final TextView directors = (TextView)findViewById(R.id.directors_name);
        final TextView about = (TextView)findViewById(R.id.about);
        final TextView writers = (TextView)findViewById(R.id.writers_list);
        final TextView stars= (TextView)findViewById(R.id.stars_list);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final RecyclerView review = (RecyclerView)findViewById(R.id.reviews);

        review.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                TextView text = (TextView)findViewById(R.id.review);
                if(text.getMaxLines()==3)

                {
                    text.setMaxLines(300);
                }
                else
                {
                    text.setMaxLines(3);
                }


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


       /* final ListView review = (ListView)findViewById(R.id.reviews);
        review.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        }); */

        movieID = getIntent().getIntExtra("id", 0);

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(movieID, ApiClient.API_KEY, "credits,reviews");

        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                movie = response.body();
                movieId.setText(movie.getOriginalTitle());
                date.setText(movie.getReleaseDate());
                temp.setText(movie.getGenres());
                Glide.with(getApplicationContext())
                        .load(movie.getFullPosterPath())
                        .into((ImageView) findViewById(R.id.movies_detalis_image));
                directors.setText(movie.getCredits().getDirectors());
                writers.setText(movie.getCredits().getWriters());
                about.setText(movie.getOverview());
                stars.setText(movie.getCredits().getStars());
                CastGridAdapter mAdapter = new CastGridAdapter(movie.getCredits().getCast());
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setAdapter(mAdapter);
                ReviewListAdapter reviewListAdapter = new ReviewListAdapter(movie.getReviews().getResults());
                RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(getApplicationContext());
                //LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                review.setLayoutManager(reviewLayoutManager);
                review.setAdapter(reviewListAdapter);


               /* final ReviewListAdapter reviewAdapter = new ReviewListAdapter(getApplicationContext(), R.layout.review_element, movie.getReviews().getResults());
                review.setAdapter(reviewAdapter); */
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
