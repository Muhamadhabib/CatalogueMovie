package com.habib.cataloguefavorite.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.habib.cataloguefavorite.MovieFragment;
import com.habib.cataloguefavorite.TvFragment;

public class PageAdapter extends FragmentPagerAdapter {
    private int num;
    public PageAdapter(FragmentManager fm,int num) {
        super(fm);
        this.num = num;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new MovieFragment();
            case 1:
                return new TvFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
