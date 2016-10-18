package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Review;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.MyViewHolder> {

    private List<Review> reviewList;
    public View view;
    public Context c;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, readmore;
        public TextView expandableTextView;

        public MyViewHolder(View view_) {
            super(view_);
            view=view_;
            name=(TextView)view.findViewById(R.id.name);
            expandableTextView=(TextView)view.findViewById(R.id.review);
            readmore=(TextView)view.findViewById(R.id.readmore);
            view_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (expandableTextView.getMaxLines()==3)
                    {
                        expandableTextView.setMaxLines(300);

                        readmore.setText(c.getString(R.string.Hide));
                    }

                    else
                    {
                        expandableTextView.setMaxLines(3);
                        readmore.setText(c.getString(R.string.Read_more));
                    }

                }
            });
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

}
