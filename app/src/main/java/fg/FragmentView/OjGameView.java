package fg.FragmentView;

/**
 * Created by Administrator on 2016/9/28.
 */
public interface OjGameView {

    public void ojGameRefreshSuccess();//下拉刷新成功
    public void ojGameRefreshFall();//下拉刷新失败
    public void ojGameListSuccess();//比赛list加载成功
    public void ojGameListFall();//比赛list加载失败
}
