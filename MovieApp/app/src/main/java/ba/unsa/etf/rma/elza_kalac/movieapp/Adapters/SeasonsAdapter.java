package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Episode;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Review;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Season;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;


public class SeasonsAdapter extends RecyclerView.Adapter<SeasonsAdapter.MyViewHolder> {

    private List<Season> reviewList;
    public View view;
    public Context c;
    private int selectedPos = 0;
    private ListView e ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view_) {
            super(view_);
            view=view_;
            name=(TextView)view.findViewById(R.id.season_number);
            e=(ListView)view.findViewById(R.id.episode_list);
            view_.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    notifyItemChanged(selectedPos);
                    selectedPos = getLayoutPosition();
                    notifyItemChanged(selectedPos);

                  /*  EpisodeAdapter eAd=new EpisodeAdapter(c, R.layout.episode_element, reviewList.get(selectedPos).getEpisodes());
                    e.setAdapter(eAd); */


                }
            });
        }
    }


    public SeasonsAdapter(List<Season> reviewList) {
        this.reviewList=reviewList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.season_element, parent, false);
        c=parent.getContext();
        return new MyViewHolder(itemView);
    }

    public interface OnItemClickListener {
        void onItemClick(Season item);
    }


    private  List<Season> items;
    private  OnItemClickListener listener;


    public SeasonsAdapter(List<Season> items, OnItemClickListener listener) {
        this.items = items;
        this.listener = listener;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (selectedPos==position){
            // Here I am just highlighting the background
            holder.name.setTextColor(Color.YELLOW);
        }else{
            holder.name.setTextColor(Color.WHITE);
        }
        holder.name.setText(String.valueOf(position + 1));



    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

}
