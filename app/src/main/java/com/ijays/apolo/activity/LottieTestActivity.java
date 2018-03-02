package com.ijays.apolo.activity;

import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.ijays.apolo.R;

import butterknife.BindView;

/**
 * Created by ijays on 28/02/2018.
 */
public class LottieTestActivity extends BaseActivity {
    @BindView(R.id.lottie_view)
    LottieAnimationView mLottieView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lottie_test_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        mLottieView.setImageAssetsFolder("images/");
        mLottieView.setAnimation("game.json");
        mLottieView.setRepeatCount(LottieDrawable.INFINITE);
        mLottieView.playAnimation();
    }
}
