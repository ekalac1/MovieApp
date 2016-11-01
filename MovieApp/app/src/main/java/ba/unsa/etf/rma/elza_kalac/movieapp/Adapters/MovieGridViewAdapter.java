package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Login;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieGridViewAdapter extends ArrayAdapter<Movie> {

    int resource;
    Context context;
    MovieApplication mApp;



    public MovieGridViewAdapter(Context _context, int _resource, List<Movie> items) {
        super(_context, _resource, items);
        resource = _resource;
        context=_context;}

    public MovieGridViewAdapter(Context _context, int _resource, List<Movie> items, MovieApplication mApp) {
        super(_context, _resource, items);
        resource = _resource;
        context=_context;
        this.mApp=mApp;}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LinearLayout newView;
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
        final Movie movie = getItem(position);
        TextView movieName = (TextView)newView.findViewById(R.id.movieName);
        TextView date=(TextView)newView.findViewById(R.id.relaseDate);
        TextView voteAverage = (TextView)newView.findViewById(R.id.vote_average);
        ImageView favourite = (ImageView)newView.findViewById(R.id.favorite_movie);

        favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mApp.getAccount()==null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(newView.getRootView().getContext());
                builder.setMessage(R.string.message)
                        .setTitle(R.string.Sign_in)
                .setPositiveButton(R.string.sign, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(newView.getRootView().getContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       context.startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                }).show(); }
                else {
                    PostBody postMovie = new PostBody(mApp.movie, movie.getId(), mApp.favorite, mApp);
                    Call<PostResponse> call = mApp.getApiService().PostFavorite(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), postMovie);
                    call.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if (response.body().getStatusCode()==12)
                                Toast.makeText(context, "Dodano u favorite", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<PostResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        if (movie.getReleaseDate()!=(""))
        {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate=new Date();
            try {
                startDate = df.parse(movie.getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String year = movie.getReleaseDate();
            year=year.substring(0, 4);
            movieName.setText(movie.getTitle() + " (" + year + ")");
            date.setText((new SimpleDateFormat("d MMMM yyyy")).format(startDate).toString());
        }
        else {
                date.setText("");
                movieName.setText(movie.getOriginalTitle());
            }

        voteAverage.setText(String.valueOf(movie.getVoteAverage()));

        if (movie.getPosterPath()!=null)
        {
            Glide.with(context)
                    .load(movie.getFullPosterPath(getContext()))
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    .placeholder(R.drawable.movies)
                    .into((ImageView) newView.findViewById(R.id.imageView));
        }
        return newView;
    }

}



