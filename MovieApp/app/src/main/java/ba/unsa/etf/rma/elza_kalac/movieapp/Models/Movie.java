package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.CreditsResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.GalleryResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.ReviewResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TrailerResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

/**
 * Created by Laptop on 23.09.2016..
 */
public class Movie {
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
    private int id;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
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
    @SerializedName("genres")
    @Expose
    private List<Genre> genresList = new ArrayList<Genre>();
    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    public TrailerResponse getVideos() {
        return videos;
    }

    public void setVideos(TrailerResponse videos) {
        this.videos = videos;
    }

    @SerializedName("videos")
    private TrailerResponse videos;


    @SerializedName("credits")
    @Expose
    private CreditsResponse credits;

    public CreditsResponse getCredits() {
        return credits;
    }

    public void setCredits(CreditsResponse credits) {
        this.credits = credits;
    }

    public List<Genre> getGenresList() {
        return genresList;
    }

    public String getGenres() {
        String genres = "";
        for (Genre g : genresList) {
            genres = genres + g.getGenre() + ", ";
        }
        if (genres.length()!=0)
        genres = genres.substring(0, genres.length() - 2);
        return genres;
    }

    private ReviewResponse reviews;

    public void setReviews(ReviewResponse reviews) {this.reviews=reviews;}
    public ReviewResponse getReviews() {return reviews;}

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public int getId() {
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

    public double getVoteAverage() {
        return voteAverage;
    }

    @SerializedName("images")
    private GalleryResponse gallery;
    public GalleryResponse getGallery() {return gallery;}

    public String getFullPosterPath(Context c) {;
        return c.getString(R.string.MovieImageBaseAdress)+c.getString(R.string.MovieImageWidth500) + getPosterPath();
    }


}
