package com.ijays.apolo.activity;

import android.animation.AnimatorSet;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;

import com.ijays.apolo.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ijaysdev on 09/07/2017.
 */

public class ViewStubTestActivity extends BaseActivity {
    @BindView(R.id.view_stub)
    ViewStub mViewStub;
    @BindView(R.id.iv_spring_anim)
    ImageView mIvSpringAnim;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_stub_layout;
    }


    @OnClick(R.id.bt_view_stub_inflate)
    void onClickInflate() {

        if (mViewStub.getParent() != null)
            mViewStub.inflate();
//        mViewStub.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.bt_spring_anim)
    void onClickSpringAnim(View v) {
//        SpringAnimation animX = new SpringAnimation(mIvSpringAnim, DynamicAnimation.TRANSLATION_X);
//        SpringAnimation animY = new SpringAnimation(mIvSpringAnim, DynamicAnimation.TRANSLATION_Y);
        SpringAnimation animationX = new SpringAnimation(mIvSpringAnim, SpringAnimation.SCALE_X, 1f);
        SpringAnimation animationY = new SpringAnimation(mIvSpringAnim, SpringAnimation.SCALE_Y, 1f);
        animationX.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        animationX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        animationX.setStartValue(0.8f);

        animationY.getSpring().setStiffness(SpringForce.STIFFNESS_LOW);
        animationY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_HIGH_BOUNCY);
        animationY.setStartValue(0.8f);

        animationX.start();
        animationY.start();

    }
}
