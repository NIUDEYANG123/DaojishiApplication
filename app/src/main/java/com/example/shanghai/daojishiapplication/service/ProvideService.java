package com.example.shanghai.daojishiapplication.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.example.shanghai.daojishiapplication.IMyAidlInterface;

/**
 * 用于aidl的service
 * Created by shanghai on 2018/4/10.
 */

public class ProvideService extends Service{
    private static final String TAG="ProvideService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println(TAG+"onBind");
        return new MyBinder();
    }

    private class MyBinder extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public boolean forwardPayMoney(float money) throws RemoteException {
            System.out.println(TAG+"public boolean forwardPayMoney(float money) throws RemoteException");
            return false;
        }
    }
}
