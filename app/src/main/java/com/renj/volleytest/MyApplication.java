package com.renj.volleytest;

import android.app.Application;

import com.renj.volleylibrary.VHttpUtil;
import com.renj.volleylibrary.imageloader.VImageLoadUtil;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-09-10   10:10
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VHttpUtil.initRequestQueue(this);
        VImageLoadUtil.initImageQueue(this);
    }
}
