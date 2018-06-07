package com.example.shanghai.daojishiapplication.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.shanghai.daojishiapplication.R;


@SuppressLint("AppCompatCustomView")
public class RainDrop extends TextView {

    Paint paint = null;
    // 坐标
    float x = 370f;
    float y = 370f;


    int defaultRadius = 265;
    int defaultRadius2 = 265;
    int radius = 265;
    int radius2 = 265;
    int alpha = 255;
    int defaultWidth = 40;
    int defaultHeigth = 20;
    int width = 40;
    int width2 = 20;

    float increate = 0.5f;

    Bitmap bitmap;

    public RainDrop(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        new Thread(new RainDropThread()).start();
    }

    public RainDrop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        new Thread(new RainDropThread()).start();
    }

    public RainDrop(Context context) {
        super(context);
        init();
        new Thread(new RainDropThread()).start();
    }


    public void setXAndY(int screenWidth, int screenHeight) {
        Log.e("tSize", "screenWidth = " + screenWidth);
        if (screenWidth == 1440) {
            x = 370;
            y = 370;
            radius = 265;
            defaultRadius = 265;
            radius2 = 265;
            defaultRadius2 = 265;
            defaultWidth = 40;
            defaultHeigth = 20;

        } else if (screenWidth == 1080) {
            x = 370 * 3 / 4;
            y = 370 * 3 / 4;
            radius = 185;
            radius2 = 185;
            defaultRadius = 185;
            defaultRadius2 = 185;
            defaultWidth = 40;
            defaultHeigth = 20;

        } else if (screenWidth == 720) {
            x = 370 * 2 / 4;
            y = 370 * 2 / 4;
            radius = 137;
            radius2 = 137;
            defaultRadius = 137;
            defaultRadius2 = 137;
            defaultWidth = 15;
            defaultHeigth = 15;

            increate = 0.4f;

        } else if (screenWidth == 480 && screenHeight == 800) {
            x = 370 * 1 / 3 + 16;
            y = 370 * 1 / 3 + 16;
            radius = 97;
            radius2 = 97;
            defaultRadius = 97;
            defaultRadius2 = 97;

            width = 20;
            width2 = 15;
            defaultWidth = 20;
            defaultHeigth = 15;

            increate = 0.33f;
        }
        postInvalidate();
    }

    /**
     * 初始化
     */
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Style.STROKE);
        this.setLongClickable(true);
        setBackgroundResource(R.mipmap.repayment_btn_bg1);
        BitmapDrawable bd = (BitmapDrawable) getResources().getDrawable(R.mipmap.repayment_btn_bg1);
        this.bitmap = bd.getBitmap();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.WHITE);
        paint.setColor(Color.argb(alpha, 240, 200, 180));
        paint.setStrokeWidth(width);
        canvas.drawCircle(x, y, radius, paint);

        paint.setStrokeWidth(width2);
        paint.setColor(Color.argb(alpha / 2, 240, 200, 180));
        canvas.drawCircle(x, y, radius2, paint);

        paint.setColor(Color.argb(255, 255, 255, 255));
        canvas.drawBitmap(bitmap, 0, 0, paint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    /**
     * 绘制线程
     *
     * @author KOP-liven
     */

    public class RainDropThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                // 不断改变半径和透明度
                radius += increate;
                radius2 += increate * 2;
                width += increate * 2;
                width2 += increate * 4;
                alpha -= 5;

                if (alpha < 0) {
                    alpha = 255;
                    width = defaultWidth;
                    width2 = defaultHeigth;
                    radius = defaultRadius;
                    radius2 = defaultRadius2;
                }
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 非UI线程要改变UI需要使用postInvalidate();方法
                postInvalidate();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //removeCallbacks()当前activity销毁

    }
}
