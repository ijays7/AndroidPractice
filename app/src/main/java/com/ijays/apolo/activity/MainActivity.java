package com.ijays.apolo.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ijays.apolo.AppConstants;
import com.ijays.apolo.R;
import com.ijays.apolo.util.ScrollUtil;
import com.ijays.apolo.util.ViewUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends ImmersiveActivity implements Toolbar.OnMenuItemClickListener {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.scroll_view)
    NestedScrollView mScrollView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {
        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(this);
    }


    @OnClick(R.id.fab)
    void onClickFab(View view) {
        //保存ScrollView的图像并存储
        View topView = View.inflate(view.getContext(), R.layout.top_layout, null);
        View bottomView = View.inflate(view.getContext(), R.layout.logo_layout, null);

        Bitmap topBitmap = ScrollUtil.getViewBitmap(topView);
        Bitmap bottomBitmap = ScrollUtil.getViewBitmap(bottomView);
        Log.e("SONGJIE", "bottom bm width-->" + bottomBitmap.getWidth());

        Log.e("SONGJIE", "width-->" + topBitmap.getWidth() + "---height--->" + topBitmap.getHeight());

        String mPath = AppConstants.MOCHA_PATH + "test.png";

        Bitmap bm = ScrollUtil.shotScrollView(mScrollView);
        Log.e("SONGJIE", "bm is null?--" + (bm == null));
        String path = ScrollUtil.saveImageToFile(mPath, ScrollUtil.addBitmap(topBitmap,bm, bottomBitmap),
                100, Bitmap.CompressFormat.PNG);
        Log.e("SONGJIE", "save path is -->" + path);
    }

    @OnClick(R.id.bt_wave)
    void onClickWave() {
        Intent intent = new Intent(MainActivity.this, WaveActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    @OnClick(R.id.bt_active_progress)
    void onClickActive() {
        Intent intent = new Intent(MainActivity.this, ActiveProgressbarActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    @OnClick(R.id.bt_view_stub)
    void onClickViewStub() {
        Intent intent = new Intent(MainActivity.this, ViewStubTestActivity.class);
        startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }

    @OnClick(R.id.bt_behavior)
    void onClickBehavior() {
        startActivity(new Intent(MainActivity.this, BehaviorTestActivity.class));
    }

    @OnClick(R.id.bt_transition)
    void onClickTransition() {
        startActivity(new Intent(MainActivity.this, TransitionActivity.class));
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nested_layout:
                startActivity(new Intent(MainActivity.this, NestedActivity.class));
                return true;
            default:
                break;
        }
        return false;
    }
}
