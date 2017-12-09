package com.ijays.apolo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.ijays.apolo.util.ViewUtils;

/**
 * Created by ijaysdev on 22/11/2017.
 */

public class RingProgressbar extends View {
    private int mTopTextMargin;
    private int mRightTextMargin;
    private int mProgressBarHeight;//进度条的高度
    private int mWidth, mHeight;
    private Paint mPaint;
    private TextPaint mTextPaint;
    private Path mPath;

    private Rect mBounds;

    private String mTopText = "到店 1 次，共 454 人";
    private String mText = "17.5%";

    public RingProgressbar(Context context) {
        this(context, null);
    }

    public RingProgressbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingProgressbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(15);
        mPaint.setColor(Color.RED);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(ViewUtils.sp2px(getContext(), 14));

        mRightTextMargin = ViewUtils.dp2Px(getContext(), 12);
        mTopTextMargin = ViewUtils.dp2Px(getContext(), 6);
        mProgressBarHeight = ViewUtils.dp2Px(getContext(), 14);

        mBounds = new Rect();

        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measurement(widthMeasureSpec, true), measurement(heightMeasureSpec, false));
    }

    private int measurement(int measureSpec, boolean isWidth) {
        int result;
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == View.MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : 50;
            result += padding;
            if (isWidth) {
                result = Math.max(result, size);
            } else {
                result = Math.min(result, size);
            }
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawLine(0, 0, mWidth / 2, 0, mPaint);


        mTextPaint.getTextBounds(mTopText, 0, mTopText.length(), mBounds);
        int topTextHeight = mBounds.height();
        Log.e("SONGJIE", "top text height-->" + topTextHeight);
        canvas.drawText(mTopText, 20, topTextHeight, mTextPaint);

        mTextPaint.getTextBounds(mText, 0, mText.length(), mBounds);
        float textLength = mTextPaint.measureText(mText);
        canvas.drawText(mText, (mWidth - textLength),
                topTextHeight + 10 + mBounds.height(), mTextPaint);


        mPaint.setColor(Color.RED);
        int height = topTextHeight + mTopTextMargin + mProgressBarHeight;
        canvas.drawRoundRect(20, topTextHeight + mTopTextMargin, mWidth - mRightTextMargin - textLength,
                height, 15, 15, mPaint);
        mPaint.setColor(Color.BLUE);
        canvas.drawRoundRect(20, topTextHeight + mTopTextMargin, 150,
                height, 15, 15, mPaint);
    }
}
