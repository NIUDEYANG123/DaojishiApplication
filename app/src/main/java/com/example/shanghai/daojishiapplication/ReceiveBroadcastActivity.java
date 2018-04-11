package com.example.shanghai.daojishiapplication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 启动B应用，B应用再给当前应用发广播
 */
public class ReceiveBroadcastActivity extends AppCompatActivity {
    BroadcastReceiver broadcastReceiver;
    IntentFilter intentFilter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_broadcast);
        intentFilter=new IntentFilter();
        intentFilter.addAction("test_send_cast");
        broadcastReceiver=new MyReceiver();
        registerReceiver(broadcastReceiver,intentFilter);

        View launch = findViewById(R.id.lanuch);//启动B应用
        launch.setOnClickListener(v->{
            isApkInstalled(ReceiveBroadcastActivity.this,"com.test.zackratos.rvitemam");
           // System.out.println(isWeixinAvilible(ReceiveBroadcastActivity.this)+"");
            Intent intent = new Intent();
            intent.setData(Uri.parse("csd://pull.csd.demo/cyn?type=110"));
            intent.putExtra("", "");//这里Intent当然也可传递参数,但是一般情况下都会放到上面的URL中进行传递
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }

      static class MyReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if(intent!=null){
                if(!TextUtils.isEmpty(intent.getAction())){
                    System.out.println("!TextUtils.isEmpty(intent.getAction()");
                }
                if(!TextUtils.isEmpty(intent.getStringExtra("info"))){
                   System.out.println(intent.getStringExtra("info"));
                }
            }
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                System.out.println(pn);
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
    public boolean isApkInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            System.out.println(info.toString());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
