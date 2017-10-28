package persenter;

import android.os.Handler;

import net.NetInterface.RequestUserPolygons;
import net.RequestUserPolygonsImpl;

import java.util.ArrayList;

import persenter.netRequestListener.UserPolygonsListener;
import persenter.persenterInterface.UserPolygonsPersonter;
import www.fjutoj.com.fjutacmer.view.UserPolygonsView;

/**
 * Created by Administrator on 2017/3/19.
 */
public class UserPolysonsPersonterImpl implements UserPolygonsListener,UserPolygonsPersonter {

    private UserPolygonsView mUserPolygonsView;
    private RequestUserPolygonsImpl mRequestUserPolygons;
    private ArrayList<Double> list;

    public UserPolysonsPersonterImpl(UserPolygonsView v, Handler handler){
        mUserPolygonsView = v;
        this.mRequestUserPolygons  = new RequestUserPolygonsImpl(this,handler);
        list = new ArrayList<>();
    }


    @Override
    public void getUserPolygonsData(final String user) {

        Thread t = new Thread(){

            @Override
            public void run() {
                mRequestUserPolygons.getUserPolygons(user,list);
            }
        };
        t.start();

    }

    @Override
    public ArrayList<Double> getPolygonsList() {
        return list;
    }

    @Override
    public void destory() {
        mRequestUserPolygons.destory();
        mRequestUserPolygons = null;
        mUserPolygonsView = null;
        list = null;
    }
    @Override
    public void getPolygonsDataSuccess() {
        mUserPolygonsView.getPolygonsDataSuccess();
    }

    @Override
    public void getPolygonsDataFall() {
        mUserPolygonsView.getPolygonsDataFall();
    }


}
