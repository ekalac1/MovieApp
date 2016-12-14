package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters.PagerAdapters;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.FavoritesMovies;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.FavoritesTvShows;

public class FavoritesPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public FavoritesPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new FavoritesMovies();
            case 1:
                return new FavoritesTvShows();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
