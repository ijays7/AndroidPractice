package com.ijays.apolo.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class NestedActivity extends BaseActivity {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_nested_layout;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.hasFixedSize();

        mTopBarLayout.post(new Runnable() {
            @Override
            public void run() {
                mTopBarHeight = mTopBarLayout.getMeasuredHeight();
                mNestedScrollView.scrollTo(0, 0);
            }
        });

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.i("xyz", "xyz " + scrollX + " " + scrollY + " " + oldScrollX + " " + oldScrollY);
                if (scrollY < mTopBarHeight) {
                    mStatusBarBgView.setAlpha(scrollY / mTopBarHeight);
                    mTitleBarBgView.setAlpha(scrollY / mTopBarHeight);
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

        mRecyclerView.setAdapter(new RecyclerView.Adapter() {
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                int px32 = 32;
                TextView textView = new TextView(NestedActivity.this);
                textView.setPadding(px32, px32, px32, px32);

                return new RecyclerView.ViewHolder(textView) {
                    @Override
                    public String toString() {
                        return super.toString();
                    }
                };
            }

            @Override
            public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
                TextView textView = (TextView) holder.itemView;
                textView.setText(dataList.get(position));
            }

            @Override
            public int getItemCount() {
                return dataList.size();
            }
        });
    }
}
