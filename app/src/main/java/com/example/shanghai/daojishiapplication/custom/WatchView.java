package com.example.shanghai.daojishiapplication.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class WatchView extends View {
    private String TAG = WatchView.class.getSimpleName();
    private Paint mPaintCircle;//绘制圆圈的画笔;
    private float mCircleRadius;//绘制刻度盘的半径：
    private float mCircleCenterX;//绘制圆圈的中心坐标X;
    private float mCircleCenterY;//绘制圆圈的中心坐标Y;
    private float padding = 50;//中心圆距离控件的边距；

    private Paint mPaintDegree;//绘制刻度圆盘的刻度的画笔;
    private int mDegreeCount = 24;//刻度总数;

    private Paint mTimeHourPointPaint;//绘制小时画笔；
    private float mTimeHourLength = 0;//设置时针长度；
    private float mTimeMinuteLength;//设置分钟长度;

    private float mTimeDegreeHourLength = 60;//刻度盘小时刻度长度;
    private float mTimeDegreeMintueLength = 40;//刻度盘小时刻度长度;

    private Paint mTimeMintuePointPaint;//绘制分针画笔；

    private Mythread myThread;
    private boolean startOpenThreadFlag = false;
    private int angle = 0;

    public WatchView(Context context) {
        this(context, null);
        init(null, 0);
    }

    public WatchView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
        init(attrs, 0);
    }

    public WatchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }


    private void init(AttributeSet attrs, int defStyle) {
        //设置绘制圆的画笔颜色：
        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);//给Paint加上抗锯齿标志。然后将Paint对象作为参数传给canvas的绘制方法。
//      paintCircle.setAntiAlias(true);
        mPaintCircle.setStrokeWidth(10);
        mPaintCircle.setStyle(Paint.Style.STROKE);
        mPaintCircle.setColor(Color.WHITE);

        //设置绘制刻度画笔颜色；
        mPaintDegree = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintDegree.setColor(Color.WHITE);
        mPaintDegree.setStrokeWidth(3);

        //设置时针画笔;
        mTimeHourPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeHourPointPaint.setStrokeWidth(20);
        //设置分针画笔;
        mTimeMintuePointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimeMintuePointPaint.setStrokeWidth(10);

       /* if (myThread == null) {
            startOpenThreadFlag = true;
            myThread = new Mythread();
            myThread.start();

        }*/
       invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG,"ondraw");
        angle++;
        canvas.drawCircle(mCircleCenterX, mCircleCenterY, mCircleRadius, mPaintCircle);
        canvasDegree(canvas);
        canvasTimePoint(canvas);
        postInvalidateDelayed(10000);
    }

    /**
     * 绘制时针;
     *
     * @param canvas
     */
    private void canvasTimePoint(Canvas canvas) {
        canvas.save();//保存圆有的图层;
        canvas.translate(mCircleCenterX, mCircleCenterY);// 移动绘制画布圆心坐标;
        canvas.drawLine(0, 0, 120, 120, mTimeHourPointPaint);//时针；
//        angle
        float[] position = calculateMintuePosition(angle);

//        canvas.drawLine(0, 0, 120, 200, mTimeMintuePointPaint);//分针
        canvas.drawLine(0, 0, position[0], position[1], mTimeMintuePointPaint);//分针
        canvas.restore();

    }

    /**
     * 绘制刻度（表盘为例）：
     */
    private void canvasDegree(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < mDegreeCount; i++) {
            //区分整点与非整点
            if (i == 0 || i == 6 || i == 12 || i == 18) {
                mPaintDegree.setStrokeWidth(5);
                mPaintDegree.setTextSize(40);
                canvas.drawLine(mCircleCenterX, mCircleCenterY - mCircleRadius, mCircleCenterX, mCircleCenterY - mCircleRadius + mTimeDegreeHourLength, mPaintDegree);

                String dergree = String.valueOf(i);
                canvas.drawText(dergree,
                        mCircleCenterX - mPaintDegree.measureText(dergree) / 2,
                        mCircleCenterY - mCircleRadius + 95,
                        mPaintDegree);
            } else {
                mPaintDegree.setStrokeWidth(3);
                mPaintDegree.setTextSize(20);
                canvas.drawLine(mCircleCenterX, mCircleCenterY - mCircleRadius, mCircleCenterX, mCircleCenterY - mCircleRadius + mTimeDegreeMintueLength, mPaintDegree);
                String dergree = String.valueOf(i);
                canvas.drawText(dergree,
                        mCircleCenterX - mPaintDegree.measureText(dergree) / 2,
                        mCircleCenterY - mCircleRadius + 60,
                        mPaintDegree);
            }
            canvas.rotate(360 / mDegreeCount, mCircleCenterX, mCircleCenterY);//每次画布围绕圆心旋转的的度数。

        }


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e(TAG,"onmeasure");
       /* mCircleCenterX = getWidth() / 2;
        mCircleCenterY = getHeight() / 2;
        if (mCircleCenterX > mCircleCenterY) {
            mCircleRadius = mCircleCenterY - padding;
        } else {
            mCircleRadius = mCircleCenterX - padding;
        }
        mTimeHourLength = mCircleRadius - 2 * mTimeDegreeHourLength;
        mTimeMinuteLength = mCircleRadius - 2 * mTimeDegreeMintueLength;*/

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCircleCenterX = w / 2;
        mCircleCenterY = h / 2;
        if (mCircleCenterX > mCircleCenterY) {
            mCircleRadius = mCircleCenterY - padding;
        } else {
            mCircleRadius = mCircleCenterX - padding;
        }
        Log.e(TAG,"onSize"+"w="+w+"h="+h+"R="+mCircleRadius);
        mTimeHourLength = mCircleRadius - 2 * mTimeDegreeHourLength;
        mTimeMinuteLength = mCircleRadius - 2 * mTimeDegreeMintueLength;

    }

    /**
     * 计算分针的坐标。
     *
     * @param angle
     * @return
     */
    private float[] calculateMintuePosition(float angle) {
        Log.e(TAG,"angle="+angle);
        float x = (float) (mTimeMinuteLength * Math.cos(Math.toRadians(angle)));
        float y = (float) (mTimeMinuteLength * Math.sin(Math.toRadians(angle)));

        return new float[]{x, y};
    }

    /**
     * 计算时针的坐标。
     *
     * @param angle
     * @return
     */
    private float[] calculateHourPosition(float angle) {
        float x = (float) (mTimeHourLength * Math.cos(Math.toRadians(angle)));
        float y = (float) (mTimeHourLength * Math.sin(Math.toRadians(angle)));
        return new float[]{x, y};
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //此时的eventX与eventY为控件的右上顶点坐标值;
        float eventX = event.getX();
        float eventY = event.getY();
        //要把触摸点的坐标原点移动到控件的中心，
        float centerEventX = event.getX() - mCircleCenterX;
        float centerEventY = event.getY() - mCircleCenterY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "eventX==" + eventX);
                Log.d(TAG, "eventY==" + eventY);
                Log.d(TAG, "centerEventX===================" + centerEventX);
                Log.d(TAG, "centerEventY===================" + centerEventY);

                break;

            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    public class Mythread extends Thread {
        @Override
        public void run() {
            while (startOpenThreadFlag) {
                angle++;
                if (angle == 360) {
                    angle = 0;
                }
                postInvalidate();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
