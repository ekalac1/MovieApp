package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.AuthentificationResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.TvShowResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication.order;

public class Login extends AppCompatActivity {

    AuthentificationResponse token, login, session;
    String sessionId;
    ApiInterface apiService;
    MovieApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setTitle(R.string.login);

        final EditText username = (EditText) findViewById(R.id.username);
        final EditText password = (EditText) findViewById(R.id.password);
        username.setText("vanderwoodsen");
        password.setText("ekalac1");
        final Button loginButton = (Button) findViewById(R.id.login);
        TextView newAcc = (TextView) findViewById(R.id.new_acc);
        TextView forgot = (TextView) findViewById(R.id.forgot_details);

        loginButton.setBackground(getDrawable(R.color.golden));

        newAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/account/signup"));
                startActivity(browserIntent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themoviedb.org/account/reset-password"));
                startActivity(browserIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mApp = (MovieApplication) getApplicationContext();
                apiService = mApp.getApiService();
                loginButton.setBackground(getDrawable(R.color.pressed));

                Call<AuthentificationResponse> call = apiService.getToken(ApiClient.API_KEY);

                call.enqueue(new Callback<AuthentificationResponse>() {
                    @Override
                    public void onResponse(Call<AuthentificationResponse> call, Response<AuthentificationResponse> response) {
                        token = response.body();
                        if (token.isSuccess()) {
                            Call<AuthentificationResponse> call1 = apiService.Login(ApiClient.API_KEY, username.getText().toString(), password.getText().toString(), token.getRequestToken());
                            call1.enqueue(new Callback<AuthentificationResponse>() {
                                @Override
                                public void onResponse(Call<AuthentificationResponse> call, Response<AuthentificationResponse> response) {
                                    if (response.errorBody() != null) {
                                        loginButton.setBackground(getDrawable(R.color.golden));
                                        Toast.makeText(getApplicationContext(), R.string.login_error, Toast.LENGTH_LONG).show();
                                        password.setText("");
                                    } else {
                                        login = response.body();
                                        if (login.isSuccess()) {
                                            Call<AuthentificationResponse> call2 = apiService.CreateSession(ApiClient.API_KEY, login.getRequestToken());
                                            call2.enqueue(new Callback<AuthentificationResponse>() {
                                                @Override
                                                public void onResponse(Call<AuthentificationResponse> call, Response<AuthentificationResponse> response) {
                                                    session = response.body();
                                                    if (session.isSuccess())
                                                        sessionId = session.getSessionId();
                                                    Call<Account> call4 = apiService.GetAccount(ApiClient.API_KEY, sessionId);
                                                    call4.enqueue(new Callback<Account>() {
                                                        @Override
                                                        public void onResponse(Call<Account> call, Response<Account> response) {
                                                            Account a = response.body();
                                                            a.setSessionId(sessionId);
                                                            mApp.setAccount(a);
                                                            Call<MoviesListResponse> call3 = mApp.getApiService().getFavoritesMovies(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
                                                            call3.enqueue(new Callback<MoviesListResponse>() {
                                                                @Override
                                                                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                                                                    mApp.getAccount().setFavoriteMovies(response.body().getResults());
                                                                    Call<MoviesListResponse> call1 = mApp.getApiService().getMoviesWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
                                                                    call1.enqueue(new Callback<MoviesListResponse>() {
                                                                        @Override
                                                                        public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                                                                            mApp.getAccount().setWatchListMovies(response.body().getResults());
                                                                            Call<TvShowResponse> call1 = mApp.getApiService().getFavoritesTvShows(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
                                                                            call1.enqueue(new Callback<TvShowResponse>() {
                                                                                @Override
                                                                                public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                                                                    mApp.getAccount().setFavoriteTvShows(response.body().getResults());
                                                                                    Call<TvShowResponse> call1 = mApp.getApiService().getTvShowWatchList(mApp.getAccount().getAccountId(), ApiClient.API_KEY, mApp.getAccount().getSessionId(), order);
                                                                                    call1.enqueue(new Callback<TvShowResponse>() {
                                                                                        @Override
                                                                                        public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                                                                                            mApp.getAccount().setWatchListTvShow(response.body().getResults());
                                                                                            onBackPressed();
                                                                                        }
                                                                                        @Override
                                                                                        public void onFailure(Call<TvShowResponse> call, Throwable t) {
                                                                                        }
                                                                                    });
                                                                                }

                                                                                @Override
                                                                                public void onFailure(Call<TvShowResponse> call, Throwable t) {
                                                                                }
                                                                            });

                                                                        }

                                                                        @Override
                                                                        public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                                                                        }
                                                                    });

                                                                }

                                                                @Override
                                                                public void onFailure(Call<MoviesListResponse> call, Throwable t) {
                                                                }
                                                            });
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Account> call, Throwable t) {

                                                        }
                                                    });
                                                }

                                                @Override
                                                public void onFailure(Call<AuthentificationResponse> call, Throwable t) {

                                                }
                                            });

                                        }
                                    }

                                }

                                @Override
                                public void onFailure(Call<AuthentificationResponse> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthentificationResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), R.string.on_failure, Toast.LENGTH_LONG).show();

                    }
                });
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
