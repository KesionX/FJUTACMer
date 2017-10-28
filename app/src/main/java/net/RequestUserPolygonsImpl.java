package net;

import android.os.Handler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



import persenter.netRequestListener.UserPolygonsListener;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2017/3/19.
 */
public class RequestUserPolygonsImpl implements net.NetInterface.RequestUserPolygons{

    private UserPolygonsListener mUserPolygonsListener;
    private Handler handler;

    public RequestUserPolygonsImpl(UserPolygonsListener mUserPolygonsListener, Handler handler) {
        this.mUserPolygonsListener = mUserPolygonsListener;
        this.handler = handler;
    }

    @Override
    public void getUserPolygons(String user, ArrayList<Double> list) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        String url = MYURL.OLD_ROOT_URL+MYURL.POLYGONS_DATA+MYURL.USERNAME+"="+user;
        String str = singleHttpClick.getHttpClickMeg(url);
        if (!"".equals(str) || str != null) {
            AxJson(str, list);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mUserPolygonsListener.getPolygonsDataSuccess();
                }
            });
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mUserPolygonsListener.getPolygonsDataFall();
                }
            });
        }
    }

    private void AxJson(String str, ArrayList<Double> mlist) {
        try {
            JSONArray array = new JSONArray(str);
            double min,max;
            ArrayList<Double> list = new ArrayList<>();
            for (int i=0,n=array.length();i<n;++i){
                    Double d = new Double(array.getDouble(i));
              //  JSONObject obj =  new JSONObject(array.getString(i));
               // obj.get
               // double d =
                list.add(d);
            }

            min = list.get(0);
            for(int i=1,n=list.size();i<n;++i){
                min = list.get(i)<min ? list.get(i):min;
            }
            max= list.get(0);
            for(int i=1,n=list.size();i<n;++i){
                max = list.get(i)>max ? list.get(i):max;
            }
            double mu = (max+min);
            for (int i=0,n=list.size();i<n;++i){
                Double d = new Double(list.get(i)/mu);
                //  JSONObject obj =  new JSONObject(array.getString(i));
                // obj.get
                // double d =
                mlist.add(d);
            }

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destory() {
        mUserPolygonsListener = null;
        handler = null;
    }
}
