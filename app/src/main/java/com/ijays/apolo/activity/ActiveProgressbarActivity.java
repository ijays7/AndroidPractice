package com.ijays.apolo.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import com.ijays.apolo.R;
import com.ijays.apolo.view.CircleActiveProgressBar;
import com.ijays.apolo.view.CustomProgressBar;
import com.ijays.apolo.view.HorizontalProgressBar;
import com.ijays.apolo.view.NiceProgressBar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ijaysdev on 02/06/2017.
 */

public class ActiveProgressbarActivity extends BaseActivity {
    @BindView(R.id.circle_3)
    CircleActiveProgressBar mCircleBar;
    @BindView(R.id.circle_1)
    CircleActiveProgressBar mCircleBar1;
    @BindView(R.id.circle_2)
    CircleActiveProgressBar mCircleBar2;
    @BindView(R.id.horizontal_progress_bar)
    HorizontalProgressBar mHorizontalPb;
    @BindView(R.id.pb1)
    NiceProgressBar mPb1;
    @BindView(R.id.txt_progress)
    TextView mProgressTxt;
    @BindView(R.id.custom)
    CustomProgressBar customProgressBar;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_active_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            slide();
        }

        mCircleBar.setProgress(10543).
                startProgressAnimation();
        mCircleBar1.setProgress(24382).
                startProgressAnimation();
        mCircleBar2.setProgress(209830).
                startProgressAnimation();

        mCircleBar.setOnProgressListener(currentProgress -> {
            mProgressTxt.setText(String.format(getString(R.string.current_progress), currentProgress));
        });

        mHorizontalPb.setTotalProgress(100);
        mHorizontalPb.setProgress(60);
        mHorizontalPb.setOnClickListener((v -> {
            mHorizontalPb.setProgress(100);
        }));
        mPb1.setTextMax(33)
                .setTextColor(getResources().getColor(R.color.google_red))
                .setDefaultWheelColor(getResources().getColor(R.color.google_blue))
                .setWheelColor(getResources().getColor(R.color.google_orange))
                .show();

        customProgressBar.postDelayed(() -> {
            customProgressBar.setMaxProgress(100);
//        customProgressBar.setProgress(100);
            customProgressBar.setFastProgress(100);
        }, 100);


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void slide() {
        Slide slide = new Slide(Gravity.BOTTOM);
        slide.setDuration(400);
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(slide);
    }

    @OnClick(R.id.custom)
    void onClickCustom() {
        Log.e("SONGJIE", "dsds");
        customProgressBar.post(runnable);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            if (customProgressBar.getProgress() < customProgressBar.getMaxProgress()) {
            Log.e("SONGJIE", "runnable");
            customProgressBar.decrementProgreeBy(10);
            customProgressBar.postDelayed(runnable, 500);
//            } else {
//                customProgressBar.setProgress(0);
//                customProgressBar.postDelayed(runnable, 2000);
//
//            }
        }
    };


}
