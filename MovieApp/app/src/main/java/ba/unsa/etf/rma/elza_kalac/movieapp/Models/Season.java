package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Season {
    @SerializedName("episode_count")
    private int episodeCount;
    @SerializedName("id")
    private int id;
    @SerializedName("air_date")
    private String air_date;
    @SerializedName("season_number")
    private int seasonNumber;
    @SerializedName("episodes")
    private List<Episode> episodes;



    public int getId() {
        return id;
    }

    public String getAir_date() {
        return air_date;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }
}
