package persenter;

import android.os.Handler;

import net.NetInterface.RequestMessageLine;
import net.RequestMessageLineImpl;

import java.util.ArrayList;


import persenter.netRequestListener.MessageLineListenser;
import persenter.persenterInterface.MessageLinePersonter;
import ui.LineChartView;
import www.fjutoj.com.fjutacmer.view.MessageLineView;

/**
 * Created by Administrator on 2017/3/18.
 */
public class MessageLinePersonterImpl implements MessageLineListenser,MessageLinePersonter{

    private MessageLineView messageLineView;
    private ArrayList<ui.LineChartView.RatingPoint> list;
    private RequestMessageLine mRequestMessageLine;

    public MessageLinePersonterImpl(MessageLineView messageLineView, Handler handler){
        this.list = new ArrayList<>();
        this.messageLineView = messageLineView;
        this.mRequestMessageLine = new RequestMessageLineImpl(this,handler);

    }


    @Override
    public void getLineData(final String userName) {
        Thread t = new Thread(){
            @Override
            public void run() {
                mRequestMessageLine.requestMessageLine(userName,list);
            }
        };
        t.start();
    }

    @Override
    public void destory() {
        mRequestMessageLine.destory();
        messageLineView = null;
        list = null;
        mRequestMessageLine = null;
    }

    @Override
    public ArrayList<LineChartView.RatingPoint> getRatingPointList() {
        return this.list;
    }
    @Override
    public void getLineDataSuccess() {
        messageLineView.getLineDataSuccess();
    }

    @Override
    public void getLineDataFall() {
        messageLineView.getLineDataFall();
    }

}
