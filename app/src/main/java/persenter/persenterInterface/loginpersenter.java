package persenter.persenterInterface;

import android.widget.ImageView;

import ui.CircleImageView;

/**
 * Created by Administrator on 2016/9/20.
 */
public interface loginpersenter {

    public void setTouXiang(CircleImageView imageView, String account);//设置头像
    public void Login(String account,String pass);//登陆
    public void accountFormatError();//账号格式不符
    public void passFormatError();//密码格式不符
    public void destory();//释放内存

}
