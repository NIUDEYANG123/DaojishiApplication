package com.example.shanghai.daojishiapplication.impl;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.shanghai.daojishiapplication.anonation.UseCase;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by shanghai on 2018/4/8.
 * 动态代理类对应的调用处理程序类
 */

public class InvocationHandlerImpl implements InvocationHandler {
    private Object object;

    public InvocationHandlerImpl(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long stime=System.currentTimeMillis();
        for(Method m:object.getClass().getDeclaredMethods()){
            if(m.getName().equals(method.getName())){
                UseCase annotation = m.getAnnotation(UseCase.class);
                if(null!=annotation){
                    System.out.println(annotation.description());//拿到方法上的注解
                }
            }
        }
        Object invoke = method.invoke(object, args);
        long ftime = System.currentTimeMillis();
        System.out.println(proxy.getClass().getSimpleName()+"执行任务耗时"+(ftime - stime)+"毫秒"+"返回值="+invoke);
        return invoke;
    }
}
