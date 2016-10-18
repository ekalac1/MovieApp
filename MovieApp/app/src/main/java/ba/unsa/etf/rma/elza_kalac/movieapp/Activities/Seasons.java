package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.CastGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.EpisodeAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.SeasonsAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.SeasonsGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Episode;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.SearchResults;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Season;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Seasons extends AppCompatActivity {

    private static final String TAG = MovieActivity.class.getSimpleName();
    TvShow tvshow;
    int tvshowID;
    List<Episode> episodeList;
    String tvShowName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seasons);
        tvshowID = getIntent().getIntExtra("id", 0);
        tvShowName=getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(tvShowName);

        final TextView seasons = (TextView)findViewById(R.id.seasons_text);
        final GridView seasonsList = (GridView)findViewById(R.id.list);
        final TextView seasonYear = (TextView)findViewById(R.id.seasons_year);
        final ListView episodesList = (ListView)findViewById(R.id.episode_list);

        episodesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent (getApplicationContext(), EpisodeDetails.class);

                intent.putExtra("tvID", tvshowID);
                intent.putExtra("seasonID", episodeList.get(position).getSeasonNumber());
                intent.putExtra("episodeID", position+1);
                intent.putExtra("name", tvShowName);
                startActivity(intent);
            }
        });

        seasons.setText(R.string.Seasons1);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShow> call = apiService.getTvShowDetails(tvshowID, ApiClient.API_KEY, "credits");
        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                tvshow = response.body();
                tvshow.getSeasons().remove(0);
                SeasonsGridAdapter g = new SeasonsGridAdapter(getApplicationContext(), R.layout.season_element, tvshow.getSeasons());
                seasonsList.setAdapter(g);
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });


        Call<Season> call1 = apiService.getSeasonEpisodes(tvshowID, 1, ApiClient.API_KEY);
        call1.enqueue(new Callback<Season>() {
            @Override
            public void onResponse(Call<Season> call, Response<Season> response) {
                Season season = response.body();
                EpisodeAdapter e = new EpisodeAdapter(getApplicationContext(), R.layout.episode_element, season.getEpisodes());
                episodesList.setAdapter(e);
                episodeList=season.getEpisodes();
                seasonYear.setText("Season 1 ("+season.getAir_date().substring(0,4)+")");
            }

            @Override
            public void onFailure(Call<Season> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });

        seasonsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Call<Season> call = apiService.getSeasonEpisodes(tvshowID, position + 1, ApiClient.API_KEY);
                call.enqueue(new Callback<Season>() {
                    @Override
                    public void onResponse(Call<Season> call, Response<Season> response) {
                        Season season = response.body();
                        EpisodeAdapter e = new EpisodeAdapter(getApplicationContext(), R.layout.episode_element, season.getEpisodes());
                        episodesList.setAdapter(e);
                        episodeList=season.getEpisodes();
                        seasonYear.setText("Season " + String.valueOf(position+1)+" ("+ season.getAir_date().substring(0, 4)+")");

                    }

                    @Override
                    public void onFailure(Call<Season> call, Throwable t) {
                        // Log error here since request failed
                        Log.e(TAG, t.toString());
                        Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
                    }
                });


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

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
