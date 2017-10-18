package com.ijays.apolo.activity;

import android.os.Bundle;
import android.view.View;

import com.ijays.apolo.R;
import com.ijays.apolo.fragment.AutoTransitionFragment;
import com.ijays.apolo.fragment.BaseFragment;
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

        mainFragment.setOnItemClickListener(this);

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
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
