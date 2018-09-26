package com.renj.volleylibrary.request;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-03    10:49
 * <p/>
 * 描述：Volley网络请求工具类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class RVHttpUtil {
    private static RequestQueue mRequestQueue;
    private static Context mContext;
    private static RVHttpUtil mRVHttpUtil;

    private RVHttpUtil() {
    }

    /**
     * 初始化全局请求队列，在继承至Application的类中调用即可
     *
     * @param context 上下文
     */
    public static void initRequestQueue(Context context) {
        mRVHttpUtil.mContext = context;
        if (mRequestQueue == null) {
            synchronized (RVHttpUtil.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
    }

    /**
     * 获取HttpUtil工具类对象
     *
     * @return
     */
    public static RVHttpUtil newInstance() {
        if (mRVHttpUtil == null) {
            synchronized (RVHttpUtil.class) {
                if (mRVHttpUtil == null) {
                    mRVHttpUtil = new RVHttpUtil();
                }
            }
        }
        return mRVHttpUtil;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    @org.jetbrains.annotations.Contract(pure = true)
    public Context getContext() {
        return mContext;
    }

    /*******************************************************
     * 取消链接 *
     *******************************************************/
    /**
     * 取消所有指定tag的请求
     *
     * @param tag
     */
    public void cancelAll(@NonNull Object tag) {
        if (tag == null) return;
        mRequestQueue.cancelAll(tag);
    }

    /**
     * 自定义取消过滤后的请求
     *
     * @param filter
     */
    public void cancelAll(RequestQueue.RequestFilter filter) {
        mRequestQueue.cancelAll(filter);
    }

    /**
     * 取消所有请求
     */
    public void callAll() {
        cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
