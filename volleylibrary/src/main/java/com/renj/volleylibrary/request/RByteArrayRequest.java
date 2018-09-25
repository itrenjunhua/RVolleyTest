package com.renj.volleylibrary.request;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
 * 描述：获取 {@code byte[]} 类型数据请求
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RByteArrayRequest implements IRequest<byte[]> {

    private Builder builder;

    public RByteArrayRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public ResultCallBack<byte[]> execute() {
        final ResultCallBack<byte[]> bytesResultCallBack = ResultCallBack.<byte[]>create();

        if (!NetWorkUtils.isConnectedByState(RVHttpUtil.newInstance().getContext())) {
            bytesResultCallBack.onNetWork();
            return bytesResultCallBack;
        }

        ByteArrayRequest byteArrayRequest = new ByteArrayRequest(builder.method, builder.url, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                bytesResultCallBack.onSucceed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                bytesResultCallBack.onError(error);
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

        if (builder.tag != null) byteArrayRequest.setTag(builder.tag);
        RVHttpUtil.newInstance().getRequestQueue().add(byteArrayRequest);

        return bytesResultCallBack;
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

        private Builder() {
        }

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

        public RByteArrayRequest build() {
            return new RByteArrayRequest(this);
        }
    }
}
