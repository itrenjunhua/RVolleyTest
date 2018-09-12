package com.renj.volleylibrary;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.renj.volleylibrary.entity.BaseBean;
import com.renj.volleylibrary.entity.FileEntity;
import com.renj.volleylibrary.request.BeanRequest;
import com.renj.volleylibrary.request.ByteArrayRequest;
import com.renj.volleylibrary.request.FormRequest;
import com.renj.volleylibrary.request.MultipartRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-03    10:49
 * <p/>
 * 描述：<b>Volley网络请求工具类</b><br/>
 * 1.提供通过GET或POST方式，或者指定其他的请求方式获取byte[]类型、String类型、JsonObject类型、JsonArray类型和JavaBean类型的数据；<br/>
 * 2.使用POST方式提交表单数据；<br/>
 * 4.支持指定提交数据的编码；<br/>
 * 3.使用POST方式提交表单数据和文件(单个文件或者多个文件)；<br/>
 * 5.支持设置Tag和取消网络请求。<br/>
 * 如果需要设置Header、Cookie等信息需要另外扩展
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class VHttpUtil {
    public static RequestQueue mRequestQueue;
    public static Context mContext;
    private static VHttpUtil mVHttpUtil;

    private VHttpUtil() {
    }

    /**
     * 初始化全局请求队列，在继承至Application的类中调用即可
     *
     * @param context 上下文
     */
    public static void initRequestQueue(Context context) {
        mVHttpUtil.mContext = context;
        if (mRequestQueue == null) {
            synchronized (VHttpUtil.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                }
            }
        }
    }

    /**
     * 获取HttpUtil工具类对象
     *
     * @return
     */
    public static VHttpUtil newInstance() {
        if (mVHttpUtil == null) {
            synchronized (VHttpUtil.class) {
                if (mVHttpUtil == null) {
                    mVHttpUtil = new VHttpUtil();
                }
            }
        }
        return mVHttpUtil;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /*******************************************************
     * 结果为byte[]类型数据 *
     *******************************************************/
    /**
     * 获取String类型的数据
     *
     * @param method   请求方式
     * @param url      地址
     * @param params   普通方式提交的参数
     * @param tag      指定请求的标记，不指定传null
     * @param listener 监听
     */
    public void byteArrayData(int method, String url, final Map<String, String> params, Object tag, final ResultListener<byte[]> listener) {
        if (!NetWorkUtils.isConnectedByState(mContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }

        ByteArrayRequest byteArrayRequest = new ByteArrayRequest(method, url, new Response.Listener<byte[]>() {
            @Override
            public void onResponse(byte[] response) {
                if (listener != null) {
                    listener.onSucceed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };
        if (tag != null) byteArrayRequest.setTag(tag);
        mRequestQueue.add(byteArrayRequest);
    }

    /**
     * GET方式并且没有参数获取byte[]类型数据
     *
     * @param url      地址
     * @param listener 监听
     */
    public void getByteArrayData(String url, ResultListener<byte[]> listener) {
        byteArrayData(Request.Method.GET, url, null, null, listener);
    }

    /**
     * GET方式获取byte[]类型数据
     *
     * @param url      地址
     * @param params   普通方式提交的参数
     * @param listener 监听
     */
    public void getByteArrayData(String url, Map<String, String> params, ResultListener<byte[]> listener) {
        byteArrayData(Request.Method.GET, url, params, null, listener);
    }

    /**
     * POST方式并且没有参数获取byte[]类型数据
     *
     * @param url      地址
     * @param listener 监听
     */
    public void postByteArrayData(String url, ResultListener<byte[]> listener) {
        byteArrayData(Request.Method.POST, url, null, null, listener);
    }

    /**
     * POST方式获取byte[]类型数据
     *
     * @param url      地址
     * @param params   普通方式提交的参数
     * @param listener 监听
     */
    public void postByteArrayData(String url, Map<String, String> params, ResultListener<byte[]> listener) {
        byteArrayData(Request.Method.POST, url, params, null, listener);
    }

    /*******************************************************
     * 结果为String类型数据 *
     *******************************************************/
    /**
     * 获取String类型的数据
     *
     * @param method   请求方式
     * @param url      地址
     * @param params   普通方式提交的参数
     * @param tag      指定请求的标记，不指定传null
     * @param listener 监听
     */
    public void stringData(int method, String url, final Map<String, String> params, Object tag, final ResultListener<String> listener) {
        if (!NetWorkUtils.isConnectedByState(mContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }

        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (listener != null) {
                    listener.onSucceed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };
        if (tag != null) stringRequest.setTag(tag);
        mRequestQueue.add(stringRequest);
    }

    /**
     * GET方式并且没有参数获取String类型数据
     *
     * @param url      地址
     * @param listener 监听
     */
    public void getStringData(String url, ResultListener<String> listener) {
        stringData(Request.Method.GET, url, null, null, listener);
    }

    /**
     * GET方式获取String类型数据
     *
     * @param url      地址
     * @param params   普通方式提交的参数
     * @param listener 监听
     */
    public void getStringData(String url, Map<String, String> params, ResultListener<String> listener) {
        stringData(Request.Method.GET, url, params, null, listener);
    }

    /**
     * POST方式并且没有参数获取String类型数据
     *
     * @param url      地址
     * @param listener 监听
     */
    public void postStringData(String url, ResultListener<String> listener) {
        stringData(Request.Method.POST, url, null, null, listener);
    }

    /**
     * POST方式获取String类型数据
     *
     * @param url      地址
     * @param params   普通方式提交的参数
     * @param listener 监听
     */
    public void postStringData(String url, Map<String, String> params, ResultListener<String> listener) {
        stringData(Request.Method.POST, url, params, null, listener);
    }

    /*******************************************************
     * 结果为JSONObject类型数据 *
     *******************************************************/
    /**
     * 获取JsonObject类型的数据
     *
     * @param method      请求方式
     * @param url         请求地址
     * @param params      普通方式提交的参数
     * @param jsonRequest 提交json类型的参数，没有可以为null
     * @param tag         指定请求的标记，不指定传null
     * @param listener    监听
     */
    public void jsonObjectData(int method, String url, final Map<String, String> params, JSONObject jsonRequest, Object tag, final ResultListener<JSONObject> listener) {
        if (!NetWorkUtils.isConnectedByState(mContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, jsonRequest, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onSucceed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };
        if (tag != null) jsonObjectRequest.setTag(tag);
        mRequestQueue.add(jsonObjectRequest);
    }

    /**
     * GET方式获取JsonObject类型的数据
     *
     * @param url         地址
     * @param jsonRequest 提交json类型的参数，没有可以为null
     * @param listener    监听
     */
    public void getJsonObjctData(String url, JSONObject jsonRequest, ResultListener<JSONObject> listener) {
        jsonObjectData(Request.Method.GET, url, null, jsonRequest, null, listener);
    }

    /**
     * GET方式获取JsonObject类型的数据
     *
     * @param url         地址
     * @param params      普通方式提交的参数
     * @param jsonRequest 提交json类型的参数，没有可以为null
     * @param listener    监听
     */
    public void getJsonObjctData(String url, Map<String, String> params, JSONObject jsonRequest, ResultListener<JSONObject> listener) {
        jsonObjectData(Request.Method.GET, url, params, jsonRequest, null, listener);
    }

    /**
     * POST方式获取JsonObject类型的数据
     *
     * @param url         地址
     * @param jsonRequest 提交json类型的参数，没有可以为null
     * @param listener    监听
     */
    public void postJsonObjctData(String url, JSONObject jsonRequest, ResultListener<JSONObject> listener) {
        jsonObjectData(Request.Method.POST, url, null, jsonRequest, null, listener);
    }

    /**
     * POST方式获取JsonObject类型的数据
     *
     * @param url         地址
     * @param params      普通方式提交的参数
     * @param jsonRequest 提交json类型的参数，没有可以为null
     * @param listener    监听
     */
    public void postJsonObjctData(String url, Map<String, String> params, JSONObject jsonRequest, Object tag, ResultListener<JSONObject> listener) {
        jsonObjectData(Request.Method.POST, url, params, jsonRequest, null, listener);
    }

    /*******************************************************
     * 结果为JSONArray类型数据 *
     *******************************************************/
    /**
     * 获取JsonArray类型的数据
     *
     * @param url      请求地址
     * @param listener 监听
     */
    public void jsonArrayData(String url, Object tag, final ResultListener<JSONArray> listener) {
        if (!NetWorkUtils.isConnectedByState(mContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (listener != null) {
                    listener.onSucceed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        });
        if (tag != null) jsonArrayRequest.setTag(tag);
        mRequestQueue.add(jsonArrayRequest);
    }

    /*******************************************************
     * 结果为Bean类型数据 *
     *******************************************************/
    /**
     * 获取JavaBean类型的数据
     *
     * @param method   请求方式
     * @param url      请求地址
     * @param params   请求参数
     * @param clazz    javaBean的Class文件，<b>注意：这个class类型的JabaBean必须继承至BaseBean<b/>
     * @param tag      指定请求的标记，不指定传null
     * @param listener 监听
     * @param <T>      泛型 <b>注意：T extends BaseBean<b/>
     */
    public <T extends BaseBean> void beanData(int method, String url, final Map<String, String> params, Class<T> clazz, Object tag, final ResultListener<T> listener) {
        if (!NetWorkUtils.isConnectedByState(mContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }

        BeanRequest<T> beanRequest = new BeanRequest<T>(method, url, clazz, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                if (listener != null) {
                    listener.onSucceed(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onError(error);
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };
        if (tag != null) beanRequest.setTag(tag);
        mRequestQueue.add(beanRequest);
    }

    /**
     * GET方式不需要参数获取JavaBean类型的数据
     *
     * @param url      请求地址
     * @param clazz    javaBean的Class文件，<b>注意：这个class类型的JabaBean必须继承至BaseBean<b/>
     * @param listener 监听
     * @param <T>      泛型 <b>注意：T extends BaseBean<b/>
     */
    public <T extends BaseBean> void getBeanData(String url, Class<T> clazz, ResultListener<T> listener) {
        beanData(Request.Method.GET, url, null, clazz, null, listener);
    }

    /**
     * GET方式获取JavaBean类型的数据
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param clazz    javaBean的Class文件，<b>注意：这个class类型的JabaBean必须继承至BaseBean<b/>
     * @param listener 监听
     * @param <T>      泛型 <b>注意：T extends BaseBean<b/>
     */
    public <T extends BaseBean> void getBeanData(String url, Map<String, String> params, Class<T> clazz, ResultListener<T> listener) {
        beanData(Request.Method.GET, url, params, clazz, null, listener);
    }

    /**
     * POST方式不需要参数获取JavaBean类型的数据
     *
     * @param url      请求地址
     * @param clazz    javaBean的Class文件，<b>注意：这个class类型的JabaBean必须继承至BaseBean<b/>
     * @param listener 监听
     * @param <T>      泛型 <b>注意：T extends BaseBean<b/>
     */
    public <T extends BaseBean> void postBeanData(String url, Class<T> clazz, ResultListener<T> listener) {
        beanData(Request.Method.POST, url, null, clazz, null, listener);
    }

    /**
     * POST方式获取JavaBean类型的数据
     *
     * @param url      请求地址
     * @param params   请求参数
     * @param clazz    javaBean的Class文件，<b>注意：这个class类型的JabaBean必须继承至BaseBean<b/>
     * @param listener 监听
     * @param <T>      泛型 <b>注意：T extends BaseBean<b/>
     */
    public <T extends BaseBean> void postBeanData(String url, Map<String, String> params, Class<T> clazz, ResultListener<T> listener) {
        beanData(Request.Method.POST, url, params, clazz, null, listener);
    }

    /*******************************************************
     * 提交表单数据 *
     *******************************************************/
    /**
     * POST方式提交表单数据
     *
     * @param url      地址
     * @param params   普通方式提交的参数
     * @param charSet  指定编码
     * @param tag      指定请求的标记，不指定传null
     * @param listener 监听
     */
    public void postFormData(String url, Map<String, Object> params, Charset charSet, Object tag, final ResultListener<String> listener) {
        if (!NetWorkUtils.isConnectedByState(mContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }

        FormRequest formRequest = new FormRequest(url, params, charSet, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSucceed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        if (tag != null) formRequest.setTag(tag);
        mRequestQueue.add(formRequest);
    }

    /**
     * POST方式提交表单数据，不设置Tag并且指定编码为utf-8
     *
     * @param url      地址
     * @param params   普通方式提交的参数
     * @param listener 监听
     */
    public void postFormData(String url, Map<String, Object> params, ResultListener<String> listener) {
        postFormData(url, params, Charset.forName("utf-8"), null, listener);
    }

    /*******************************************************
     * 提交文件数据 *
     *******************************************************/
    /**
     * POST方式提交参数和单个文件
     *
     * @param url        地址
     * @param params     参数
     * @param fileEntity 单个文件实体
     * @param charSet    指定编码
     * @param tag        指定请求的标记，不指定传null
     * @param listener   监听
     */
    public void postSingleMultipartData(String url, Map<String, Object> params, FileEntity fileEntity, Charset charSet, Object tag, final ResultListener<String> listener) {
        if (!NetWorkUtils.isConnectedByState(mContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }

        MultipartRequest multipartRequest = new MultipartRequest(url, params, charSet, fileEntity, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                listener.onSucceed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        if (tag != null) multipartRequest.setTag(tag);
        mRequestQueue.add(multipartRequest);
    }

    /**
     * POST方式提交参数和多个文件
     *
     * @param url            地址
     * @param params         参数
     * @param fileEntityList 文件实体集合
     * @param charSet        指定编码
     * @param tag            指定请求的标记，不指定传null
     * @param listener       监听
     */
    public void postMultipartData(String url, Map<String, Object> params, List<FileEntity> fileEntityList, Charset charSet, Object tag, final ResultListener<String> listener) {
        if (!NetWorkUtils.isConnectedByState(mContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }

        MultipartRequest multipartRequest = new MultipartRequest(url, params, charSet, fileEntityList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSucceed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onError(error);
            }
        });
        if (tag != null) multipartRequest.setTag(tag);
        mRequestQueue.add(multipartRequest);
    }

    /**
     * POST方式提交参数和单个文件，不设置Tag并且指定编码为utf-8
     *
     * @param url        地址
     * @param params     参数
     * @param fileEntity 单个文件实体
     * @param listener   监听
     */
    public void postSingleMultipartData(String url, Map<String, Object> params, FileEntity fileEntity, ResultListener<String> listener) {
        postSingleMultipartData(url, params, fileEntity, Charset.forName("utf-8"), null, listener);
    }

    /**
     * POST方式提交参数和多个文件，不设置Tag并且指定编码为utf-8
     *
     * @param url            地址
     * @param params         参数
     * @param fileEntityList 文件实体集合
     * @param listener       监听
     */
    public void postMultipartData(String url, Map<String, Object> params, List<FileEntity> fileEntityList, ResultListener<String> listener) {
        postMultipartData(url, params, fileEntityList, Charset.forName("utf-8"), null, listener);
    }

    /*******************************************************
     * 取消链接 *
     *******************************************************/
    /**
     * 取消所有指定tag的请求
     *
     * @param tag
     */
    public void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    /**
     * 自定义取消过滤后的请求
     *
     * @param filter
     */
    public void cancelAll(RequestQueue.RequestFilter filter) {
        mRequestQueue.cancelAll(filter);
    }

    /**
     * 取消所有请求
     */
    public void callAll() {
        cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }
}
