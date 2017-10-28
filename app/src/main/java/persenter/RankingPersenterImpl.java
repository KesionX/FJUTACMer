package persenter;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestRanking;
import net.RequestRankingImpl;

import java.util.ArrayList;

import bean.RankingAcmer;
import fg.FragmentView.RankingView;
import persenter.netRequestListener.RankingListener;
import persenter.persenterInterface.RankingPersenter;
import url.MYURL;

/**
 * Created by Administrator on 2017/2/23.
 */
public class RankingPersenterImpl implements RankingPersenter ,RankingListener{

    private String order;
    private int page;
    private RankingView rankingView;
    private RequestRanking requestRanking;

    private ArrayList<RankingAcmer> list;

    public RankingPersenterImpl(RankingView rankingView,Handler handler){

        this.rankingView = rankingView;
        this.requestRanking = new RequestRankingImpl(this,handler);
        this.page = 1 ;
        this.order = MYURL.RANK;
        this.list = new ArrayList<>();
    }


    @Override
    public void getRankingData(final String order) {
        Log.d("ddd","sss");
        page = 1;
        this.order = order;
        list.clear();
        Thread t = new Thread(){

            @Override
            public void run() {
                requestRanking.getRankingDataMore(page,getOrder(),list);
            }
        };
        t.start();
    }


    private String getOrder(){

        return this.order;
    }

    @Override
    public void getMoreRankingData() {
        page ++;
        Thread t = new Thread(){

            @Override
            public void run() {
                requestRanking.getRankingDataMore(page,getOrder(),list);
            }
        };
        t.start();
    }

    @Override
    public ArrayList<RankingAcmer> getArrayListRankingAcmer() {
        return this.list;
    }

    @Override
    public void freshRankingData() {
        page = 1;
        list.clear();
        Thread t = new Thread(){

            @Override
            public void run() {
                requestRanking.freshRankingData(page,getOrder(),list);
            }
        };
        t.start();
    }

    @Override
    public void destoty() {
        requestRanking.destory();
        requestRanking = null;
        list = null;
        rankingView = null;
    }

    @Override
    public ArrayList getCloneList(){
        ArrayList<RankingAcmer> list = new ArrayList<>();

        for (RankingAcmer ra: this.list) {
            list.add(ra);
        }

        return  list;
    }

    @Override
    public void getRankingDataMoreSuccess() {
        rankingView.getRankingDataMoreSuccess();
    }

    @Override
    public void getRankingDataMoreFall() {
        rankingView.getRankingDataMoreFall();
    }

    @Override
    public void getRankingDataRefrshSuccess() {
        rankingView.freshRankingDataSuccess();
    }

    @Override
    public void getRankingDataRefreshFall() {
        rankingView.freshRankingDataFall();
    }
}
