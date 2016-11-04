package ba.unsa.etf.rma.elza_kalac.movieapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.TvShowGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieApplication extends Application {

    public static String watchlist = "watchlist";
    public static String favorite = "favorite";
    public static String movie = "movie";
    public static String tvShow = "tv";
    public static String order = "created_at.desc";
    List<Movie> favoriteMovies;
    List<Movie> ratedMovies;
    List<Movie> watchListMovies;
    List<TvShow> favoriteTvShows;
    List<TvShow> ratedTvShow;
    List<TvShow> watchListTvShow;

    ApiInterface apiService;
    Account account;

    private static MovieApplication singleton;

    public static MovieApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        apiService = ApiClient.getClient().create(ApiInterface.class);
      /*  SharedPreferences sharedPref = this.getSharedPreferences("username", Context.MODE_PRIVATE);
        String username = sharedPref.getString("username", null);
        if (username!="")
        {
            account = new Account();
            account.setUsername(username);
        } */

    }

    public ApiInterface getApiService() {
        return apiService;
    }

    public List<TvShow> getRatedTvShow() {
        return ratedTvShow;
    }

    public void setRatedTvShow(List<TvShow> ratedTvShow) {
        this.ratedTvShow = ratedTvShow;
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

    public List<TvShow> getWatchListTvShow() {
        return watchListTvShow;
    }

    public void setWatchListTvShow(List<TvShow> watchListTvShow) {
        this.watchListTvShow = watchListTvShow;
    }

    public void setAccount(Account a) {
        this.account = a;

      /*  SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("username", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (account==null)
        editor.putString("username", "");
        else
        editor.putString("username", account.getUsername());
        editor.apply(); */
    }

    public Account getAccount() {
        return account;
    }
}
