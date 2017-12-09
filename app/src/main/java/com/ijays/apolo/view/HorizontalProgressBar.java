package com.ijays.apolo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ijays.apolo.R;
import com.ijays.apolo.util.ViewUtils;

import java.text.DecimalFormat;

/**
 * 水平的进度条
 * Created by ijaysdev on 04/06/2017.
 */

public class HorizontalProgressBar extends View {
    private static final int DEFAULT_ANIM_DURATION = 1000;

    private int mViewHeight;
    //进度条画笔的宽度
    private int mProgressPaintWidth;
    //Tip的宽度和高度
    private int mTipWidth, mTipHeight;

    //Tip和progressbar之间的距离
    private int mProgress2Tip;
    private int mTriangleHeight;

    private int mProgressBgColor, mProgressColor, mTipColor, mTextColor;

    private int mAnimDuration;

    private float mTotalProgress;//进度条的进度总值
    private float mProgress;//进度条要到的值
    private float mCurrentProgress;//当前进度的值

    private float mLeftDistance;//左边的边距
    private float mRatio;//展示的进度占总进度的比例

    private Path mPath;//绘制三角形的path

    private RectF mTipRect;
    private RectF mBgRect;

    private Paint mBgPaint;
    private Paint mProgressPaint;
    private Paint mTextPaint;
    private Paint mTipPaint;

    private DecimalFormat mDecimalFormat;

    private ValueAnimator mValueAnimator;


    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {

        mAnimDuration = DEFAULT_ANIM_DURATION;
        mTipWidth = ViewUtils.dp2Px(context, 30);
        mTipHeight = ViewUtils.dp2Px(context, 15);
        mProgressPaintWidth = ViewUtils.dp2Px(context, 10);
        mProgress2Tip = ViewUtils.dp2Px(context, 8);
        mTriangleHeight = ViewUtils.dp2Px(context, 3);
        int textSize = ViewUtils.sp2px(context, 10);

        mProgressBgColor = ContextCompat.getColor(context, R.color.grey);
        mProgressColor = ContextCompat.getColor(context, R.color.colorAccent);
        mTipColor = ContextCompat.getColor(context, R.color.colorAccent);
        mTextColor = Color.WHITE;

        mBgPaint = getPaint(mProgressPaintWidth, mProgressBgColor, Paint.Style.STROKE);
        mProgressPaint = getPaint(mProgressPaintWidth, mProgressColor, Paint.Style.STROKE);
        mTipPaint = getPaint(1, mTipColor, Paint.Style.FILL);

        mBgRect = new RectF();
        mTextPaint = new Paint();
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(textSize);

        mTipRect = new RectF();
        mPath = new Path();

        mViewHeight = getPaddingTop() + mTipHeight + mProgress2Tip + mProgressPaintWidth + getPaddingBottom();

    }

    private Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);

        return paint;
    }

    private void initAnimation() {
        mValueAnimator = ValueAnimator.ofFloat(0, mProgress);
        mValueAnimator.setDuration(mAnimDuration);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());
        mValueAnimator.addUpdateListener((animation -> {
            float value = (float) animation.getAnimatedValue();
            mRatio = value / mTotalProgress;

            mCurrentProgress = (getWidth()) * mRatio;

            if (mCurrentProgress >= mTipWidth / 2 && mCurrentProgress <= (getWidth() - mTipWidth / 2)) {
                mLeftDistance = mCurrentProgress - mTipWidth / 2;
            }

            invalidate();
        }));

        mValueAnimator.start();
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
            result = isWidth ? getSuggestedMinimumWidth() : mViewHeight;
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
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(getPaddingLeft(), getPaddingTop() + mTipHeight + mProgress2Tip, getWidth() - getPaddingRight(),
                getPaddingTop() + mTipHeight + mProgress2Tip, mBgPaint);
        canvas.drawLine(getPaddingLeft(), getPaddingTop() + mTipHeight + mProgress2Tip, mCurrentProgress - getPaddingRight(),
                getPaddingTop() + mTipHeight + mProgress2Tip, mProgressPaint);

        drawTips(canvas);
    }

    private void drawTips(Canvas canvas) {

        mTipRect.set(mLeftDistance, getPaddingTop(), mLeftDistance + mTipWidth, mTipHeight + getPaddingTop());
        canvas.drawRoundRect(mTipRect, 5, 5, mTipPaint);

        drawTriangle(canvas);
        drawText(canvas, formatNumber(mRatio * 100) + "%");
    }

    private void drawTriangle(Canvas canvas) {

        //tip宽度一半的坐标
        float centerCoordinate = mLeftDistance + mTipWidth / 2;
        float yCoordinate = mTipHeight + getPaddingTop();
        mPath.moveTo(centerCoordinate - mTriangleHeight, yCoordinate);
        mPath.lineTo(centerCoordinate, yCoordinate + mTriangleHeight);
        mPath.lineTo(centerCoordinate + mTriangleHeight, yCoordinate);
        canvas.drawPath(mPath, mTipPaint);
        mPath.reset();
    }


    private void drawText(Canvas canvas, String textString) {
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        float baseline = getPaddingTop() + (mTipHeight - fontMetrics.bottom - fontMetrics.top) / 2;
        float textWidth = mTextPaint.measureText(textString);

        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, mLeftDistance + (mTipWidth - textWidth) / 2, baseline, mTextPaint);
    }

    private String formatNumber(float value) {
        if (mDecimalFormat == null) {
            mDecimalFormat = new DecimalFormat("0");
        }
        return mDecimalFormat.format(value);
    }

    public HorizontalProgressBar setTotalProgress(float totalProgress) {
        this.mTotalProgress = totalProgress;
        return this;
    }

    public HorizontalProgressBar setTextColor(int color) {
        this.mTextColor = color;
        return this;
    }

    public HorizontalProgressBar setAnimationDuration(int duration) {
        this.mAnimDuration = duration;
        return this;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pauseAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.pause();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.resume();
        }
    }

    public void setProgress(float progress) {
        this.mProgress = progress;
        initAnimation();
    }
}
