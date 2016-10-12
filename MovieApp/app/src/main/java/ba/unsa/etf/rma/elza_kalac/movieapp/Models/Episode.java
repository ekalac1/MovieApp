package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("air_date")
    private String airDate;
    @SerializedName("name")
    private String name;
    @SerializedName("vote_average")
    private double voteAverage;

    public String getAirDate() {
        return airDate;
    }

    public String getName() {
        return name;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
