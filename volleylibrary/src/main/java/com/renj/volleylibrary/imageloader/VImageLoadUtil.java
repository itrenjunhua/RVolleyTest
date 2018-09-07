package com.renj.volleylibrary.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.renj.volleylibrary.NetWorkUtils;
import com.renj.volleylibrary.ResultListener;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-06    11:43
 * <p/>
 * 描述：Volley图片加载工具类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class VImageLoadUtil {
    private static VImageLoadUtil mVImageLoadUtil;
    private static Context mcContext;
    private static RequestQueue mRequestQueue;
    private static VImageCache mVImageCache;
    private static ImageLoader mImageLoader;

    /**
     * 初始化图片加载队列，只需在Application中调用即可
     *
     * @param context
     */
    public static void initImageQueue(Context context) {
        VImageLoadUtil.mcContext = context;
        if (mRequestQueue == null) {
            synchronized (VImageLoadUtil.class) {
                if (mRequestQueue == null) {
                    mRequestQueue = Volley.newRequestQueue(context);
                    mVImageCache = new VImageCache();
                    mImageLoader = new ImageLoader(mRequestQueue, mVImageCache);
                }
            }
        }
    }

    /**
     * 获取Volley图片加载工具类实例
     *
     * @return
     */
    public static VImageLoadUtil newInstance() {
        if (mVImageLoadUtil == null) {
            synchronized (VImageLoadUtil.class) {
                if (mVImageLoadUtil == null) {
                    mVImageLoadUtil = new VImageLoadUtil();
                }
            }
        }
        return mVImageLoadUtil;
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    /*******************************************************
     * 使用ImageRequest加载图片 *
     *******************************************************/
    /**
     * 使用ImageRequest加载图片
     *
     * @param url          图片地址
     * @param imageView    ImageView控件
     * @param maxWidth     图片宽度
     * @param maxHeight    图片高度
     * @param decodeConfig 图片的颜色属性  值：ALPHA_8、RGB_565、ARGB_4444、ARGB_8888
     */
    public void loadImageByRequest(String url, final ImageView imageView, int maxWidth, int maxHeight, Bitmap.Config decodeConfig) {
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                imageView.setImageBitmap(response);
            }
        }, maxWidth, maxHeight, decodeConfig, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        mRequestQueue.add(imageRequest);
    }

    /**
     * 使用ImageRequest加载图片
     *
     * @param url          图片地址
     * @param maxWidth     图片宽度
     * @param maxHeight    图片高度
     * @param decodeConfig 图片的颜色属性  值：ALPHA_8、RGB_565、ARGB_4444、ARGB_8888
     * @param listener     网络请求监听
     */
    public void loadImageByRequest(String url, int maxWidth, int maxHeight, Bitmap.Config decodeConfig, final ResultListener<Bitmap> listener) {
        Log.d("VImageLoadUtil", "NetWorkUtils.getCurrentNetworkState(mcContext):" + NetWorkUtils.getCurrentNetworkState(mcContext));
        if (!NetWorkUtils.isConnectedByState(mcContext)) {
            if (listener != null) {
                listener.onNetWork();
            }
            return;
        }

        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                if (listener != null) listener.onSucceed(response);
            }
        }, maxWidth, maxHeight, decodeConfig, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) listener.onError(error);
            }
        });

        mRequestQueue.add(imageRequest);
    }

    /**
     * 使用ImageRequest加载图片，图片颜色属性指定为ARGB_8888
     *
     * @param url       图片地址
     * @param imageView ImageView控件
     * @param maxWidth  图片宽度
     * @param maxHeight 图片高度
     */
    public void loadImageByRequest(String url, ImageView imageView, int maxWidth, int maxHeight) {
        loadImageByRequest(url, imageView, maxWidth, maxHeight, Bitmap.Config.ARGB_8888);
    }

    /**
     * 使用ImageRequest加载图片，图片颜色属性指定为ARGB_8888
     *
     * @param url       图片地址
     * @param maxWidth  图片宽度
     * @param maxHeight 图片高度
     * @param listener  网络请求监听
     */
    public void loadImageByRequest(String url, int maxWidth, int maxHeight, ResultListener<Bitmap> listener) {
        loadImageByRequest(url, maxWidth, maxHeight, Bitmap.Config.ARGB_8888, listener);
    }

    /**
     * 使用ImageRequest加载图片，图片颜色属性指定为ARGB_8888并且加载原图
     *
     * @param url       图片地址
     * @param imageView ImageView控件
     */
    public void loadImageByRequest(String url, ImageView imageView) {
        loadImageByRequest(url, imageView, 0, 0, Bitmap.Config.ARGB_8888);
    }

    /**
     * 使用ImageRequest加载图片，图片颜色属性指定为ARGB_8888并且加载原图
     *
     * @param url      图片地址
     * @param listener 网络请求监听
     */
    public void loadImageByRequest(String url, ResultListener<Bitmap> listener) {
        loadImageByRequest(url, 0, 0, Bitmap.Config.ARGB_8888, listener);
    }

    /*******************************************************
     * 使用NetworkImageView加载图片 *
     *******************************************************/
    /**
     * 使用NetworkImageView加载图片
     *
     * @param url              图片地址
     * @param netWorkImageView NetworkImageView控件
     */
    public void loadImageByNetworkView(String url, NetworkImageView netWorkImageView) {
        netWorkImageView.setImageUrl(url, mImageLoader);
    }

    /*******************************************************
     * 使用ImageLoader加载图片 *
     *******************************************************/
    /**
     * 使用ImageLoader加载图片，加载的图片为原图
     *
     * @param imageView  ImageView控件
     * @param url        图片地址
     * @param defaultImg 默认图片
     * @param errorImg   加载错误的图片
     */
    public void loadImageByImageLoader(ImageView imageView, String url, int defaultImg, int errorImg) {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, defaultImg, errorImg);
        mImageLoader.get(url, imageListener);
    }

    /**
     * 使用ImageLoader加载图片，加载指定大小的图片
     *
     * @param imageView  ImageView控件
     * @param url        图片地址
     * @param defaultImg 默认图片
     * @param errorImg   加载错误的图片
     * @param maxWidth   加载图片的宽
     * @param maxHeight  加载图片的高
     */
    public void loadImageByImageLoader(ImageView imageView, String url, int defaultImg, int errorImg, int maxWidth, int maxHeight) {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, defaultImg, errorImg);
        mImageLoader.get(url, imageListener, maxWidth, maxHeight);
    }
}
