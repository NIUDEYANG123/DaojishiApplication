package com.example.shanghai.daojishiapplication.impl;

import com.example.shanghai.daojishiapplication.anonation.UseCase;
import com.example.shanghai.daojishiapplication.interce.Subject;
import com.example.shanghai.daojishiapplication.interce.Suject2;

/**
 * Created by shanghai on 2018/4/8.
 */

public class RealSubject implements Subject{
    @Override
    @UseCase(id=1,description = "我是showAnnotation 正在执行任务：")
    public void showAnnotation(String task) {
        //System.out.println("正在执行任务：" + task);
        try {
            Thread.sleep(666);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    @UseCase(id=2,description = "我是showInt 正在执行任务：")
    public int showInt(int i) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return i;
    }
}
