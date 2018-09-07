package com.renj.volleylibrary.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.UUID;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-28    10:55
 * <p/>
 * 描述：自定义用于提交表单数据的请求
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class FormRequest extends Request<String> {
    private final String BOUNDARY = "------" + UUID.randomUUID().toString(); // 随机生成边界值
    private final String NEW_LINE = "\r\n"; // 换行符
    private final String MULTIPART_FORM_DATA = "multipart/form-data"; // 数据类型
    private Charset charSet = Charset.defaultCharset(); // 编码

    private Response.Listener listener; // 监听
    private Map<String, Object> params; // 需要提交的参数

    public FormRequest(String url, Map<String, Object> params, Response.Listener listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, errorListener);
        this.listener = listener;
        this.params = params;
    }

    public FormRequest(String url, Map<String, Object> params, Charset charSet, Response.Listener listener, Response.ErrorListener errorListener) {
        this(url, params, listener, errorListener);
        this.charSet = charSet;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {
            return Response.success(new String(response.data, HttpHeaderParser.parseCharset(response.headers)), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // 解析异常
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(String response) {
        listener.onResponse(response);
    }

    /**
     * 获取实体的方法，把参数拼接成表单提交的数据格式
     *
     * @return
     * @throws AuthFailureError
     */
    @Override
    public byte[] getBody() throws AuthFailureError {
        if (params == null || params.size() <= 0) {
            return super.getBody();
        }
        // ------WebKitFormBoundarykR96Kta4gvMACHfq                 第一行
        // Content-Disposition: form-data; name="login_username"    第二行
        //                                                          第三行
        // abcde                                                    第四行

        // ------WebKitFormBoundarykR96Kta4gvMACHfq--               结束行

        // 开始拼接数据
        StringBuffer stringBuffer = new StringBuffer();
        for (String key : params.keySet()) {
            Object value = params.get(key);
            stringBuffer.append("--" + BOUNDARY).append(NEW_LINE); // 第一行
            stringBuffer.append("Content-Disposition: form-data; name=\"").append(key).append("\"").append(NEW_LINE); // 第二行
            stringBuffer.append(NEW_LINE); // 第三行
            stringBuffer.append(value).append(NEW_LINE); // 第四行
        }
        // 所有参数拼接完成，拼接结束行
        stringBuffer.append("--" + BOUNDARY + "--").append(NEW_LINE);// 结束行
        try {
            return stringBuffer.toString().getBytes(charSet);
        } catch (Exception e) {
            e.printStackTrace();
            // 使用默认的编码方式，Android为utf-8
            return stringBuffer.toString().getBytes();
        }
    }

    /**
     * 该方法的作用：在 http 头部中声明内容类型为表单数据
     *
     * @return
     */
    @Override
    public String getBodyContentType() {
        // multipart/form-data; boundary=----WebKitFormBoundarykR96Kta4gvMACHfq
        return MULTIPART_FORM_DATA + ";boundary=" + BOUNDARY;
    }
}
