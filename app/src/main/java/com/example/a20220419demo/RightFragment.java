package com.example.a20220419demo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class RightFragment extends Fragment {
    public static final String[] str = {"亮度设置", "其他设置中的各种信息", "用户设置中的各种信息"};

    //创建一个RightFragment实例，其中包括要传递的数据包
    public static RightFragment newInstance(int index) {
        RightFragment rightFragment = new RightFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index", index);
        rightFragment.setArguments(bundle);//将bundle对象作为Fragment的参数保存
        return rightFragment;
    }

    public int getShowIndex() {
        return getArguments().getInt("index", 0);//获取要显示的列表项索引
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (getShowIndex() == 0) {
            ScrollView scrollView = new ScrollView(getActivity());
            SeekBar seekBar = new SeekBar(getActivity());
            seekBar.setPadding(10,10,10,10);
            seekBar.setMax(100);
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    setAppScreenBrightness(i);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            scrollView.addView(seekBar);
            return scrollView;
        } else {
            ScrollView scrollView = new ScrollView(getActivity());
            TextView textView = new TextView(getActivity());
            textView.setPadding(10, 10, 10, 10);
            scrollView.addView(textView);
            textView.setText(str[getShowIndex()]);
            return scrollView;
        }
    }

    /**
     * 2.设置 APP界面屏幕亮度值方法
     **/
    private void setAppScreenBrightness(int birghtessValue) {
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.screenBrightness = birghtessValue / 255.0f;
        window.setAttributes(lp);
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }
}
