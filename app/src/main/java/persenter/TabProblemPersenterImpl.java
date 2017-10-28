package persenter;

import android.os.Handler;

import net.NetInterface.RequestTabProblem;
import net.getTabProblemImpl;

import java.util.ArrayList;

import fg.FragmentView.TabProblemView;
import fg.MyProblemFragment;
import persenter.netRequestListener.TabProblemVpListenser;
import persenter.persenterInterface.TabProblemPersenter;

/**
 * Created by Administrator on 2016/10/1.
 */
public class TabProblemPersenterImpl implements TabProblemVpListenser, TabProblemPersenter {

    private ArrayList<String> list;
    private TabProblemView tabProblemView;
    private RequestTabProblem requestTabProblem;
    private Handler handler;
    private ArrayList<MyProblemFragment> vplist;

    public TabProblemPersenterImpl(TabProblemView tabProblemView, Handler handler) {
        this.tabProblemView = tabProblemView;
        this.list = new ArrayList<>();
        this.handler = handler;
        this.requestTabProblem = new getTabProblemImpl(this, handler);
        this.vplist = new ArrayList<MyProblemFragment>();
    }

    @Override
    public void getViewPageData() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                requestTabProblem.getViewPageData(list,vplist);
            }
        };
        thread.start();

    }

    @Override
    public ArrayList<String> getStringList() {
        return list;
    }

    @Override
    public ArrayList<MyProblemFragment> getMyProblemFragmentVp() {
        return vplist;
    }

    @Override
    public void destory() {
        requestTabProblem.destory();
        requestTabProblem = null;
        list = null;
        handler = null;
    }

    @Override
    public void getViewPageDataSuccess() {
        tabProblemView.getViewPageSuccess();
    }

    @Override
    public void getViewPageDataFall() {
        tabProblemView.getViewPageFall();
    }


}
