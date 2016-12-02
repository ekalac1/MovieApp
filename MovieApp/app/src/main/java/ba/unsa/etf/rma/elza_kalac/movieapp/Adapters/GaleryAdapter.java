package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class GaleryAdapter extends RecyclerView.Adapter<GaleryAdapter.MyViewHolder> {

    private List<String> reviewList;
    public View view;
    public Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public MyViewHolder(View view_) {
            super(view_);
            view=view_;
           image=(ImageView)view.findViewById(R.id.galery_element);
        }
    }


    public GaleryAdapter(Context c, List<String> reviewList) {
        this.reviewList=reviewList;
        this.c=c;
    }

    @Override
    public GaleryAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.galery_image_element, parent, false);
        c=parent.getContext();
        return new GaleryAdapter.MyViewHolder(itemView);
    }

    public interface OnItemClickListener {
        void onItemClick(Bitmap item);
    }

    private  List<String> items;
    private GaleryAdapter.OnItemClickListener listener;


    public GaleryAdapter(List<String> items, GaleryAdapter.OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(GaleryAdapter.MyViewHolder holder, int position) {
        String review = reviewList.get(position);
        Glide.with(c)
                .load(review)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.image);
    }


    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
