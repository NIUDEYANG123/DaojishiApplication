package com.example.shanghai.daojishiapplication.proxy;

import com.example.shanghai.daojishiapplication.impl.InvocationHandlerImpl;
import com.example.shanghai.daojishiapplication.impl.RealSubject;
import com.example.shanghai.daojishiapplication.interce.Subject;
import com.example.shanghai.daojishiapplication.interce.Suject2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by shanghai on 2018/4/8.
 */

public class DynProxyFactory {

    public static Subject getInstance(int i) {
        Subject s = new RealSubject();
        InvocationHandler invocationHandler = new InvocationHandlerImpl(s);
        Subject proxy = null;
        proxy = (Subject) Proxy.newProxyInstance(s.getClass().getClassLoader(), s.getClass().getInterfaces(), invocationHandler);
        return proxy;
    }
}
