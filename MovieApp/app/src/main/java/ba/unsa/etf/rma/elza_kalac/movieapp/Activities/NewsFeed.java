package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.API.RssAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.Details.MapsActivity;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Favorites;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Ratings;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Settings;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Watchlist;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.NewsListAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Feed;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.FeedItem;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.RealmModels.RealmFeedItem;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class NewsFeed extends AppCompatActivity {

    ListView entrysList;
    Feed feed;
    ActionBarDrawerToggle mToogle;
    NavigationView slideMenu;
    DrawerLayout mDrawerLayout;
    MovieApplication mApp;
    NewsListAdapter n;

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        Realm.init(getApplicationContext());

        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);

        realm = Realm.getDefaultInstance();

        mApp = (MovieApplication) getApplicationContext();
        slideMenu = (NavigationView) findViewById(R.id.navigationSlide);

        slideMenu = (NavigationView) findViewById(R.id.navigationSlide);

        if (mApp.getAccount() == null) {
            slideMenu.getMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(1).setVisible(false);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setTitle(R.string.login_);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setIcon(R.drawable.login);

        } else {
            slideMenu.getMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(1).setVisible(true);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setTitle(R.string.logout);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setIcon(R.drawable.logout);
        }

        slideMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.maps:
                        startActivity(new Intent(getApplicationContext(), MapsActivity.class));
                        break;
                    case R.id.settings:
                        if (mApp.getAccount() == null) Alert();
                        else {
                            startActivity(new Intent(getApplicationContext(), Settings.class));
                        }
                        break;
                    case R.id.favorites:
                        if (mApp.getAccount() == null) Alert();
                        else {
                            startActivity(new Intent(getApplicationContext(), Favorites.class));
                        }
                        break;
                    case R.id.watchlist:
                        if (mApp.getAccount() == null) Alert();
                        else {
                            startActivity(new Intent(getApplicationContext(), Watchlist.class));
                        }
                        break;
                    case R.id.ratings:
                        if (mApp.getAccount() == null) Alert();
                        else {
                            startActivity(new Intent(getApplicationContext(), Ratings.class));
                        }
                        break;
                    case R.id.logout:
                        if (mApp.getAccount() == null) {
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            mApp.setAccount(null);
                            mDrawerLayout.closeDrawer(GravityCompat.START);
                            Toast.makeText(getApplicationContext(), R.string.logout_done, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                        break;
                }
                return false;
            }
        });


        getSupportActionBar().setTitle(R.string.newsFeed);

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

        if (isNetworkAvailable()) {
            realm.beginTransaction();
            RealmResults<RealmFeedItem> items = realm.where(RealmFeedItem.class).findAll();
            items.deleteAllFromRealm();
            realm.commitTransaction();

        } else {
            realm.beginTransaction();
            RealmResults<RealmFeedItem> items = realm.where(RealmFeedItem.class).findAll();
            List<FeedItem> temp = new ArrayList<>();
            for (RealmFeedItem item : items) {
                temp.add(new FeedItem().getFeedItem(item));
            }
            n = new NewsListAdapter(getApplicationContext(), R.layout.news_element, temp);
            entrysList.setAdapter(n);
        }

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
                feed = response.body();
                n = new NewsListAdapter(getApplicationContext(), R.layout.news_element, feed.getmChannel().getFeedItems());
                entrysList.setAdapter(n);
                entrysList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feed.getmChannel().getFeedItems().get(position).getMlink()));
                        startActivity(browserIntent);
                    }
                });
                realm.beginTransaction();
                for (FeedItem item : response.body().getmChannel().getFeedItems()) {
                    realm.copyToRealm(item.getRealmFeedItem());
                }
                realm.commitTransaction();
            }

            @Override
            public void onFailure(Call<Feed> call, Throwable t) {
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)) {
            return true;
        } else if (item.getItemId() == R.id.search) {
            startActivity(new Intent(getApplicationContext(), SearchActivity.class));
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
                Toast.makeText(this, R.string.exit, Toast.LENGTH_SHORT).show();
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

    private void Alert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NewsFeed.this);
        builder.setMessage(R.string.message)
                .setTitle(R.string.Sign_in)
                .setPositiveButton(R.string.sign, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.not_now, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                }).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        slideMenu = (NavigationView) findViewById(R.id.navigationSlide);

        if (mApp.getAccount() == null) {
            slideMenu.getMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(1).setVisible(false);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setTitle(R.string.login_);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setIcon(R.drawable.login);

        } else {
            slideMenu.getMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(1).setVisible(true);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setTitle(R.string.logout);
            slideMenu.getMenu().getItem(2).getSubMenu().getItem(1).setIcon(R.drawable.logout);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        return true;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
