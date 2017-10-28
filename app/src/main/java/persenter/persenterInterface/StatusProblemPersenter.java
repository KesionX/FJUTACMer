package persenter.persenterInterface;

import java.util.ArrayList;

import bean.StatusProblem;

/**
 * Created by Administrator on 2016/10/4.
 */
public interface StatusProblemPersenter {

    public void getStatusDataMore();
    public void getStatusDataRefresh();
    public ArrayList<StatusProblem> getStatusDataList();
    public ArrayList<StatusProblem> getCloneDataList();
    public void destory();
}
