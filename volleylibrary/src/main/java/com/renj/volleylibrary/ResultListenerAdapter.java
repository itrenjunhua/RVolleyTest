package com.renj.volleylibrary;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-09-10   10:24
 * <p>
 * 描述：Volley网络请求回调接口适配器类，可选择重写方法
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class ResultListenerAdapter<T> implements ResultListener<T> {
    @Override
    public void onSucceed(T result) {

    }

    @Override
    public void onNetWork() {

    }

    @Override
    public void onError(Throwable e) {

    }
}
