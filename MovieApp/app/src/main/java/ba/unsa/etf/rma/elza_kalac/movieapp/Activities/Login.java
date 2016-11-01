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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    AuthentificationResponse token, login, session;
    String sessionId;
    int accountId;
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
        Button loginButton = (Button) findViewById(R.id.login);
        TextView newAcc = (TextView) findViewById(R.id.new_acc);
        TextView forgot = (TextView) findViewById(R.id.forgot_details);

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
                                        Toast.makeText(getApplicationContext(), "Wrong username or password", Toast.LENGTH_LONG).show();
                                        password.setText("");
                                    }
                                    else {
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
                                                                Call<MoviesListResponse> call3 = apiService.getFavoritesMovies(accountId, ApiClient.API_KEY, sessionId);
                                                                call3.enqueue(new Callback<MoviesListResponse>() {
                                                                    @Override
                                                                    public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                                                                        Account a = new Account();
                                                                        a.setUsername(username.getText().toString());
                                                                        a.setSessionId(sessionId);
                                                                        mApp.setAccount(a);

                                                                      /*  SharedPreferences sharedPref = getApplication().getSharedPreferences("username", getApplicationContext().MODE_PRIVATE);
                                                                        SharedPreferences.Editor editor = sharedPref.edit();
                                                                        editor.putString("username", username.getText().toString());
                                                                        editor.apply(); */
                                                                        onBackPressed();
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
