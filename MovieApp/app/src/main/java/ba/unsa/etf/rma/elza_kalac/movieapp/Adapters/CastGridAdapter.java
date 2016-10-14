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

import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.ActorDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Review;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class CastGridAdapter extends RecyclerView.Adapter<CastGridAdapter.MyViewHolder> {

    private List<Cast> castsList;
    public View view;
    public Context context;

    public interface OnItemClickListener {
        void onItemClick(Review item);
    }

    private List<Review> items;
    private OnItemClickListener listener;

    public CastGridAdapter(List<Review> items, OnItemClickListener listener) {
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


    public CastGridAdapter(Context context, List<Cast> castsList) {
        this.context=context;
        this.castsList = castsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_element, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Cast cast = castsList.get(position);
        holder.name.setText(cast.getName());
        holder.character.setText(cast.getCharacter_name());
        Glide.with(context)
                .load(cast.getFullPosterPath())
                .override(105 * 3, 130 * 3)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.cast_image);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myIntent=new Intent(context, ActorDetails.class);
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
