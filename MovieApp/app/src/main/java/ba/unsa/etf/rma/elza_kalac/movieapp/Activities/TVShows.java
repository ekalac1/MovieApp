package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.MoviesPagerAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.TvShowsPagerAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class TVShows extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshows);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tvtab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("most popular"));
        tabLayout.addTab(tabLayout.newTab().setText("latest"));
        tabLayout.addTab(tabLayout.newTab().setText("highest-rated"));
        tabLayout.addTab(tabLayout.newTab().setText("AIRING TODAY"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.tvpager);
        final TvShowsPagerAdapter adapter = new TvShowsPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
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


      final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabWithId(R.id.tab_tvshows);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_news) {
                    Intent intent = new Intent(getApplicationContext(), NewsFeed.class);
                    startActivity(intent);
                }

                if (tabId == R.id.tab_movies) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabWithId(R.id.tab_tvshows);

    }
}
