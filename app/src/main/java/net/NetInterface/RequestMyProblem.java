package net.NetInterface;

import java.util.ArrayList;

import bean.MyProblemBean;

/**
 * Created by Administrator on 2016/10/2.
 */
public interface RequestMyProblem {

    public void getMyProblemData(int page, int index, ArrayList<MyProblemBean> list);
    public void getRefreshMyProblemData( int page,int index, ArrayList<MyProblemBean> list);
    public void destory();
}
