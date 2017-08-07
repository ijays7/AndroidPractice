package com.ijays.apolo.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ijays.apolo.R;
import com.ijays.apolo.adapter.BehaviorFragmentAdapter;
import com.ijays.apolo.fragment.BehaviorTestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ijaysdev on 22/07/2017.
 */

public class BehaviorTestActivity extends BaseActivity {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_behavior_test_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        List<Fragment> fragList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            BehaviorTestFragment fragment = BehaviorTestFragment.newInstance();
            fragList.add(fragment);
        }
        mViewPager.setAdapter(new BehaviorFragmentAdapter(getSupportFragmentManager(),
                fragList));

        mTabLayout.setupWithViewPager(mViewPager);
    }
}
