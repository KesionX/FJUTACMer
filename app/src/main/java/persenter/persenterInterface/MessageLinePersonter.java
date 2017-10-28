package persenter.persenterInterface;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/18.
 */
public interface MessageLinePersonter {

    public void  getLineData(String userName);
    public void destory();
    public ArrayList<ui.LineChartView.RatingPoint> getRatingPointList();
}
