package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.PagerAdapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.FavoritesMovies;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.FavoritesTvShows;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.WatchListMovie;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.WatchListTvShows;

public class WatchListPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public WatchListPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new WatchListMovie();
            case 1:
                return new WatchListTvShows();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
