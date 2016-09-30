package ba.unsa.etf.rma.elza_kalac.movieapp.Adapters;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.AiringTodayTvShowsFragment;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.HighestRatedTvShowFragment;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.LatestTvShowsFragment;
import ba.unsa.etf.rma.elza_kalac.movieapp.Fragments.MostPopularTvShowsFragment;

/**
 * Created by Laptop on 30.09.2016..
 */
public class TvShowsPagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public TvShowsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MostPopularTvShowsFragment tab1 = new MostPopularTvShowsFragment();
                return tab1;
            case 1:
                LatestTvShowsFragment tab2 = new LatestTvShowsFragment();
                return tab2;
            case 2:
                 HighestRatedTvShowFragment tab3 = new HighestRatedTvShowFragment();
                return tab3;
            case 3:
                AiringTodayTvShowsFragment tab4 = new AiringTodayTvShowsFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
