package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.SeasonsGridAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
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

        final TextView t =(TextView)findViewById(R.id.proba);
        actorID=getIntent().getIntExtra("id", 0);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Cast> call = apiService.getActor(actorID, ApiClient.API_KEY);
        call.enqueue(new Callback<Cast>() {
            @Override
            public void onResponse(Call<Cast> call, Response<Cast> response) {

                t.setText(response.body().getName());

            }

            @Override
            public void onFailure(Call<Cast> call, Throwable t) {
                // Log error here since request failed
                Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        });
    }
}
