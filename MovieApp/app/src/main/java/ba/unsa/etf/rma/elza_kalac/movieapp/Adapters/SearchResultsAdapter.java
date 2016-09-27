package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ActorsListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.MainActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Actor;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchResultsAdapter extends ArrayAdapter<Movie> {
    private static final String TAG = MainActivity.class.getSimpleName();

    public List<Actor> actorsList = new ArrayList<>();

    int resource;
    Context context;

    public SearchResultsAdapter(Context _context, int _resource, List<Movie> items) {
        super(_context, _resource, items);
        resource = _resource;
        context=_context;}

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

        Movie s = getItem(position);
        TextView titleText = (TextView)newView.findViewById(R.id.title);
        final TextView actors = (TextView)newView.findViewById(R.id.actors);
        titleText.setText(s.getTitle());

        if (s.getReleaseDate()!=(""))
        {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
            Date startDate=new Date();
            try {
                startDate = df.parse(s.getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            (new SimpleDateFormat("dd/mm/yyyy")).format(startDate);

            String year = s.getReleaseDate();

            TextView date = (TextView)newView.findViewById(R.id.relaseYear);

            date.setText((new SimpleDateFormat("dd MMM yyyy")).format(startDate).toString());
        }

        if (s.getPosterPath()!=(""))
        {
            Glide.with(context)
                    .load(s.getFullPosterPath())
                    .into((ImageView) newView.findViewById(R.id.imageView2));

        }
      /*  ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Actor>> call = apiService.getActors(s.getId(), ApiClient.API_KEY);
        call.enqueue(new Callback<List<Actor>>() {
            @Override
            public void onResponse(Call<List<Actor>> call, Response<List<Actor>> response) {
                actorsList = response.body();
//                actors.setText(actorsList.get(0).getName());
            }

            @Override
            public void onFailure(Call<List<Actor>> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
                Toast.makeText(newView.getContext(), R.string.on_failure, Toast.LENGTH_LONG).show();
            }
        }); */

        return newView;
    }

}
