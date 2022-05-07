package com.example.a20220419demo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText1 = (EditText) findViewById(R.id.name);
                EditText editText2 = (EditText) findViewById(R.id.password);
                String name = editText1.getText().toString();
                String pwd = editText2.getText().toString();
                //账号为ys  密码为123456
                if (name.equals("ys") && pwd.equals("123456")) {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "账号密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
//        //网络状态
//        int netType = networkInfo.getType();
//        //假设预置的网络状态为有网络
//        if (netType == 0){
//            Toast.makeText(MainActivity.this,"此时有数据连接，与预设状态相同",Toast.LENGTH_SHORT).show();
//        }else{
//            Toast.makeText(MainActivity.this,"此时不是数据连接，与预设状态不相同",Toast.LENGTH_SHORT).show();
//        }

        final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        //剩余内存
        long freeMemory = memoryInfo.availMem;
        //内存的总量
        long totalMemory = memoryInfo.totalMem;
        String memory = "开机完成时的剩余内存为" +  String.valueOf(memoryInfo.availMem / (1024 * 1024)) + "MB";

        Button button1 = findViewById(R.id.bbbb);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,memory,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.putExtra("memory",memory);
                intent.setClass(MainActivity.this,NinethActivity.class);
                startActivity(intent);
            }
        });

    }
}