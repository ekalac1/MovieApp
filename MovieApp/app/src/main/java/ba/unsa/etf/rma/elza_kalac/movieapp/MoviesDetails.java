package ba.unsa.etf.rma.elza_kalac.movieapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MoviesDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_details);
        TextView movieId=(TextView)findViewById(R.id.textView);
        movieId.setText(getIntent().getStringExtra("id"));
    }
}
