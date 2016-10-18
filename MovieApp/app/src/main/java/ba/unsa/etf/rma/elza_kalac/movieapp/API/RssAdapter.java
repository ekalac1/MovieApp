package ba.unsa.etf.rma.elza_kalac.movieapp.API;

import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Feed;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;


public interface RssAdapter {

        @GET("/data/rss.php?file=topstories.xml")
        Call<Feed> getItems();
    }

