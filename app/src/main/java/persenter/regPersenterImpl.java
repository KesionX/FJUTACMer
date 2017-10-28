package persenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import net.NetInterface.RegRequest;
import net.RegRequestImpl;

import persenter.netRequestListener.regStatusListener;
import persenter.persenterInterface.regPersonter;
import www.fjutoj.com.fjutacmer.view.RegView;

/**
 * Created by Administrator on 2016/9/22.
 */
public class regPersenterImpl implements regPersonter ,regStatusListener {
    private RegView regView;
    private RegRequest regRequest;
    private Thread  thread;
    public static final int RETUsernameExist = 0;
    public static final int RETSuccess = 1;
    public static final int RETFail = 2;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case RETUsernameExist:
                    regView.regUsernameExist();
                    break;
                case RETSuccess:
                    regView.regSuccess();
                    break;
                case RETFail:
                    regView.regFail();
                    break;
            }
        }
    };
    public regPersenterImpl(RegView regView){
        this.regView = regView;

        this.regRequest = new RegRequestImpl(this);

    }

    /**
     * 先判断格式再发送网络注册请求
     * **/
    @Override
    public void requestReg(final String username, final String password, final String rpass, final String nick, final int gender, final String school,
                           final String email, final String motto) {
        thread = new Thread() {

            @Override
            public void run() {
                regRequest.requestReg(username,  password,rpass,nick, gender,
                        school, email, motto,handler);
            }
        };
        thread.start();
    }

    @Override
    public void destory() {
        regView = null;
        regRequest = null;
        thread = null;
        handler = null;
    }


    /***
     * 格式判断
     * */
    private boolean regFormatIsOk(){
        return false;
    }

    @Override
    public void regSuccess() {
        Message message = new Message();
        message.what = RETSuccess;
        handler.sendMessage(message);
    }

    @Override
    public void regUsernameExist() {
        Message message = new Message();
        message.what = RETUsernameExist;
        handler.sendMessage(message);
    }

    @Override
    public void regFail() {
        Message message = new Message();
        message.what = RETFail;
        handler.sendMessage(message);
    }
}
