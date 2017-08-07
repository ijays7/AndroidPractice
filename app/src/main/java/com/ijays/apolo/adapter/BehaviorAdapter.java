package com.ijays.apolo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ijays.apolo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ijaysdev on 22/07/2017.
 */

public class BehaviorAdapter extends RecyclerView.Adapter<BehaviorAdapter.BehaviorVH> {
    private List<String> mDataList;

    public BehaviorAdapter(List<String> mDataList) {
        this.mDataList = mDataList;
    }

    @Override
    public BehaviorVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_behavior_layout, parent,
                false);
        return new BehaviorVH(view);
    }

    @Override
    public void onBindViewHolder(BehaviorVH holder, int position) {
        holder.tv.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    static class BehaviorVH extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        public BehaviorVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
