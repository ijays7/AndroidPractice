package com.ijays.apolo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.ijays.apolo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements Toolbar.OnMenuItemClickListener {
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
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
