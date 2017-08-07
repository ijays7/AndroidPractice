package com.ijays.apolo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.ijays.apolo.R;

import java.util.List;

/**
 * Created by ijaysdev on 22/07/2017.
 */

public class NewsContentBehavior extends HeaderScrollingBehavior {
    private Context mContext;

    public NewsContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        offsetChildAsNeeded(child, dependency);
        return true;
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

    private void offsetChildAsNeeded(View child, View dependency) {
        child.setTranslationY(-dependency.getTranslationY() / getHeaderOffsetRange() * getScrollRange(dependency));
    }

    @Override
    protected int getScrollRange(View view) {
        if (isDependOn(view)) {
            return Math.max(0, view.getMeasuredHeight() - getFinalHeight());
        }
        return super.getScrollRange(view);
    }

    private int getHeaderOffsetRange() {
        return mContext.getResources().getDimensionPixelSize(R.dimen.news_header_pager_offset);
    }

    private int getFinalHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.news_header_title_height) +
                mContext.getResources().getDimensionPixelOffset(R.dimen.news_header_tab_height);
    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.news_header_pager;
    }
}
