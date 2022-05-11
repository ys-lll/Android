package com.example.a20220419demo.BroadcastReceiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.a20220419demo.MainActivity;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//添加标记

        Toast.makeText(context,"手机重启了!!!",Toast.LENGTH_SHORT).show();

//         getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("ServiceCast") ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //网络状态

        if (networkInfo != null && networkInfo.isAvailable()){
            int netType = networkInfo.getType();
            //假设预置的网络状态为有网络
            if (netType == 0){
                Toast.makeText(context,"此时有数据连接，与预设状态相同",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"此时不是数据连接，与预设状态不相同",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context,"此时没有网络，与预设状态不相同",Toast.LENGTH_SHORT).show();
        }

        context.startActivity(intent1);
    }
}
