package com.ijays.apolo.fragment;

import android.animation.Animator;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ijays.apolo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 水波纹动画
 * Created by ijaysdev on 22/10/2017.
 */

public class CircleAnimFragment extends BaseFragment {
    @BindView(R.id.fl_container)
    FrameLayout mContainer;
    @BindView(R.id.iv_circle_4)
    ImageView mCircle4;

    @Override
    int getLayoutId() {
        return R.layout.fragment_circle_anim_layout;
    }

    @Override
    protected void initView(View view) {
        view.post(() -> {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                addAnim(view);
            }
        });
    }

    @OnClick(R.id.iv_circle_1)
    void onClickCircle1() {
        revealFromCenter(mContainer, R.color.google_blue);
    }

    @OnClick(R.id.iv_circle_4)
    void onClickCircle4() {
        revealFromConer(mContainer, R.color.google_orange);
    }


    private void revealFromCenter(ViewGroup viewRoot, @ColorRes int color) {
        int cx = (viewRoot.getLeft() + viewRoot.getRight()) / 2;
        int cy = (viewRoot.getTop() + viewRoot.getBottom()) / 2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            revealAnimate(viewRoot, color, cx, cy);
        }
    }

    private void revealFromConer(ViewGroup viewRoot, @ColorRes int color) {
        int cx = viewRoot.getWidth() - (mCircle4.getPaddingRight() + viewRoot.getPaddingRight()) - mCircle4.getWidth() / 2;
        int cy = mCircle4.getHeight() / 2 + mCircle4.getPaddingTop();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            revealAnimate(viewRoot, color, cx, cy);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Animator revealAnimate(ViewGroup viewRoot, @ColorRes int color, int x, int y) {
        float finalRadius = (float) Math.hypot(viewRoot.getWidth(), viewRoot.getHeight());
//        float finalRadius = viewRoot.getWidth();

        Animator anim = ViewAnimationUtils.createCircularReveal(viewRoot, x, y, 0, finalRadius);
        viewRoot.setBackgroundColor(ContextCompat.getColor(viewRoot.getContext(), color));
        anim.setDuration(500);
        anim.start();
        return anim;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void addAnim(View view) {
        final View rootView = view.findViewById(R.id.root_view);
        int w = rootView.getWidth();
        int h = rootView.getHeight();

        int cx = w / 2;
        int cy = h / 2;

        int finalRadius = (int) (Math.sqrt(w * w + h * h) + 1);
        Animator anim;
        anim = ViewAnimationUtils.createCircularReveal(rootView, cx, cy, 0, finalRadius);
        anim.setDuration(400);
        anim.start();

    }
}
