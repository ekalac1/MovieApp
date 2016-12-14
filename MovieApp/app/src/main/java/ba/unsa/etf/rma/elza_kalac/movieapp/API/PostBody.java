package ba.unsa.etf.rma.elza_kalac.movieapp.API;


import com.google.gson.annotations.SerializedName;

import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;

public class PostBody {


    @SerializedName("media_type")
    private String mediaType;
    @SerializedName("media_id")
    private int mediaID;
    @SerializedName("watchlist")
    private boolean watchlist;
    @SerializedName("favorite")
    private boolean favorite;
    @SerializedName("value")
    private double value;

    public PostBody(String mediaType, int mediaID, String type, MovieApplication m) {
        this.mediaType = mediaType;
        this.mediaID = mediaID;
        if (type.equals(m.favorite))
            favorite = true;
        else if (type.equals(m.watchlist))
            watchlist = true;
    }

    public PostBody(double value) {
        this.value = value;
    }
}
