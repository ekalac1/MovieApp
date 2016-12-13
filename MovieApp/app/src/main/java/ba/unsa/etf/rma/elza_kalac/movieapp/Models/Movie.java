package ba.unsa.etf.rma.elza_kalac.movieapp.Models;

import android.content.Context;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.MovieRealm;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.CreditsResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.GalleryResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.ReviewResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TrailerResponse;


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
    public String getSmallFullPosterPath(Context c) {;
        return c.getString(R.string.MovieImageBaseAdress)+c.getString(R.string.MovieImageWidth342) + getPosterPath();
    }


    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public void setEpisodeRuntime(List<Integer> episodeRuntime) {
        this.episodeRuntime = episodeRuntime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setGenresList(List<Genre> genresList) {
        this.genresList = genresList;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public void setGallery(GalleryResponse gallery) {
        this.gallery = gallery;
    }

    public MovieRealm getMovieRealm (Movie m)
    {
        MovieRealm movie= new MovieRealm();
        movie.setTitle(m.getTitle());
        movie.setReleaseDate(m.getReleaseDate());
        movie.setId(m.getId());
        movie.setVoteAverage(m.getVoteAverage());
        movie.setOverview(m.getOverview());
        return movie;
    }
    public Movie getMovie (MovieRealm m)
    {
        Movie movie = new Movie();
        movie.setTitle(m.getTitle());
        movie.setTitle(m.getTitle());
        movie.setReleaseDate(m.getReleaseDate());
        movie.setId(m.getId());
        movie.setVoteAverage(m.getVoteAverage());
        movie.setOverview(m.getOverview());
        return movie;
    }
}
