package com.habib.movie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.habib.movie.favorite.FavMovieFragment;
import com.habib.movie.favorite.FavTvFragment;

public class PageAdapter2 extends FragmentPagerAdapter {

    private int num;
    public PageAdapter2(FragmentManager fm, int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new FavMovieFragment();
            case 1:
                return new FavTvFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
