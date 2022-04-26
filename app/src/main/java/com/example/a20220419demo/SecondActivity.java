package com.example.a20220419demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends Activity {
    private int mProgressStatus = 0; //完成进度
    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        GridView gridView = findViewById(R.id.grid);
        ProgressBar circleP = findViewById(R.id.progressBar);

        List<Integer> imgId = new ArrayList<Integer>();

        imgId.add(R.drawable.img1);
        imgId.add(R.drawable.img2);
        imgId.add(R.drawable.img3);
        imgId.add(R.drawable.img4);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 0x111) {
                    circleP.setProgress(mProgressStatus);
                } else {
                    circleP.setVisibility(View.GONE);
                    gridView.setVisibility(View.VISIBLE);
                }
            }
        };

        BaseAdapter adapter1 = new BaseAdapter() {
            @Override
            public int getCount() {
                return imgId.size();
            }

            @Override
            public Object getItem(int i) {
                return i;
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int position, View view, ViewGroup viewGroup) {
                ImageView imageView;
                if (view == null) {
                    imageView = new ImageView(SecondActivity.this);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    imageView.setPadding(5, 0, 5, 0);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(250, 250));
                } else {
                    imageView = (ImageView) view;
                }
                if (imgId.get(position) != null) {
                    imageView.setImageResource(imgId.get(position));
                }


//                Message m = new Message();
//
//                if (position == (imgId.size()-1)){
//                    m.what = 0x110;
//                    mHandler.sendMessage(m);
//                }else {
//                    m.what = 0x111;
//                    mHandler.sendMessage(m);
//                }
//                try{
//                    Thread.sleep(500);
//                }catch (InterruptedException e){
//                    e.printStackTrace();
//                }

                return imageView;
            }
        };

        ListView listView = findViewById(R.id.list);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.names, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    circleP.setVisibility(View.VISIBLE);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                mProgressStatus = work();
                                Message m = new Message();
                                if (mProgressStatus < 100) {
                                    m.what = 0x111;
                                    mHandler.sendMessage(m);
                                } else {
                                    m.what = 0x110;
                                    mHandler.sendMessage(m);
                                    break;
                                }
                            }
                        }

                        private int work() {
                            mProgressStatus += Math.random() * 10;
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return mProgressStatus;
                        }
                    }).start();

                    gridView.setAdapter(adapter1);

                    gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            AlertDialog alert = new AlertDialog.Builder(SecondActivity.this).create();
                            alert.setTitle("系统提示:");
                            alert.setMessage("是否删除图片？");
                            alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int k) {
                                    imgId.remove(i);
                                    gridView.setAdapter(adapter1);
                                }
                            });
                            alert.show();


                            return true;
                        }
                    });

                }else if (i == 1){
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putCharSequence("message","通过bundle传过来的数据");
                    intent.putExtras(bundle);

                    startActivity(intent);
                }else if (i == 2){
                    Intent intent = new Intent(SecondActivity.this,fourthActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Serializable","Serializable类型数据");
                    intent.putExtra("int",1);
                    intent.putExtra("byte",'c');
                    intent.putExtras(bundle);


                    startActivity(intent);
                }


                int a = i + 1;
                Toast.makeText(SecondActivity.this, "您选择了第" + a + "项", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
