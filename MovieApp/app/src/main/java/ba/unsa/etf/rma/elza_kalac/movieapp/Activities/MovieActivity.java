package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Favorites;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Ratings;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Settings;
import ba.unsa.etf.rma.elza_kalac.movieapp.Activities.UserPrivilegies.Watchlist;
import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.PagerAdapters.MoviesPagerAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.MovieApplication;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;
import ba.unsa.etf.rma.elza_kalac.movieapp.SignUpAlertListeners;
import io.fabric.sdk.android.Fabric;

public class MovieActivity extends AppCompatActivity implements SignUpAlertListeners {

    private ActionBarDrawerToggle mToogle;
    NavigationView slideMenu;
    DrawerLayout mDrawerLayout;
    MovieApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        mApp=(MovieApplication)getApplicationContext();

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.movies);
        ImageView imageView = new ImageView(actionBar.getThemedContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        imageView.setImageResource(android.R.drawable.ic_menu_search);
        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        layoutParams.rightMargin = 40;
        imageView.setLayoutParams(layoutParams);
        actionBar.setCustomView(imageView);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);

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

        slideMenu = (NavigationView) findViewById(R.id.navigationSlide);

        if (mApp.getAccount()==null)
        {
            slideMenu.getMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(1).getSubMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(1).getSubMenu().getItem(1).setTitle(R.string.login);
        }
        else
        {
            slideMenu.getMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(1).getSubMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(1).getSubMenu().getItem(1).setTitle(R.string.logout);
        }


        slideMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.settings:
                        if (mApp.getAccount()==null) showAlert();
                        else
                        startActivity(new Intent(getApplicationContext(), Settings.class));
                        break;
                    case R.id.favorites:
                        if (mApp.getAccount()==null) showAlert();
                        else
                        startActivity(new Intent(getApplicationContext(), Favorites.class));
                        break;
                    case R.id.watchlist:
                        if (mApp.getAccount()==null) showAlert();
                        else
                        startActivity(new Intent(getApplicationContext(), Watchlist.class));
                        break;
                    case R.id.ratings:
                        if (mApp.getAccount()==null) showAlert();
                        else
                        startActivity(new Intent(getApplicationContext(), Ratings.class));
                        break;
                    case R.id.logout:
                        if (mApp.getAccount()==null) showAlert();

                        mApp.setAccount(null);
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        Toast.makeText(getApplicationContext(), R.string.logout_done, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_1_name));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_2_name));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.tab_3_name));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final MoviesPagerAdapter adapter = new MoviesPagerAdapter
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


        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabWithId(R.id.tab_movies);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_news) {
                        Intent intent = new Intent(getApplicationContext(), NewsFeed.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }

                    if (tabId == R.id.tab_tvshows) {
                        Intent intent = new Intent(getApplicationContext(), TVShows.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mApp.getAccount()==null)
        {
            slideMenu.getMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(1).getSubMenu().getItem(0).setVisible(false);
            slideMenu.getMenu().getItem(1).getSubMenu().getItem(1).setTitle(R.string.login);
        }
        else
        {
            slideMenu.getMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(1).getSubMenu().getItem(0).setVisible(true);
            slideMenu.getMenu().getItem(1).getSubMenu().getItem(1).setTitle(R.string.logout);
        }
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabWithId(R.id.tab_movies);
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


    public void showAlert()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(MovieActivity.this);
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

}