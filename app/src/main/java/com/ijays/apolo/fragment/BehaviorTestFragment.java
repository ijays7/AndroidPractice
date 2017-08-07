package com.ijays.apolo.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ijays.apolo.R;
import com.ijays.apolo.adapter.BehaviorAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ijaysdev on 22/07/2017.
 */

public class BehaviorTestFragment extends BaseFragment {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private Context mContext;

    public static BehaviorTestFragment newInstance() {

        Bundle args = new Bundle();

        BehaviorTestFragment fragment = new BehaviorTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    int getLayoutId() {
        return R.layout.fragment_behavior_layout;
    }

    @Override
    protected void initView() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dataList.add("This is item-->" + i);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,
                false));
        mRecyclerView.setAdapter(new BehaviorAdapter(dataList));

    }
}
