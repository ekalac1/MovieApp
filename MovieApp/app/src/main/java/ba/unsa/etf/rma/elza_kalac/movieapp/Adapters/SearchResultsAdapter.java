package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class SearchResultsAdapter extends ArrayAdapter<Movie> {

    int resource;
    public static final String BASE_URL = "http://image.tmdb.org/t/p/";
    String URLA;
    Context context;



    public SearchResultsAdapter(Context _context, int _resource, List<Movie> items) {
        super(_context, _resource, items);
        resource = _resource;
        context=_context;}

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
        TextView titleText = (TextView)newView.findViewById(R.id.title);
        titleText.setText(s.getTitle());

        if (s.getReleaseDate()!=(""))
        {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date startDate=new Date();
            try {
                startDate = df.parse(s.getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            (new SimpleDateFormat("dd/mm/yyyy")).format(startDate);

            String year = s.getReleaseDate();

            TextView date = (TextView)newView.findViewById(R.id.relaseYear);

            date.setText((new SimpleDateFormat("dd MMM yyyy")).format(startDate).toString());
        }

        if (s.getPosterPath()!=(""))
        {
            URLA=BASE_URL+"w500"+s.getPosterPath();
            Glide.with(context)
                    .load(URLA)
                    .into((ImageView) newView.findViewById(R.id.imageView2));
            //za početak ce mi ovdje biti vote average,
            //jer da bih dobila trajanje filma moram povuci film pojedinacno. to cu promjeniti kada budem uradila onClick na element grida
        }

        return newView;
    }

}
