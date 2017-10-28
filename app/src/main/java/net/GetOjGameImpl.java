package net;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.requestOjGame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.OjGameAdapter;
import bean.recentlyGame;
import persenter.netRequestListener.OjGameListListenser;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/9/28.
 */
public class GetOjGameImpl implements requestOjGame {

    private Handler handler;
    private OjGameListListenser ojGameListListenser;
    private ArrayList<recentlyGame> list;

    public GetOjGameImpl(Handler handler, OjGameListListenser ojGameListListenser, ArrayList<recentlyGame> list) {
        this.handler = handler;
        this.ojGameListListenser = ojGameListListenser;
        this.list = list;
    }

    @Override
    public void requestOjGameList() {
        String strOJ = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.OJ_GAME);
        if(strOJ!=null||!"".equals(strOJ)){
            recentlyGame mGame = new recentlyGame("本OJ最近比赛","",0,"","",0,OjGameAdapter.TYPE_TITLE);
            list.add(mGame);
            AxBenOjJson(strOJ);
        }

        String str = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.OTHEROJ);
        if (str!=null||!"".equals(str)){
          //  recentlyGame game = new recentlyGame(0,"","","其他OJ比赛:","","",OjGameAdapter.TYPE_TITLE,"");
            recentlyGame game = new recentlyGame(0,"oj","link","其他OJ比赛","time","week",OjGameAdapter.TYPE_TITLE,"access");

            list.add(game);
            AxJson(str);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ojGameListListenser.getOjGameListSuccess();
                }
            });
        }else{
            ojGameListListenser.getOjGameListFall();
        }

    }



    @Override
    public void requestOjGameRefresh() {
        list.clear();
        String strOJ = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.OJ_GAME);
        if(strOJ!=null||!"".equals(strOJ)){
            recentlyGame mGame = new recentlyGame("本OJ最近比赛","",0,"","",0,OjGameAdapter.TYPE_TITLE);
            list.add(mGame);
            AxBenOjJson(strOJ);
        }

        String str = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.OTHEROJ);
        Log.d("game","game "+str);
        if (str!=null||!"".equals(str)){
            //  recentlyGame game = new recentlyGame(0,"","","其他OJ比赛:","","",OjGameAdapter.TYPE_TITLE,"");
            recentlyGame game = new recentlyGame(0,"oj","link","其他OJ比赛","time","week",OjGameAdapter.TYPE_TITLE,"access");
            list.add(game);
            AxJson(str);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    ojGameListListenser.refreshOjGameSuccess();
                }
            });
        }else{
            ojGameListListenser.refreshOjGameFall();
        }
    }

    @Override
    public void destory() {

        list=null;
        handler=null;
        ojGameListListenser=null;
    }
    private void AxBenOjJson(String strOJ) {
        try {
            JSONArray jsonArray = new JSONArray(strOJ);
            for(int i=0,n=jsonArray.length();i<n;++i){
                JSONObject jsonObject = new JSONObject(jsonArray.getString(i));
                int cid = jsonObject.getInt(MYURL.CID);
                String name = jsonObject.getString(MYURL.NAME);
                String begin = jsonObject.getString(MYURL.BEGIN);
                String end = jsonObject.getString(MYURL.END);
                String accessoj = jsonObject.getString(MYURL.ACCESSOJ);
                int kind = jsonObject.getInt(MYURL.KIND);
                recentlyGame recentlyGame = new recentlyGame(name,accessoj,cid,begin,end,kind,OjGameAdapter.TYPE_ITEM2);
                list.add(recentlyGame);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    private void AxJson(String s){
        try {
            JSONArray jsonArray = new JSONArray(s);
           // Log.d("json",s);
            for (int i=0,n = jsonArray.length() ;i<n;++i){
                String str = jsonArray.getString(i);
             //   Log.d("json",str);
                JSONObject jsonObject = new JSONObject(str);
                int id = jsonObject.getInt(MYURL.ID);
                 String oj = jsonObject.getString(MYURL.OJ);
                String Link = jsonObject.getString(MYURL.LINK);
                String name = jsonObject.getString(MYURL.NAME);
                String start_time = jsonObject.getString(MYURL.START_TIME);
                String  week = jsonObject.getString(MYURL.WEEK);
                String access = jsonObject.getString(MYURL.ACCESS);
                recentlyGame recentlyGame = new recentlyGame(id,oj,Link,name,start_time,week, OjGameAdapter.TYPE_ITEM,access);
                list.add(recentlyGame);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
