package ba.unsa.etf.rma.elza_kalac.movieapp;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MovieGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.TvShowGridViewAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.User;
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
    ApiInterface apiService;
    User account;

    boolean pushM, pushTv;

    private static MovieApplication singleton;

    public static MovieApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        SharedPreferences settings = getSharedPreferences("account", 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        //  calendar.set(Calendar.HOUR_OF_DAY, 13);
        // calendar.set(Calendar.MINUTE, 42);
        Intent intentAlarm = new Intent(this, AlarmReciever.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis()+1000,AlarmManager.INTERVAL_DAY,  PendingIntent.getBroadcast(this,1,  intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
        if (settings.getBoolean("accInfo", false))
        {
            final Account a = new Account();
            a.setUsername(settings.getString("accUserName", null));
            a.setSessionId(settings.getString("accSession", null));
            a.setAccountId(settings.getInt("accId", 0));
            a.setName(settings.getString("accName", ""));
            pushM=(settings.getBoolean("accMovie", true));
            pushTv=(settings.getBoolean("accTv", true));
            setAccount(a);
            Call<MoviesListResponse> call=apiService.getFavoritesMovies(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), order);
            call.enqueue(new Callback<MoviesListResponse>() {
                @Override
                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                    account.setFavoriteMovies(response.body().getResults());
                }
                @Override
                public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                }
            });
            Call<MoviesListResponse> call1 = apiService.getMoviesWatchList(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), order);
            call1.enqueue(new Callback<MoviesListResponse>() {
                @Override
                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                    account.setWatchListMovies(response.body().getResults());
                }
                @Override
                public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                }
            });
            Call<TvShowResponse> call2 = apiService.getFavoritesTvShows(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), order);
            call2.enqueue(new Callback<TvShowResponse>() {
                @Override
                public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                    account.setFavoriteTvShows(response.body().getResults());
                }
                @Override
                public void onFailure(Call<TvShowResponse> call, Throwable t) {

                }
            });
            Call<TvShowResponse> call3 = apiService.getTvShowWatchList(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), order);
            call3.enqueue(new Callback<TvShowResponse>() {
                @Override
                public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                    account.setWatchListTvShow(response.body().getResults());
                }
                @Override
                public void onFailure(Call<TvShowResponse> call, Throwable t) {

                }
            });
        }
        else
            setAccount(null);
    }
    public ApiInterface getApiService() {
        return apiService;
    }

    public void setAccount(Account a) {
        if (a!=null)
        {
            User n = new User();
            n.setName(a.getName());
            n.setSessionId(a.getSessionId());
            n.setUsername(a.getUsername());
            n.setAccountId(a.getAccountId());
            n.setMoviePushNof(pushM);
            n.setTvShowPushNot(pushTv);
            account=n;
        }
        else account=null;
    }

    public User getAccount() {
        return account;
    }
}
