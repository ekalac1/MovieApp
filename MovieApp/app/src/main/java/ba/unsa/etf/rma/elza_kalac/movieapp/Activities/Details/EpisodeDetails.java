package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
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
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Episode;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EpisodeDetails extends AppCompatActivity {

    int tvShowId, seasonID, episodeID;
    ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_details);

        Intent intent = getIntent();
        tvShowId = intent.getIntExtra("tvID", 0);
        seasonID = intent.getIntExtra("seasonID", 0);
        episodeID = intent.getIntExtra("episodeID", 0);

        getSupportActionBar().setTitle(intent.getStringExtra("name"));

        final TextView episodeTitle = (TextView) findViewById(R.id.episode_title);
        final TextView episodeAirDate = (TextView) findViewById(R.id.episode_air_date);
        final TextView episodeVote = (TextView) findViewById(R.id.episode_vote);
        final TextView episodeOverview = (TextView) findViewById(R.id.episode_overview);
        final RecyclerView episodeCast = (RecyclerView) findViewById(R.id.episode_cast);

        MovieApplication mApp = (MovieApplication) getApplicationContext();
        apiService = mApp.getApiService();
        Call<Episode> call = apiService.getEpisode(tvShowId, seasonID, episodeID, ApiClient.API_KEY, "credits");
        call.enqueue(new Callback<Episode>() {
            @Override
            public void onResponse(Call<Episode> call, Response<Episode> response) {

                Episode episode = response.body();
                if (episode.getAirDate() != null) {
                    String year = episode.getAirDate().substring(0, 4);
                    episodeTitle.setText(episode.getName() + " (" + year + ")");
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    Date startDate = new Date();
                    try {
                        startDate = df.parse(episode.getAirDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    episodeAirDate.setText((new SimpleDateFormat("dd. MMMM yyyy.")).format(startDate).toString());
                }
                episodeVote.setText(String.valueOf(episode.getVoteAverage()) + "/10");
                episodeOverview.setText(episode.getOverview());

                Glide.with(getApplicationContext())
                        .load(episode.getFullPosterPath(getApplicationContext()))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into((ImageView) findViewById(R.id.episode_image));

                CastGridAdapter mAdapter = new CastGridAdapter(getApplicationContext(), episode.getCreditsResponse().getCast(), "movie");
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                episodeCast.setLayoutManager(mLayoutManager);
                episodeCast.setAdapter(mAdapter);
            }


            @Override
            public void onFailure(Call<Episode> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
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
