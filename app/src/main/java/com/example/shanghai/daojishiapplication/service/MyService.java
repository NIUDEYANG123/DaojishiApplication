package com.example.shanghai.daojishiapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.app.INotificationSideChannel;
import android.util.Log;

/**
 * 与客户端跨进程通信
 */
public class MyService extends Service {
    /** Command to the service to display a message */
    public static final int MSG_SAY_HELLO = 1;
    private static final String TAG ="wzj" ;

    /* 用于接收从客户端传递过来的数据 */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    System.out.println("thanks,Service had receiver message from client!");
                    Messenger client=msg.replyTo;
                    Message replyMsg=Message.obtain(null,MyService.MSG_SAY_HELLO);
                    Bundle bundle=new Bundle();
                    bundle.putString("reply","ok~,I had receiver message from you! ");
                    replyMsg.setData(bundle);
                    try {
                        client.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    /* 创建Messenger并传入Handler实例对象 */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    /* 当绑定Service时,该方法被调用,将通过mMessenger返回一个实现IBinder接口的实例对象 */
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("Service is invoke onBind");
        return mMessenger.getBinder();
    }


}
