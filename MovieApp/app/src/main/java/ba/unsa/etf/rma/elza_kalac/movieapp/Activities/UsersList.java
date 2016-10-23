package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class UsersList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        RelativeLayout layout = (RelativeLayout ) findViewById(R.id.activity_users_list);

        TextView t = new TextView(this);
        t.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        t.setText("POKUSAJ");
        t.setTextSize(40);
        t.setVisibility(View.VISIBLE);
        layout.addView(t);
    }
}
