package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void homeButtonClick(View view) {
        EditText msg = findViewById(R.id.editTextPhone);
        String s = msg.getText().toString();
        Log.d("s========>", s);

        // 显式启动，写法一：class跳转
//        Intent intent = new Intent(this, MainActivity2.class);
//        this.startActivity(intent);

        //显式启动，写法二：包名.类名
//        Intent intent = new Intent();
//        intent.setClassName(this,"com.example.myapplication.MainActivity2");
//        this.startActivity(intent);

        // 显式启动，写法三：ComponentName
//        Intent intent = new Intent();
//        ComponentName cname = new ComponentName(this, MainActivity2.class);
//        intent.setComponent(cname);
//        startActivity(intent);

        // 隐式启动
//        Intent intent = new Intent();
//        intent.setAction("action.nextActivity");
//        startActivity(intent);

        Intent intent = new Intent("action.nextActivity");
        startActivity(intent);
    }
}