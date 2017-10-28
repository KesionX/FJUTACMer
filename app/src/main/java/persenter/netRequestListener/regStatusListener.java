package persenter.netRequestListener;

/**
 * Created by Administrator on 2016/9/22.
 */
public interface regStatusListener {
    public void regSuccess();//注册成功
    public void regUsernameExist();//账号已存在
    public void regFail();//注册失败，格式错误
}
