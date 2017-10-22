package com.ijays.apolo.fragment;

import android.graphics.Rect;
import android.support.transition.ChangeBounds;
import android.support.transition.ChangeClipBounds;
import android.support.transition.ChangeTransform;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.view.ViewCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ijays.apolo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ijaysdev on 10/10/2017.
 */

public class ClipTransitionFragment extends BaseFragment {
    @BindView(R.id.iv_photo)
    ImageView mIvPhoto;
    @BindView(R.id.iv_add)
    ImageView mIvAdd;
    @BindView(R.id.iv_move)
    ImageView mIvMove;
    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;

    private static final Rect RECT = new Rect(0, 0, 100, 100);
    private Transition mTransition;
    private Transition mTransformTransition;

    @Override
    int getLayoutId() {
        return R.layout.fragment_clip_transition_layout;
    }

    @Override
    protected void initView(View view) {
        //修改剪切的区域，在Api18 即Android4.3之前无效
        mTransition = new ChangeClipBounds();
        //修改View 的缩放和旋转
        mTransformTransition = new ChangeTransform();

    }

    @OnClick(R.id.bt_transition)
    void onClickTransition() {
        TransitionManager.beginDelayedTransition(mFlContainer, mTransition);
        if (RECT.equals(ViewCompat.getClipBounds(mIvPhoto))) {
            ViewCompat.setClipBounds(mIvPhoto, null);
        } else {
            ViewCompat.setClipBounds(mIvPhoto, RECT);
        }
    }

    @OnClick({R.id.bt_change_transform, R.id.iv_add})
    void onClickTransform() {
        TransitionManager.beginDelayedTransition(mFlContainer, mTransformTransition);
        if (mIvAdd.getRotation() == 45) {
            mIvAdd.setRotation(0);
        } else {
            mIvAdd.setRotation(45);
        }
    }

    @OnClick(R.id.bt_change_position)
    void onClickPosition() {
        TransitionManager.beginDelayedTransition(mFlContainer);
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mIvMove.getLayoutParams();
        if ((lp.gravity & Gravity.LEFT) != Gravity.LEFT) {
            lp.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        } else {
            lp.gravity = Gravity.CENTER;
        }
        mIvMove.setLayoutParams(lp);

    }
}
