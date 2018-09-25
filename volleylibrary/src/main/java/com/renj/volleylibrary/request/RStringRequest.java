package com.renj.volleylibrary.request;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.renj.volleylibrary.NetWorkUtils;

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
public class RStringRequest implements IRequest<String> {

    private Builder builder;

    public RStringRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public ResultCallBack<String> execute() {
        final ResultCallBack<String> stringResultCallBack = ResultCallBack.<String>create();

        if (!NetWorkUtils.isConnectedByState(RVHttpUtil.newInstance().getContext())) {
            stringResultCallBack.onNetWork();
            return stringResultCallBack;
        }

        StringRequest stringRequest = new StringRequest(builder.method, builder.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                stringResultCallBack.onSucceed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stringResultCallBack.onError(error);
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

        if (builder.tag != null) stringRequest.setTag(builder.tag);
        RVHttpUtil.newInstance().getRequestQueue().add(stringRequest);

        return stringResultCallBack;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        private int method = Method.GET;
        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;

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

        public RStringRequest build() {
            return new RStringRequest(this);
        }
    }
}
