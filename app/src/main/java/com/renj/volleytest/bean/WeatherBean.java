package com.renj.volleytest.bean;

import com.renj.volleylibrary.entity.BaseBean;

import java.util.List;

/**
 * ======================================================================
 * <p>
 * 作者：Renj
 * 邮箱：renjunhua@anlovek.com
 * <p>
 * 创建时间：2018-09-10   11:17
 * <p>
 * 描述：
 * <p>
 * 修订历史：
 * <p>
 * ======================================================================
 */
public class WeatherBean extends BaseBean{

    /**
     * code : 200
     * msg : 成功!
     * data : {"yesterday":{"date":"9日星期日","high":"高温 28℃","fx":"西南风","low":"低温 16℃","fl":"<![CDATA[<3级]]>","type":"晴"},"city":"北京","aqi":"80","forecast":[{"date":"10日星期一","high":"高温 28℃","fengli":"<![CDATA[<3级]]>","low":"低温 19℃","fengxiang":"南风","type":"多云"},{"date":"11日星期二","high":"高温 26℃","fengli":"<![CDATA[<3级]]>","low":"低温 18℃","fengxiang":"南风","type":"多云"},{"date":"12日星期三","high":"高温 29℃","fengli":"<![CDATA[<3级]]>","low":"低温 18℃","fengxiang":"南风","type":"多云"},{"date":"13日星期四","high":"高温 29℃","fengli":"<![CDATA[<3级]]>","low":"低温 20℃","fengxiang":"南风","type":"多云"},{"date":"14日星期五","high":"高温 28℃","fengli":"<![CDATA[<3级]]>","low":"低温 19℃","fengxiang":"西南风","type":"阴"}],"ganmao":"各项气象条件适宜，无明显降温过程，发生感冒机率较低。","wendu":"24"}
     */

    public int code;
    public String msg;
    public DataBean data;

    public static class DataBean {
        /**
         * yesterday : {"date":"9日星期日","high":"高温 28℃","fx":"西南风","low":"低温 16℃","fl":"<![CDATA[<3级]]>","type":"晴"}
         * city : 北京
         * aqi : 80
         * forecast : [{"date":"10日星期一","high":"高温 28℃","fengli":"<![CDATA[<3级]]>","low":"低温 19℃","fengxiang":"南风","type":"多云"},{"date":"11日星期二","high":"高温 26℃","fengli":"<![CDATA[<3级]]>","low":"低温 18℃","fengxiang":"南风","type":"多云"},{"date":"12日星期三","high":"高温 29℃","fengli":"<![CDATA[<3级]]>","low":"低温 18℃","fengxiang":"南风","type":"多云"},{"date":"13日星期四","high":"高温 29℃","fengli":"<![CDATA[<3级]]>","low":"低温 20℃","fengxiang":"南风","type":"多云"},{"date":"14日星期五","high":"高温 28℃","fengli":"<![CDATA[<3级]]>","low":"低温 19℃","fengxiang":"西南风","type":"阴"}]
         * ganmao : 各项气象条件适宜，无明显降温过程，发生感冒机率较低。
         * wendu : 24
         */

        public YesterdayBean yesterday;
        public String city;
        public String aqi;
        public String ganmao;
        public String wendu;
        public List<ForecastBean> forecast;

        public static class YesterdayBean {
            /**
             * date : 9日星期日
             * high : 高温 28℃
             * fx : 西南风
             * low : 低温 16℃
             * fl : <![CDATA[<3级]]>
             * type : 晴
             */

            public String date;
            public String high;
            public String fx;
            public String low;
            public String fl;
            public String type;
        }

        public static class ForecastBean {
            /**
             * date : 10日星期一
             * high : 高温 28℃
             * fengli : <![CDATA[<3级]]>
             * low : 低温 19℃
             * fengxiang : 南风
             * type : 多云
             */

            public String date;
            public String high;
            public String fengli;
            public String low;
            public String fengxiang;
            public String type;
        }
    }
}
