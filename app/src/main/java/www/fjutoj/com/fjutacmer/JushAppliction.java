package www.fjutoj.com.fjutacmer;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/10/9.
 */
public class JushAppliction  extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
