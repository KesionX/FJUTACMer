
package persenter.persenterInterface;

import java.util.ArrayList;

import bean.RankingAcmer;

/**
 * Created by Administrator on 2017/2/23.
 */
public interface RankingPersenter {

    /**
     * 获取排名数据
     * @param order
     */
    public void getRankingData(String order);

    /**
     * 加载更多排名数据
     */
    public void getMoreRankingData();


    public ArrayList<RankingAcmer> getArrayListRankingAcmer();

    public ArrayList getCloneList();
    /**
     * 刷新排名数据
     */
    public void freshRankingData();


    /**
     * 释放内存
     */
    public void destoty();

}
