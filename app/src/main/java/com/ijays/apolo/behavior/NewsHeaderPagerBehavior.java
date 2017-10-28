package com.ijays.apolo.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import com.ijays.apolo.BuildConfig;
import com.ijays.apolo.R;
import com.ijays.apolo.behavior.helper.ViewOffsetBehavior;

import java.lang.ref.WeakReference;

import static android.content.ContentValues.TAG;

/**
 * Created by ijaysdev on 25/07/2017.
 */

public class NewsHeaderPagerBehavior extends ViewOffsetBehavior<View> {

    private Context mContext;
    private OverScroller mOverScroller;

    private WeakReference<CoordinatorLayout> mParent;
    private WeakReference<View> mChild;

    public NewsHeaderPagerBehavior() {
        Log.e("SONGJIE", "no param");
    }

    public NewsHeaderPagerBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("SONGJIE", "two param");
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mOverScroller = new OverScroller(context);
    }

    @Override
    protected void layoutChild(CoordinatorLayout parent, View child, int layoutDirection) {
        super.layoutChild(parent, child, layoutDirection);
        mParent = new WeakReference<>(parent);
        mChild = new WeakReference<>(child);
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild,
                                       View target, int nestedScrollAxes) {

        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0 && canScroll(child, 0) && !isClosed(child);
    }

    @Override
    public boolean onNestedPreFling(CoordinatorLayout coordinatorLayout, View child, View target, float velocityX, float velocityY) {
        //没有关闭
        return !isClosed(child);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);
        //dy>0 向上滑动，dy<0 向下滑动

        float halfOfDis = dy / 4.0f;
        if (!canScroll(child, halfOfDis)) {
            child.setTranslationY(halfOfDis > 0 ? getHeaderOffsetRange() : 0);
        } else {
            child.setTranslationY(child.getTranslationY() - halfOfDis);
        }
        //consumed all scroll behavior after we started Nested Scrolling
        consumed[1] = dy;

    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout parent, View child, MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP && !isClosed(child)) {
            handleActionUp(parent, child);
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    private void handleActionUp(CoordinatorLayout parent, View child) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "handleActionUp: ");
        }
        if (mFlingRunnable != null) {
            child.removeCallbacks(mFlingRunnable);
            mFlingRunnable = null;
        }
        mFlingRunnable = new FlingRunnable(parent, child);
        if (child.getTranslationY() < getHeaderOffsetRange() / 3.0f) {
            mFlingRunnable.scrollToClosed(300);
        } else {
            mFlingRunnable.scrollToOpen(600);
        }
    }

    private boolean canScroll(View child, float pendingDy) {

        int pendingTranslationY = (int) (child.getTranslationY() - pendingDy);
        if (pendingTranslationY >= getHeaderOffsetRange() && pendingTranslationY <= 0) {
            return true;
        }
        return false;
    }

    private boolean isClosed(View child) {
        return (child.getTranslationY() == getHeaderOffsetRange());
    }

    private int getHeaderOffsetRange() {
        return mContext.getResources().getDimensionPixelSize(R.dimen.news_header_pager_offset);
    }


//    private class FlingRunnable implements Runnable {
//        private CoordinatorLayout mParent;
//        private View mLayout;
//
//        public FlingRunnable(CoordinatorLayout parent, View layout) {
//            this.mParent = parent;
//            this.mLayout = layout;
//        }
//
//        @Override
//        public void run() {
//
//        }
//
//        public void scrollToClose(int duration) {
//
//        }
//    }

    private FlingRunnable mFlingRunnable;

    private class FlingRunnable implements Runnable {
        private final CoordinatorLayout mParent;
        private final View mLayout;

        FlingRunnable(CoordinatorLayout parent, View layout) {
            mParent = parent;
            mLayout = layout;
        }

        public void scrollToClosed(int duration) {
            float curTranslationY = ViewCompat.getTranslationY(mLayout);
            float dy = getHeaderOffsetRange() - curTranslationY;
            if (BuildConfig.DEBUG) {
                Log.d(TAG, "scrollToClosed:offest:" + getHeaderOffsetRange());
                Log.d(TAG, "scrollToClosed: cur0:" + curTranslationY + ",end0:" + dy);
                Log.d(TAG, "scrollToClosed: cur:" + Math.round(curTranslationY) + ",end:" + Math.round(dy));
                Log.d(TAG, "scrollToClosed: cur1:" + (int) (curTranslationY) + ",end:" + (int) dy);
            }
            mOverScroller.startScroll(0, Math.round(curTranslationY - 0.1f), 0, Math.round(dy + 0.1f), duration);
            start();
        }

        public void scrollToOpen(int duration) {
            float curTranslationY = ViewCompat.getTranslationY(mLayout);
            mOverScroller.startScroll(0, (int) curTranslationY, 0, (int) -curTranslationY, duration);
            start();
        }

        private void start() {
            if (mOverScroller.computeScrollOffset()) {
                mFlingRunnable = new FlingRunnable(mParent, mLayout);
                ViewCompat.postOnAnimation(mLayout, mFlingRunnable);
            } else {
//                onFlingFinished(mParent, mLayout);
            }
        }


        @Override
        public void run() {
            if (mLayout != null && mOverScroller != null) {
                if (mOverScroller.computeScrollOffset()) {
                    if (BuildConfig.DEBUG) {
                        Log.d(TAG, "run: " + mOverScroller.getCurrY());
                    }
                    ViewCompat.setTranslationY(mLayout, mOverScroller.getCurrY());
                    ViewCompat.postOnAnimation(mLayout, this);
                } else {
//                    onFlingFinished(mParent, mLayout);
                }
            }
        }
    }

}
