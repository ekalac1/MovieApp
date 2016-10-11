package ba.unsa.etf.rma.elza_kalac.movieapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;


import java.io.FileNotFoundException;
import java.util.List;

import ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.NewsListAdapter;
import ba.unsa.etf.rma.elza_kalac.movieapp.Downloader;
import ba.unsa.etf.rma.elza_kalac.movieapp.EntryXmlPullParser;
import ba.unsa.etf.rma.elza_kalac.movieapp.Models.Entry;
import ba.unsa.etf.rma.elza_kalac.movieapp.R;

public class NewsFeed extends AppCompatActivity {

    public ListView entrysList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed);

        entrysList = (ListView)findViewById(R.id.news_list);

        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabWithId(R.id.tab_news);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_tvshows) {
                    Intent intent = new Intent(getApplicationContext(), TVShows.class);
                    startActivity(intent);
                }

                if (tabId == R.id.tab_movies) {
                    Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
                    startActivity(intent);
                }

            }
        });

        if(isNetworkAvailable()){
            Log.i("StackSites", "starting download Task");
            SitesDownloadTask download = new SitesDownloadTask();
            download.execute();
        }else{
            NewsListAdapter mAdapter = new NewsListAdapter(getApplicationContext(), R.layout.news_element, EntryXmlPullParser.getStackSitesFromFile(getApplicationContext()));
            entrysList.setAdapter(mAdapter);
        }

    }

    //Helper method to determine if Internet connection is available.
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
     * AsyncTask that will download the xml file for us and store it locally.
     * After the download is done we'll parse the local file.
     */
    private class SitesDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            //Download the file
            try {
                Downloader.DownloadFromUrl("http://www.boxofficemojo.com/data/rss.php?file=topstories.xml", openFileOutput("StackSites.xml", Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            //setup our Adapter and set it to the ListView.
            List<Entry> proba = EntryXmlPullParser.getStackSitesFromFile(getApplicationContext());
            NewsListAdapter mAdapter = new NewsListAdapter(getApplicationContext(), R.layout.news_element, EntryXmlPullParser.getStackSitesFromFile(getApplicationContext()));
            entrysList.setAdapter(mAdapter);
            Log.i("StackSites", "adapter size = "+ mAdapter.getCount());
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.selectTabWithId(R.id.tab_news);

    }
}
