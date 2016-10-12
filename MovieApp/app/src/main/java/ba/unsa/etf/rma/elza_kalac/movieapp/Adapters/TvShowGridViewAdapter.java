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

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

/**
 * Created by Laptop on 01.10.2016..
 */
public class TvShowGridViewAdapter extends ArrayAdapter<TvShow> {

    int resource;
    Context context;


    public TvShowGridViewAdapter(Context _context, int _resource, List<TvShow> items) {
        super(_context, _resource, items);
        resource = _resource;
        context = _context;
    }

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
        TvShow tvShow = getItem(position);
        TextView tvShowName = (TextView) newView.findViewById(R.id.tvShowName);
        TextView date = (TextView) newView.findViewById(R.id.tvShowRelaseDate);
        TextView voteAverage = (TextView)newView.findViewById(R.id.tvShowAverage);

        if (tvShow.getFirstAirDate() != ("")) {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date startDate = new Date();
            try {
                startDate = df.parse(tvShow.getFirstAirDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            (new SimpleDateFormat("dd/mm/yyyy")).format(startDate);

            String year = tvShow.getFirstAirDate();
            year = year.substring(0, 4);
            tvShowName.setText(tvShow.getName() + " (" + year + ")");
            date.setText((new SimpleDateFormat("dd MMM yyyy")).format(startDate).toString());
        } else {
            date.setText("");
            tvShowName.setText(tvShow.getName());
        }


        voteAverage.setText(String.valueOf(tvShow.getVoteAverage()));

        if (tvShow.getPosterPath()!=("")) {

            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            Glide.with(getContext())
                    .load(tvShow.getFullPosterPath(getContext()))
                    .override(width / 2, 750)
                    .into((ImageView) newView.findViewById(R.id.tvShowImageView));
        }
        return newView;
    }

}
