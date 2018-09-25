package com.renj.volleylibrary.request;

import android.support.annotation.NonNull;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-09-12   16:13
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public final class ResultCallBack<T> {
    private ResultListener resultListener;

    private ResultCallBack() {
    }

    /**
     * 创建新的 {@link ResultCallBack} 对象
     *
     * @param <T> 泛型
     * @return {@link ResultCallBack} 对象
     */
    @NonNull
    static <T> ResultCallBack<T> create() {
        return new ResultCallBack<>();
    }

    public void onResult(@NonNull ResultListener<T> resultListener) {
        this.resultListener = resultListener;
    }

    void onSucceed(T result) {
        resultListener.onSucceed(result);
    }

    void onNetWork() {
        resultListener.onNetWork();
    }

    void onError(Throwable e) {
        resultListener.onError(e);
    }

    public static class ResultListener<T> {
        /**
         * 成功时回调
         *
         * @param result 请求的结果
         */
        public void onSucceed(T result){}

        /**
         * 没有网络时回调
         */
        public void onNetWork(){}

        /**
         * 请求发生错误时回调
         *
         * @param e 错误对象
         */
        public void onError(Throwable e){}
    }
}
