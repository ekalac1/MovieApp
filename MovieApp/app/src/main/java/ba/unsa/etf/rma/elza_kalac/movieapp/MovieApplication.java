package ba.unsa.etf.rma.elza_kalac.movieapp;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PlacesApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PlacesApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.TvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.User;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.Action;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.MovieRealm;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmTvShow;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieApplication extends Application {

    public static String watchlist = "watchlist";
    public static String favorite = "favorite";
    public static String movie = "movie";
    public static String tvShow = "tv";
    public static String order = "created_at.desc";

    SharedPreferences settings;

    ApiInterface apiService;
    PlacesApiInterface placesApiInterface;
    User account;
    Realm realm;

    boolean pushM, pushTv;

    private static MovieApplication singleton;

    @Override
    public void onCreate() {
        super.onCreate();
        singleton = this;
        apiService = ApiClient.getClient().create(ApiInterface.class);
        placesApiInterface = PlacesApiClient.getClient().create(PlacesApiInterface.class);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        settings = getSharedPreferences("account", 0);

        setRealmInstance();

        setAlarmForPushNotifications();

        if (settings.getBoolean("accInfo", false)) {
            loadAccInfo();
        } else
            setAccount(null);

        if (isNetworkAvailable() & account != null) {
          //  SyncActions();
        }
    }


    public void setAccount(Account a) {
        if (a != null) {
            User n = new User();
            n.setName(a.getName());
            n.setSessionId(a.getSessionId());
            n.setUsername(a.getUsername());
            n.setAccountId(a.getAccountId());
            n.setMoviePushNof(pushM);
            n.setTvShowPushNot(pushTv);
            account = n;
        } else account = null;
    }


    private void loadFavoriteMoviesFromRealm() {
        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("favorite", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("favorite", true).findAll();
            List<Movie> temp = new ArrayList<>();
            for (MovieRealm m : rows) {
                temp.add((new Movie()).getMovie(m));
            }
            account.setFavoriteMovies(temp);
        }
    }

    private void loadRatedMoviesFromRealm() {
        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("rated", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("rated", true).findAll();
            List<Movie> temp = new ArrayList<>();
            for (MovieRealm m : rows) {
                temp.add((new Movie()).getMovie(m));
            }
            account.setRatedMovies(temp);
        }
    }

    private void loadWatchlistMoviesFromRealm() {
        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("watchlist", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            RealmResults<MovieRealm> rows = realm.where(MovieRealm.class).equalTo("watchlist", true).findAll();
            List<Movie> temp = new ArrayList<>();
            for (MovieRealm m : rows) {
                temp.add((new Movie()).getMovie(m));
            }
            account.setWatchListMovies(temp);
        }
    }

    private void loadWatchlistTvShowsFromRealm() {
        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<RealmTvShow> rows = realm.where(RealmTvShow.class).equalTo("watchlist", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            RealmResults<RealmTvShow> rows = realm.where(RealmTvShow.class).equalTo("watchlist", true).findAll();
            List<TvShow> temp = new ArrayList<>();
            for (RealmTvShow m : rows) {
                temp.add(new TvShow().getTvShow(m));
            }
            account.setWatchListTvShow(temp);
        }
    }

    private void loadFavoriteTvShowsFromRealm() {
        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<RealmTvShow> rows = realm.where(RealmTvShow.class).equalTo("favorite", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            RealmResults<RealmTvShow> rows = realm.where(RealmTvShow.class).equalTo("favorite", true).findAll();
            List<TvShow> temp = new ArrayList<>();
            for (RealmTvShow m : rows) {
                temp.add(new TvShow().getTvShow(m));
            }
            account.setFavoriteTvShows(temp);
        }
    }

    private void loadRatedTvShowsFromRealm() {
        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<RealmTvShow> rows = realm.where(RealmTvShow.class).equalTo("rated", true).findAll();
            rows.deleteAllFromRealm();
            realm.commitTransaction();
        } else {
            RealmResults<RealmTvShow> rows = realm.where(RealmTvShow.class).equalTo("rated", true).findAll();
            List<TvShow> temp = new ArrayList<>();
            for (RealmTvShow m : rows) {
                temp.add(new TvShow().getTvShow(m));
            }
            account.setRatedTvShow(temp);
        }
    }
    public void SyncActions() {
        SyncFavoriteMovies();
        SyncUnFavoriteMovies();
        SyncFavoriteTvShow();
        SyncUnFavoriteTvShow();
        SyncWatchlistMovie();
        SyncUnWatchlistMovie();
        SyncWatchlistTvShow();
        SyncUnWatchlistTvShow();
        SyncRatedMovies();
        SyncRatedTvShows();
    }

    private void loadAccInfo() {
        final Account a = new Account();
        a.setUsername(settings.getString("accUserName", null));
        a.setSessionId(settings.getString("accSession", null));
        a.setAccountId(settings.getInt("accId", 0));
        a.setName(settings.getString("accName", ""));
        pushM = (settings.getBoolean("accMovie", true));
        pushTv = (settings.getBoolean("accTv", true));
        setAccount(a);
        loadFavoriteMoviesFromRealm();
        loadWatchlistMoviesFromRealm();
        loadWatchlistTvShowsFromRealm();
        loadFavoriteTvShowsFromRealm();
        loadRatedMoviesFromRealm();
        loadRatedTvShowsFromRealm();

        Call<MoviesListResponse> call = apiService.getFavoritesMovies(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), order);
        call.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                account.setFavoriteMovies(response.body().getResults());
                realm.beginTransaction();
                for (Movie m : response.body().getResults()) {
                    MovieRealm movie = m.getMovieRealm(m);
                    movie.setFavorite(true);
                    realm.copyToRealm(movie);
                }
                realm.commitTransaction();
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
                realm.beginTransaction();
                for (Movie m : response.body().getResults()) {
                    MovieRealm movie = m.getMovieRealm(m);
                    movie.setWatchlist(true);
                    realm.copyToRealm(movie);
                }
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<MoviesListResponse> call, Throwable t) {

            }
        });
        Call<MoviesListResponse> ratedMovies = apiService.getMoviesRatings(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), order);
        ratedMovies.enqueue(new Callback<MoviesListResponse>() {
            @Override
            public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                account.setRatedMovies(response.body().getResults());
                realm.beginTransaction();
                for (Movie m : response.body().getResults()) {
                    MovieRealm movie = m.getMovieRealm(m);
                    movie.setRated(true);
                    realm.copyToRealm(movie);
                }
                realm.commitTransaction();
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
                realm.beginTransaction();
                for (TvShow m : response.body().getResults()) {
                    RealmTvShow movie = m.getRealmTvShow(m);
                    movie.setFavorite(true);
                    realm.copyToRealm(movie);
                }
                realm.commitTransaction();

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
                realm.beginTransaction();
                for (TvShow m : response.body().getResults()) {
                    RealmTvShow movie = m.getRealmTvShow(m);
                    movie.setWatchlist(true);
                    realm.copyToRealm(movie);
                }
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });

        Call<TvShowResponse> ratedTvShow = apiService.getTvShowRatings(a.getAccountId(), ApiClient.API_KEY, a.getSessionId(), order);
        ratedTvShow.enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                account.setRatedTvShow(response.body().getResults());
                realm.beginTransaction();
                for (TvShow m : response.body().getResults()) {
                    RealmTvShow movie = m.getRealmTvShow(m);
                    movie.setRated(true);
                    realm.copyToRealm(movie);
                }
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<TvShowResponse> call, Throwable t) {

            }
        });
    }

    private void setAlarmForPushNotifications() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        Intent intentAlarm = new Intent(this, AlarmReciever.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, PendingIntent.getBroadcast(this, 1, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    private void setRealmInstance() {
        Realm.init(getApplicationContext());
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();
    }



    private void SyncFavoriteMovies() {
        final RealmResults<Action> movieFavorite = realm.where(Action.class).equalTo("actionType", "favorite").equalTo("mediaType", "movie").findAll();
        for (final Action a : movieFavorite) {
            PostBody postMovie;
            postMovie = new PostBody(movie, a.getId(), favorite, this);
            Call<PostResponse> call = getApiService().PostFavorite(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), postMovie);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 1) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "favorite").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                    Call<MoviesListResponse> call1 = getApiService().getFavoritesMovies(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), order);
                    call1.enqueue(new Callback<MoviesListResponse>() {
                        @Override
                        public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                            getAccount().setFavoriteMovies(response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }
    }

    private void SyncUnFavoriteMovies() {
        final RealmResults<Action> movieFavorite = realm.where(Action.class).equalTo("actionType", "removefavorite").equalTo("mediaType", "movie").findAll();
        for (final Action a : movieFavorite) {
            PostBody postMovie;
            postMovie = new PostBody(movie, a.getId(), watchlist, this);
            Call<PostResponse> call = getApiService().PostFavorite(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), postMovie);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 13) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "removefavorite").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                    Call<MoviesListResponse> call1 = getApiService().getFavoritesMovies(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), order);
                    call1.enqueue(new Callback<MoviesListResponse>() {
                        @Override
                        public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                            getAccount().setFavoriteMovies(response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }
    }

    private void SyncFavoriteTvShow() {
        final RealmResults<Action> tvFavorite = realm.where(Action.class).equalTo("actionType", "favorite").equalTo("mediaType", "tv").findAll();
        for (final Action a : tvFavorite) {
            PostBody postMovie;
            postMovie = new PostBody(tvShow, a.getId(), favorite, this);
            Call<PostResponse> call = getApiService().PostFavorite(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), postMovie);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 1) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "favorite").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                    Call<TvShowResponse> call1 = getApiService().getFavoritesTvShows(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), order);
                    call1.enqueue(new Callback<TvShowResponse>() {
                        @Override
                        public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                            getAccount().setFavoriteTvShows(response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<TvShowResponse> call, Throwable t) {
                        }
                    });
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }
    }

    private void SyncUnFavoriteTvShow() {
        final RealmResults<Action> tvFavorite = realm.where(Action.class).equalTo("actionType", "removefavorite").equalTo("mediaType", "tv").findAll();
        for (final Action a : tvFavorite) {
            PostBody postMovie;
            postMovie = new PostBody(tvShow, a.getId(), watchlist, this);
            Call<PostResponse> call = getApiService().PostFavorite(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), postMovie);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 13) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "removefavorite").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                    Call<TvShowResponse> call1 = getApiService().getFavoritesTvShows(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), order);
                    call1.enqueue(new Callback<TvShowResponse>() {
                        @Override
                        public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                            getAccount().setFavoriteTvShows(response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<TvShowResponse> call, Throwable t) {
                        }
                    });
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }
    }

    private void SyncWatchlistMovie() {
        final RealmResults<Action> movieFavorite = realm.where(Action.class).equalTo("actionType", "watchlist").equalTo("mediaType", "movie").findAll();
        for (final Action a : movieFavorite) {
            PostBody postMovie;
            postMovie = new PostBody(movie, a.getId(), watchlist, this);
            Call<PostResponse> call = getApiService().MarkWatchList(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), postMovie);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 1) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "watchlist").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                    Call<MoviesListResponse> call1 = getApiService().getMoviesWatchList(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), order);
                    call1.enqueue(new Callback<MoviesListResponse>() {
                        @Override
                        public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                            getAccount().setWatchListMovies(response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }
    }

    private void SyncUnWatchlistMovie() {
        final RealmResults<Action> movieFavorite = realm.where(Action.class).equalTo("actionType", "removewatchlist").equalTo("mediaType", "movie").findAll();
        for (final Action a : movieFavorite) {
            PostBody postMovie;
            postMovie = new PostBody(movie, a.getId(), favorite, this);
            Call<PostResponse> call = getApiService().MarkWatchList(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), postMovie);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 13) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "removewatchlist").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                    Call<MoviesListResponse> call1 = getApiService().getMoviesWatchList(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), order);
                    call1.enqueue(new Callback<MoviesListResponse>() {
                        @Override
                        public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                            getAccount().setWatchListMovies(response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                        }
                    });
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }
    }

    private void SyncWatchlistTvShow() {
        final RealmResults<Action> tvFavorite = realm.where(Action.class).equalTo("actionType", "watchlist").equalTo("mediaType", "tv").findAll();
        for (final Action a : tvFavorite) {
            PostBody postMovie;
            postMovie = new PostBody(tvShow, a.getId(), watchlist, this);
            Call<PostResponse> call = getApiService().MarkWatchList(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), postMovie);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 1) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "watchlist").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                    Call<TvShowResponse> call1 = getApiService().getTvShowWatchList(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), order);
                    call1.enqueue(new Callback<TvShowResponse>() {
                        @Override
                        public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                            getAccount().setWatchListTvShow(response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<TvShowResponse> call, Throwable t) {
                        }
                    });
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }

    }

    private void SyncRatedMovies() {

        final RealmResults<Action> movieRated = realm.where(Action.class).equalTo("actionType", "rate").equalTo("mediaType", "movie").findAll();

        for (final Action a : movieRated) {
            PostBody rate = new PostBody(a.getVote());
            Call<PostResponse> call = apiService.RateMovie(a.getId(), ApiClient.API_KEY, getAccount().getSessionId(), rate);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 1 || response.body().getStatusCode() == 12) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "rate").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }
    }

    public void SyncRatedTvShows()
    {
        final RealmResults<Action> movieRated = realm.where(Action.class).equalTo("actionType", "rate").equalTo("mediaType", "tv").findAll();

        for (final Action a : movieRated) {
            PostBody rate = new PostBody(a.getVote());
            Call<PostResponse> call = apiService.RateTvshow(a.getId(), ApiClient.API_KEY, getAccount().getSessionId(), rate);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 1 || response.body().getStatusCode() == 12) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "rate").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }
    }

    private void SyncUnWatchlistTvShow() {
        final RealmResults<Action> tvFavorite = realm.where(Action.class).equalTo("actionType", "removewatchlist").equalTo("mediaType", "tv").findAll();
        for (final Action a : tvFavorite) {
            PostBody postMovie;
            postMovie = new PostBody(tvShow, a.getId(), favorite, this);
            Call<PostResponse> call = getApiService().MarkWatchList(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), postMovie);
            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    if (response.body().getStatusCode() == 13) {
                        realm.beginTransaction();
                        Action movie = realm.where(Action.class).equalTo("id", a.getId()).equalTo("actionType", "removewatchlist").findFirst();
                        movie.deleteFromRealm();
                        realm.commitTransaction();
                    }
                    Call<TvShowResponse> call1 = getApiService().getTvShowWatchList(getAccount().getAccountId(), ApiClient.API_KEY, getAccount().getSessionId(), order);
                    call1.enqueue(new Callback<TvShowResponse>() {
                        @Override
                        public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                            getAccount().setWatchListTvShow(response.body().getResults());
                        }

                        @Override
                        public void onFailure(Call<TvShowResponse> call, Throwable t) {
                        }
                    });
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                }
            });
        }

    }

    public ApiInterface getApiService() {
        return apiService;
    }

    public static MovieApplication getInstance() {
        return singleton;
    }

    public PlacesApiInterface getPlacesApiInterface() {
        return placesApiInterface;
    }

    public void setPlacesApiInterface(PlacesApiInterface placesApiInterface) {
        this.placesApiInterface = placesApiInterface;
    }

    public Realm getRealm() {
        return realm;
    }

    public void setRealm(Realm realm) {
        this.realm = realm;
    }

    public User getAccount() {
        return account;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
