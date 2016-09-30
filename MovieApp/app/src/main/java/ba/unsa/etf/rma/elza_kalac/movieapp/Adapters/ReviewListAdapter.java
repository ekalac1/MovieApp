package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Cast;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Review;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.MyViewHolder> {

    private List<Review> reviewList;
    public View view;
    public Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView expandableTextView;

        public MyViewHolder(View view_) {
            super(view_);
            view=view_;
            name=(TextView)view.findViewById(R.id.name);
            expandableTextView=(TextView)view.findViewById(R.id.review);
        }
    }


    public ReviewListAdapter(List<Review> reviewList) {
        this.reviewList=reviewList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_element, parent, false);
        c=parent.getContext();
        return new MyViewHolder(itemView);
    }

    public interface OnItemClickListener {
        void onItemClick(Review item);
    }

    private  List<Review> items;
    private  OnItemClickListener listener;


    public ReviewListAdapter(List<Review> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }








    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.name.setText(review.getAuthor());
        holder.expandableTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    /*

    int resource;
    Context context;

    public ReviewListAdapter(Context _context, int _resource, List<Review> items) {
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
        Review review=getItem(position);
        TextView name = (TextView)newView.findViewById(R.id.name);
        ExpandableTextView review_text=(ExpandableTextView)newView.findViewById(R.id.expand_text_view);
        name.setText(review.getAuthor());
        review_text.setText(review.getContent());

        return newView;
    } */

}
