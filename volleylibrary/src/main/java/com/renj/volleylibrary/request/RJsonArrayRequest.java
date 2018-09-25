package com.renj.volleylibrary.request;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.renj.volleylibrary.NetWorkUtils;

import org.json.JSONArray;

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
public class RJsonArrayRequest implements IRequest<JSONArray> {

    private Builder builder;

    public RJsonArrayRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public ResultCallBack<JSONArray> execute() {
        final ResultCallBack<JSONArray> beanResultCallBack = ResultCallBack.<JSONArray>create();

        if (!NetWorkUtils.isConnectedByState(RVHttpUtil.newInstance().getContext())) {
            beanResultCallBack.onNetWork();
            return beanResultCallBack;
        }

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(builder.url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (beanResultCallBack != null) {
                    beanResultCallBack.onSucceed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (beanResultCallBack != null) {
                    beanResultCallBack.onError(error);
                }
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
        if (builder.tag != null) jsonArrayRequest.setTag(builder.tag);
        RVHttpUtil.newInstance().getRequestQueue().add(jsonArrayRequest);

        return beanResultCallBack;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;

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

        public RJsonArrayRequest build() {
            return new RJsonArrayRequest(this);
        }
    }
}
