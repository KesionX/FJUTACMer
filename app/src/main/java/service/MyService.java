package service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2017/9/29.
 */
public class MyService extends Service implements SocketClient.SocketStringMessageListener{
    SocketClient socketClient =null;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG_KE_MESSAGE","inter");
        new Thread(){
            @Override
            public void run() {
                socketClient = new SocketClient("10.0.2.2",2333);
                socketClient.connection();
                socketClient.setOnSocketStringMessageListener(MyService.this);
            }
        }.start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);



    }

    @Override
    public void receiveStringMessage(String message) {
        Log.d("TAG_KE_MESSAGE",message);
    }
}
