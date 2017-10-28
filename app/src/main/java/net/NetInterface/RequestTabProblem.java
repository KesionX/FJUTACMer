package net.NetInterface;

import java.util.ArrayList;

import fg.MyProblemFragment;

/**
 * Created by Administrator on 2016/10/1.
 */
public interface RequestTabProblem {

    public void getViewPageData(ArrayList<String> list, ArrayList<MyProblemFragment> vplist);

    public void destory();
}
