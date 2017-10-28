package net.NetInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/19.
 */
public interface RequestUserPolygons {

    public void getUserPolygons(String user, ArrayList<Double> list);
    public void destory();
}
