package persenter.netRequestListener;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface loginStatusListenser {

    public void  loginSuccess();//登陆成功
    public void  WrongPassword();//密码错误
    public void  NoSuchUser();//没有这个账号
    public void SystemError();//系统错误
}
