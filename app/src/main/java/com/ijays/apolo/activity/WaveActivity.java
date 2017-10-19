package com.ijays.apolo.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;

import com.ijays.apolo.R;
import com.ijays.apolo.view.WaveView;

import butterknife.BindView;

/**
 * Created by ijaysdev on 28/05/2017.
 */

public class WaveActivity extends BaseActivity {
    @BindView(R.id.wave_view)
    WaveView mWaveView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wave_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setupTransition();
        }

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupTransition() {
        Explode explode = new Explode();
        explode.setDuration(500L);
        explode.excludeTarget(android.R.id.statusBarBackground, true);
        explode.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(explode);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mWaveView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWaveView.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWaveView.onStop();
    }
}
