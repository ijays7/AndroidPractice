package com.ijays.apolo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.ijays.apolo.R;
import com.ijays.apolo.util.ViewUtils;


/**
 * Created by ijaysdev on 27/02/2017.
 */

public class CustomProgressBar extends View {
    private static final int DEFAULT_TEXT_VISIBILITY = 0;//set text visible as default
    private static final int DEFAULT_REACHED_COLOR = Color.rgb(66, 145, 241);
    private static final int DEFAULT_UNREACHED_COLOR = Color.rgb(204, 204, 204);

    private int mMaxProgress = 100;

    private int mCurrentProgress = 0;

    private int mFastProgress;

    private int mReachBarColor, mUnreachedBarColor;

    private int mTextColor;

    private float mTextSize;

    private float mOffset;

    //是否绘制左右矩形、文字
    private boolean mDrawReachBar = true;
    private boolean mDrawUnreachedBar = true;
    private boolean mIfDrawText = true;

    private float mDrawTextStart, mDrawTextEnd;
    private float mReachedBarHeight, mUnreachedBarHeight;

    private String mCurrentDrawText;

    private Paint mTextPaint;
    private Paint mReachBarPaint, mUnreachedBarPaint;

    private RectF mReachedRectF;
    private RectF mUnreachedRectF;

    private final float default_progress_text_offset;
    private final float default_text_size;
    private final float default_reached_bar_height;
    private final float default_unreached_bar_height;

    private ValueAnimator mValueAnimator;


    public CustomProgressBar(Context context) {
        this(context, null);
    }

    public CustomProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        default_reached_bar_height = ViewUtils.dp2Px(getContext(), 1.5f);
        default_unreached_bar_height = ViewUtils.dp2Px(getContext(), 1.0f);
        default_text_size = ViewUtils.sp2px(getContext(), 10);
        default_progress_text_offset = ViewUtils.dp2Px(getContext(), 3.0f);

        initAttrs(context, attrs);
        initPaint();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        mReachedRectF = new RectF(0, 0, 0, 0);
        mUnreachedRectF = new RectF(0, 0, 0, 0);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NumberProgressBar);

        mReachBarColor = ta.getColor(R.styleable.NumberProgressBar_progress_reached_color, ContextCompat.getColor(getContext(),
                R.color.google_green));
        mUnreachedBarColor = ta.getColor(R.styleable.NumberProgressBar_progress_unreached_color, ContextCompat.getColor(getContext(),
                R.color.grey));
        mTextColor = ta.getColor(R.styleable.NumberProgressBar_progress_text_color, ContextCompat.getColor(getContext(),
                R.color.grey));
        mTextSize = ta.getDimension(R.styleable.NumberProgressBar_progress_text_size, default_text_size);

        mReachedBarHeight = ta.getDimension(R.styleable.NumberProgressBar_progress_reached_bar_height, default_reached_bar_height);
        mUnreachedBarHeight = ta.getDimension(R.styleable.NumberProgressBar_progress_unreached_bar_height, default_unreached_bar_height);
        mOffset = ta.getDimension(R.styleable.NumberProgressBar_progress_text_offset, default_progress_text_offset);

        mCurrentProgress = ta.getInt(R.styleable.NumberProgressBar_progress_current, 0);
        int textVisibility = ta.getInt(R.styleable.NumberProgressBar_progress_text_visibility, DEFAULT_TEXT_VISIBILITY);
        if (textVisibility != DEFAULT_TEXT_VISIBILITY) {
            mIfDrawText = false;
        }
        ta.recycle();
    }

    private void initPaint() {
        mReachBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachBarPaint.setColor(mReachBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }


    private float mRatio;

    private void initAnimation() {
        mValueAnimator = ValueAnimator.ofInt(0, mMaxProgress);
        mValueAnimator.setDuration(800);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.addUpdateListener((animation -> {
//            float value = (float) animation.getAnimatedValue();
//            mRatio = value / mMaxProgress;

            Log.e("SONGJIE","===vaue==>"+animation.getAnimatedValue());
            mCurrentProgress = (int) animation.getAnimatedValue();

//            if (mCurrentProgress >= mTipWidth / 2 && mCurrentProgress <= (getWidth() - mTipWidth / 2)) {
//                mLeftDistance = mCurrentProgress - mTipWidth / 2;
//            }

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
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
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
    protected int getSuggestedMinimumWidth() {
        return (int) mTextSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return (int) Math.max(mTextSize, Math.max(mReachedBarHeight, mUnreachedBarHeight));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIfDrawText) {
            calculateDrawRectF();
        } else {
            calculateDrawRectWithoutProgressText();
        }

        if (mDrawReachBar) {
            canvas.drawRect(mReachedRectF, mReachBarPaint);
        }
//        if (mDrawUnreachedBar) {
//            canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
//        }
        if (mIfDrawText) {
            canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTextPaint);
        }
    }

    private void calculateDrawRectWithoutProgressText() {
        mReachedRectF.left = getPaddingLeft();
//        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.top = 0;
        mReachedRectF.bottom = getHeight();
//        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;

        mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMaxProgress() * 1.0f)
                * getProgress() + getPaddingLeft();

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.top = 0;
        mUnreachedRectF.bottom = getHeight();
//        mUnreachedRectF.top = getHeight() / 2.0f - mUnreachedBarHeight / 2.0f;
//        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
    }

    private void calculateDrawRectF() {

        mCurrentDrawText = String.format("%d", getProgress() * 100 / getMaxProgress());
        mCurrentDrawText = mCurrentDrawText + "%";
        float drawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        if (getProgress() == 0) {
            //此时不绘制左侧矩形
            mDrawReachBar = false;
            mDrawTextStart = getPaddingLeft();

        } else {
            mDrawReachBar = true;
            mReachedRectF.left = getPaddingLeft();
            mReachedRectF.top = getHeight() / 2 - mReachedBarHeight / 2;
            mReachedRectF.bottom = getHeight() / 2 + mReachedBarHeight / 2;
            mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMaxProgress() * 1f)
                    * getProgress() + mOffset + getPaddingLeft();
            mDrawTextStart = mReachedRectF.right + mOffset;
        }
        mDrawTextEnd = getHeight() / 2.0f - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f);

        if (mDrawTextStart + drawTextWidth >= getWidth() - getPaddingRight()) {
            //此时仅绘制文字加左边的矩形
            mDrawTextStart = getWidth() - getPaddingRight() - drawTextWidth;
            mReachedRectF.right = mDrawTextStart - mOffset;
        }
        float unreachedBarStart = mDrawTextStart + drawTextWidth + mOffset;
        if (unreachedBarStart >= getWidth() - getPaddingRight()) {
            mDrawUnreachedBar = false;
        } else {
            mDrawUnreachedBar = true;
            mUnreachedRectF.left = unreachedBarStart;
            mUnreachedRectF.right = getWidth() - getPaddingRight();
            mUnreachedRectF.top = getHeight() / 2.0f - mUnreachedBarHeight / 2.0f;
            mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
        }
    }

    public int getProgress() {
        return mCurrentProgress;
    }

    public int getMaxProgress() {
        return mMaxProgress;
    }

    public void incrementProgressBy(int progress) {
        setProgress(getProgress() + progress);
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = maxProgress;
    }

    public void setFastProgress(int progress) {
        this.mFastProgress = progress;
        initAnimation();
    }

    public void setProgress(int progress) {
        if (progress <= getMaxProgress()) {
            this.mCurrentProgress = progress;
            invalidate();
        }
    }

    public void decrementProgreeBy(int progress) {
        setProgress(getProgress() - progress);
    }


}
