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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.FeedItem;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class GalleryGridAdapter extends ArrayAdapter<String> {
    int resource;
    Context context;

    public GalleryGridAdapter(Context _context, int _resource, List<String> items) {
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
        String news= getItem(position);

        ImageView image=(ImageView)newView.findViewById(R.id.galery_element);

        Glide.with(context)
                .load(news)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(image);

        return newView;
    }

}
