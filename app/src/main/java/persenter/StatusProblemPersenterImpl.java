package persenter;

import android.os.Handler;

import net.NetInterface.RequestStatusProblem;
import net.RequestStatusProblemImpl;

import java.util.ArrayList;

import bean.StatusProblem;
import fg.FragmentView.StatusProblemView;
import persenter.netRequestListener.StatusProblemListener;
import persenter.persenterInterface.StatusProblemPersenter;

/**
 * Created by Administrator on 2016/10/4.
 */
public class StatusProblemPersenterImpl implements StatusProblemListener,StatusProblemPersenter{

    private StatusProblemView statusProblemView;
    private RequestStatusProblem requestStatusProblem;
    private Handler handler;
    private ArrayList<StatusProblem> list;
    private int page=1;
    public StatusProblemPersenterImpl(StatusProblemView statusProblemView,Handler handler){
        this.statusProblemView = statusProblemView;
        this.handler = handler;
        this.requestStatusProblem = new RequestStatusProblemImpl(this,handler);
        this.list = new ArrayList<>();
        this.page=1;
    }
    @Override
    public void getStatusDataMore() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                requestStatusProblem.getStatusDataMore(page,list);
                page++;
            }
        };
        thread.start();
    }

    @Override
    public void getStatusDataRefresh() {
        list.clear();
        Thread thread = new Thread(){
            @Override
            public void run() {
                page=1;
                requestStatusProblem.getStatusDataRefresh(page,list);
                page++;
            }
        };
        thread.start();
    }

    @Override
    public ArrayList<StatusProblem> getStatusDataList() {
        return list;
    }

    @Override
    public ArrayList<StatusProblem> getCloneDataList() {
        ArrayList<StatusProblem> list =  new ArrayList<>();

        for (StatusProblem sp : this.list) {
            list.add(sp);
        }

        return list;
    }

    @Override
    public void destory() {
        requestStatusProblem.destory();
        list = null;
        statusProblemView = null;
        requestStatusProblem = null;
        handler = null;
    }

    @Override
    public void getStatusDataMoreSuccess() {
        statusProblemView.getStatusMoreSuccess();
    }

    @Override
    public void getStatusDataMoreFall() {
        statusProblemView.getStatusMoreFall();
    }

    @Override
    public void getStatusDataRefrshSuccess() {
        statusProblemView.getStatusRefreshSuccess();
    }

    @Override
    public void getStatusDataRefreshFall() {
        statusProblemView.getStatusRefreshFall();
    }


}
