package Catch;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MyImageLoader {

    public static ImageLoader imageLoader;

    public static ImageLoader getMyImageLoader(Context context){
        if(imageLoader==null){
            imageLoader = new ImageLoader(MyVolley.getRequestQueue(context),BitmapImageCache.getBitmapImageCache());

        }
        return imageLoader;
    }
}
