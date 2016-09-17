package ba.unsa.etf.rma.elza_kalac.movieapp;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Laptop on 22.08.2016..
 */
public class MyAdapter extends ArrayAdapter<Movie> {

    int resource;



    public MyAdapter(Context context, int _resource, List<Movie> items) {
        super(context, _resource, items);
        resource = _resource; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// Kreiranje i inflate-anje view klase
        LinearLayout newView;
        if (convertView == null) {
// Ukoliko je ovo prvi put da se pristupa klasi

// Potrebno je kreirati novi objekat i inflate-at ga
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater) getContext().
                    getSystemService(inflater);
            li.inflate(resource, newView, true);
        } else {
            newView = (LinearLayout) convertView;
        }
        Movie s = getItem(position);

            TextView ime = (TextView)newView.findViewById(R.id.nazivFilma);
            TextView sport=(TextView)newView.findViewById(R.id.Zanr);
            ImageView slika = (ImageView)newView.findViewById(R.id.imageView);
            TextView ocjene = (TextView)newView.findViewById(R.id.voteAverage);

        ime.setText(s.getOriginalTitle());
        sport.setText(s.getReleaseDate());
        slika.setImageResource(R.drawable.unknown);
        ocjene.setText("Vote average: "+s.getVoteAverage().toString());


        return newView;
    }
}

