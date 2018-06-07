package com.example.shanghai.daojishiapplication;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.example.shanghai.daojishiapplication.custom.CustomVolumControlBar;
import com.example.shanghai.daojishiapplication.custom.RainDrop;
import com.example.shanghai.daojishiapplication.custom.SaleProgressView;
import com.example.shanghai.daojishiapplication.custom.WaveCircleView;
import com.example.shanghai.daojishiapplication.custom.WaveLayout;

/**
 * 通过大量自定义控件的练习熟悉view绘制
 */
public class CustomViewActivity extends AppCompatActivity {

    private WaveLayout wl_content;
    private CustomVolumControlBar customVolumControlBar;
    private SaleProgressView saleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        //RainDrop rainDrop = (RainDrop)findViewById(R.id.rain);//水波纹简单的一种
        //rainDrop.setXAndY(720,1280);
        //水波纹健壮的一种
        // wl_content = (WaveLayout) findViewById(R.id.wl_content);
        // showWaveLayout();
        //时钟刻度WatchView
        //带刻度的进度条一共320度的圆弧ShowPercentView
        //小球随圆环转动
        //音量控制
        customVolumControlBar = findViewById(R.id.volum_bar);
        saleProgressView = findViewById(R.id.spv);
        showSaleProgress();


    }

    /**
     * 展示淘宝进度条
     */
    private void showSaleProgress() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(1, 100);
        valueAnimator.setDuration(10000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                saleProgressView.setTotalAndCurrentCount(100, (Integer) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private void showWaveLayout() {
        wl_content.postDelayed(waveRunable, 1000);
    }

    private Runnable waveRunable = new Runnable() {
        @Override
        public void run() {
            WaveCircleView waveCircleView = new WaveCircleView(CustomViewActivity.this);
            wl_content.addCircleView(waveCircleView);
            if (wl_content.getChildCount() < 10) {
                wl_content.postDelayed(this, 1000);
            }
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                customVolumControlBar.down();//控制音量
                return true;
            case KeyEvent.KEYCODE_VOLUME_UP:
                customVolumControlBar.up();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
