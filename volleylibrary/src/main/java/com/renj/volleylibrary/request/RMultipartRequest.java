package com.renj.volleylibrary.request;

import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.renj.volleylibrary.NetWorkUtils;
import com.renj.volleylibrary.entity.FileEntity;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-09-12   15:42
 * <p>
 * 描述：上传文件的请求
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class RMultipartRequest implements IRequest<String> {

    private Builder builder;

    public RMultipartRequest(Builder builder) {
        this.builder = builder;
    }

    @Override
    public ResultCallBack<String> execute() {
        final ResultCallBack<String> stringResultCallBack = ResultCallBack.<String>create();

        if (!NetWorkUtils.isConnectedByState(RVHttpUtil.newInstance().getContext())) {
            stringResultCallBack.onNetWork();
            return stringResultCallBack;
        }

        /*
         * builder.fileEntity 和 builder.fileEntityList 可能出现4种情况：
         * 1.builder.fileEntity 和 builder.fileEntityList 都不为空 => 合并提交
         * 2.builder.fileEntity 和 builder.fileEntityList 都为空 => 不需要处理，直接提交 空的 builder.fileEntityList 即可
         * 3.builder.fileEntity 不为空但 builder.fileEntityList 为空 =>
         *   创建 builder.fileEntityList 对象，将 builder.fileEntity 增加到列表中然后提交 builder.fileEntityList
         * 4.builder.fileEntity 为空但 builder.fileEntityList 不为空 => 不需要处理，直接提交 builder.fileEntityList 即可
         */

        // 传递了单个文件，但是没有传递数组文件，将单个文件添加到数组文件中
        if (builder.fileEntity != null && builder.fileEntityList == null) {
            builder.fileEntityList = new ArrayList<>();
            builder.fileEntityList.add(builder.fileEntity);
        }

        // 传递了单个文件，并且传递的多个文件，合并成数组文件
        if (builder.fileEntity != null && builder.fileEntityList != null) {
            builder.fileEntityList.add(builder.fileEntity);
        }

        MultipartRequest multipartRequest = new MultipartRequest(builder.url, builder.formParams, builder.charSet, builder.fileEntityList,
                new Response.Listener<String>() {
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

        if (builder.tag != null) multipartRequest.setTag(builder.tag);
        RVHttpUtil.newInstance().getRequestQueue().add(multipartRequest);

        return stringResultCallBack;
    }

    public static Builder create() {
        return new Builder();
    }

    public static class Builder {
        private String url;
        private Object tag;
        private Map<String, String> headers;
        private Map<String, String> params;
        private Map<String, Object> formParams;
        private FileEntity fileEntity;
        private List<FileEntity> fileEntityList;
        private Charset charSet = Charset.defaultCharset();

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

        public Builder formParams(@NonNull Map<String, Object> formParams) {
            this.formParams = formParams;
            return this;
        }

        public Builder fileEntity(@NonNull FileEntity fileEntity) {
            this.fileEntity = fileEntity;
            return this;
        }

        public Builder fileEntity(@NonNull List<FileEntity> fileEntityList) {
            this.fileEntityList = fileEntityList;
            return this;
        }

        public Builder charSet(@NonNull Charset charSet) {
            this.charSet = charSet;
            return this;
        }

        public RMultipartRequest build() {
            return new RMultipartRequest(this);
        }
    }
}
