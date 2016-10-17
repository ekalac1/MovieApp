package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVShowDetails extends AppCompatActivity {

    private static final String TAG = MovieActivity.class.getSimpleName();
    public TvShow tvshow;
    public int tvshowID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_details);

        getSupportActionBar().setTitle(R.string.tv_show);

        tvshowID = getIntent().getIntExtra("id", 0);
        final Intent intent = new Intent(getApplicationContext(), Seasons.class);
        intent.putExtra("id", tvshowID);

        final TextView movieId = (TextView) findViewById(R.id.tv_show_detalis_title);
        final TextView date = (TextView) findViewById(R.id.tv_show_detalis_release_date);
        final TextView temp = (TextView) findViewById(R.id.tv_show_detalis_genres);
        final TextView directors = (TextView)findViewById(R.id.tv_show_directors_name);
        final TextView about = (TextView)findViewById(R.id.tv_show_about);
        final TextView writers = (TextView)findViewById(R.id.tv_show_writers_list);
        final TextView stars= (TextView)findViewById(R.id.tv_show_stars_list);
        final RecyclerView cast = (RecyclerView) findViewById(R.id.tv_show_cast);
        final TextView votes = (TextView)findViewById(R.id.tv_show_votes);
        final TextView seasonsList = (TextView)findViewById(R.id.season_list);
        final TextView yearsList = (TextView)findViewById(R.id.years_list);
        final TextView seeAll = (TextView)findViewById(R.id.see_all);

        yearsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        seasonsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<TvShow> call = apiService.getTvShowDetails(tvshowID, ApiClient.API_KEY, "credits");

        call.enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                tvshow = response.body();
                movieId.setText(tvshow.getName());
                intent.putExtra("name", tvshow.getName());
                String seasonList="";
                for (int i=1; i<=tvshow.getSeasons().size(); i++)
                {
                    seasonList=seasonList+String.valueOf(i)+" ";
                }
                seasonsList.setText(seasonList);

                if (tvshow.getFirstAirDate()!=null)
                {
                    DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                    Date startDate=new Date();
                    Date endDate = new Date();
                    try {
                        startDate = df.parse(tvshow.getFirstAirDate());
                        if (tvshow.getLastAirDate()!="")
                            endDate=df.parse(tvshow.getLastAirDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String tempe=((new SimpleDateFormat("yyyy")).format(startDate).toString());
                    int startYear=Integer.parseInt(tempe);
                    String m = "-";
                    String temp2="";
                    if (tvshow.getLastAirDate()!=null)
                    {
                        temp2=(new SimpleDateFormat("yyyy")).format(endDate).toString();

                        int endYear=Integer.parseInt(temp2);
                        List<Integer> yearList = new ArrayList<Integer>();
                        String list = "";
                        for (int i=startYear; i<endYear; i++)
                        {
                            yearList.add(i);
                            list=list+String.valueOf(i)+" ";
                        }
                        yearsList.setText(list);
                    }
                    if (tvshow.getStatus().equals("Returning Series"))
                    date.setText("Tv Series ("+tempe+m+" )");
                    else date.setText("Tv Series ("+tempe+m+temp2+")");
                }
                else {
                    date.setText("");
                }
                temp.setText(tvshow.getGenres());
                Glide.with(getApplicationContext())
                        .load(tvshow.getFullPosterPath(getApplicationContext()))
                        .override(360, 300)
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into((ImageView) findViewById(R.id.tv_show_image));

                if (tvshow.getCredits().getDirectors()!="")
                directors.setText(tvshow.getCredits().getDirectors());
                else
                {
                    directors.setVisibility(View.GONE);
                    TextView temp = (TextView)findViewById(R.id.tv_show_directors);
                    temp.setVisibility(View.GONE);
                }
                if ((tvshow.getCredits().getWriters()!=""))
                writers.setText(tvshow.getCredits().getWriters());
                else {
                    writers.setVisibility(View.GONE);
                    TextView temp = (TextView)findViewById(R.id.tv_show_writers);
                    temp.setVisibility(View.GONE);
                }
                about.setText(tvshow.getOverview());
                if (tvshow.getCredits().getStars()!="")
                {
                    stars.setText(tvshow.getCredits().getStars());
                    CastGridAdapter mAdapter = new CastGridAdapter(getApplicationContext(), tvshow.getCredits().getCast(), "tv");
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                    cast.setLayoutManager(mLayoutManager);
                    cast.setAdapter(mAdapter);
                }
                else
                {
                    stars.setVisibility(View.GONE);
                    TextView temp = (TextView)findViewById(R.id.tv_show_stars);
                    temp.setVisibility(View.GONE);
                    cast.setVisibility(View.GONE);
                }
                votes.setText(String.valueOf(tvshow.getVoteAverage()));

            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });
    }
}
