package persenter;

import android.os.Handler;

import net.GetOjGameImpl;
import net.NetInterface.requestOjGame;

import java.util.ArrayList;

import bean.recentlyGame;
import fg.FragmentView.OjGameView;
import persenter.netRequestListener.OjGameListListenser;
import persenter.persenterInterface.OjGamePersenter;

/**
 * Created by Administrator on 2016/9/28.
 */
public class OjGameImpl implements OjGamePersenter,OjGameListListenser{

    private ArrayList<recentlyGame> list;
    private OjGameView ojGameView;
    private requestOjGame ojGame;

    public OjGameImpl(OjGameView ojGameView, Handler handler) {
        this.ojGameView = ojGameView;
        list = new ArrayList<recentlyGame>();
        ojGame = new GetOjGameImpl(handler,this,list);

    }

    @Override
    public void getOjGameListSuccess() {
        ojGameView.ojGameListSuccess();
    }

    @Override
    public void getOjGameListFall() {
        ojGameView.ojGameListFall();
    }

    @Override
    public void refreshOjGameSuccess() {
        ojGameView.ojGameRefreshSuccess();
    }

    @Override
    public void refreshOjGameFall() {
        ojGameView.ojGameRefreshFall();
    }

    @Override
    public void requestOjGameListData() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                ojGame.requestOjGameList();
            }
        };
        thread.start();

    }

    @Override
    public void requestOjGameListRefreshData() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                ojGame.requestOjGameRefresh();
            }
        };
        thread.start();
    }

    @Override
    public ArrayList<recentlyGame> getOjGameList() {
        return list;
    }

    @Override
    public void destory() {
        ojGame.destory();
        list = null;
        ojGameView = null;
        ojGame = null;
    }
}
