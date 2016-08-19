package com.android.ming.app;

import android.app.Application;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;

import com.android.ming.utils.ComTools;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.ming.utils.AppUtil;
import com.android.ming.utils.SPUtil;
import com.wo.main.WP_App;

/**
 * Created by KingYang on 16/4/14.
 * E-Mail: admin@kingyang.cn
 */
public class App extends Application {
    private RequestQueue queue;// 请求队列
    private ImageLoader imageLoader;// 图片异步加载器

    public RequestQueue getQueue() {
        return queue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // 网络通信相关
        queue = Volley.newRequestQueue(this);
//        imageLoader = new ImageLoader(queue, new MyImageCache());
        imageLoader = new ImageLoader(queue, new MyImageCache());
//        getImageLoader();
        WP_App.on_AppInit(getApplicationContext());
        // 生成UID
        if (TextUtils.isEmpty(SPUtil.getString(getApplicationContext(), Consts.SP.UID))) {
            SPUtil.putString(getApplicationContext(), Consts.SP.UID, AppUtil.createUid(getApplicationContext()));
        }
    }

    private class MyImageCache implements ImageLoader.ImageCache {
        private LruCache<String, Bitmap> mImageCache;

        public MyImageCache() {
            int maxSize = 20 * 1024 * 1024;// 缓存大小:10MB
            mImageCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String s) {
            return mImageCache.get(s);
        }

        @Override
        public void putBitmap(String s, Bitmap bitmap) {
            mImageCache.put(s, bitmap);
        }
    }

//    /**
//     * 获得图片加载器
//     *
//     * @return
//     */
//    public ImageLoader getImageLoader() {
//        if (imageLoader == null) {
//
//            // 这个是ImageLoader 的缓存，每次新启动应用，都会走这里
//            final LruCache<String, Bitmap> mImageCache = new LruCache<String, Bitmap>(
//                    20);
//            ImageLoader.ImageCache imageCache = new ImageLoader.ImageCache() {
//                @Override
//                public void putBitmap(String key, Bitmap value) {
//                    mImageCache.put(key, value);
//                    // 保存到本地
//                    ComTools.saveBitmap2(value, key, getApplicationContext());
//                }
//
//
//                @Override
//                public Bitmap getBitmap(String key) {
//                    return mImageCache.get(key);
//                }
//            };
//            imageLoader = new ImageLoader(queue, imageCache);
//        }
//
//
//        return imageLoader;
//    }
}
