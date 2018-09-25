package com.renj.volleylibrary.request;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.renj.volleylibrary.NetWorkUtils;
import com.renj.volleylibrary.entity.BaseBean;

import java.util.Map;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-09-12   15:42
 * <p>
 * 描述：获取 {@code JavaBean 类型数据请求
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RBeanRequest<T extends BaseBean> implements IRequest<T> {

    private Builder builder;

    public RBeanRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public ResultCallBack<T> execute() {
        final ResultCallBack<T> beanResultCallBack = ResultCallBack.<T>create();

        if (!NetWorkUtils.isConnectedByState(RVHttpUtil.newInstance().getContext())) {
            beanResultCallBack.onNetWork();
            return beanResultCallBack;
        }

        BeanRequest<T> beanRequest = new BeanRequest<T>(builder.method, builder.url, builder.clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                beanResultCallBack.onSucceed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                beanResultCallBack.onError(error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (builder.headers != null) {
                    return builder.headers;
                }
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (builder.params != null) {
                    return builder.params;
                }
                return super.getParams();
            }
        };

        if (builder.tag != null) beanRequest.setTag(builder.tag);
        RVHttpUtil.newInstance().getRequestQueue().add(beanRequest);

        return beanResultCallBack;
    }

    public static <T extends BaseBean> Builder<T> create() {
        return new Builder<T>();
    }

    public static class Builder<T extends BaseBean> {
        private int method = Method.GET;
        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Class<T> clazz;

        private Builder() {
        }

        public Builder<T> method(int method) {
            this.method = method;
            return this;
        }

        public Builder<T> url(@NonNull String url) {
            this.url = url;
            return this;
        }

        public Builder<T> tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder<T> headers(@NonNull Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder<T> params(@NonNull Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder<T> clazz(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public RBeanRequest<T> build() {
            return new RBeanRequest<T>(this);
        }
    }
}
