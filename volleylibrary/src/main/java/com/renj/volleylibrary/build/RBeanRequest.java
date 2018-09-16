package com.renj.volleylibrary.build;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.renj.volleylibrary.NetWorkUtils;
import com.renj.volleylibrary.ResultCallBack;
import com.renj.volleylibrary.VHttpUtil;
import com.renj.volleylibrary.entity.BaseBean;
import com.renj.volleylibrary.request.BeanRequest;

import java.util.Map;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-09-12   15:42
 * <p>
 * 描述：
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

        if (!NetWorkUtils.isConnectedByState(VHttpUtil.mContext)) {
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
        VHttpUtil.mRequestQueue.add(beanRequest);

        return beanResultCallBack;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder<T extends BaseBean> {
        private int method = Method.GET;
        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Class<T> clazz;

        public Builder method(int method) {
            this.method = method;
            return this;
        }

        public Builder url(@NonNull String url) {
            this.url = url;
            return this;
        }

        public Builder tag(Object tag) {
            this.tag = tag;
            return this;
        }

        public Builder headers(@NonNull Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder params(@NonNull Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder clazz(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public RBeanRequest build() {
            return new RBeanRequest(this);
        }
    }
}
