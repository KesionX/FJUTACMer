package net;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestStatusProblem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import bean.StatusProblem;
import persenter.netRequestListener.StatusProblemListener;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/10/4.
 */
public class RequestStatusProblemImpl implements RequestStatusProblem {

    private StatusProblemListener statusProblemListener;
    private Handler handler;

    public RequestStatusProblemImpl(StatusProblemListener statusProblemListener, Handler handler) {
        this.statusProblemListener = statusProblemListener;
        this.handler = handler;
    }


    @Override
    public void getStatusDataMore(int page, ArrayList<StatusProblem> list) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        String str = singleHttpClick.getHttpClickMeg(MYURL.OLD_ROOT_URL + MYURL.PROBLEM_STAUS + MYURL.PAGE + "=" + page);
       // Log.d("mainkk","kk"+str);
        if (!"".equals(str) || str != null) {
            AxJson(str, list);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    statusProblemListener.getStatusDataMoreSuccess();
                }
            });

        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    statusProblemListener.getStatusDataMoreFall();
                }
            });
        }

    }


    @Override
    public void getStatusDataRefresh(int page, ArrayList<StatusProblem> list) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        String str = singleHttpClick.getHttpClickMeg(MYURL.OLD_ROOT_URL + MYURL.PROBLEM_STAUS + MYURL.PAGE + "=" + page);
        if (!"".equals(str) || str != null) {
            AxJson(str, list);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    statusProblemListener.getStatusDataRefrshSuccess();
                }
            });
        } else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    statusProblemListener.getStatusDataRefreshFall();
                }
            });
        }
    }

    @Override
    public void destory() {
        statusProblemListener = null;
        handler = null;
    }

    private void AxJson(String str, ArrayList<StatusProblem> list) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            String data = jsonObject.getString(MYURL.DATA);
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0, n = jsonArray.length(); i < n; ++i) {
                JSONObject obj = new JSONObject(jsonArray.getString(i));
                int rid = obj.getInt(MYURL.RID);
                int pid = obj.getInt(MYURL.PID);
                String username = obj.getString(MYURL.USERNAME);
                int cid = obj.getInt(MYURL.CID);
                int lang = obj.getInt(MYURL.LANG);
                String submitTime = obj.getString(MYURL.SUBMIT_TIME);
                int result = obj.getInt(MYURL.RESULT);
                int score = obj.getInt(MYURL.SCORE);
                String timeUsed = obj.getString(MYURL.TIME_USED);
                String memoryUsed = obj.getString(MYURL.MEMORY_USED);
                String codeLength = obj.getString(MYURL.CODE_LENG);
                String nick = obj.getString(MYURL.NICK);
                StatusProblem statusProblem = new StatusProblem(rid, pid, username, cid, submitTime, lang, result, score, timeUsed, memoryUsed, codeLength, nick);
                list.add(statusProblem);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
