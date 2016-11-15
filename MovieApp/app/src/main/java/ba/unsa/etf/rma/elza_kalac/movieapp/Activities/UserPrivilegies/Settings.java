package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class Settings extends AppCompatActivity {
    MovieApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(R.string.settings);
        mApp=(MovieApplication)getApplicationContext();
        TextView name = (TextView)findViewById(R.id.name);
        name.setText("Elza Kalac");
        TextView username = (TextView)findViewById(R.id.username);
        username.setText(mApp.getAccount().getUsername());
        Switch movies =(Switch)findViewById(R.id.movies_not);
        movies.setChecked(mApp.getAccount().isMoviePushNof());
        movies.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mApp.getAccount().setMoviePushNof(isChecked);
            }
        });
        Switch tvShows = (Switch)findViewById(R.id.tv_not);
        tvShows.setChecked(mApp.getAccount().isTvShowPushNot());
        tvShows.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mApp.getAccount().setTvShowPushNot(isChecked);
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
