package com.example.shanghai.daojishiapplication.custom;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.example.shanghai.daojishiapplication.R;

/**
 * 圆形进度条带渐变的，带小圆球的
 */
public class RoundProgressBarView extends View {

    private int roundColor;
    private int roundProgressColor;
    private float roundWidth;
    private float pointWidth;
    private int padding;
    private int digree = 0;
    private Interpolator mInterpolator = new DecelerateInterpolator();
    private Paint paint;
    private ValueAnimator valueAnimator;
    private Paint progressPaint;

    public RoundProgressBarView(Context context) {
        super(context);
        init(null, 0);
    }

    public RoundProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RoundProgressBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        paint = new Paint();
        progressPaint = new Paint();
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.RoundProgressBarView, defStyle, 0);
        roundWidth = a.getDimension(R.styleable.RoundProgressBarView_roundWidth, 20);
        pointWidth = a.getDimension(R.styleable.RoundProgressBarView_pointWidth, 10);
        padding = (int) a.getDimension(R.styleable.RoundProgressBarView_padding, 10);
        digree = a.getInt(R.styleable.RoundProgressBarView_digree, 0);
        roundColor = a.getColor(R.styleable.RoundProgressBarView_roundColor, Color.RED);
        roundProgressColor = a.getColor(R.styleable.RoundProgressBarView_roundProgressColor, Color.GREEN);
        a.recycle();
        valueAnimator = newValueAnimator();
    }

    /**
     * 创建值动画从0到360度
     */
    private ValueAnimator newValueAnimator() {
        ValueAnimator mWaveAnimator = new ValueAnimator();
        mWaveAnimator.setIntValues(0, 360);
        mWaveAnimator.setDuration(3000);
        mWaveAnimator.setRepeatCount(0);
        mWaveAnimator.setInterpolator(mInterpolator);
        mWaveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
//                (Float) animation.getAnimatedValue();
            }
        });
        mWaveAnimator.start();
        return mWaveAnimator;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth() / 2;
        int radius = (int) (centre - roundWidth / 2) - padding;
        paint.setColor(roundColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(roundWidth);
        paint.setAntiAlias(true);
        paint.setAlpha(180);
        canvas.drawCircle(centre, centre, radius, paint);
        /*if (digree > 360)
            digree = 0;
        else if (digree > 335)
            digree += 3;
        else if (digree > 305)
            digree += 2;
        else if (digree > 270)
            digree += 1;
        else if (digree > 225)
            digree += 3;
        else if (digree > 180)
            digree += 4;
        else if (digree > 135)
            digree += 7;
        else if (digree > 90)
            digree += 10;
        else if (digree > 45)
            digree += 10;
        else
            digree += 7;*/
        digree = (int) valueAnimator.getAnimatedValue();
        Log.e("RoundProgress", "digree=" + digree);
        int x1, y1;
        //x1 = (int) (centre + radius * Math.cos(digree * Math.PI / 180));
        // y1 = (int) (centre + radius * Math.sin(digree * Math.PI / 180));

        x1 = (int) (centre + radius * Math.cos(Math.toRadians(digree)));
        y1 = (int) (centre + radius * Math.sin(Math.toRadians(digree)));

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(roundProgressColor);
        paint.setAlpha(255);
        canvas.drawCircle(x1, y1, roundWidth, paint);

        progressPaint.setColor(Color.RED);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(roundWidth);
        progressPaint.setAntiAlias(true);
        progressPaint.setStrokeCap(Paint.Cap.ROUND);//圆弧末端是圆角
        SweepGradient mSweepGradient = new SweepGradient(centre, centre, new int[]{Color.BLUE, Color.GREEN, Color.YELLOW}, null);
        progressPaint.setShader(mSweepGradient);
        RectF rectF = new RectF(padding + 8, padding + 8, getWidth() - padding - 8, getHeight() - padding - 8);
        canvas.drawArc(rectF, 0, digree, false, progressPaint);
        if (digree < 360) {
            postInvalidateDelayed(50);
        }
    }
}

