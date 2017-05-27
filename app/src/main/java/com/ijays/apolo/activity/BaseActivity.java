package com.ijays.apolo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by ijaysdev on 25/05/2017.
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);
        initToolbar(savedInstanceState);
        initViews(savedInstanceState);
        initData();
        initListener();
    }

    /**
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * initialize views
     *
     * @param savedInstanceState
     */
    protected void initViews(Bundle savedInstanceState) {
    }

    /**
     * @param savedInstanceState
     */
    protected void initToolbar(Bundle savedInstanceState) {
    }

    /**
     *
     */
    protected void initData() {
    }

    /**
     *
     */
    protected void initListener() {
    }

}
