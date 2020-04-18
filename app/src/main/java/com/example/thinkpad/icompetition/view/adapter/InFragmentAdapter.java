package com.example.thinkpad.icompetition.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by a'su's
 * on 2018/7/12.
 */

public class InFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<Fragment> mFragment;
    public InFragmentAdapter(FragmentManager fm, ArrayList<Fragment> mFragment) {
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
                title="最新";
                break;
            case 2:
                title="兴趣";
                break;
            default:
                title="热门";
                break;
        }
        return title;
    }
}
