package tool;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import url.MYURL;

/**
 * Created by Administrator on 2016/9/27.
 */
public class SingleHttpClick {
    private static SingleHttpClick singleHttpClick = null;
    private static HttpClient httpclient;

    public SingleHttpClick() {
        httpclient = new DefaultHttpClient();
        //请求超时
        httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
      //  读取超时
        httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpClientParams.setCookiePolicy(httpclient.getParams(), CookiePolicy.BROWSER_COMPATIBILITY);
        // Log.d("mm","Dad");
    }

    public static SingleHttpClick getSingleHttpClick() {
        if (singleHttpClick == null) {
            singleHttpClick = new SingleHttpClick();
        }
        return singleHttpClick;
    }

    public synchronized String getHttpClickMeg(String url) {
        // Log.d("main","ddd"+url);

        try {
            HttpGet httpget = new HttpGet(url);

            HttpResponse response = null;
            response = httpclient.execute(httpget);

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                InputStream instream = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
                final StringBuilder s = new StringBuilder();
                String line;
                //读取服务器返回的数据
                while ((line = reader.readLine()) != null) {
                    s.append(line);
                }
                instream.close();
                reader.close();
                String str = s.toString();
                //    Log.d("main","kk"+str);
                return str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }


    public  synchronized String doPost(String url, Map<String, String> map, String charset) {

        HttpPost httpPost = null;
        String result = null;
        try {

            httpPost = new HttpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            if (list.size() > 0) {
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, charset);
                httpPost.setEntity(entity);
            }
            HttpResponse response = httpclient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


}
