package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Laptop on 10.10.2016..
 */
public class Season {
    @SerializedName("episode_count")
    private int episodeCount;

    public int getEpisodeCount() {
        return episodeCount;
    }
}
