package net;

import android.os.Handler;

import net.NetInterface.RequestMessageLine;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import persenter.netRequestListener.MessageLineListenser;
import tool.SingleHttpClick;
import ui.LineChartView;
import url.MYURL;

/**
 * Created by Administrator on 2017/3/18.
 */
public class RequestMessageLineImpl implements RequestMessageLine{

    private MessageLineListenser messageLineListenser;

    private Handler handler;


    public RequestMessageLineImpl(MessageLineListenser messageLineListenser, Handler handler) {
        this.messageLineListenser = messageLineListenser;

        this.handler = handler;
    }

    @Override
    public void requestMessageLine(String userName,ArrayList<ui.LineChartView.RatingPoint> list) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        String url = MYURL.OLD_ROOT_URL+MYURL.RATING_POINT_LIST+MYURL.USER+"="+userName;
        String str = singleHttpClick.getHttpClickMeg(url);
        if (!"".equals(str) || str != null) {
            AxJson(str, list);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    messageLineListenser.getLineDataSuccess();
                }
            });
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    messageLineListenser.getLineDataFall();
                }
            });
        }

    }

    private void AxJson(String str, ArrayList<LineChartView.RatingPoint> list) {
        try {
            JSONArray array = new JSONArray(str);

            for (int i=0,n=array.length();i<n;++i){
                JSONObject obj = new JSONObject(array.getString(i));
                long time = obj.getLong(MYURL.TIME);
                int rating  = obj.getInt(MYURL.RATING);
                String text = obj.getString(MYURL.TEXT);
                int rank = obj.getInt(MYURL.RANK);
                ui.LineChartView.RatingPoint r = new LineChartView.RatingPoint(time,rating,text,rank);
                list.add(r);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destory() {
        messageLineListenser = null;

        handler = null;
    }
}
