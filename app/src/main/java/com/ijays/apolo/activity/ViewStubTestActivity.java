package com.ijays.apolo.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.transition.Fade;
import android.view.View;
import android.view.ViewStub;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import com.ijays.apolo.R;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

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
    @BindView(R.id.pie_view)
    AnimatedPieView mPieView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_stub_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupTransition();
        }

        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.setStartAngle(-90)
                .addData(new SimplePieInfo(10, getColor("FF446767")), true)
                .addData(new SimplePieInfo(20, getColor("FFFFD28C")), true)
                .addData(new SimplePieInfo(100, getColor("FFbb76b4")), true)
                .addData(new SimplePieInfo(50, getColor("ff957de0"), "长文字test"), false)
                .setDrawText(true)
                .setDuration(2000)
                .setTextLineStrokeWidth(4)
                .setTextSize(14)
                .setPieRadiusScale(0.7f)
                .setOnPieSelectListener(new OnPieSelectListener<IPieInfo>() {
                    @Override
                    public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isScaleUp) {
                        if (isScaleUp) {
//                            Toast.makeText(MainActivity.this, pieInfo.getDesc(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
        mPieView.applyConfig(config);
        mPieView.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupTransition() {
        Fade fade = new Fade();
        fade.setDuration(500L);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setEnterTransition(fade);
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

    private int getColor(String colorStr) {
        if (TextUtils.isEmpty(colorStr)) return Color.BLACK;
        if (!colorStr.startsWith("#")) colorStr = "#" + colorStr;
        return Color.parseColor(colorStr);
    }
}
