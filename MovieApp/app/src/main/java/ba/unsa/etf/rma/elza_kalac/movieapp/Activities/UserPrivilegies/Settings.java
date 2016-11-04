package ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class Settings extends AppCompatActivity {
    MovieApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mApp=(MovieApplication)getApplicationContext();

        TextView name = (TextView)findViewById(R.id.name);
        name.setText(mApp.getAccount().getName());
        TextView username = (TextView)findViewById(R.id.username);
        username.setText(mApp.getAccount().getUsername());

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
