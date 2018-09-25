package com.renj.volleytest.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.renj.volleylibrary.imageloader.RVImageLoadUtil;
import com.renj.volleytest.R;

public class ImageDataActivity extends AppCompatActivity {
    private Button btGet;
    private ImageView ivContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_data);

        btGet = findViewById(R.id.bt_get);
        ivContent = findViewById(R.id.iv_content);

        btGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void getData() {
        String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536569318955&di=eee6ecb9abcae86661b9ae604d529273&imgtype=0&src=http%3A%2F%2Fc4-q.mafengwo.net%2Fs10%2FM00%2FE8%2FF3%2FwKgBZ1h8w6eAeR2LAAEqPxeiTh499.jpeg%3FimageView2%2F2%2Fw%2F600%2Fh%2F600%2Fq%2F90";
        RVImageLoadUtil.newInstance().loadImageByRequest(url, ivContent);
    }
}
