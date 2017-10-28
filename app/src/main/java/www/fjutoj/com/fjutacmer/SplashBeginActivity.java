package www.fjutoj.com.fjutacmer;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;

import persenter.loginPersenterimpl;
import persenter.persenterInterface.loginpersenter;
import service.MyService;
import tool.LogPreferences;
import tool.StatusColor;
import ui.MyToast;
import www.fjutoj.com.fjutacmer.view.LoginView;

public class SplashBeginActivity extends FragmentActivity implements LoginView{

    private static final int OK = 1;
    private static final int NO = 2 ;
    private boolean logOrHome;
    private loginpersenter mloginpersenter;
    private String account;
    private String ps;
    private SleepOKThread sleepOkThread;
    private SleepNOThread sleepNOThread;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==OK) {

                Intent intent = new Intent(SplashBeginActivity.this, TabHostActivity.class);
                startActivity(intent);
                finish();
            }
            else if(msg.what==NO){
                    Intent intent = new Intent(SplashBeginActivity.this,LoginActivity.class);
                    startActivity(intent);
                finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        StatusColor.setTranslucentStatus(this,R.color.colortransparent);
        setContentView(R.layout.activity_splash_begin);
        IntiData();
        panDunSharePre();
        login();

     //   startService(new Intent(this, MyService.class));

    }


    private void login() {
        if(logOrHome)
            mloginpersenter.Login(account,ps);
        else {
            sleepNOThread = new SleepNOThread();
            sleepNOThread.start();
          /*  Intent intent = new Intent(SplashBeginActivity.this,LoginActivity.class);
            startActivity(intent);*/
        }
    }

    private void panDunSharePre() {
        LogPreferences logPreferences = LogPreferences.getLogPreferences(this,LogPreferences.AP_PRE);
        logOrHome = logPreferences.getIsok();
        account = logPreferences.getAt();
        ps = logPreferences.getPs();
    }

    private void IntiData() {

        mloginpersenter = new loginPersenterimpl(this,this,handler);
    }



    @Override
    public void loginSuccess() {
        sleepOkThread = new SleepOKThread();
        sleepOkThread.start();
    }

    @Override
    public void WrongPassword() {
        MyToast.ShowToast(this,"密码错误", Toast.LENGTH_LONG);
        sleepNOThread = new SleepNOThread();
        sleepNOThread.start();

    }

    @Override
    public void NoSuchUser() {
        MyToast.ShowToast(this,"没有此账号",Toast.LENGTH_LONG);
        sleepNOThread = new SleepNOThread();
        sleepNOThread.start();

    }

    @Override
    public void SystemError() {
        MyToast.ShowToast(this,"系统错误，请检查网络",Toast.LENGTH_LONG);
        sleepNOThread = new SleepNOThread();
        sleepNOThread.start();

    }

    @Override
    public void accountFormatError() {

    }

    @Override
    public void passFormatError() {

    }

    class SleepOKThread extends Thread{

        @Override
        public void run() {
            try {
                sleep(3000);
                Message message = new Message();
                message.what=OK;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    class SleepNOThread extends Thread{

        @Override
        public void run() {
            try {
                sleep(3000);
                Message message = new Message();
                message.what= NO;
                handler.sendMessage(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
