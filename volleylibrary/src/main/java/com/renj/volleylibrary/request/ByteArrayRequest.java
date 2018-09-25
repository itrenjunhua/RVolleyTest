package com.renj.volleylibrary.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

/**
 * ======================================================================
 * 作者：Renj
 * <p>
 * 创建时间：2017-03-06   0:23
 * <p>
 * 描述：自定义Volley请求，返回byte[]的数据类型
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
/*public*/ class ByteArrayRequest extends Request<byte[]> {
    private Response.Listener listener;

    public ByteArrayRequest(int method, String url, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
    }

    public ByteArrayRequest(String url, Response.Listener listener, Response.ErrorListener errorListener) {
        this(Method.GET, url,listener, errorListener);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new VolleyError(e));
        }
    }

    @Override
    protected void deliverResponse(byte[] response) {
        listener.onResponse(response);
    }
}
