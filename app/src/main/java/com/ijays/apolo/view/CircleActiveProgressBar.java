package com.ijays.apolo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ijays.apolo.R;
import com.ijays.apolo.util.ViewUtils;

/**
 * Created by ijaysdev on 02/06/2017.
 */

public class CircleActiveProgressBar extends View {

    private int mCircleBgColor;
    private int mProgressColor;

    //进度条的宽度
    private int mCircleWidth;

    private int mAnimDuration;
    private int mAnimDelayTime;

    private float mRadius;
    //圆心坐标
    private float mCenterX, mCenterY;

    //最终显示的进度
    private float mProgress;
    //当前进度
    private float mCurrentProgress;
    //进度条的总值
    private float mTotalProgress;

    private Paint mPaintBg;
    private Paint mPaintProgress;
    private Paint mTextPaint;

    private Rect mBounds;
    private Rect mOuterTextBounds;

    private RectF mRectF;

    private String mInnerText;// 圆圈中间的文字
    private String mBottomText;// 圆圈底部的文字

    private ValueAnimator mValueAnimator;
    private OnProgressListener mOnProgressListener;


    public CircleActiveProgressBar(Context context) {
        this(context, null);
    }

    public CircleActiveProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleActiveProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar);

        mProgressColor = ta.getColor(R.styleable.CircleProgressBar_circle_progress_color,
                ContextCompat.getColor(context, R.color.colorAccent));
        mCircleBgColor = ta.getColor(R.styleable.CircleProgressBar_circle_bg_color,
                ContextCompat.getColor(context, R.color.colorPrimary));
        mProgress = ta.getInt(R.styleable.CircleProgressBar_circle_progress, 0);
        mTotalProgress = ta.getInt(R.styleable.CircleProgressBar_circle_total, 100);
        mInnerText = ta.getString(R.styleable.CircleProgressBar_circle_inner_text);
        mBottomText = ta.getString(R.styleable.CircleProgressBar_circle_outer_text);
        ta.recycle();

        mCircleWidth = 10;
        mAnimDuration = 800;
        mAnimDelayTime = 500;
        mTotalProgress = 244755;
        mPaintBg = getPaint(mCircleWidth, mCircleBgColor);
        mPaintProgress = getPaint(mCircleWidth, mProgressColor);
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(ViewUtils.sp2px(getContext(), 14));

        mRectF = new RectF();
        mBounds = new Rect();
        mOuterTextBounds = new Rect();

        initAnim();

    }

    private void initAnim() {
        mValueAnimator = ValueAnimator.ofFloat(0, mProgress);
        mValueAnimator.setDuration(mAnimDuration);
        mValueAnimator.setStartDelay(mAnimDelayTime);
        mValueAnimator.setInterpolator(new DecelerateInterpolator());

        mValueAnimator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();

            mCurrentProgress = value / mTotalProgress * 360;
            if (mOnProgressListener != null) {
                mOnProgressListener.onProgress(roundTwo(mCurrentProgress));
            }
            invalidate();
        });
    }


    private Paint getPaint(int strokeWidth, int color) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStrokeWidth(strokeWidth);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        return paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mCenterX = w / 2 - getPaddingLeft() - getPaddingRight();
        mCenterY = h / 2 - getPaddingTop() - getPaddingBottom() - 30;

        mRadius = Math.min(w, h) / 2 - mCircleWidth;
        mRectF.set(mCenterX - mRadius, mCenterY - mRadius,
                mCenterX + mRadius, mCenterY + mRadius);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        mCurrentProgress = mProgress / mTotalProgress * 360;

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaintBg);
        canvas.drawArc(mRectF, 270, mCurrentProgress, false, mPaintProgress);

        mTextPaint.getTextBounds(mInnerText, 0, mInnerText.length(), mBounds);

        canvas.drawText(mInnerText, (mRectF.centerX()) - (mTextPaint.measureText(mInnerText) / 2),
                mRectF.centerY() + mBounds.height() / 2,
                mTextPaint);

        mTextPaint.getTextBounds(mBottomText, 0, mBottomText.length(), mBounds);
        canvas.drawText(mBottomText, (mCenterX - (mTextPaint.measureText(mBottomText) / 2)),
                mRectF.bottom + 40 + mBounds.height() / 2, mTextPaint);
    }

    public CircleActiveProgressBar setProgress(int mProgress) {
        this.mProgress = mProgress;
        initAnim();
        return this;
    }

    public CircleActiveProgressBar setTotalProgress(int total) {
        this.mTotalProgress = total;
        return this;
    }

    public CircleActiveProgressBar setBottomText(String text) {
        this.mBottomText = text;
        invalidate();
        return this;
    }

    public CircleActiveProgressBar setCenterText(int text) {
        this.mInnerText = text + "人";
        invalidate();
        return this;
    }

    public static float roundTwo(float originNum) {
        return (float) (Math.round(originNum * 10) / 10.0);
    }

    public void startProgressAnimation() {
        mValueAnimator.start();
    }

    public void setOnProgressListener(OnProgressListener listener) {
        this.mOnProgressListener = listener;
    }

    public interface OnProgressListener {
        void onProgress(float currentProgress);
    }
}
