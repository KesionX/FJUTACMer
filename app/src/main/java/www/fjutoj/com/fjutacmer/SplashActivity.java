package www.fjutoj.com.fjutacmer;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import persenter.loginPersenterimpl;
import persenter.persenterInterface.loginpersenter;
import tool.LogPreferences;
import tool.StatusColor;
import tool.SystemStatusManager;
import ui.MyToast;
import www.fjutoj.com.fjutacmer.view.LoginView;

/**
 * Created by Administrator on 2016/9/20.
 */
public class SplashActivity extends FragmentActivity implements LoginView{

    private Handler handler;
    private boolean logOrHome;
    private loginpersenter mloginpersenter;
    private String account;
    private String ps;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       // StatusColor.setTranslucentStatus(this,R.color.colorwhite2);
        setContentView(R.layout.splash_layout);
        Log.d("mainlll","splash");
//        IntiData();
//        panDunSharePre();
//        login();

    }

    private void login() {
        if(logOrHome)
            mloginpersenter.Login(account,ps);
        else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            },3000);

        }
    }

    private void panDunSharePre() {
        LogPreferences logPreferences = LogPreferences.getLogPreferences(this,LogPreferences.AP_PRE);
        logOrHome = logPreferences.getIsok();
        account = logPreferences.getAt();
        ps = logPreferences.getPs();
    }

    private void IntiData() {
        handler = new Handler();
        mloginpersenter = new loginPersenterimpl(this,this,handler);
    }


    @Override
    public void loginSuccess() {

   /*     JPushInterface.setAlias(this,account, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });*/

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,TabHostActivity.class);
                startActivity(intent);
            }
        },3000);

    }

    @Override
    public void WrongPassword() {
        MyToast.ShowToast(this,"密码错误", Toast.LENGTH_LONG);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },3000);
    }

    @Override
    public void NoSuchUser() {
        MyToast.ShowToast(this,"没有此账号",Toast.LENGTH_LONG);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },3000);
    }

    @Override
    public void SystemError() {
        MyToast.ShowToast(this,"系统错误，请检查网络",Toast.LENGTH_LONG);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        },3000);
    }

    @Override
    public void accountFormatError() {

    }

    @Override
    public void passFormatError() {

    }
}
