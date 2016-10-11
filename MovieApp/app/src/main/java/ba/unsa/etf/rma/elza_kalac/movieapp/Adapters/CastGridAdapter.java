package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class CastGridAdapter extends RecyclerView.Adapter<CastGridAdapter.MyViewHolder> {

    private List<Cast> castsList;
    public View view;
    public Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView cast_image;
        public TextView name, character;

        public MyViewHolder(View view_) {
            super(view_);
            view=view_;
            name=(TextView)view.findViewById(R.id.actors_name);
            character=(TextView)view.findViewById(R.id.character);
            cast_image=(ImageView)view.findViewById(R.id.cast_image);
        }
    }


    public CastGridAdapter(List<Cast> castsList) {
        this.castsList=castsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cast_element, parent, false);
        c=parent.getContext();

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Cast cast = castsList.get(position);
        holder.name.setText(cast.getName());
        holder.character.setText(cast.getCharacter_name());
        Glide.with(c)
                .load(cast.getFullPosterPath())
                .override(105 * 3, 130 * 3)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.cast_image);
    }

    @Override
    public int getItemCount() {
        return castsList.size();
    }
}
