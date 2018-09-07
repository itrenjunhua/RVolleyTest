package com.renj.volleylibrary.entity;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-03    10:16
 * <p/>
 * 描述：使用自定义的BeanRequest请求数据时所有的javabean需要继承BaseBean
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class BaseBean {

    public BaseBean formJson(String result) {
        BaseBean baseBean;
        Gson gson = new Gson();
        try {
            baseBean = gson.fromJson(result, this.getClass());
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            baseBean = new BaseBean();
        }
        return baseBean;
    }

    public String toJson() {
        Gson gson = new Gson();
        try {
            return gson.toJson(this);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
