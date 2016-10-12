package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Episode;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class EpisodeAdapter extends ArrayAdapter<Episode> {

    int resource;
    Context context;


    public EpisodeAdapter(Context _context, int _resource, List<Episode> items) {
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


        Episode episode = getItem(position);
        TextView episodeName = (TextView)newView.findViewById(R.id.episode_name);
        TextView episodeRate = (TextView)newView.findViewById(R.id.episode_rate);
        TextView episodeDate = (TextView)newView.findViewById(R.id.episode_date);

        episodeName.setText(episode.getName());
        episodeRate.setText(String.valueOf(episode.getVoteAverage()));
        episodeDate.setText(episode.getAirDate());

        return newView;
    }

}
