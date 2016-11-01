package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.RssAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.NewsListAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Feed;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class NewsFeed extends AppCompatActivity {

    public ListView entrysList;
    public Feed proba;
    private ActionBarDrawerToggle mToogle;
    NavigationView slideMenu;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        slideMenu = (NavigationView) findViewById(R.id.navigationSlide);

        slideMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        break;
                    case R.id.favorites:
                        startActivity(new Intent(getApplicationContext(), Favorites.class));
                        break;
                    case R.id.watchlist:
                        startActivity(new Intent(getApplicationContext(), Watchlist.class));
                        break;
                    case R.id.ratings:
                        startActivity(new Intent(getApplicationContext(), Ratings.class));
                        break;
                    case R.id.logout:
                        MovieApplication mApp=(MovieApplication)getApplicationContext();
                        mApp.setAccount(null);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        Toast.makeText(getApplicationContext(), "Logout done", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.newsFeed);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(android.R.drawable.ic_menu_search);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayOptions( ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowTitleEnabled(true);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchActivity.class));
            }
        });

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToogle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);

        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        entrysList = (ListView) findViewById(R.id.news_list);

        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabWithId(R.id.tab_news);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_tvshows) {
                    Intent intent = new Intent(getApplicationContext(), TVShows.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                if (tabId == R.id.tab_movies) {
                    Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

       Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.boxofficemojo.com")
                .client(new OkHttpClient())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();

        RssAdapter rssAdapter = retrofit.create(RssAdapter.class);
        Call<Feed> call = rssAdapter.getItems();
        call.enqueue(new Callback<Feed>() {
            @Override
            public void onResponse(Call<Feed> call, Response<Feed> response) {
                proba = response.body();
                NewsListAdapter n = new NewsListAdapter(getApplicationContext(), R.layout.news_element, proba.getmChannel().getFeedItems());
                entrysList.setAdapter(n);
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
            }
        });

        entrysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(proba.getmChannel().getFeedItems().get(position).getMlink()));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            if (exit) {
                finish(); // finish activity
            } else {
                Toast.makeText(this, "Press Back again to Exit.",
                        Toast.LENGTH_SHORT).show();
                exit = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, 3 * 1000);
            }
        }
    }
}
