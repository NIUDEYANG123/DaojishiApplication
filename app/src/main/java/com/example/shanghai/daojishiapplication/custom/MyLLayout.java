package com.example.shanghai.daojishiapplication.custom;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by shanghai on 2018/4/13.
 */

public class MyLLayout extends LinearLayout {
    public MyLLayout(Context context) {
        super(context);
    }

    public MyLLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MyLLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        System.out.println("disallowIntercept"+disallowIntercept);
        super.requestDisallowInterceptTouchEvent(disallowIntercept);

    }

    /**
     * 可以实现拦截
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("onInterceptTouchEvent"+ev.getAction());
      /* if(ev.getAction()==MotionEvent.ACTION_DOWN){
            System.out.println(" onInterceptTouchEvent(MotionEvent ev)+拦截了");
            return true;
        }*/
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 可以实现拦截
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("dispatchTouchEvent"+ev.getAction());
        /*if(ev.getAction()==MotionEvent.ACTION_DOWN){
            System.out.println(" dispatchTouchEvent(MotionEvent ev)+拦截了");
            return true;
        }*/
        return super.dispatchTouchEvent(ev);
    }
}
