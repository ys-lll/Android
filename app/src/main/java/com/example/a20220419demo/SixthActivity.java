package com.example.a20220419demo;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SixthActivity extends Activity {

    private MyDataBaseHelper dbHelper;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sixth);
        dbHelper = new MyDataBaseHelper(this,"FifthHomeWork",null,1);
        dbHelper.getWritableDatabase();

        EditText billText = findViewById(R.id.billtext);
        EditText priceText = findViewById(R.id.pricetext);
        ListView listView = findViewById(R.id.priceList);

        Button btn = findViewById(R.id.add);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.clear();
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                String strbill = billText.getText().toString();
                String strprice = priceText.getText().toString();

                if (strbill != ""){
                    values.put("billtext",strbill);
                    values.put("price",strprice);
                    db.insert("Bill",null,values);

                    Cursor cursor = db.query("Bill",null,null,null,null,null,null);
                    if (cursor.moveToFirst()){
                        do {
                            int i = cursor.getColumnIndex("billtext");
                            int j = cursor.getColumnIndex("price");
                            String str1 = cursor.getString(i);
                            String str2 = cursor.getString(j);

                            //这里是因为之前在数据库中存了一些空白数据，影响观看，所以加个判断
                            if (!str1.equals("") && str1 != null){
                                list.add("记账信息为:"+str1+"   "+"金额为:"+str2);
                            }
                        }while (cursor.moveToNext());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SixthActivity.this,android.R.layout.simple_list_item_1,list);

                    listView.setAdapter(adapter);

                    cursor.close();
                }
            }
        });

    }
}
