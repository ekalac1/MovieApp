package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.ActorDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.TVShowDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Review;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.SearchResults;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

/**
 * Created by Hugsby on 14-Nov-16.
 */

public class FIlmographyAdapter extends RecyclerView.Adapter<FIlmographyAdapter.MyViewHolder> {

    private List<SearchResults> castsList;
    public View view;
    public Context context;
    public String activity;

    public interface OnItemClickListener {
        void onItemClick(Review item);
    }

    List<SearchResults> items;
    FIlmographyAdapter.OnItemClickListener listener;

    public FIlmographyAdapter(List<SearchResults> items, FIlmographyAdapter.OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView cast_image;
        public TextView name, character;

        public MyViewHolder(View view_) {
            super(view_);
            view = view_;
            name = (TextView) view.findViewById(R.id.actors_name);
            character = (TextView) view.findViewById(R.id.character);
            cast_image = (ImageView) view.findViewById(R.id.cast_image);
        }
    }


    public FIlmographyAdapter(Context context, List<SearchResults> castsList, String activity) {
        this.context = context;
        this.castsList = castsList;
        this.activity = activity;
    }

    @Override
    public FIlmographyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_element, parent, false);
        return new FIlmographyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FIlmographyAdapter.MyViewHolder holder, final int position) {
        final SearchResults cast = castsList.get(position);
        if (cast.getMediaType().equals("movie"))
            holder.name.setText(cast.getTitle());
        else holder.name.setText(cast.getName());
        Glide.with(context)
                .load(cast.getFullPosterPath(context))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.cast_image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent myIntent;
                    if (cast.getMediaType()=="movie")
                    myIntent = new Intent(context, MoviesDetailsActivity.class);
                    else myIntent = new Intent(context, TVShowDetails.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    myIntent.putExtra("id", cast.getId());
                    context.startActivity(myIntent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return castsList.size();
    }
}

