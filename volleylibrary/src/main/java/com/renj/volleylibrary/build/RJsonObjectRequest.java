package com.renj.volleylibrary.build;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.renj.volleylibrary.NetWorkUtils;
import com.renj.volleylibrary.ResultCallBack;
import com.renj.volleylibrary.VHttpUtil;
import com.renj.volleylibrary.entity.BaseBean;

import org.json.JSONObject;

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
public class RJsonObjectRequest implements IRequest<JSONObject> {

    private Builder builder;

    public RJsonObjectRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public ResultCallBack<JSONObject> execute() {
        final ResultCallBack<JSONObject> beanResultCallBack = ResultCallBack.<JSONObject>create();

        if (!NetWorkUtils.isConnectedByState(VHttpUtil.mContext)) {
            beanResultCallBack.onNetWork();
            return beanResultCallBack;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(builder.method, builder.url, builder.jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
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
        if (builder.tag != null) jsonObjectRequest.setTag(builder.tag);
        VHttpUtil.mRequestQueue.add(jsonObjectRequest);

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
        private JSONObject jsonRequest;

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

        public Builder jsonRequest(JSONObject jsonRequest) {
            this.jsonRequest = jsonRequest;
            return this;
        }

        public RJsonObjectRequest build() {
            return new RJsonObjectRequest(this);
        }
    }
}
