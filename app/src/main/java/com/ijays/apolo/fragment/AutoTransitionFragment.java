package com.ijays.apolo.fragment;

import android.graphics.Color;
import android.support.transition.AutoTransition;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ijays.apolo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ijaysdev on 08/10/2017.
 */

public class AutoTransitionFragment extends BaseFragment {
    @BindView(R.id.tv_test)
    TextView mLabelTv;
    @BindView(R.id.ll_container)
    LinearLayout mContainer;
    @BindView(R.id.ll)
    LinearLayout mLL;
    private Transition mTransition;

    @Override
    int getLayoutId() {
        return R.layout.fragment_auto_transition_layout;
    }

    @Override
    protected void initView(View view) {

        //指明默认的效果，先渐隐，在相应的move，resize，最后再渐显
        mTransition = new AutoTransition();
    }

    int count = 0;

    @OnClick(R.id.bt_transition)
    void onClickTransition() {
        //在当前场景到下一帧的过渡效果是什么
        TransitionManager.beginDelayedTransition(mContainer, mTransition);
        if (mLL.getVisibility() == View.VISIBLE) {
            mLL.setVisibility(View.GONE);
        } else {
            mLL.setVisibility(View.VISIBLE);
        }
//        if (count % 2 == 0) {
//            mLabelTv.setTextColor(Color.RED);
//        } else {
//            mLabelTv.setTextColor(Color.BLUE);
//        }
//        count++;
    }
}
