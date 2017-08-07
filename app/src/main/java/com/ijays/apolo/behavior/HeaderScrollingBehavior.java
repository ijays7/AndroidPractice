package com.ijays.apolo.behavior;

import android.content.Context;
import android.graphics.Rect;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import com.ijays.apolo.behavior.helper.ViewOffsetBehavior;
import com.ijays.apolo.util.MathUtil;
import com.ijays.apolo.util.ViewUtils;

import java.util.List;

/**
 * Created by ijaysdev on 23/07/2017.
 */

public abstract class HeaderScrollingBehavior extends ViewOffsetBehavior<View> {

    private final Rect mTempRect1 = new Rect();
    private final Rect mTempRect2 = new Rect();

    private int mVerticalLayoutGap;
    private int mOverlayTop;

    public HeaderScrollingBehavior() {
    }

    public HeaderScrollingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec,
                                  int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        int childLpheight = child.getLayoutParams().height;
        if (childLpheight == CoordinatorLayout.LayoutParams.MATCH_PARENT
                || childLpheight == CoordinatorLayout.LayoutParams.WRAP_CONTENT) {

            List<View> dependencies = parent.getDependencies(child);
            View header = getFirstDependency(dependencies);
            if (header != null) {
                if (ViewCompat.getFitsSystemWindows(header) && !ViewCompat.getFitsSystemWindows(child)) {


                    ViewCompat.setFitsSystemWindows(child, true);
                    if (ViewCompat.getFitsSystemWindows(child)) {
                        child.requestLayout();
                        return true;
                    }
                }

                if (ViewCompat.isLaidOut(header)) {
                    int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
                    if (availableHeight == 0) {
                        availableHeight = parent.getHeight();
                    }

                    int height = availableHeight - header.getMeasuredHeight() + getScrollRange(header);
                    int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
                            childLpheight == CoordinatorLayout.LayoutParams.MATCH_PARENT ?
                                    View.MeasureSpec.EXACTLY : View.MeasureSpec.AT_MOST);

                    parent.onMeasureChild(child, parentWidthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
                    return true;
                }
            }


        }

        return false;
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        List<View> dependencies = parent.getDependencies(child);
        View header = getFirstDependency(dependencies);

        if (header != null) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            Rect available = mTempRect1;
            available.set(parent.getPaddingLeft() + lp.leftMargin, header.getBottom() + lp.topMargin,
                    parent.getWidth() - parent.getPaddingRight() - lp.rightMargin,
                    parent.getHeight() + header.getPaddingBottom() - -parent.getPaddingBottom() - lp.bottomMargin);

            Rect out = mTempRect2;
            GravityCompat.apply(ViewUtils.resolveGravity(lp.gravity), child.getMeasuredWidth(),
                    child.getMeasuredHeight(), available, out, layoutDirection);

            int overlap = getOverlapPixelsForOffset(header);
            child.layout(out.left, out.top - overlap, out.right, out.bottom - overlap);
            mVerticalLayoutGap = out.top - header.getBottom();

        } else {
            super.layoutChild(parent, child, layoutDirection);
            mVerticalLayoutGap = 0;
        }
    }

    float getOverlapRationForoffset(View header) {
        return 1f;
    }

    final int getOverlapPixelsForOffset(View header) {
        return mOverlayTop == 0 ? 0 : MathUtil.constrain(Math.round(getOverlapRationForoffset(header) * mOverlayTop),
                0, mOverlayTop);
    }

    protected abstract View getFirstDependency(List<View> views);

    protected int getScrollRange(View view) {
        return view.getMeasuredHeight();
    }


}
