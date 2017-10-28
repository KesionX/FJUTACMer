package net.NetInterface;

import java.util.ArrayList;

import bean.RankingAcmer;

/**
 * Created by Administrator on 2017/2/23.
 */
public interface RequestRanking {

    /**
     * 网端获取更多数据
     * @param page
     * @param order
     * @param list
     */
    public void getRankingDataMore(int page, String order, ArrayList<RankingAcmer> list);


    /**
     * 网端刷新数据
     * @param page
     * @param order
     * @param list
     */
    public void freshRankingData(int page, String order, ArrayList<RankingAcmer> list);

    /**
     * 释放内存
     */
    public void destory();

}
