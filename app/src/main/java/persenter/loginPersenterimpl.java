package persenter;

import android.content.Context;
import android.os.Handler;

import com.android.volley.toolbox.ImageLoader;

import net.NetInterface.RequestLogin;
import net.loginListenerImpl;

import Catch.MyImageLoader;
import persenter.netRequestListener.loginStatusListenser;

import persenter.persenterInterface.loginpersenter;
import ui.CircleImageView;
import url.MYURL;
import www.fjutoj.com.fjutacmer.R;
import www.fjutoj.com.fjutacmer.view.LoginView;

/**
 * Created by Administrator on 2016/9/20.
 */
public class loginPersenterimpl implements loginpersenter ,loginStatusListenser{

    private  LoginView loginView;
    private RequestLogin requestLogin;
    private ImageLoader imageLoader;

    public loginPersenterimpl(LoginView loginView, Context context, Handler handler){
        this.loginView = loginView;
        this.requestLogin = new loginListenerImpl(this,handler);
        imageLoader = MyImageLoader.getMyImageLoader(context);
    }
    @Override
    public void setTouXiang(CircleImageView imageView, String account) {
        //图片缓存
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView,
                R.mipmap.default_touxiang,R.mipmap.default_touxiang);
        imageLoader.get(MYURL.ROOT_ICON_URL+ MYURL.HEADURL+account,imageListener);
    }

    @Override
    public void Login(final String account, final String pass) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                requestLogin.LoginAccount( account, pass );
            }
        };
        thread.start();
    }

    @Override
    public void accountFormatError() {

    }

    @Override
    public void passFormatError() {

    }

    @Override
    public void destory() {
        requestLogin.destory();
        loginView = null;
        requestLogin = null;
        imageLoader = null;

    }


    @Override
    public void loginSuccess() {
        loginView.loginSuccess();
    }

    @Override
    public void WrongPassword() {
        loginView.WrongPassword();
    }

    @Override
    public void NoSuchUser() {
        loginView.NoSuchUser();
    }

    @Override
    public void SystemError() {
        loginView.SystemError();
    }
}
