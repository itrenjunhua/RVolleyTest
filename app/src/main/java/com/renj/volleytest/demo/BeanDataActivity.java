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

import com.renj.volleylibrary.request.IRequest;
import com.renj.volleylibrary.request.RBeanRequest;
import com.renj.volleylibrary.request.ResultCallBack;
import com.renj.volleytest.R;
import com.renj.volleytest.bean.WeatherBean;

import java.util.HashMap;
import java.util.Map;

public class BeanDataActivity extends AppCompatActivity {
    private EditText etCityName;
    private Button btGet;
    private TextView tvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bean_data);

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
            Toast.makeText(BeanDataActivity.this, "请输入城市名", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> params = new HashMap<>();
        params.put("city", cityName);
        RBeanRequest.create()
                .method(IRequest.Method.GET)
                .url("https://www.apiopen.top/weatherApi")
                .params(params)
                .clazz(WeatherBean.class)
                .tag("GetBeanData")
                .build()
                .execute()
                .onResult(new ResultCallBack.ResultListener<WeatherBean>() {
                    @Override
                    public void onSucceed(WeatherBean result) {
                        tvContent.setText("onSucceed => " + result.msg);
                        Log.i("StringDataActivity", "onSucceed => " + result.toJson());
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
