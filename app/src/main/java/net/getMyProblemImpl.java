package net;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestMyProblem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bean.MyProblemBean;
import persenter.netRequestListener.MyProblemListenser;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/10/2.
 */
public class getMyProblemImpl implements RequestMyProblem{
    private MyProblemListenser myProblemListenser;
    private Handler handler;
    private static final int GETDATA = 0;
    private static final int REFRESHDATA = 1;
    public getMyProblemImpl(MyProblemListenser myProblemListenser, Handler handler){
        this.myProblemListenser = myProblemListenser;
        this.handler  = handler;
    }

    @Override
    public void getMyProblemData(int page, int index, ArrayList<MyProblemBean> list) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        String str = singleHttpClick.getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.PROBLEMLIST+MYURL.PAGE+
                "="+page+"&"+MYURL.INDEX+"="+index);
        if (str!=null||!"".equals(str)){
            AxJson(str,list,GETDATA);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    myProblemListenser.getMyProblemDataSuccess();
                }
            });

        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    myProblemListenser.getMyProblemDataFall();
                }
            });

        }
    }
    @Override
    public void getRefreshMyProblemData(int page,int index,  ArrayList<MyProblemBean> list) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        String str = singleHttpClick.getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.PROBLEMLIST+MYURL.PAGE+
                "="+page+"&"+MYURL.INDEX+"="+index);
        if (str!=null||!"".equals(str)){
            AxJson(str,list,REFRESHDATA);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    myProblemListenser.getRefreshMyProblemSuccess();
                }
            });

        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    myProblemListenser.getRefreshMyProblemFall();
                }
            });

        }

    }

    private void AxJson(String str, ArrayList<MyProblemBean> list, int is) {
        if(is==REFRESHDATA){
            list.clear();
        }
        try {
            JSONObject jsonObject = new JSONObject(str);
            String data = jsonObject.getString(MYURL.DATA);
            JSONArray jsonArray = new JSONArray(data);
            for(int i=0,n=jsonArray.length();i<n;++i){
                String s = jsonArray.getString(i);
                JSONObject object = new JSONObject(s);
                int pid = object.getInt(MYURL.PID);
                String title = object.getString(MYURL.TITLE);
                title = title.trim();
                int ac = object.getInt(MYURL.AC);
                int submit = object.getInt(MYURL.SUBMIT);
                int status = object.getInt(MYURL.STATUS);
                //Log.d("problem",s);
                MyProblemBean myProblemBean = new MyProblemBean(pid,ac,title,submit,status);
                list.add(myProblemBean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public void destory() {
        myProblemListenser = null;
        handler = null;
    }
}
