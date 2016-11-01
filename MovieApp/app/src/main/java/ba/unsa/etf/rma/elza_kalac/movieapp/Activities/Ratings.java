package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.ApiClient;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.PagerAdapters.FavoritesPagerAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.PagerAdapters.RatingPagerAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Account;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.Responses.MoviesListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ratings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ratings);

        getSupportActionBar().setTitle(R.string.ratings);

        MovieApplication mApp = (MovieApplication)getApplicationContext();
        Account a;
        a=mApp.getAccount();
        if (a!=null)
        {
            Call<MoviesListResponse> call = mApp.getApiService().getMoviesRatings(a.getAccountId(), ApiClient.API_KEY, a.getSessionId());
            call.enqueue(new Callback<MoviesListResponse>() {
                @Override
                public void onResponse(Call<MoviesListResponse> call, Response<MoviesListResponse> response) {
                    Toast.makeText(getApplicationContext(), String.valueOf(response.body().getTotalResults()), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(Call<MoviesListResponse> call, Throwable t) {

                }
            });
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.movies));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tv_shows));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final RatingPagerAdapter adapter = new RatingPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

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
