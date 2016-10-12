package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.MostPopularMoviesFragment;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.LatestMoviesFragment;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.HighestRatedMoviesFragment;

public class MoviesPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MoviesPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MostPopularMoviesFragment tab1 = new MostPopularMoviesFragment();
                return tab1;
            case 1:
                LatestMoviesFragment tab2 = new LatestMoviesFragment();
                return tab2;
            case 2:
                HighestRatedMoviesFragment tab3 = new HighestRatedMoviesFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}

