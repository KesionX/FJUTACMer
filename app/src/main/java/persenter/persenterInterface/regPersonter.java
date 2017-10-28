package persenter.persenterInterface;

/**
 * Created by Administrator on 2016/9/22.
 */
public interface regPersonter {

    public void requestReg(String username, String password,String rpass,String nick, int gender,String school,
                           String email, String motto);//请求注册

    public void destory();
}
