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

public class MovieGridViewAdapter extends ArrayAdapter<Movie> {

    int resource;
    Context context;



    public MovieGridViewAdapter(Context _context, int _resource, List<Movie> items) {
        super(_context, _resource, items);
        resource = _resource;
        context=_context;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LinearLayout newView;
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
        TextView movieName = (TextView)newView.findViewById(R.id.movieName);
        TextView date=(TextView)newView.findViewById(R.id.relaseDate);
        TextView voteAverage = (TextView)newView.findViewById(R.id.vote_average);

        if (movie.getReleaseDate()!=(""))
        {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date startDate=new Date();
            try {
                startDate = df.parse(movie.getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            (new SimpleDateFormat("dd/mm/yyyy")).format(startDate);

            String year = movie.getReleaseDate();
            year=year.substring(0, 4);
            movieName.setText(movie.getTitle() + " (" + year + ")");
            date.setText((new SimpleDateFormat("dd m yyyy")).format(startDate).toString());
        }
        else {
                date.setText("");
                movieName.setText(movie.getOriginalTitle());
            }

        voteAverage.setText(String.valueOf(movie.getVoteAverage()));



       /*
       double vote = Double.valueOf(movie.getVoteAverage());
       if (vote<=2)
        {
            star_one.setImageResource(R.drawable.star_filled);
        }
        else if (vote<=4)
        {
            star_one.setImageResource(R.drawable.star_filled);
            star_two.setImageResource(R.drawable.star_filled);
        }
        else if (vote<=6)
        {
            star_one.setImageResource(R.drawable.star_filled);
            star_two.setImageResource(R.drawable.star_filled);
            star_three.setImageResource(R.drawable.star_filled);
        }
        else if (vote<=8)
        {
            star_one.setImageResource(R.drawable.star_filled);
            star_two.setImageResource(R.drawable.star_filled);
            star_three.setImageResource(R.drawable.star_filled);
            star_four.setImageResource(R.drawable.star_filled);
        }
        else
        {
            star_one.setImageResource(R.drawable.star_filled);
            star_two.setImageResource(R.drawable.star_filled);
            star_three.setImageResource(R.drawable.star_filled);
            star_four.setImageResource(R.drawable.star_filled);
            star_five.setImageResource(R.drawable.star_filled);
        } */



        if (!movie.getPosterPath().equals(""))
        {

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            Glide.with(context)
                    .load(movie.getFullPosterPath(getContext()))
                    .override(width/2, 750)
                    .into((ImageView) newView.findViewById(R.id.imageView));
        }
        return newView;
    }

}



