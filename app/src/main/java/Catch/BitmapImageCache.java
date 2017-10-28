package Catch;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/8/1.
 */
public class BitmapImageCache implements ImageLoader.ImageCache {
    public static BitmapImageCache bitmapImageCache = null;
    private static LruCache<String,Bitmap> mCache;

    public BitmapImageCache(){
        int maxSize = 30 * 1024 * 1024;
        mCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight();
            }

        };
    }

    public static BitmapImageCache getBitmapImageCache(){
        if(bitmapImageCache == null){
            bitmapImageCache = new BitmapImageCache();
        }
        return bitmapImageCache;
    }


    @Override
    public Bitmap getBitmap(String s) {
        return mCache.get(s);
    }

    @Override
    public void putBitmap(String s, Bitmap bitmap) {
        mCache.put(s,bitmap);
    }
}
