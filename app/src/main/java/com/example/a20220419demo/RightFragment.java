package com.example.a20220419demo;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

public class RightFragment extends Fragment {

    //创建一个RightFragment实例，其中包括要传递的数据包
    public static RightFragment newInstance(int index){
        RightFragment rightFragment = new RightFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("index",index);
        rightFragment.setArguments(bundle);//将bundle对象作为Fragment的参数保存
        return rightFragment;
    }

    public int getShowIndex(){
        return getArguments().getInt("index",0);//获取要显示的列表项索引
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_rightfrag,container,true);

        return view;
    }

    /**
     * 2.设置 APP界面屏幕亮度值方法
     * **/
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
