package com.ijays.apolo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ijays.apolo.R;
import com.ijays.apolo.util.ViewUtils;

/**
 * 波浪动画
 * Created by ijaysdev on 28/05/2017.
 */

public class WaveView extends View {
    private static final int SIN = 0;
    private static final int COS = 1;
    private static final int SIN_AND_COS = 2;
    public static final int DEFAULT_WAVE_DURATION = 2000;
    public static final int DEFAULT_WAVE_Amplitude = 15;
    //默认的波浪宽度
    public static final int DEFAULT_WAVE_LENGTH = 500;


    private int mWidth, mHeight;

    private int mWaveType;

    private int mWaveAmplitude;//振幅

    private int mOffset;
    private int mCenterY;

    private int mCycleCount;//屏幕宽度内显示多少个周期
    private int mWaveLength;//一个周期波浪的长度

    private int mWaveStartColor, mWaveEndColor;//波浪起始和结束的颜色

    private int mWaveDuration;

    private Path mWavePath;

    private Paint mWavePaint;

    private Shader mWaveShader;//渐变器

    private ValueAnimator mWaveAnimator;


    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WaveView);


        mWaveAmplitude = ta.getDimensionPixelOffset(R.styleable.WaveView_waveAmplitude,
                ViewUtils.dp2Px(getContext(), DEFAULT_WAVE_Amplitude));
        mWaveLength = ta.getDimensionPixelOffset(R.styleable.WaveView_waveLength,
                ViewUtils.dp2Px(getContext(), DEFAULT_WAVE_LENGTH));

        mWaveType = ta.getInt(R.styleable.WaveView_waveType, SIN);
        mWaveDuration = ta.getInt(R.styleable.WaveView_waveDuration, DEFAULT_WAVE_DURATION);

        mWaveStartColor = ta.getColor(R.styleable.WaveView_waveStartColor,
                ContextCompat.getColor(getContext(), R.color.wave_start));
        mWaveEndColor = ta.getColor(R.styleable.WaveView_waveEndColor,
                ContextCompat.getColor(getContext(), R.color.wave_end));
        ta.recycle();

        initPaint();
        initAnimation();
    }

    private void initPaint() {
        mWavePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWavePaint.setStrokeWidth(1);
        mWavePaint.setStyle(Paint.Style.FILL_AND_STROKE);

        int[] colors = new int[2];
        colors[0] = mWaveStartColor;
        colors[1] = mWaveEndColor;

        mWavePath = new Path();

        mWaveShader = new LinearGradient(0, 0, getWidth(), 0, colors, null,
                Shader.TileMode.CLAMP);

    }

    private void initAnimation() {
        mWaveAnimator = ValueAnimator.ofInt(0, mWaveLength);
        mWaveAnimator.setDuration(mWaveDuration);
        mWaveAnimator.setInterpolator(new LinearInterpolator());
        mWaveAnimator.setRepeatCount(ValueAnimator.INFINITE);

        mWaveAnimator.addUpdateListener((ValueAnimator animation) -> {
            mOffset = (int) animation.getAnimatedValue();
            invalidate();

        });

        mWaveAnimator.start();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        mCycleCount = (int) Math.round(mWidth / mWaveLength + 1.5);
        mCenterY = mHeight * 3 / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mWavePaint.setShader(mWaveShader);
        switch (mWaveType) {
            case SIN:
                drawWaveSin(canvas);
                break;
            case COS:
                drawWaveCos(canvas);
                break;
            case SIN_AND_COS:
                drawSinAndCos(canvas);
                break;
            default:
                break;
        }
    }

    private void drawWaveSin(Canvas canvas) {
        mWavePath.reset();
        mWavePath.moveTo(-mWaveLength + mOffset, mCenterY);

        for (int i = 0; i < mCycleCount; i++) {
            mWavePath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mOffset,
                    mCenterY - mWaveAmplitude,
                    (-mWaveLength / 2) + (i * mWaveLength) + mOffset,
                    mCenterY);

            mWavePath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mOffset,
                    mCenterY + mWaveAmplitude,
                    i * mWaveLength + mOffset,
                    mCenterY);
        }

        fillTop();
        canvas.drawPath(mWavePath, mWavePaint);
    }

    private void drawWaveCos(Canvas canvas) {
        mWavePath.reset();
        mWavePath.moveTo(-mWaveLength + mOffset, mCenterY);

        for (int i = 0; i < mCycleCount; i++) {
            mWavePath.quadTo((-mWaveLength * 3 / 4) + (i * mWaveLength) + mOffset,
                    mCenterY + mWaveAmplitude,
                    (-mWaveLength / 2) + (i * mWaveLength) + mOffset,
                    mCenterY);

            mWavePath.quadTo((-mWaveLength / 4) + (i * mWaveLength) + mOffset,
                    mCenterY - mWaveAmplitude,
                    i * mWaveLength + mOffset,
                    mCenterY);
        }

        fillTop();
        canvas.drawPath(mWavePath, mWavePaint);
    }

    private void drawSinAndCos(Canvas canvas) {
        drawWaveSin(canvas);
        drawWaveCos(canvas);
    }

    private void fillTop() {
        mWavePath.lineTo(mWidth, 0);
        mWavePath.lineTo(0, 0);
        mWavePath.close();

    }

    public void onPause() {
        if (mWaveAnimator != null && mWaveAnimator.isRunning()
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWaveAnimator.pause();
        }
    }

    public void onResume() {
        if (mWaveAnimator != null && mWaveAnimator.isRunning()
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWaveAnimator.resume();
        }
    }


    public void onStop() {
        if (mWaveAnimator != null && mWaveAnimator.isRunning()) {
            mWaveAnimator.cancel();
            mWaveAnimator = null;
        }
    }

}
