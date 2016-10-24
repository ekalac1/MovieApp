package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActorDetails extends AppCompatActivity {
    public int actorID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_details);

        getSupportActionBar().setTitle(R.string.actor);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left96);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actorID=getIntent().getIntExtra("id", 0);

        final TextView actorName =(TextView)findViewById(R.id.actor_name);
        final TextView actorBirth = (TextView)findViewById(R.id.actor_birthday);
        final TextView actorWeb = (TextView)findViewById(R.id.actor_website);
        final TextView actorBiography = (TextView)findViewById(R.id.actor_biograpy);
        final TextView actorReadMore = (TextView)findViewById(R.id.actor_read_more_label);
        final RecyclerView actorRoles = (RecyclerView)findViewById(R.id.actor_roles);

       actorBiography.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (actorBiography.getMaxLines()==5) {
                   actorBiography.setMaxLines(50);
                   actorReadMore.setText(R.string.Hide);
               }
               else {
                   actorBiography.setMaxLines(5);
                   actorReadMore.setText(R.string.Read_more);
               }
           }
       });
        actorReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actorBiography.getMaxLines()==5) {
                    actorBiography.setMaxLines(50);
                    actorReadMore.setText(R.string.Hide);
                }
                else {
                    actorBiography.setMaxLines(5);
                    actorReadMore.setText(R.string.Read_more);
                }
            }
        });
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Cast> call = apiService.getActor(actorID, ApiClient.API_KEY, "combined_credits");
        call.enqueue(new Callback<Cast>() {
            @Override
            public void onResponse(Call<Cast> call, Response<Cast> response) {
                Cast actor=response.body();

                actorName.setText(actor.getName());

                if (actor.getBirthday()!=null) {
                    DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                    Date startDate = new Date();
                    try {
                        startDate = df.parse(actor.getBirthday());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    TextView temp= (TextView)findViewById(R.id.actor_date_label);
                    temp.setText(R.string.birth_date);
                    actorBirth.setText(new SimpleDateFormat("d. MMM yyyy").format(startDate).toString());
                }
                else
                {
                    actorBirth.setVisibility(View.GONE);
                    TextView temp= (TextView)findViewById(R.id.actor_date_label);
                    temp.setVisibility(View.GONE);

                }
                if (actor.getHomePage()==null || actor.getHomePage().equals(""))
                {
                    TextView temp = (TextView)findViewById(R.id.actor_web_label);
                    temp.setVisibility(View.GONE);
                    actorWeb.setVisibility(View.GONE);
                }
                else {
                    actorWeb.setText(actor.getHomePage());
                    TextView temp = (TextView)findViewById(R.id.actor_web_label);
                    temp.setText(R.string.Website);
                }
                if (actor.getBiography()==null || actor.getBiography().equals(""))
                {
                    actorBiography.setVisibility(View.GONE);
                    TextView temp = (TextView)findViewById(R.id.actor_biograpy_label);
                    TextView readMore = (TextView)findViewById(R.id.actor_read_more_label);
                    readMore.setVisibility(View.GONE);
                    temp.setVisibility(View.GONE);
                }
                else
                {
                    actorBiography.setText(actor.getBiography());
                    TextView temp = (TextView)findViewById(R.id.actor_biograpy_label);
                    TextView readMore = (TextView)findViewById(R.id.actor_read_more_label);
                    readMore.setText(R.string.Read_more);
                    temp.setText(R.string.Biography);
                }






                Glide.with(getApplicationContext())
                        .load(actor.getFullPosterPath(getApplicationContext()))
                        .centerCrop()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into((ImageView)findViewById(R.id.actor_detalis_image));

                CastGridAdapter mAdapter = new CastGridAdapter(getApplicationContext(), actor.getCast().getCast(), "actor");
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                actorRoles.setLayoutManager(mLayoutManager);
                actorRoles.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Cast> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }
}
