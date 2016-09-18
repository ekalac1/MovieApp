package ba.unsa.etf.rma.elza_kalac.movieapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Laptop on 22.08.2016..
 */
public class MyAdapter extends ArrayAdapter<Movie> {

    int resource;
    public static final String BASE_URL = "http://image.tmdb.org/t/p/";
    String URLA;



    public MyAdapter(Context context, int _resource, List<Movie> items) {
        super(context, _resource, items);
        resource = _resource; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
// Kreiranje i inflate-anje view klase
        LinearLayout newView;

        if (convertView == null) {
// Ukoliko je ovo prvi put da se pristupa klasi

// Potrebno je kreirati novi objekat i inflate-at ga
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

            TextView ime = (TextView)newView.findViewById(R.id.nazivFilma);
            TextView sport=(TextView)newView.findViewById(R.id.Zanr);
            ImageView slika = (ImageView)newView.findViewById(R.id.imageView);
            TextView ocjene = (TextView)newView.findViewById(R.id.voteAverage);

        //w500 oznacava velicinu slike
        //koja na mom mobitelu odgovara
        URLA=BASE_URL+"w500"+s.getPosterPath();

        ime.setText(s.getOriginalTitle());
        sport.setText(s.getReleaseDate());
        new DownloadImageTask(slika).execute(URLA);
       // slika.setImageResource(R.drawable.unknown);

        ocjene.setText("Vote average: "+s.getVoteAverage().toString());



        return newView;
    }

}


class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        bmImage.setImageBitmap(result);
    }
}



