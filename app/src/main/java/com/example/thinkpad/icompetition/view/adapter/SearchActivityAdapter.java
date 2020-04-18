package com.example.thinkpad.icompetition.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class SearchActivityAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragment;
    public SearchActivityAdapter(FragmentManager fm, ArrayList<Fragment> mFragment) {
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

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 1:
                title="用户";
                break;
            default:
                title="比赛";
                break;
        }
        return title;
    }
}
