package Catch;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Administrator on 2016/8/1.
 */
public class MyVolley {
    public static RequestQueue requestQueue;
    public  MyVolley(Context context){
        requestQueue = Volley.newRequestQueue(context);
    }

    public static RequestQueue getRequestQueue(Context context){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

}
