package com.ijays.apolo.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;

import com.ijays.apolo.R;
import com.ijays.apolo.adapter.BehaviorAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ijaysdev on 10/08/2017.
 */

public class MaterialAnimActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_material_anim_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataList.add("This is item-->" + i);
        }
        setupTransition();

        BehaviorAdapter adapter = new BehaviorAdapter(dataList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(adapter);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupTransition() {
        Slide slide = new Slide(Gravity.LEFT);
        slide.setDuration(800);
        slide.setInterpolator(new FastOutSlowInInterpolator());
//        slide.excludeTarget(android.R.id.statusBarBackground, true);
//        slide.excludeTarget(android.R.id.navigationBarBackground, true);
        getWindow().setExitTransition(slide);
    }


}
