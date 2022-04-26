package com.example.a20220419demo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class fourthActivity extends Activity {
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        tv = findViewById(R.id.tv);

        byte b = 'a';
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        TextView inttext = findViewById(R.id.intText);
        TextView bytetext = findViewById(R.id.byteText);
        TextView Serializabletext = findViewById(R.id.SerializableText);
        Serializabletext.setText("传入的Serializable类型数据为"+bundle.getSerializable("Serializable").toString());
        int a = intent.getIntExtra("int",0);
        byte s = intent.getByteExtra("byte", (byte) 'a');

        inttext.setText("传入的int类型数据为"+a);
        bytetext.setText("传入的byte类型数据为"+s);

        initReceiver();

    }

    private void initReceiver() {

        IntentFilter timeFilter = new IntentFilter();

//        timeFilter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");

//        timeFilter.addAction("android.net.ethernet.STATE_CHANGE");

        timeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

//        timeFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");

//        timeFilter.addAction("android.net.wifi.STATE_CHANGE");


        registerReceiver(netReceiver, timeFilter);

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (netReceiver != null) {

            unregisterReceiver(netReceiver);

            netReceiver = null;

        }
    }

    BroadcastReceiver netReceiver = new BroadcastReceiver() {

        @Override

        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();

            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();



                if (networkInfo != null && networkInfo.isAvailable()) {

                    int type2 = networkInfo.getType();

                    String typeName = networkInfo.getTypeName();

                    tv.setText(type2 + "--" + typeName);

                    switch (type2) {

                        case 0://移动 网络 2G 3G 4G 都是一样的 实测 mix2s 联通卡

                            Log.d("Feeee", "有网络");
                            Toast.makeText(fourthActivity.this,"有网络",Toast.LENGTH_SHORT).show();

                            break;

                        case 1: //wifi网络

                            Log.d("Feeee", "wifi");
                            Toast.makeText(fourthActivity.this,"wifi",Toast.LENGTH_SHORT).show();

                            break;

                        case 9: //网线连接

                            Log.d("Feeee", "有有线网络");
                            Toast.makeText(fourthActivity.this,"有有线网络",Toast.LENGTH_SHORT).show();

                            break;

                    }

                } else {// 无网络

                    Log.d("Feeee", "无网络");
                    Toast.makeText(fourthActivity.this,"无网络",Toast.LENGTH_SHORT).show();

                }

            }

        }

    };


}
