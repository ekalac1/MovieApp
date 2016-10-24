package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.AuthentificationResponse;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersList extends AppCompatActivity {

    AuthentificationResponse token, login, session;
    String sessionId;
    int accountId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<AuthentificationResponse> call = apiService.getToken(ApiClient.API_KEY);

        call.enqueue(new Callback<AuthentificationResponse>() {
            @Override
            public void onResponse(Call<AuthentificationResponse> call, Response<AuthentificationResponse> response) {
                token = response.body();
                if (token.isSuccess()) {
                    Call<AuthentificationResponse> call1 = apiService.Login(ApiClient.API_KEY, "vanderwoodsen", "ekalac1", token.getRequestToken());
                    call1.enqueue(new Callback<AuthentificationResponse>() {
                        @Override
                        public void onResponse(Call<AuthentificationResponse> call, Response<AuthentificationResponse> response) {
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
                                                accountId = response.body().getAccountId();
                                                if (response.body().getAccountId() != 0) {
                                                    Call<MoviesListResponse> call3 = apiService.getFavorites(accountId, ApiClient.API_KEY, sessionId);
                                                    call3.enqueue(new Callback<MoviesListResponse>() {
                                                        @Override
                                                        public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                                                            List<Movie> m = response.body().getResults();
                                                            Toast.makeText(getApplicationContext(), m.get(0).getTitle(), Toast.LENGTH_LONG).show();
                                                        }

                                                        @Override
                                                        public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                                                        }
                                                    });
                                                }
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
}
