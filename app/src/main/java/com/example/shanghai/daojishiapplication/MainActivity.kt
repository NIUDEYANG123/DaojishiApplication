package com.example.shanghai.daojishiapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.shanghai.daojishiapplication.anonation.UseCase
import com.example.shanghai.daojishiapplication.impl.RealSubject
import com.example.shanghai.daojishiapplication.interce.Subject
import com.example.shanghai.daojishiapplication.proxy.DynProxyFactory
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Proxy

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv.setOnClickListener{v->startActivity(Intent(this,Sample5Activity::class.java))}
        tv_invocation.setOnClickListener{
            v->
            run {
                val instance = DynProxyFactory.getInstance()
                instance.showAnnotation("任务已")
               instance.showInt(100)

            }
        }

        tv_annotation.setOnClickListener{
            v->
             run {
              RealSubject().javaClass.methods.forEach{
                  Log.e("tag_name",it.name)
                  val annotation = it.getAnnotation(UseCase::class.java)
                  if(annotation!=null){
                  Log.e("tag_annotation",annotation.description)}
               }
            }
        }
    }
}
