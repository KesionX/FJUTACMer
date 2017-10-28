package persenter.persenterInterface;

import java.util.ArrayList;

import bean.MyProblemBean;

/**
 * Created by Administrator on 2016/10/2.
 */
public interface MyProblemPersenter {

    public void getMyProblemMoreData();
    public void getRefreshProblemData();
    public ArrayList<MyProblemBean> getMyProblemList();
    public void destory();
}
