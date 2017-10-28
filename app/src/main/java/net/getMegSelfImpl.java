package net;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.StringRequest;

import net.NetInterface.getMegSelf;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import Catch.MyVolley;
import bean.SingleMegSelf;
import persenter.netRequestListener.getMegSelfListener;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/9/26.
 */
public class getMegSelfImpl implements getMegSelf {

    getMegSelfListener megSelfListener;
    SingleMegSelf singleMegSelf;
    String meg;
    private  Handler handler;
    public getMegSelfImpl(getMegSelfListener megSelfListener, Handler handler){
        this.megSelfListener = megSelfListener;
        this.handler = handler;

    }

    @Override
    public void getmegSelf() {

        getHttpClickMegSelf();

    }

    void AxJSON(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            String ret = jsonObject.getString(MYURL.RET);
            if(MYURL.SUCCESS.equals(ret)){
                String username = jsonObject.getString(MYURL.USERNAME);
                String nick = jsonObject.getString(MYURL.NICK);
                String motto = jsonObject.getString(MYURL.MOTTO);
                String school = jsonObject.getString(MYURL.SCHOOL);
                String email = jsonObject.getString(MYURL.EMAIL);
                int gender = jsonObject.getInt(MYURL.GENDER);
                int acb = jsonObject.getInt(MYURL.ACB);
                int acnum = jsonObject.getInt(MYURL.ACNUM);
                int submitnum = jsonObject.getInt(MYURL.SUBMITNUM);
                int rating = jsonObject.getInt(MYURL.RATING);
                int ratingnum = jsonObject.getInt(MYURL.RATINGNUM);
                int rank = jsonObject.getInt(MYURL.RANK);
                Log.d("maink","use"+username);
                singleMegSelf = SingleMegSelf.getSingleMegSelf(username,nick,motto,email,school,gender,acb,submitnum,acnum,ratingnum,rating,rank);
                Log.d("maink","ss"+singleMegSelf.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        megSelfListener.getMegSelfSuccess();
                    }
                });

            }else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        megSelfListener.getMegSelfFall();
                    }
                });

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void destory() {
        megSelfListener =  null;
        handler = null;
    }

    public void getHttpClickMegSelf() {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        meg = singleHttpClick.getHttpClickMeg(MYURL.ROOT_URL+MYURL.SINGLE_MEG_SELF);
        handler.post(new Runnable() {
            @Override
            public void run() {
                AxJSON(meg);
            }
        });

    }
}
