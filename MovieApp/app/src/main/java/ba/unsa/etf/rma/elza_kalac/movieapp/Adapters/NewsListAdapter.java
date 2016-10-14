package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.FeedItem;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class NewsListAdapter extends ArrayAdapter<FeedItem> {

    int resource;
    Context context;

    public NewsListAdapter(Context _context, int _resource, List<FeedItem> items) {
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
        FeedItem news= getItem(position);
        TextView newsTitle = (TextView)newView.findViewById(R.id.news_title);
        TextView newsText = (TextView)newView.findViewById(R.id.news_text);

        newsTitle.setText(news.getMtitle());
         newsText.setText(news.getMlink());

        return newView;
    }


}
