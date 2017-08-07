package com.ijays.apolo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.ijays.apolo.R;

import java.util.List;

/**
 * Created by ijaysdev on 25/07/2017.
 */

public class NewsTabBehavior extends HeaderScrollingBehavior {
    private Context mContext;

    public NewsTabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    protected View getFirstDependency(List<View> views) {
        for (View view : views) {
            if (isDependOn(view)) {
                return view;
            }
        }
        return null;
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetAsNeeded(child, dependency);
        return true;
    }

    private void offsetAsNeeded(View child, View dependency) {
        float offsetRange = dependency.getTop() + getFinalHeight() - child.getTop();
        int headerOffsetRange = getHeaderOffsetRange();
        if (dependency.getTranslationY() == headerOffsetRange) {
            child.setTranslationY(offsetRange);
        } else if (dependency.getTranslationY() == 0) {
            child.setTranslationY(0);
        } else {
            child.setTranslationY((int) (dependency.getTranslationY() / (getHeaderOffsetRange() * 1.0f) * offsetRange));
        }
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.news_header_pager;
    }

    private int getHeaderOffsetRange() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.news_header_pager_offset);
    }

    private int getFinalHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.news_header_title_height);
    }

}
