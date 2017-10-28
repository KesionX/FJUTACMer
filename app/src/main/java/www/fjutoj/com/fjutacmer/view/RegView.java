package www.fjutoj.com.fjutacmer.view;

/**
 * Created by Administrator on 2016/9/22.
 */
public interface RegView {
    public void regSuccess();//注册成功
    public void regUsernameExist();//账号已存在
    public void regFail();//注册失败，格式错误
    public void regAccountFormatError();//账号格式不符
    public void regPassFormatError();//密码格式不符
    public void regNickFormatError();//昵称格式不符
    public void regSchoolFormatError();//学校格式不符
    public void regMottoFormatError();//座右铭格式不符
    public void regRpassError();//密码重新输入错误
}
