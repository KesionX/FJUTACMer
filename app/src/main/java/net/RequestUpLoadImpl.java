package net;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestUpLoad;

import org.json.JSONException;
import org.json.JSONObject;

import db.DbHelper;
import persenter.netRequestListener.UpLoadListener;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/10/10.
 */
public class RequestUpLoadImpl implements RequestUpLoad{
    private UpLoadListener upLoadListener;
    private Handler handler;

    public RequestUpLoadImpl( UpLoadListener upLoadListener,Handler handler){
        this.upLoadListener = upLoadListener;
        this.handler = handler;
    }

    @Override
    public void requestUpLoad() {
        String str = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.UPLOAD);
        Log.d("url",str);
        if(!"".equals(str)||str!=null){
            AxJson(str);
        }else{

        }

    }

    private void AxJson(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            final int versionCode = jsonObject.getInt(MYURL.VERSION_CODE);
            final String versionName = jsonObject.getString(MYURL.VERSION_NAME);
            final String versioninfo = jsonObject.getString(MYURL.VERSION_INFO);
            final String apkUrl  =jsonObject.getString(MYURL.URL);
            if(versionCode> DbHelper.VERSION){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        upLoadListener.NeedUpLoad(versionCode,versionName,versioninfo,apkUrl);
                    }
                });
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void destory() {
        upLoadListener = null;
        handler  = null;
    }
}
