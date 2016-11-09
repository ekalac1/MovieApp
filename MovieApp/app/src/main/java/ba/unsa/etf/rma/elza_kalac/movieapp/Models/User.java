package ba.unsa.etf.rma.elza_kalac.movieapp.Models;


import java.util.List;

public class User {

    private String username;
    private String name;
    private int accountId;
    private String sessionId;

    private List<Movie> favoriteMovies;
    private List<Movie> ratedMovies;
    private List<Movie> watchListMovies;
    private List<TvShow> favoriteTvShows;
    private List<TvShow> ratedTvShow;
    private List<TvShow> watchListTvShow;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public List<Movie> getFavoriteMovies() {
        return favoriteMovies;
    }

    public void setFavoriteMovies(List<Movie> favoriteMovies) {
        this.favoriteMovies = favoriteMovies;
    }

    public List<Movie> getRatedMovies() {
        return ratedMovies;
    }

    public void setRatedMovies(List<Movie> ratedMovies) {
        this.ratedMovies = ratedMovies;
    }

    public List<Movie> getWatchListMovies() {
        return watchListMovies;
    }

    public void setWatchListMovies(List<Movie> watchListMovies) {
        this.watchListMovies = watchListMovies;
    }

    public List<TvShow> getFavoriteTvShows() {
        return favoriteTvShows;
    }

    public void setFavoriteTvShows(List<TvShow> favoriteTvShows) {
        this.favoriteTvShows = favoriteTvShows;
    }

    public List<TvShow> getRatedTvShow() {
        return ratedTvShow;
    }

    public void setRatedTvShow(List<TvShow> ratedTvShow) {
        this.ratedTvShow = ratedTvShow;
    }

    public List<TvShow> getWatchListTvShow() {
        return watchListTvShow;
    }

    public void setWatchListTvShow(List<TvShow> watchListTvShow) {
        this.watchListTvShow = watchListTvShow;
    }
}
