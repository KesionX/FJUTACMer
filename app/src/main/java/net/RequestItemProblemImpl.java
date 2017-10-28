package net;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestItemProblem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bean.ItemProblem;
import persenter.netRequestListener.ItemProblemListenser;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/10/3.
 */
public class RequestItemProblemImpl implements RequestItemProblem {
    private ItemProblemListenser itemProblemListenser;
    private Handler handler;
    public RequestItemProblemImpl(ItemProblemListenser itemProblemListenser, Handler handler){
        this.itemProblemListenser = itemProblemListenser;
        this.handler = handler;
    }

    @Override
    public void getProblemData(int ojpid) {
        SingleHttpClick singleHttpClick=  SingleHttpClick.getSingleHttpClick();
        String str = singleHttpClick.getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.PROBLEM+MYURL.PID
        +"="+ojpid);
        Log.d("main","str"+str);
        if(str!=null||!"".equals(str)){
            AxJson(str);
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    itemProblemListenser.getProblemDataFall();
                }
            });

        }
    }



        private void AxJson(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            String ret = jsonObject.getString(MYURL.RET);
            if(MYURL.SUCCESS.equals(ret)){
                 int pid = jsonObject.getInt(MYURL.PID);
                 String title = jsonObject.getString(MYURL.TITLE);
                 int type = jsonObject.getInt(MYURL.TYPE);
                 String  oj = jsonObject.getString(MYURL.OJ);
                 String ojid = jsonObject.getString(MYURL.OJPID);
                 String Int64 = jsonObject.getString(MYURL.INT64);
                 String timelimit = jsonObject.getString(MYURL.TIMELIMIT);
                 String memoryLimit = jsonObject.getString(MYURL.MEMORYLIMIT);
                 String des = jsonObject.getString(MYURL.DES);
                 String input = jsonObject.getString(MYURL.INPUT);
                 String output = jsonObject.getString(MYURL.OUTPUT);
                 String s1  = jsonObject.getString(MYURL.SAMPLEINPUT);
                 String s2 = jsonObject.getString(MYURL.SAMPLEOUTPUT);
                JSONArray jsonArray1 = new JSONArray(s1);
                JSONArray jsonArray2 = new JSONArray(s2);
                ArrayList<String > sampleinput = new ArrayList<>();
                ArrayList<String > sampleoutput = new ArrayList<>();
                for(int i=0,n = jsonArray1.length();i<n;++i){
                    sampleinput.add(jsonArray1.getString(i));
                }

                for (int i=0,n = jsonArray2.length();i<n;++i){
                    sampleoutput.add(jsonArray2.getString(i));
                }
                final ItemProblem itemProblem = new ItemProblem(pid,title,oj,type,ojid,Int64,timelimit,
                        memoryLimit,des,input,output,sampleinput,sampleoutput);
                Log.d("main",itemProblem.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        itemProblemListenser.getProblemDataSuccess(itemProblem);
                    }
                });


            }else{
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        itemProblemListenser.getProblemDataFall();
                    }
                });

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void subimitProblemCode(int pidnum, String code, int lang, int cid) {
  /*      String str = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.SB+
                MYURL.CODE+"="+code+"&"+MYURL.PID+"="+pidnum+"&"+MYURL.LANGUAGE+"="+lang+"&"+MYURL.CID+"="+cid+
                "&"+MYURL.NOREDIRECT+"="+1);*/
        Map<String,String > stringStringMap = new HashMap<>();

        stringStringMap.put(MYURL.CODE,code);
        stringStringMap.put(MYURL.PID,pidnum+"");
        stringStringMap.put(MYURL.LANGUAGE,lang+"");
        stringStringMap.put(MYURL.CID,cid+"");
        stringStringMap.put(MYURL.NOREDIRECT,1+"");
        Log.d("miankk",code+pidnum+" "+lang+""+cid);
        String str = SingleHttpClick.getSingleHttpClick().doPost(MYURL.OLD_ROOT_URL+MYURL.SB,stringStringMap,"utf-8");
        Log.d("mainkk",str);
        if(!"".equals(str)||str!=null){
            AxSubmitJson(str);
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    itemProblemListenser.submitCodeFall("链接错误");
                }
            });
        }

    }

    private void AxSubmitJson(String str) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            String ret = jsonObject.getString(MYURL.RET);
            if(MYURL.SUCCESS.equals(ret)){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        itemProblemListenser.submitCodeSucccess();
                    }
                });

            }else {
                final String info = jsonObject.getString(MYURL.INFO);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        itemProblemListenser.submitCodeFall(info);
                    }
                });
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destory() {
        itemProblemListenser = null;
        handler = null;
    }
}
