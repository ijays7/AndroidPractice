package com.ijays.apolo.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.AlignContent;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.ijays.apolo.R;
import com.ijays.apolo.util.ViewUtils;
import com.ijays.apolo.view.GridItemDecoration;
import com.ijays.apolo.view.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ijaysdev on 10/12/2017.
 */

public class FlexBoxTestActivity extends BaseActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_flex_box_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        FlexboxLayoutManager flexManager = new FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP);
        flexManager.setAlignItems(AlignItems.FLEX_START);
        mRecyclerView.addItemDecoration(new MyItemDecoration(this, MyItemDecoration.VERTICAL_LIST, R.drawable.flex_divider_shape
        ));
//        flexManager.setAlignContent(AlignContent.FLEX_START);
        mRecyclerView.setLayoutManager(flexManager);
        FlexBoxAdapter adapter = new FlexBoxAdapter(generateDataList());
        mRecyclerView.setAdapter(adapter);
    }

    private List<String> generateDataList() {
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i == 8) {
                dataList.add("这是一条有点长的测试item" + i);
            } else {
                dataList.add("测试item-->" + i);
            }
        }
        return dataList;
    }

    static class FlexBoxAdapter extends RecyclerView.Adapter<FlexBoxVH> {
        private List<String> mDataList;

        public FlexBoxAdapter(List<String> mDataList) {
            this.mDataList = mDataList;
        }

        @Override
        public FlexBoxVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_flex_box_layout, parent, false);
            return new FlexBoxVH(view);
        }

        @Override
        public void onBindViewHolder(FlexBoxVH holder, int position) {

            holder.mTvFlex.setText(mDataList.get(position));
            holder.mTvFlex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "you click-->" + mDataList.get(position), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDataList == null ? 0 : mDataList.size();
        }
    }

    static class FlexBoxVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_flex)
        TextView mTvFlex;


        public FlexBoxVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
