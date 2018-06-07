package com.example.shanghai.daojishiapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tamic.rx.fastdown.client.DLClientFactory;
import com.tamic.rx.fastdown.client.Type;
import com.tamic.rx.fastdown.core.Download;
import com.tamic.rx.fastdown.core.Priority;

public class DownLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        new Download.Builder()
                .url("this is url")//下载url
                .priority(Priority.HIGH)
                .savepath("保存路径")
                .isImplicit(false)//是否显示UI
                .channel(3000)//渠道可选
                .client(DLClientFactory.createClient(Type.NORMAL, this))//下载器
                //.setCallback(new DLCallback())//下载回调
                .build(this)
                .start();
    }
}
