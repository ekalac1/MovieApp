package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Season;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class SeasonsGridAdapter extends ArrayAdapter<Season> {

    int resource;
    Context context;
    
    public SeasonsGridAdapter(Context _context, int _resource, List<Season> items) {
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
        Season news = getItem(position);
        TextView name=(TextView)newView.findViewById(R.id.season_number);
        name.setText(String.valueOf(position + 1));



        return newView;
    }


}
