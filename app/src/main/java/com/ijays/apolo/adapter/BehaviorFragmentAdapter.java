package com.ijays.apolo.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by ijaysdev on 22/07/2017.
 */

public class BehaviorFragmentAdapter extends FragmentPagerAdapter {
    private String[] mTitle = {"Tab1", "Tab2", "Tab3", "Tab4"};

    private List<? extends Fragment> mFragList;

    public BehaviorFragmentAdapter(FragmentManager fm, List<Fragment> mFragList) {
        super(fm);
        this.mFragList = mFragList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragList.get(position);
    }

    @Override
    public int getCount() {
        return mFragList == null ? 0 : mFragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
