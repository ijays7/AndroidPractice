package com.ijays.apolo.fragment;

import android.os.Bundle;

import com.ijays.apolo.R;

/**
 * Created by ijaysdev on 12/08/2017.
 */

public class ShareElementFragment extends BaseFragment {

    public static ShareElementFragment newInstance() {

        Bundle args = new Bundle();

        ShareElementFragment fragment = new ShareElementFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_share_element_layout;
    }
}
