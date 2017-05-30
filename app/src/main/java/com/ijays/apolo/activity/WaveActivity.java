package com.ijays.apolo.activity;

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
