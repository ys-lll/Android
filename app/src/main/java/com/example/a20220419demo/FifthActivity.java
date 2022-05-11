package com.example.a20220419demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class FifthActivity extends Activity {

    //ToolBar的居中标题栏
    private TextView tv_toolbar_title;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fifth);


        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);
        //设置toolbar的居中标题栏文本
        tv_toolbar_title.setText("设置");

    }
}
