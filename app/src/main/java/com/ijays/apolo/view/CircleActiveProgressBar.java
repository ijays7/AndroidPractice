package com.ijays.apolo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import com.ijays.apolo.R;

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

    private RectF mRectF;

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

        init(context);
    }

    private void init(Context context) {
        mProgressColor = ContextCompat.getColor(context, R.color.colorAccent);
        mCircleBgColor = ContextCompat.getColor(context, R.color.wave_end);

        mCircleWidth = 15;
        mAnimDuration = 800;
        mAnimDelayTime = 500;
        mTotalProgress = 360;
        mPaintBg = getPaint(mCircleWidth, mCircleBgColor);
        mPaintProgress = getPaint(mCircleWidth, mProgressColor);

        mRectF = new RectF();

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
        mCenterY = h / 2 - getPaddingTop() - getPaddingBottom();

        mRadius = Math.min(w, h) / 2 - mCircleWidth;
        mRectF.set(mCenterX - mRadius, mCenterY - mRadius,
                mCenterX + mRadius, mCenterY + mRadius);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaintBg);
        canvas.drawArc(mRectF, 270, mCurrentProgress, false, mPaintProgress);
    }

    public CircleActiveProgressBar setProgress(float mProgress) {
        this.mProgress = mProgress;
        initAnim();
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
