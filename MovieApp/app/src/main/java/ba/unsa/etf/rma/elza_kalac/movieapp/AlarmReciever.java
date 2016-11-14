package ba.unsa.etf.rma.elza_kalac.movieapp;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MoviesDetailsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.TVShowDetails;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.MovieActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AlarmReciever extends BroadcastReceiver {

    MovieApplication mApp;
    List<Movie> movies, commonMovies;
    List<TvShow> tvShows;
    Context context;
    Calendar calendar;
    ApiInterface apiService;


    @Override
    public void onReceive(Context c, Intent intent) {

        context = c;
        mApp = (MovieApplication) context.getApplicationContext();
        apiService = mApp.getApiService();
        movies = new ArrayList<>();
        commonMovies=new ArrayList<>();
        tvShows = new ArrayList<>();
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        {
            if (mApp.getAccount() != null) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Calendar.getInstance().add(Calendar.DAY_OF_WEEK, 1);
                Calendar gte = Calendar.getInstance();
                gte.add(Calendar.DATE, 1);
                String date = df.format(gte.getTime());
                gte.add(Calendar.DATE, 1);
                String date1 = df.format(gte.getTime());

                Call<MoviesListResponse> call = apiService.DiscoverMovies(ApiClient.API_KEY, "release_date.asc", date, date1);
                call.enqueue(new Callback<MoviesListResponse>() {
                    @Override
                    public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                        movies = response.body().getResults();
                        for (int i=0; i<movies.size(); i++)
                            for (Movie n : mApp.getAccount().getWatchListMovies())
                                if (movies.get(i).getId() == n.getId()) {
                                    commonMovies.add(n);
                                    for(int j=0; j<commonMovies.size(); j++)
                                    {
                                        NotificationCompat.Builder mBuilder =
                                                new NotificationCompat.Builder(context)
                                                        .setSmallIcon(R.drawable.videocamera)
                                                        .setContentTitle(commonMovies.get(j).getTitle())
                                                        .setAutoCancel(true)
                                                        .setContentText(commonMovies.get(j).getOverview());
                                        // Creates an explicit intent for an Activity in your app
                                        Intent resultIntent = new Intent(context, MoviesDetailsActivity.class);
                                         resultIntent.putExtra("id", commonMovies.get(j).getId());
                                        // The stack builder object will contain an artificial back stack for the
                                        // started Activity.
                                        // This ensures that navigating backward from the Activity leads out of
                                        // your application to the Home screen.
                                        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                                        // Adds the back stack for the Intent (but not the Intent itself)
                                        // Adds the Intent that starts the Activity to the top of the stack
                                        stackBuilder.addNextIntent(resultIntent);
                                        stackBuilder.addParentStack(MovieActivity.class);
                                        PendingIntent resultPendingIntent =
                                               PendingIntent.getActivity(context, commonMovies.get(j).getId(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                        mBuilder.setContentIntent(resultPendingIntent);
                                        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                        // mId allows you to update the notification later on.
                                        mNotificationManager.notify(commonMovies.get(j).getId(), mBuilder.build());
                                    }


                                }
                    }

                    @Override
                    public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                    }
                });

                Call<TvShowResponse> tvshowCall = apiService.getAiringTodayTvShows(ApiClient.API_KEY, 1);
                tvshowCall.enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                        tvShows.addAll(response.body().getResults());
                        if (response.body().getTotalPages() != 1) {
                            Call<TvShowResponse> tvshowCall = apiService.getAiringTodayTvShows(ApiClient.API_KEY, 2);
                            tvshowCall.enqueue(new Callback<TvShowResponse>() {
                                @Override
                                public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                    tvShows.addAll(response.body().getResults());
                                    for (TvShow m : tvShows)
                                        for (TvShow n : mApp.getAccount().getWatchListTvShow())
                                            if (m.getId() == n.getId()) {
                                                NotificationCompat.Builder mBuilder =
                                                        new NotificationCompat.Builder(context)
                                                                .setSmallIcon(R.drawable.television)
                                                                .setContentTitle(n.getName())
                                                                .setAutoCancel(true)
                                                                .setContentText(n.getOverview());
                                                // Creates an explicit intent for an Activity in your app
                                                Intent resultIntent = new Intent(context, TVShowDetails.class);
                                                resultIntent.putExtra("id", m.getId());
                                                // The stack builder object will contain an artificial back stack for the
                                                // started Activity.
                                                // This ensures that navigating backward from the Activity leads out of
                                                // your application to the Home screen.
                                                TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                                                // Adds the back stack for the Intent (but not the Intent itself)
                                                // Adds the Intent that starts the Activity to the top of the stack
                                                stackBuilder.addNextIntent(resultIntent);
                                                stackBuilder.addParentStack(MovieActivity.class);
                                                PendingIntent resultPendingIntent =
                                                        PendingIntent.getActivity(context, n.getId(), resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                                mBuilder.setContentIntent(resultPendingIntent);
                                                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                                // mId allows you to update the notification later on.
                                                mNotificationManager.notify(n.getId(), mBuilder.build());
                                            }
                                }

                                @Override
                                public void onFailure(Call<TvShowResponse> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<TvShowResponse> call, Throwable t) {

                    }
                });
            }
        }
    }
}
