package com.example.a20220419demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

public class RightFragActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rightfrag);


    }

    /**
     * 2.设置 APP界面屏幕亮度值方法
     * **/
    private void setAppScreenBrightness(int birghtessValue) {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = birghtessValue / 255.0f;
        window.setAttributes(lp);
    }
}
