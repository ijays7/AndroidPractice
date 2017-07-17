package com.ijays.apolo.activity;

import android.view.View;
import android.view.ViewStub;

import com.ijays.apolo.R;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ijaysdev on 09/07/2017.
 */

public class ViewStubTestActivity extends BaseActivity {
    @BindView(R.id.view_stub)
    ViewStub mViewStub;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_stub_layout;
    }


    @OnClick(R.id.bt_view_stub_inflate)
    void onClickInflate() {
        mViewStub.inflate();
//        mViewStub.setVisibility(View.VISIBLE);
    }
}
