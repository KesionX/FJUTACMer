package persenter.persenterInterface;

import java.util.ArrayList;

import fg.MyProblemFragment;

/**
 * Created by Administrator on 2016/10/1.
 */
public interface TabProblemPersenter {
    public void getViewPageData();
    public ArrayList<String> getStringList();
    public ArrayList<MyProblemFragment> getMyProblemFragmentVp();

    public void destory();
}
