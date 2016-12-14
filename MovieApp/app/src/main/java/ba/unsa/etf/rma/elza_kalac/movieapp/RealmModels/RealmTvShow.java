package ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels;


import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;


public class RealmTvShow extends RealmObject {

    private String posterPath;
    private double popularity;
    private int id;
    private String backdropPath;
    private double voteAverage;
    private String overview;
    private boolean favorite;
    private boolean watchlist;
    private boolean rated;
    private boolean mostPopular;
    private boolean latest;
    private boolean highestRated;
    private String genres;
    private String firstAirDate;
    private String originalLanguage;
    private int voteCount;
    private String name;
    private String originalName;
    private String lastAirDate;
    private String stars;
    private RealmList<RealmCast> cast;

    public RealmList<RealmCast> getCast() {
        return cast;
    }

    public void setCast(RealmList<RealmCast> cast) {
        this.cast = cast;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getSeasons() {
        return seasons;
    }

    public void setSeasons(String seasons) {
        this.seasons = seasons;
    }

    private String seasons;

    public boolean isAiringToday() {
        return airingToday;
    }

    public void setAiringToday(boolean airingToday) {
        this.airingToday = airingToday;
    }

    private boolean airingToday;

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public boolean isMostPopular() {
        return mostPopular;
    }

    public void setMostPopular(boolean mostPopular) {
        this.mostPopular = mostPopular;
    }

    public boolean isLatest() {
        return latest;
    }

    public void setLatest(boolean latest) {
        this.latest = latest;
    }

    public boolean isHighestRated() {
        return highestRated;
    }

    public void setHighestRated(boolean highestRated) {
        this.highestRated = highestRated;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getLastAirDate() {
        return lastAirDate;
    }

    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }

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
