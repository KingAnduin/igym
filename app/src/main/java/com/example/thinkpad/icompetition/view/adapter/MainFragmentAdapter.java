package com.example.thinkpad.icompetition.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by a'su's
 * on 2018/7/12.
 */

public class MainFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragment;
    public MainFragmentAdapter(FragmentManager fm, ArrayList<Fragment> mFragment) {
        super(fm);
        this.mFragment=mFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment.get(position);
    }

    @Override
    public int getCount() {
        return mFragment.size();
    }
}
