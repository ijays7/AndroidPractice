package com.ijays.apolo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

import com.ijays.apolo.R;

/**
 * Created by ijaysdev on 25/07/2017.
 */

public class NewsTitleBehavior extends CoordinatorLayout.Behavior<View> {
    private Context mContext;

    public NewsTitleBehavior() {
    }

    public NewsTitleBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return isDependOn(dependency);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, View child, int layoutDirection) {

        //TODO
        ((CoordinatorLayout.LayoutParams) child.getLayoutParams()).topMargin = (-getTitleHeight());
        parent.onLayoutChild(child, layoutDirection);

        return true;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {

        offsetChildAsNeeded(child, dependency);
        return true;
    }

    private void offsetChildAsNeeded(View child, View dependency) {
        int headerOffsetrange = getHeaderScrollRange();
        int titleOffsetRange = getTitleHeight();

        if (dependency.getTranslationY() == headerOffsetrange) {
            child.setTranslationY(titleOffsetRange);

        } else if (dependency.getTranslationY() == 0) {
            child.setTranslationY(0);
        } else {
            child.setTranslationY((dependency.getTranslationY() / headerOffsetrange) * titleOffsetRange);
        }

    }

    private boolean isDependOn(View dependency) {
        return dependency != null && dependency.getId() == R.id.news_header_pager;
    }

    private int getTitleHeight() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.news_header_title_height);
    }

    private int getHeaderScrollRange() {
        return mContext.getResources().getDimensionPixelOffset(R.dimen.news_header_pager_offset);
    }
}
