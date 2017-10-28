package persenter;

import android.os.Handler;

import net.NetInterface.RequestMyProblem;
import net.getMyProblemImpl;

import java.util.ArrayList;

import bean.MyProblemBean;
import fg.FragmentView.MyProblemView;
import persenter.netRequestListener.MyProblemListenser;
import persenter.persenterInterface.MyProblemPersenter;

/**
 * Created by Administrator on 2016/10/2.
 */
public class MyProblemPersenterImpl implements MyProblemPersenter,MyProblemListenser{
    private MyProblemView myProblemView;
    private Handler handler;
    private RequestMyProblem requestMyProblem;
    private ArrayList<MyProblemBean> list;
    private int page;
    private int index;
    public MyProblemPersenterImpl(MyProblemView myProblemView, Handler handler){
        this.myProblemView = myProblemView;
        this.handler = handler;
        this.requestMyProblem = new getMyProblemImpl(this,handler);
        this.list = new ArrayList<>();
        this.page = myProblemView.getPage();
        this.index = 1;
    }
    @Override
    public void getMyProblemMoreData() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                requestMyProblem.getMyProblemData(page,index,list);
                index++;
            }
        };
       thread.start();
    }

    @Override
    public void getRefreshProblemData() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                index=1;
                requestMyProblem.getRefreshMyProblemData(page,index,list);
                index++;
            }
        };
       thread.start();
    }

    @Override
    public ArrayList<MyProblemBean> getMyProblemList() {
        return list;
    }

    @Override
    public void destory() {
        requestMyProblem.destory();
        myProblemView = null;
        handler = null;
        list = null;
        requestMyProblem = null;

    }

    @Override
    public void getMyProblemDataSuccess() {
        myProblemView.getMyProblemMoreSuccess();
    }

    @Override
    public void getMyProblemDataFall() {
        myProblemView.getMyProblemMoreFall();
    }

    @Override
    public void getRefreshMyProblemSuccess() {
        myProblemView.getRefreshMyProblemSuccess();
    }

    @Override
    public void getRefreshMyProblemFall() {
        myProblemView.getRefreshMyProblemFall();
    }
}
