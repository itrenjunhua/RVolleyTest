package com.renj.volleylibrary;

import com.android.volley.VolleyError;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-03    11:22
 * <p/>
 * 描述：Volley网络请求回调接口
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public interface ResultListener<T> {
    /**
     * 成功时回调
     *
     * @param result 请求的结果
     */
    void onSucceed(T result);

    /**
     * 没有网络时回调
     */
    void onNetWork();

    /**
     * 请求发生错误时回调
     *
     * @param e 错误对象
     */
    void onError(VolleyError e);
}
