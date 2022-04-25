package com.example.a20220419demo;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SeekBar;

import androidx.annotation.Nullable;

public class LeftFragment extends ListFragment {
    int checkPosition = 0;//当前选择的索引位置
    public static final String[] light = {"亮度设置"};

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_checked,light));
        if (savedInstanceState != null){
            checkPosition = savedInstanceState.getInt("curChoice",0);
        }
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);//设置为单选模式
        showLight(checkPosition);//展示右侧亮度调节界面
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice",checkPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showLight(position);

    }

    void showLight(int index){
        checkPosition = index;//更新保存当前索引位置为当前选中值
        getListView().setItemChecked(index,true);
        RightFragment rightFragment = (RightFragment) getFragmentManager().findFragmentById(R.id.rightfragment);

//        if (rightFragment == null || rightFragment.getShowIndex() != index){
//        RightFragment rightFragment = RightFragment.newInstance(index);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.rightfragment,rightFragment);
//        ft.add(R.id.rightLayout,rightFragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);//设置转换效果
            ft.commit();
//        }

    }

}
