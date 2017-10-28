package persenter;

import android.content.Context;
import android.os.Handler;

import net.NetInterface.getMegSelf;
import net.getMegSelfImpl;

import bean.SingleMegSelf;
import persenter.netRequestListener.getMegSelfListener;
import persenter.persenterInterface.hostGetMegSelf;
import www.fjutoj.com.fjutacmer.view.HostView;

/**
 * Created by Administrator on 2016/9/26.
 */
public class hostGetMegSelfImlp implements hostGetMegSelf ,getMegSelfListener {

    getMegSelf megSelf;
    HostView hostView;

    private Handler handler;
    public hostGetMegSelfImlp(HostView hostView, Handler handler){
        this.megSelf = new getMegSelfImpl(this,handler);
        this.hostView = hostView;
        this.handler = handler;
    }

    @Override
    public void GetMegSelf() {
        Thread thread  = new Thread(){

            @Override
            public void run() {
               megSelf.getmegSelf();
            }
        };
        thread.start();
    }

    @Override
    public void destory() {
        megSelf.destory();
        megSelf = null;
        hostView = null;
        handler = null;
    }

    @Override
    public void getMegSelfSuccess() {
        hostView.getMegSelfSuccess();
    }

    @Override
    public void getMegSelfFall() {
        hostView.getMegSelfFall();
    }
}
