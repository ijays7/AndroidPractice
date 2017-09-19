package com.ijays.apolo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.TextView;

import com.ijays.apolo.R;
import com.ijays.apolo.fragment.ShareElementFragment;

import butterknife.BindView;

/**
 * Created by ijaysdev on 10/08/2017.
 */

public class SharedElementActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_share_elemnt_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        String message = getIntent().getStringExtra("sample");
        mTitle.setText(message);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getWindow().getEnterTransition().setDuration(500);

        Slide slideTransition = new Slide(Gravity.LEFT);
        slideTransition.setDuration(500);

        ShareElementFragment shareFragmnet=ShareElementFragment.newInstance();
        shareFragmnet.setExitTransition(slideTransition);
        shareFragmnet.setSharedElementEnterTransition(new ChangeBounds());

        getSupportFragmentManager().beginTransaction().replace(R.id.fl_container,
                ShareElementFragment.newInstance()).commit();
    }
}
