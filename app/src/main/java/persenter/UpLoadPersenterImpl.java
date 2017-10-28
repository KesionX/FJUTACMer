package persenter;

import android.os.Handler;

import net.NetInterface.RequestUpLoad;
import net.RequestUpLoadImpl;

import fg.FragmentView.UpLoadView;
import persenter.netRequestListener.UpLoadListener;
import persenter.persenterInterface.UploadPersenter;

/**
 * Created by Administrator on 2016/10/10.
 */
public class UpLoadPersenterImpl implements UploadPersenter,UpLoadListener{

    private UpLoadView upLoadView;
    private Handler handler;
    private RequestUpLoad requestUpLoad;

    public UpLoadPersenterImpl(UpLoadView upLoadView,Handler handler){
        this.upLoadView = upLoadView;
        this.handler = handler;
        this.requestUpLoad = new RequestUpLoadImpl(this,handler);
    }

    @Override
    public void getUpLoad() {
        Thread thread = new Thread(){
            @Override
            public void run() {
                requestUpLoad.requestUpLoad();
            }
        };
        thread.start();
    }

    @Override
    public void destory() {
        upLoadView = null;
        handler = null;
        requestUpLoad = null;
    }

    @Override
    public void NeedUpLoad(int versionCode, String versionName, String versioninfo, String apkUrl) {
        upLoadView.Upload(versionCode,versionName,versioninfo,apkUrl);
    }
}
