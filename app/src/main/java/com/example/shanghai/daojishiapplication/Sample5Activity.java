package com.example.shanghai.daojishiapplication;


import com.example.shanghai.daojishiapplication.adapter.Sample5Adapter;

import org.jetbrains.annotations.NotNull;

/**
 * Created by shanghai on 2018/4/3.
 */

public class Sample5Activity extends SampleActivity<Sample5Adapter> {


    @NotNull
    @Override
    public Sample5Adapter adapter() {
       return new Sample5Adapter(this);
    }
    

}
