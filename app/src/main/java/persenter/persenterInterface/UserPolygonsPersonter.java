package persenter.persenterInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/19.
 */
public interface UserPolygonsPersonter {

    public void getUserPolygonsData(String user);
    public ArrayList<Double> getPolygonsList();
    public void destory();
}
