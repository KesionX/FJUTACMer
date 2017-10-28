package net;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import net.NetInterface.RequestLogin;

import org.json.JSONException;
import org.json.JSONObject;

import Catch.MyVolley;
import bean.SingleMegSelf;
import persenter.netRequestListener.loginStatusListenser;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/9/20.
 */
public class loginListenerImpl implements RequestLogin{

    private  loginStatusListenser loginStatusListenser;
    private Handler handler;
    public loginListenerImpl(loginStatusListenser loginStatusListenser, Handler handler){
        this.loginStatusListenser = loginStatusListenser;
        this.handler = handler;
    }

    @Override
    public void LoginAccount(String account,String pass) {
        requestLogin(account,pass);
    }

    @Override
    public void destory() {
        loginStatusListenser=null;
        handler=null;
    }

    private void requestLogin(String account,String pass){
       SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
       String str = singleHttpClick.getHttpClickMeg(MYURL.ROOTURL + MYURL.LOGINURL + MYURL.LOGIN_ACCOUNT + "=" + account
               + "&" + MYURL.LOGIN_PASS + "=" + pass);


        try {
            JSONObject jsonObject = new JSONObject(str);
            String ret = null;
            ret = jsonObject.getString(MYURL.RET);
            if(MYURL.LOGIN_SUCCESS.equals(ret)){
                String meg;
                SingleHttpClick singleHttpClickgetMeg = SingleHttpClick.getSingleHttpClick();
                meg = singleHttpClickgetMeg.getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.SINGLE_MEG_SELF);
             //   Log.d("mainself","meg "+meg);
                AxJSON(meg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(str!=null||!"".equals(str))
        {
            AnalysisJson(str);
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    loginStatusListenser.SystemError();
                }
            });
        }
      /* StringRequest stringRequest = new StringRequest(MYURL.ROOTURL + MYURL.LOGINURL + MYURL.LOGIN_ACCOUNT + "=" + account
               + "&" + MYURL.LOGIN_PASS + "=" + pass, new Response.Listener<String>() {
           @Override
           public void onResponse(String s) {
               AnalysisJson(s);
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError volleyError) {
               loginStatusListenser.SystemError();
           }
       });
       requestQueue.add(stringRequest);*/
   }

    private void AnalysisJson(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            String ret = jsonObject.getString(MYURL.RET);
            if(MYURL.LOGIN_SUCCESS.equals(ret)){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginStatusListenser.loginSuccess();
                    }
                });

            }else if(MYURL.NO_SUCH_USER.equals(ret)){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginStatusListenser.NoSuchUser();
                    }
                });

            }else if(MYURL.WRONG_PASSWORD.equals(ret)){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginStatusListenser.WrongPassword();
                    }
                });

            }else if (MYURL.SYSTEM_ERROR.equals(ret)){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loginStatusListenser.SystemError();
                    }
                });

            }
        } catch (JSONException e) {
            e.printStackTrace();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    loginStatusListenser.SystemError();
                }
            });
        }
    }


    private  void  AxJSON(String s){
        try {
            Log.d("maink",s);
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
              //  Log.d("maink","use"+username);
                SingleMegSelf.setSingleMegSelf(username,nick,motto,email,school,gender,acb,submitnum,acnum,ratingnum,rating,rank);
               // Log.d("maink","ss"+SingleMegSelf.getSingleMegSelf().toString());
            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
