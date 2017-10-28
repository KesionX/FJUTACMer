package net;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestRanking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bean.RankingAcmer;
import persenter.netRequestListener.RankingListener;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2017/2/23.
 */
public class RequestRankingImpl implements RequestRanking{

    private Handler handler;
    private RankingListener rankingListener;

    public RequestRankingImpl(RankingListener rankingListener,Handler handler){

        this.rankingListener = rankingListener;
        this.handler = handler;
    }

    @Override
    public void getRankingDataMore(int page, String order, ArrayList<RankingAcmer> list) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        String url = "";
        if(order=="rank"){
            url = MYURL.OLD_ROOT_URL + MYURL.USERLIST + MYURL.ORDER + "=" + order
                    +"&"+MYURL.PAGE+"="+page;
        }else{

            url = MYURL.OLD_ROOT_URL + MYURL.USERLIST + MYURL.ORDER + "=" + order
                    +"&"+MYURL.DESC+"=1"+"&"+MYURL.PAGE+"="+page;
        }

        String str = singleHttpClick.getHttpClickMeg(url);
        if (!"".equals(str) || str != null) {
            AxJson(str, list);
            handler.post(new Runnable() {
                @Override
                public void run() {
                   rankingListener.getRankingDataMoreSuccess();
                }
            });

        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    rankingListener.getRankingDataMoreFall();
                }
            });
        }

    }



    @Override
    public void freshRankingData(int page, String order, ArrayList<RankingAcmer> list) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();


        String url = "";
        if(order=="rank"){
            url = MYURL.OLD_ROOT_URL + MYURL.USERLIST + MYURL.ORDER + "=" + order
                    +"&"+MYURL.PAGE+"="+page;
        }else{

            url = MYURL.OLD_ROOT_URL + MYURL.USERLIST + MYURL.ORDER + "=" + order
                    +"&"+MYURL.DESC+"=1"+"&"+MYURL.PAGE+"="+page;
        }

        String str = singleHttpClick.getHttpClickMeg(url);
        if (!"".equals(str) || str != null) {
            AxJson(str, list);
           // Log.d("ddd","aaaaaaaaaaa");
            handler.post(new Runnable() {
                @Override
                public void run() {
                    rankingListener.getRankingDataRefrshSuccess();
                }
            });

        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    rankingListener.getRankingDataRefreshFall();
                }
            });
        }
    }
    private void AxJson(String str, ArrayList<RankingAcmer> list) {
        //Log.d("ddd","s："+list.size());
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
            String data = jsonObject.getString(MYURL.DATA);
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0, n = jsonArray.length(); i < n; ++i) {
                JSONObject obj = new JSONObject(jsonArray.getString(i));
                String userName = obj.getString(MYURL.USERNAME);
                String nick = obj.getString(MYURL.NICK);
                String motto = obj.getString(MYURL.MOTTO);
                int submissions = obj.getInt(MYURL.SUBMISSIONS);
                int acnum = obj.getInt(MYURL.ACNUM);
                int acb  = obj.getInt(MYURL.ACB);
                int rating  = obj.getInt(MYURL.RATING);
                int ratingNum = obj.getInt(MYURL.RATINGNUM);
                int rank = obj.getInt(MYURL.RANK);
                RankingAcmer rankingAcmer = new RankingAcmer(userName,nick,motto,submissions,
                        acnum,acb,rating,ratingNum,rank);
                list.add(rankingAcmer);
            }
          //  Log.d("ddd","e："+list.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void destory() {
        this.handler = null;
        this.rankingListener = null;
    }
}
