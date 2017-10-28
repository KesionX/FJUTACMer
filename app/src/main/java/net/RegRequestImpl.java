package net;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.toolbox.HttpClientStack;

import net.NetInterface.RegRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import persenter.netRequestListener.regStatusListener;
import tool.SingleHttpClick;
import url.MYURL;


/**
 * Created by Administrator on 2016/9/22.
 */
public class RegRequestImpl implements RegRequest{

    private regStatusListener regstatusListener;

    public RegRequestImpl(regStatusListener regstatusListener){
        this.regstatusListener = regstatusListener;
    }

    @Override
    public void requestReg(String username, String password, String rpass, String nick, int gender, String school,
                           String email, String motto, Handler handler) {
        String url = null;
     /*   try {
            //+
        *//*         url =  MYURL.REG_USERNAME+"="+ URLEncoder.encode(username,"utf-8")+"&"+ MYURL.REG_PASSWORD+"="+
                    URLEncoder.encode(password,"utf-8")+"&"+ MYURL.REG_RPASS+"="+URLEncoder.encode(rpass,"utf-8")+"&"+ MYURL.REG_NICK+"="+
                    URLEncoder.encode(nick,"utf-8")+"&"+ MYURL.REG_GENDER+"="+gender+"&"+ MYURL.REG_SCHOOL+"="+
                    URLEncoder.encode(school,"utf-8")+"&"+ MYURL.REG_EMAIL+"="+URLEncoder.encode(email,"utf-8")+"&"+ MYURL.REG_MOTTO+"="+
                    URLEncoder.encode(motto,"utf-8");*//*
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        url = MYURL.REG_USERNAME+"="+username+"&"+MYURL.REG_PASSWORD+"="+password+"&"+ MYURL.REG_RPASS+"="+rpass+"&"+MYURL.REG_NICK+"="+
                nick+"&"+MYURL.REG_GENDER+"="+gender+"&"+ MYURL.REG_SCHOOL+"="+school+"&"+MYURL.REG_EMAIL+"="+email+"&"+MYURL.REG_MOTTO+"="+
                motto+"&"+"noRedirect=1";
        URL httpMYURL = null;

    //    String str = SingleHttpClick.getSingleHttpClick().doPost(MYURL.OLD_ROOT_URL+ MYURL.REG_URL,null,"utf-8");

      //  Log.d("kekeke",str+"");

        try {
         //   httpClient.getParams().setParameter("http.socket.timeout", new Integer(30000));
            httpMYURL = new URL(MYURL.OLD_ROOT_URL+ MYURL.REG_URL);
            HttpURLConnection connection = null;
            connection = (HttpURLConnection) httpMYURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setRequestProperty("charset", "UTF-8");
           // connection.setDoOutput(true);
            OutputStream out = connection.getOutputStream();
         //   out = new DataOutputStream(connection.getOutputStream());
            //向服务器传送数据
            out.write(url.getBytes());
            Log.d("keke",connection.getResponseCode()+"");
            if(connection.getResponseCode() == 200){
                InputStream in = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder s = new StringBuilder();
                String line;
                //读取服务器返回的数据
                while ((line = reader.readLine()) != null) {
                    s.append(line);
                }

                Log.d("keke","meg : "+s.toString());
                String str = s.toString();
                JSONObject jsonObject = new JSONObject(str);

                String ret = jsonObject.getString(MYURL.RET);
                if(MYURL.REG_UsernameExist.equals(ret)){
                     regstatusListener.regUsernameExist();
                }else if(MYURL.REG_SUCCESS.equals(ret)){
                    regstatusListener.regSuccess();
                }else if(MYURL.REG_FALL.equals(ret)){
                    regstatusListener.regFail();
                }


                reader.close();
                in.close();
            }
            /*String s = "sdadad";
            Message message = new Message();
            message.what = 0;
            message.obj = s.toString();
            handler.sendMessage(message);*/
            out.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
