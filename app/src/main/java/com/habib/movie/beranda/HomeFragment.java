package com.habib.movie.beranda;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.habib.movie.PageAdapter;
import com.habib.movie.R;

public class HomeFragment extends Fragment implements TabLayout.OnTabSelectedListener{
    public static final String KEY_MOVIES = "movies";
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        tabLayout = view.findViewById(R.id.tab);
        //tabLayout.addTab(tabLayout.newTab().setText("Movie"));
        //tabLayout.addTab(tabLayout.newTab().setText("TV Show"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        setHasOptionsMenu(true);
        viewPager = view.findViewById(R.id.view_pager);
        PageAdapter pageAdapter = new PageAdapter(getChildFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(this);
        return view;
    }

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

}
