package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class SearchResults {

    @SerializedName("budget")
    @Expose
    private int budget;
    @SerializedName("homepage")
    @Expose
    private String homepage;
    @SerializedName("imdb_id")
    @Expose
    private String imdbId;
    @SerializedName("original_title")
    @Expose
    private String originalTitle;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;
    @SerializedName("release_date")
    @Expose
    private String releaseDate;
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
    @SerializedName("genres")
    private List<Genre> genresList = new ArrayList<Genre>();
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

    @SerializedName("media_type")
    private String mediaType;

    public String getMediaType() {return mediaType;}


    public String getFullPosterPath(Context c) {
        String url =c.getString(R.string.MovieImageBaseAdress);
        String url1=c.getString(R.string.MovieImageWidth500);
        return url+url1 + getPosterPath();
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Integer> getEpisodeRuntime() {
        return episodeRuntime;
    }

    public void setEpisodeRuntime(List<Integer> episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Genre> getGenresList() {
        return genresList;
    }

    public void setGenresList(List<Genre> genresList) {
        this.genresList = genresList;
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
}
