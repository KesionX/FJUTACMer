package net.NetInterface;

import java.util.ArrayList;

import bean.StatusProblem;

/**
 * Created by Administrator on 2016/10/4.
 */
public interface RequestStatusProblem {

    public void getStatusDataMore(int page, ArrayList<StatusProblem> list);
    public void getStatusDataRefresh(int page, ArrayList<StatusProblem> list);
    public void destory();

}
