package com.ijays.apolo.fragment;

import android.support.transition.Scene;
import android.support.transition.TransitionManager;
import android.view.View;
import android.widget.FrameLayout;

import com.ijays.apolo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ijaysdev on 17/10/2017.
 */

public class SceneFragment extends BaseFragment {
    private int mCurrentScene;

    private Scene mScene1;
    private Scene mScene2;
    private Scene[] mScenes;

    @BindView(R.id.fl_container)
    FrameLayout mContainer;

    @Override
    int getLayoutId() {
        return R.layout.fragment_scene_layout;
    }

    @Override
    protected void initView(View view) {
        mScene1 = Scene.getSceneForLayout(mContainer, R.layout.scene_one_layout, mContainer.getContext());
        mScene2 = Scene.getSceneForLayout(mContainer, R.layout.scene_two_layout, mContainer.getContext());
        mScenes = new Scene[]{mScene1, mScene2};

        TransitionManager.go(mScenes[0]);

    }

    @OnClick(R.id.bt_scene)
    void onClickScene() {
        mCurrentScene = (mCurrentScene + 1) % mScenes.length;
        TransitionManager.go(mScenes[mCurrentScene]);
    }
}
