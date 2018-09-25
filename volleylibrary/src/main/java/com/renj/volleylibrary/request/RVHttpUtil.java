package com.renj.volleylibrary.request;

import android.content.Context;

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
 * 描述：<b>Volley网络请求工具类</b><br/>
 * 1.提供通过GET或POST方式，或者指定其他的请求方式获取byte[]类型、String类型、JsonObject类型、JsonArray类型和JavaBean类型的数据；<br/>
 * 2.使用POST方式提交表单数据；<br/>
 * 4.支持指定提交数据的编码；<br/>
 * 3.使用POST方式提交表单数据和文件(单个文件或者多个文件)；<br/>
 * 5.支持设置Tag和取消网络请求。<br/>
 * 如果需要设置Header、Cookie等信息需要另外扩展
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
    public void cancelAll(Object tag) {
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
