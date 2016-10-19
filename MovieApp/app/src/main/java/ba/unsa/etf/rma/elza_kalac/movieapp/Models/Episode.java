package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import android.content.Context;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.CreditsResponse;

public class Episode {

    @SerializedName("air_date")
    private String airDate;
    @SerializedName("name")
    private String name;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("overview")
    private String overview;
    @SerializedName("still_path")
    private String stillPath;
    @SerializedName("season_number")
    private int seasonNumber;
    @SerializedName("credits")
    private CreditsResponse cast;

    public CreditsResponse getCreditsResponse() {return cast;}


    public String getAirDate() {
        return airDate;
    }

    public String getName() {
        return name;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {return overview;}

    public String getStillPath() {return stillPath;}

    public int getSeasonNumber() {return seasonNumber;}
    public String getFullPosterPath(Context c) {;
        return c.getString(R.string.MovieImageBaseAdress)+c.getString(R.string.MovieImageWidth500) + getStillPath();
    }



}
