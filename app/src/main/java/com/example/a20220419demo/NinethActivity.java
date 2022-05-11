package com.example.a20220419demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NinethActivity extends Activity {
    int x = 0;
    private ListenerReceiver listenerReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nineth);

//        Button btn = findViewById(R.id.getMemory);
//        //获得手机内部存储控件的状态
//        File dataFileDir = Environment.getDataDirectory();
//        String dataMemory = getMemoryInfo(dataFileDir);
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(NinethActivity.this,dataMemory,Toast.LENGTH_SHORT).show();
//            }
//        });

        listenerReceiver = new ListenerReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.test.broadcast.MEMORY_CHANGE");
        intentFilter.setPriority(100);
        registerReceiver(listenerReceiver,intentFilter);

        Intent intent = new Intent();
        intent.setAction("com.test.broadcast.MEMORY_CHANGE");
        sendOrderedBroadcast(intent, null);
    }

    class ListenerReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
            activityManager.getMemoryInfo(memoryInfo);

            TextView textView = findViewById(R.id.memorymessage);
            textView.setText("开机"+x+"s后，系统剩余内存为：" + String.valueOf(memoryInfo.availMem / (1024 * 1024)) + "MB");
            try {
                Thread.sleep(1000);
                x++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (x < 600) {
                intent.setAction("com.test.broadcast.MEMORY_CHANGE");
                context.sendOrderedBroadcast(intent, null);
            } else {
                abortBroadcast();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listenerReceiver != null){
            unregisterReceiver(listenerReceiver);
        }
    }

    /**
     * 根据路径获取内存状态
     * @param path
     * @return
     */
    @SuppressWarnings("deprecation")
    private String getMemoryInfo(File path) {
        //获得一个磁盘状态对象
        StatFs stat = new StatFs(path.getPath());
        //获得一个扇区的大小
        long blockSize = stat.getBlockSize();
        //获得扇区的总数
        long totalBlocks = stat.getBlockCount();
        //获得可用的扇区数量
        long availableBlocks = stat.getAvailableBlocks();
        //总空间
        String totalMemory = Formatter.formatFileSize(this, totalBlocks * blockSize);
        //可用空间
        String availableMemory = Formatter.formatFileSize(this, availableBlocks * blockSize);
        return "总空间：" + totalMemory + "\n可用空间：" + availableMemory;
    }
}
