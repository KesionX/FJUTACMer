package net.NetInterface;

import java.util.ArrayList;

import ui.LineChartView;

/**
 * Created by Administrator on 2017/3/18.
 */
public interface RequestMessageLine {

    public void requestMessageLine(String userName, ArrayList<LineChartView.RatingPoint> list);

    public void destory();
}
