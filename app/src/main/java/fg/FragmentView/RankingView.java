package fg.FragmentView;

/**
 * Created by Administrator on 2017/2/23.
 */
public interface RankingView {
    /**
     * 获取排名数据成功
     */
    public void getRankingDataMoreSuccess();

    /**
     * 获取排名数据失败
     */
    public void getRankingDataMoreFall();

    /**
     * 刷新排名数据成功
     */
    public void freshRankingDataSuccess();

    /**
     * 刷新排名数据失败
     */
    public void freshRankingDataFall();
}
