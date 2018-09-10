package com.renj.volleytest.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.renj.volleylibrary.ResultListenerAdapter;
import com.renj.volleylibrary.VHttpUtil;
import com.renj.volleytest.R;

import java.util.HashMap;
import java.util.Map;

public class StringDataActivity extends AppCompatActivity {
    private EditText etCityName;
    private Button btGet;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_data);

        etCityName = findViewById(R.id.et_city_name);
        btGet = findViewById(R.id.bt_get);
        tvContent = findViewById(R.id.tv_content);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        String cityName = etCityName.getText().toString().trim();
        if (TextUtils.isEmpty(cityName)) {
            Toast.makeText(StringDataActivity.this, "请输入城市名", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("city", cityName);
        // VHttpUtil.newInstance().getStringData("https://www.apiopen.top/weatherApi", params, new ResultListenerAdapter<String>() {
        VHttpUtil.newInstance().getStringData("https://www.apiopen.top/weatherApi?city=" + cityName, new ResultListenerAdapter<String>() {
            @Override
            public void onSucceed(String result) {
                tvContent.setText("onSucceed => " + result);
                Log.i("StringDataActivity", "onSucceed => " + result);
            }

            @Override
            public void onNetWork() {
                Log.i("StringDataActivity", "onNetWork");
            }

            @Override
            public void onError(Throwable e) {
                tvContent.setText("onError => " + e);
                Log.i("StringDataActivity", "onError => " + e);
            }
        });
    }
}
