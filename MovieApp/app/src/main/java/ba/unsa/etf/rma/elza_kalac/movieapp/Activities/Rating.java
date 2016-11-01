package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiInterface;
import ba.unsa.etf.rma.elza_kalac.movieapp.API.PostBody;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Movie;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.PostResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Rating extends AppCompatActivity {
    int starNum, movieID;
    ApiInterface apiService;
    MovieApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        movieID=getIntent().getIntExtra("movieID", 0);
        mApp=(MovieApplication)getApplicationContext();
        apiService=mApp.getApiService();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.movies);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(android.R.drawable.checkbox_on_background);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                300,
                300, Gravity.END | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowTitleEnabled(true);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (starNum==0)
                {
                    Toast.makeText(getApplicationContext(), "Morate ocjeniti !", Toast.LENGTH_LONG).show();
                }
                else
                {
                    PostBody rate = new PostBody(starNum);
                    Call<PostResponse> call = apiService.RateMovie(movieID, ApiClient.API_KEY, mApp.getAccount().getSessionId(), rate);
                    call.enqueue(new Callback<PostResponse>() {
                        @Override
                        public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                            if (response.body().getStatusCode()==12)
                                Toast.makeText(getApplicationContext(), "Rate added", Toast.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(Call<PostResponse> call, Throwable t) {

                        }
                    });

                }

            }
        });

        final ImageView rating1=(ImageView)findViewById(R.id.rating1);
        final ImageView rating2=(ImageView)findViewById(R.id.rating2);
        final ImageView rating3=(ImageView)findViewById(R.id.rating3);
        final ImageView rating4=(ImageView)findViewById(R.id.rating4);
        final ImageView rating5=(ImageView)findViewById(R.id.rating5);
        final ImageView rating6=(ImageView)findViewById(R.id.rating6);
        final ImageView rating7=(ImageView)findViewById(R.id.rating7);
        final ImageView rating8=(ImageView)findViewById(R.id.rating8);
        final ImageView rating9=(ImageView)findViewById(R.id.rating9);
        final ImageView rating10=(ImageView)findViewById(R.id.rating10);


        rating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=1;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_empty);
                rating3.setImageResource(R.drawable.star_empty);
                rating4.setImageResource(R.drawable.star_empty);
                rating5.setImageResource(R.drawable.star_empty);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);


            }
        });
        rating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=2;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_empty);
                rating4.setImageResource(R.drawable.star_empty);
                rating5.setImageResource(R.drawable.star_empty);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=3;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_empty);
                rating5.setImageResource(R.drawable.star_empty);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=4;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_empty);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=5;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_empty);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=6;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_empty);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=7;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_filled);
                rating8.setImageResource(R.drawable.star_empty);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=8;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_filled);
                rating8.setImageResource(R.drawable.star_filled);
                rating9.setImageResource(R.drawable.star_empty);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=9;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_filled);
                rating8.setImageResource(R.drawable.star_filled);
                rating9.setImageResource(R.drawable.star_filled);
                rating10.setImageResource(R.drawable.star_empty);
            }
        });
        rating10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                starNum=10;
                rating1.setImageResource(R.drawable.star_filled);
                rating2.setImageResource(R.drawable.star_filled);
                rating3.setImageResource(R.drawable.star_filled);
                rating4.setImageResource(R.drawable.star_filled);
                rating5.setImageResource(R.drawable.star_filled);
                rating6.setImageResource(R.drawable.star_filled);
                rating7.setImageResource(R.drawable.star_filled);
                rating8.setImageResource(R.drawable.star_filled);
                rating9.setImageResource(R.drawable.star_filled);
                rating10.setImageResource(R.drawable.star_filled);
            }
        });







    }
}
