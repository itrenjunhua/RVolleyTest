package com.renj.volleylibrary.imageloader;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * ======================================================================
 * <p/>
 * 作者：Renj
 * <p/>
 * 创建时间：2017-02-06    9:18
 * <p/>
 * 描述：Volley图片缓存类
 * <p/>
 * 修订历史：
 * <p/>
 * ======================================================================
 */
public class VImageCache implements ImageLoader.ImageCache {
    // 使用Android提供的LruCache类
    private static LruCache<String, Bitmap> mVolleyImgCache;

    public VImageCache() {
        // 使用应用程序可用的最大内存的1/8作为缓存图片的内存缓存
        int volleyImgMaxMemory = (int) (Runtime.getRuntime().maxMemory() / 8);
        mVolleyImgCache = new LruCache<String, Bitmap>(volleyImgMaxMemory) {
            // 返回图片的大小，必须重写，否则返回0
            @Override
            protected int sizeOf(String key, Bitmap value) {
                // return value.getByteCount(); // 在Android版本为KITKAT(Android 4.4)以上才可以用
                return value.getRowBytes() * value.getHeight();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return mVolleyImgCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        mVolleyImgCache.put(url, bitmap);
    }
}
