package com.ijays.apolo.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ijays.apolo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ijaysdev on 25/05/2017.
 */

public class NestedActivity extends ImmersiveActivity {
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.top_bar_layout)
    LinearLayout mTopBarLayout;
    @BindView(R.id.nested_layout)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.title_bar_view)
    View mTitleBarBgView;
    @BindView(R.id.status_bar_view)
    View mStatusBarBgView;

    private float mTopBarHeight;
    private boolean mShouldShowDefaultAlpha;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nested_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        mTitleBarBgView.post(() -> {
            mTopBarHeight = mTopBarLayout.getMeasuredHeight();
            mNestedScrollView.scrollTo(0, 0);
        });

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("xyz", "xyz " + scrollX + " " + scrollY + " " + oldScrollX + " " + oldScrollY);
                if (scrollY < mTopBarHeight) {
                    mStatusBarBgView.setAlpha(scrollY / mTopBarHeight);
                    mTitleBarBgView.setAlpha(scrollY / mTopBarHeight);
                    mShouldShowDefaultAlpha = false;
                } else if (!mShouldShowDefaultAlpha) {
                    //
                    mStatusBarBgView.setAlpha(.95f);
                    mTitleBarBgView.setAlpha(.95f);
                    mShouldShowDefaultAlpha = true;
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        final List<String> dataList = new ArrayList<>(100);
        for (int i = 0; i < 100; i++) {
            String title = "This is title " + i;
            dataList.add(title);
        }


        mRecyclerView.setAdapter(new NestedRvAdapter(dataList));
    }

    class NestedRvAdapter extends RecyclerView.Adapter<NestRvVH> {
        private List<String> dataList;

         NestedRvAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        @Override
        public NestRvVH onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_nested_layout, parent, false);
            return new NestRvVH(v);
        }

        @Override
        public void onBindViewHolder(NestRvVH holder, int position) {
            holder.textView.setText(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList == null ? 0 : dataList.size();
        }
    }

    class NestRvVH extends RecyclerView.ViewHolder {
        private TextView textView;

        public NestRvVH(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_nested);
        }
    }
}
