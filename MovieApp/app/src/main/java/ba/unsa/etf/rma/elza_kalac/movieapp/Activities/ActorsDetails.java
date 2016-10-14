package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class ActorsDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actors_details);

        getSupportActionBar().setTitle("Actor");



    }
}
