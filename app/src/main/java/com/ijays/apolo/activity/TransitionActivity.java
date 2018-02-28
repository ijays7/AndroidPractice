package com.ijays.apolo.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.ijays.apolo.R;
import com.ijays.apolo.fragment.AutoTransitionFragment;
import com.ijays.apolo.fragment.BaseFragment;
import com.ijays.apolo.fragment.CircleAnimFragment;
import com.ijays.apolo.fragment.ClipTransitionFragment;
import com.ijays.apolo.fragment.SceneFragment;
import com.ijays.apolo.fragment.TransitionMainFragment;

/**
 * Created by ijaysdev on 06/10/2017.
 */

public class TransitionActivity extends BaseActivity implements TransitionMainFragment.OnItemClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_transition_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

        TransitionMainFragment mainFragment = TransitionMainFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, mainFragment)
                .commit();

        setupTransition();
        mainFragment.setOnItemClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupTransition() {
        Slide slide = new Slide(Gravity.LEFT);
        slide.setDuration(1000);
        slide.setInterpolator(new FastOutSlowInInterpolator());
        slide.excludeTarget(android.R.id.statusBarBackground, true);
        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(slide);
    }

    @Override
    public void onItemClick(View view, int position) {
        BaseFragment fragment = null;
        switch (position) {
            case 0:
                fragment = new AutoTransitionFragment();
                break;
            case 1:
                fragment = new ClipTransitionFragment();
                break;
            case 2:
                fragment = new SceneFragment();
                break;
            case 3:
                fragment = new CircleAnimFragment();
                break;
            default:
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
