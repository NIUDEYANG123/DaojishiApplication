package com.example.shanghai.daojishiapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.shanghai.daojishiapplication.service.MyService;

public class MessageActivity extends AppCompatActivity {
    /* 与服务端交互的Messenger */
    Messenger mService = null;
    /**
     * Flag indicating whether we have called bind on the service.
     */
    boolean mBound;
    private View btn;
    private StringBuffer printMessage = new StringBuffer();
    private View send;
    private View unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        btn = findViewById(R.id.btn_bind);
        send = findViewById(R.id.btn_send);
        unbind = findViewById(R.id.btn_unbind);
        btn.setOnClickListener(v -> {
            System.out.println("onClick-->bindService");
            //当前Activity绑定服务端
            bindService(new Intent(MessageActivity.this, MyService.class), mConnection,
                    Context.BIND_AUTO_CREATE);
        });

        send.setOnClickListener(v -> {
            sayHello();
        });


        unbind.setOnClickListener(v -> {
            if (mBound) {
                System.out.println("onClick-->unbindService");
                unbindService(mConnection);
                mBound = false;
            }
        });
    }

    /* 实现与服务端链接的对象ServiceConnection */
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            /* 通过服务端传递的IBinder对象,创建相应的Messenger
             * 通过该Messenger对象与服务端进行交互 */
            mService = new Messenger(service);
            mBound = true;
        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            mService = null;
            mBound = false;
        }
    };

    public void sayHello() {
        if (!mBound) return;
        System.out.println("sayHello");
        // 创建与服务交互的消息实体Message
        Message msg = Message.obtain(null, MyService.MSG_SAY_HELLO, 0, 0);
        msg.replyTo=mRecevierReplyMsg;
        try {
            //发送消息
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private static class ReceiverReplyMsgHandler extends Handler {
        private static final String TAG = "zj";

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //接收服务端回复
                case MyService.MSG_SAY_HELLO:
                    System.out.println("receiver message from service:" + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }


    }


    private Messenger mRecevierReplyMsg = new Messenger(new ReceiverReplyMsgHandler());
}
