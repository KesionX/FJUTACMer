package www.fjutoj.com.fjutacmer.view;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface LoginView {

    public void loginSuccess();//登陆成功
    public void WrongPassword();//密码错误
    public void NoSuchUser();//账号不存在
    public void SystemError();//服务器错误

    public void accountFormatError();//账号格式不符
    public void passFormatError();//密码格式不符

}
