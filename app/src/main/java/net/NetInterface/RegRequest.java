package net.NetInterface;

import android.os.Handler;

/**
 * Created by Administrator on 2016/9/22.
 */
public interface RegRequest {

    public void requestReg(String username,
                           String password,
                           String rpass,
                           String nick,
                           int gender,
                           String school,
                           String email,
                           String motto, Handler handler);
}
