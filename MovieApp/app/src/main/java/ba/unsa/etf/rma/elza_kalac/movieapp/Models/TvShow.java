package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.CreditsResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.ReviewResponse;

/**
 * Created by Laptop on 01.10.2016..
 */
public class TvShow {

    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("popularity")
    private double popularity;
    @SerializedName("id")
    private int id;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("overview")
    private String overview;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("origin_country")
    private List<String> originCountry;
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("name")
    private String name;
    @SerializedName("original_name")
    private String originalName;
    @SerializedName("last_air_date")
    private String lastAirDate;
    public String getLastAirDate()
    {return lastAirDate;}
    @SerializedName("status")
    private String status;
    public String getStatus() {return  status;}

    @SerializedName("seasons")
    private List<Season> seasons;
    public List<Season> getSeasons() {return seasons;}
    @SerializedName("genres")
    private List<Genre> genresList = new ArrayList<Genre>();

    public String getGenres() {
        String genres = "";
        for (Genre g : genresList) {
            genres = genres + g.getGenre() + ", ";
        }
        if (genres.length()!=0)
            genres = genres.substring(0, genres.length() - 2);
        return genres;
    }

    @SerializedName("credits")
    private CreditsResponse credits;

    public CreditsResponse getCredits() {
        return credits;
    }

    public void setCredits(CreditsResponse credits) {
        this.credits = credits;
    }

    private ReviewResponse reviews;

    public void setReviews(ReviewResponse reviews) {this.reviews=reviews;}
    public ReviewResponse getReviews() {return reviews;}

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFullPosterPath() {return "http://image.tmdb.org/t/p/" + "w500" + getPosterPath();}
}
