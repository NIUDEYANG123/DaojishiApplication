package com.example.shanghai.daojishiapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class DispatchActivity extends AppCompatActivity {

    private ViewGroup ll_group;
    private View btn_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
        btn_click = findViewById(R.id.btn_click);
        ll_group = findViewById(R.id.ll_group);
        //btn_click.getParent().requestDisallowInterceptTouchEvent(true);

         btn_click.setOnClickListener(v->System.out.println("点击button"));
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


}
