package ba.unsa.etf.rma.elza_kalac.movieapp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;

public class MovieApplication extends Application {

    public static String watchlist="watchlist";
    public static String favorite="favorite";
    public static String movie="movie";
    public static String tvShow="tv";

    ApiInterface apiService;
    Account account;

    private static MovieApplication singleton;

    public static MovieApplication getInstance(){
        return singleton;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        account=null;
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

    public void setAccount(Account a) {
        this.account=a;
      /*  SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("username", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        if (account==null)
        editor.putString("username", "");
        else
        editor.putString("username", account.getUsername());
        editor.apply(); */
    }
    public Account getAccount() {return account;}
}
