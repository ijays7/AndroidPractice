package com.ijays.apolo.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ijays.apolo.R;
import com.ijays.apolo.activity.SharedElementActivity;
import com.ijays.apolo.util.TransitionHelpler;

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
        @BindView(R.id.iv)
        ImageView iv;

        public BehaviorVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener((View view) -> {
//                view.getContext().startActivity(new Intent(view.getContext(), SharedElementActivity.class));
                transitionToActivity((Activity) view.getContext(), SharedElementActivity.class, this);
            });
        }

        private void transitionToActivity(Activity activity, Class target, RecyclerView.ViewHolder viewHolder) {
            Pair<View, String>[] pairs = TransitionHelpler.createSafeTransationParticipants(activity, false,
                    new Pair<>(tv, "transition"), new Pair<>(iv, "circle"));
            startActivity(activity, target, pairs);
        }

        public void startActivity(Activity activity, Class target, Pair<View, String>[] pair) {
            Intent intent = new Intent(activity, target);
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pair);
            intent.putExtra("sample", tv.getText().toString());
            activity.startActivity(intent, options.toBundle());
        }
    }
}
