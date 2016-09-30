package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.MainActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class SearchResultsAdapter extends ArrayAdapter<Movie> {
    private static final String TAG = MainActivity.class.getSimpleName();

    public List<Cast> actorsList = new ArrayList<>();

    int resource;
    Context context;
    LinearLayout newView;

    public SearchResultsAdapter(Context _context, int _resource, List<Movie> items) {
        super(_context, _resource, items);
        resource = _resource;
        context=_context;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            newView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater li;
            li = (LayoutInflater) getContext().
                    getSystemService(inflater);
            li.inflate(resource, newView, true);
        } else {
            newView = (LinearLayout) convertView;
        }

        Movie movie = getItem(position);
        TextView titleText = (TextView)newView.findViewById(R.id.title);


        if (movie.getReleaseDate()!=(""))
        {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date startDate=new Date();
            try {
                startDate = df.parse(movie.getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            (new SimpleDateFormat("yyyy-mm-dd")).format(startDate);

            String year = movie.getReleaseDate();

            year=year.substring(0,4);

            titleText.setText(movie.getTitle() + " ( " + year + " )");

        }

        if (movie.getPosterPath()!=(""))
        {
            Glide.with(context)
                    .load(movie.getFullPosterPath())
                    .into((ImageView) newView.findViewById(R.id.imageView2));

        }
        return newView;
    }

}
