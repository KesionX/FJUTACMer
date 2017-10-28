package net;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestChallenge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapter.MyChallengeListAdapter;
import bean.Block;
import bean.BlockCondition;
import bean.BlockDec;
import bean.ChallengeProblem;
import bean.ChallengeVector;
import persenter.netRequestListener.ChallengeListener;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/10/27.
 */
public class RequestChallengeImpl implements RequestChallenge{

    private ChallengeListener challengeListener;
    private Handler handler;

    public RequestChallengeImpl(ChallengeListener challengeListener, Handler handler){
        this.challengeListener = challengeListener;
        this.handler = handler;

    }

    @Override
    public void getChallengeData(ChallengeVector challengeVector, String user) {
  /*      Log.d("uiii",MYURL.SYIML_URL+
                MYURL.CHALLENGE_BLOCK+MYURL.USER+"="+user);*/
        String s = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+
                MYURL.CHALLENGE_BLOCK_LIST+MYURL.USER+"="+user);
        if(!"".equals(s)&&s!=null){
            Axjson(challengeVector,s);

        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    challengeListener.getChallengeDataFall();
                }
            });
        }


    }

    private void Axjson(ChallengeVector challengeVector, String s) {
        try {
            JSONObject b = new JSONObject(s);
            String blist = b.getString(MYURL.BLOCKLIST);
            JSONArray arry = new JSONArray(blist);
            for(int i=0,n=arry.length();i < n;++i){
                String obj  = arry.getString(i);
                JSONObject o = new JSONObject(obj);
                int id  = o.getInt(MYURL.ID);
                String name = o.getString(MYURL.NAME);
                int group = o.getInt(MYURL.GROUP);
                int score = o.getInt(MYURL.SCORE);
                int isOpen = o.getInt(MYURL.IS_OPEN);
                int userScore = o.getInt(MYURL.USER_SCORE);
                if(isOpen==0)
                    continue;
                Block block = new Block(id,group,score,isOpen,userScore,name, MyChallengeListAdapter.TYPE_ROOT);
                challengeVector.getTotalList().add(block);
            //    Log.d("kkkk",name);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    challengeListener.getChallengeDataSuccess();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void freshChallengeData(ChallengeVector challengeVector, String user) {
        String s = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+
                MYURL.CHALLENGE_BLOCK_LIST+MYURL.USER+"="+user);
        if(!"".equals(s)&&s!=null){
            Axjson(challengeVector,s);

        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    challengeListener.getChallengeDataFall();
                }
            });
        }
    }

    @Override
    public void getBlockData(ChallengeVector challengeVector, int id, String user, int position) {
        String s = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.CHALLENGE_BLOCK+
                MYURL.ID+"="+id+"&"+MYURL.USER+"="+user);
        if(!"".equals(s)&&s!=null){
            AxBlockJson(challengeVector,s,position);
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    challengeListener.getBlockDataFall();
                }
            });
        }

    }

    @Override
    public void getCondition(int id) {
        String s = SingleHttpClick.getSingleHttpClick().getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.BLOCK_CONDITION+MYURL.ID+"="+id);
        if(!"".equals(s)&&s!=null){
            AxConditionJson(s);
        }else{
            handler.post(new Runnable() {
                @Override
                public void run() {
                    challengeListener.getConditionFall();
                }
            });
        }
    }

    private void AxConditionJson(String s) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(s);
            final ArrayList<BlockCondition> blockConditions = new ArrayList<>();
            for(int i=0,n = jsonArray.length();i < n;++i){
                String str = jsonArray.getString(i);
                JSONObject b = new JSONObject(str);
                int type = b.getInt(MYURL.TYPE);
                int par = b.getInt(MYURL.PAR);
                String blockName = b.getString(MYURL.BLOCK_NAME);
                int num = b.getInt(MYURL.NUM);
                blockConditions.add(new BlockCondition(type,par,blockName,num));
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    challengeListener.getConditionSuccess(blockConditions);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void AxBlockJson(ChallengeVector challengeVector, String s, final int position) {
        try {
            JSONObject o = new JSONObject(s);
            int id  = o.getInt(MYURL.ID);
            int rootParent = id;
            String name = o.getString(MYURL.NAME);
            int group = o.getInt(MYURL.GROUP);
            String text = o.getString(MYURL.TEXT);
            int userScore = o.getInt(MYURL.USER_SCORE);
            int score = o.getInt(MYURL.SCORE);
            String conditions = o.getString(MYURL.CONDITIONS);
            JSONArray jsonArray = new JSONArray(conditions);
            ArrayList<BlockCondition> blockConditions = new ArrayList<>();
            for(int i=0,n = jsonArray.length();i < n;++i){
                String str = jsonArray.getString(i);
                JSONObject b = new JSONObject(str);
                int type = b.getInt(MYURL.TYPE);
                int par = b.getInt(MYURL.PAR);
                String blockName = b.getString(MYURL.BLOCK_NAME);
                int num = b.getInt(MYURL.NUM);
                blockConditions.add(new BlockCondition(type,par,blockName,num));
            }
            BlockDec blockDec = new BlockDec(rootParent,id,group,name,score,text,userScore,blockConditions,MyChallengeListAdapter.TYPE_DEC);

            challengeVector.getTotalList().add(blockDec);

            String problemList = o.getString(MYURL.CHALLENGE_PROBLEMLIST);
            JSONArray arr = new JSONArray(problemList);
            for (int i=0,n = arr.length();i < n;++i){
                String str = arr.getString(i);
                JSONObject obj = new JSONObject(str);
                int solved = obj.getInt(MYURL.SOLVED);
                int pid = obj.getInt(MYURL.PID);
                int tpid = obj.getInt(MYURL.TPID);
                String title = obj.getString(MYURL.TITLE);
                int fscore = obj.getInt(MYURL.SCORE);
                ChallengeProblem challengeProblem = new ChallengeProblem(rootParent,solved,pid,tpid,title,fscore,MyChallengeListAdapter.TYPE_PRO);

                challengeVector.add(challengeProblem);
            }

            handler.post(new Runnable() {
                @Override
                public void run() {
                    challengeListener.getBlockDataSuccess(position);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Destory() {
        challengeListener = null;
        handler = null;
    }
}
