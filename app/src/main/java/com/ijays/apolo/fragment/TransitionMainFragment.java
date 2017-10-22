package com.ijays.apolo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by ijaysdev on 07/10/2017.
 */

public class TransitionMainFragment extends ListFragment {
    private OnItemClickListener mItemClickListener;


    public static TransitionMainFragment newInstance() {
        Bundle args = new Bundle();
        TransitionMainFragment fragment = new TransitionMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] dataList = {"AutoTransition", "Simple Transition API","Scene","Circle Anim"};
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, dataList);
        setListAdapter(adapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (mItemClickListener!=null){
            mItemClickListener.onItemClick(v,position);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
