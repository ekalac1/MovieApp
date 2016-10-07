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

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.SearchResults;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class SearchResultsAdapter extends ArrayAdapter<SearchResults> {

    int resource;
    Context context;
    LinearLayout newView;

    public SearchResultsAdapter(Context _context, int _resource, List<SearchResults> items) {
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

        SearchResults movie = getItem(position);
        TextView titleText = (TextView) newView.findViewById(R.id.title);
        TextView type =(TextView) newView.findViewById(R.id.actors);
        TextView voteAverage=(TextView)newView.findViewById(R.id.search_vote_average);

        voteAverage.setText(String.valueOf(movie.getVoteAverage()));


        if (movie.getMediaType().equals("movie"))
        {
            type.setText(R.string.movie);
            if (movie.getReleaseDate() != null) {
              /*  DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                Date startDate = new Date();
                try {
                    startDate = df.parse(movie.getReleaseDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                (new SimpleDateFormat("yyyy-mm-dd")).format(startDate); */

                String year = movie.getReleaseDate();
                if (year.length()>=4)
                {
                    year = year.substring(0, 4);
                    titleText.setText(movie.getTitle() + " (" + year + ")");
                }
            } else
            {
                titleText.setText(movie.getTitle());
            }

        } else if(movie.getMediaType().equals("tv"))
        {
            type.setText(R.string.tv_show);
            if (movie.getFirstAirDate()!= null) {
              /*  DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                Date startDate = new Date();
                try {
                    startDate = df.parse(movie.getReleaseDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                (new SimpleDateFormat("yyyy-mm-dd")).format(startDate); */


                String year = movie.getFirstAirDate();
                if (year.length()>=4)
                {
                    year = year.substring(0, 4);
                    titleText.setText(movie.getName() + " ( " + year + " )");
                }



            } else
            {
                titleText.setText(movie.getName());
            }
        }
        if (movie.getPosterPath()!=null)
        {
            Glide.with(context)
                    .load(movie.getFullPosterPath(getContext()))
                    .centerCrop()
                    .into((ImageView) newView.findViewById(R.id.imageView2));

        }
        return newView;
    }

}
