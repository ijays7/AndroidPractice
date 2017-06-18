package com.ijays.apolo.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.ijays.apolo.R;
import com.ijays.apolo.view.CircleActiveProgressBar;
import com.ijays.apolo.view.HorizontalProgressBar;

import butterknife.BindView;

/**
 * Created by ijaysdev on 02/06/2017.
 */

public class ActiveProgressbarActivity extends BaseActivity {
    @BindView(R.id.circle_bar)
    CircleActiveProgressBar mCircleBar;
    @BindView(R.id.horizontal_progress_bar)
    HorizontalProgressBar mHorizontalPb;
    @BindView(R.id.txt_progress)
    TextView mProgressTxt;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_active_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        mCircleBar.setProgress(100).
                startProgressAnimation();

        mCircleBar.setOnProgressListener(currentProgress -> {
            mProgressTxt.setText(String.format(getString(R.string.current_progress), currentProgress));
        });

        mHorizontalPb.setTotalProgress(100);
        mHorizontalPb.setProgress(60);
        mHorizontalPb.setOnClickListener((v -> {
            mHorizontalPb.setProgress(100);
        }));

    }

}
