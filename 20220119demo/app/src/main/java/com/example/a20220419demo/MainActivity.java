package com.example.a20220419demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    }
}