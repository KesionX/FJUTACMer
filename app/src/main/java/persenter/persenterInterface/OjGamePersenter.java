package persenter.persenterInterface;

import java.util.ArrayList;

import bean.recentlyGame;

/**
 * Created by Administrator on 2016/9/28.
 */
public interface OjGamePersenter {
    public void requestOjGameListData();//请求最近比赛list数据
    public void requestOjGameListRefreshData();//请求刷新list数据
    public ArrayList<recentlyGame> getOjGameList();
    public void destory();

}
