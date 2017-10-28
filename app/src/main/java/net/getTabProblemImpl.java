package net;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestTabProblem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import fg.MyProblemFragment;
import persenter.netRequestListener.TabProblemVpListenser;
import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/10/1.
 */
public class getTabProblemImpl implements RequestTabProblem{

    private TabProblemVpListenser tabProblemVpListenser;

    private Handler handler;
    public getTabProblemImpl(TabProblemVpListenser tabProblemVpListenser, Handler handler){
        this.tabProblemVpListenser =tabProblemVpListenser;
        this.handler = handler;
    }

    @Override
    public void getViewPageData(ArrayList<String> list, ArrayList<MyProblemFragment> vplist) {
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        String str = singleHttpClick.getHttpClickMeg(MYURL.OLD_ROOT_URL+MYURL.PROBLEMLIST+MYURL.PAGE+
                "="+1+"&"+MYURL.INDEX+"="+6);
        Log.d("main",str);

        if(str!=null||"".equals(str)){
            AxJson(str,list,vplist);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tabProblemVpListenser.getViewPageDataSuccess();
                }
            });
        }else {

            tabProblemVpListenser.getViewPageDataFall();
        }


    }

    private void AxJson(String str, ArrayList<String> list, ArrayList<MyProblemFragment> vplist) {
        try {
            JSONObject jsonObject = new JSONObject(str);
            int n = jsonObject.getInt(MYURL.TOTALPAGE);
            for(int i=1;i<=n;++i){
                list.add("第"+i+"页");
                vplist.add(new MyProblemFragment(i));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void destory() {
        tabProblemVpListenser = null;
        handler = null;
    }
}
