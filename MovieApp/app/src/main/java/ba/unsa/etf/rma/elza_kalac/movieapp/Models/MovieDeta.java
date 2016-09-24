package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Laptop on 23.09.2016..
 */
public class MovieDeta {
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("budget")
    @Expose
    private int budget;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public long getId() {
        return id;
    }

    public String getImdbId() {
        return imdbId;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public List<Integer> getEpisodeRuntime() {
        return episodeRuntime;
    }

    public String getStatus() {
        return status;
    }

    public String getTagline() {
        return tagline;
    }

    public String getTitle() {
        return title;
    }

    @SerializedName("original_language")

    @Expose
    private String originalLanguage;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private double popularity;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    @SerializedName("first_air_date")
    @Expose
    private String firstAirDate;
    @SerializedName("revenue")
    @Expose
    private int revenue;
    @SerializedName("runtime")
    @Expose
    private int runtime;
    @SerializedName("episode_run_time")
    @Expose
    private List<Integer> episodeRuntime = new ArrayList<Integer>();
    @Expose
    private String status;
    @SerializedName("tagline")
    @Expose
    private String tagline;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;
    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    public String getResults()
    {
        return title;

    }
}
