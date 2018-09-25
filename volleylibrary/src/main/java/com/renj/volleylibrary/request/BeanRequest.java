package com.renj.volleylibrary.request;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.renj.volleylibrary.entity.BaseBean;

import java.io.UnsupportedEncodingException;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-03    10:17
 * <p/>
 * 描述：自定义Volley请求，将结果封装成JavaBean类型的数据
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
/*public*/ class BeanRequest<T extends BaseBean> extends Request<T> {
    private Response.Listener<T> mListener;
    private Class<T> clazz;

    public BeanRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.clazz = clazz;
    }

    public BeanRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, clazz, listener, errorListener);
    }

    /**
     * 对服务器的相应进行解析，可以解析为我们想要的数据类型
     *
     * @param response
     * @return
     */
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        Gson gson = new Gson();
        try {
            return Response.success(gson.fromJson(new String(response.data, HttpHeaderParser.parseCharset(response.headers)), clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    /**
     * 回调Response.Listener的onResponse()方法，Response.Listener就是我们在创建Request的时候的所需要的参数
     *
     * @param response
     */
    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
