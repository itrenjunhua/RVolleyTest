package com.renj.volleytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.renj.volleytest.demo.BeanDataActivity;
import com.renj.volleytest.demo.BytesDataActivity;
import com.renj.volleytest.demo.FormDataActivity;
import com.renj.volleytest.demo.ImageDataActivity;
import com.renj.volleytest.demo.StringDataActivity;

public class MainActivity extends AppCompatActivity {

    private Button btString, btBean, btBytes, btForm, btImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btString = findViewById(R.id.bt_string);
        btBean = findViewById(R.id.bt_bean);
        btBytes = findViewById(R.id.bt_byte);
        btForm = findViewById(R.id.bt_form);
        btImg = findViewById(R.id.bt_img);

        // 获取 String 类型数据
        btString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StringDataActivity.class);
                startActivity(intent);
            }
        });

        // 获取 JavaBean 类型数据
        btBean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BeanDataActivity.class);
                startActivity(intent);
            }
        });

        // 获取 Byte[] 类型数据
        btBytes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BytesDataActivity.class);
                startActivity(intent);
            }
        });

        // 上传表单数据
        btForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FormDataActivity.class);
                startActivity(intent);
            }
        });

        // 加载图片
        btImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImageDataActivity.class);
                startActivity(intent);
            }
        });
    }
}
