package persenter.netRequestListener;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface UpLoadListener {

    public void NeedUpLoad(int versionCode, String versionName, String versioninfo, String apkUrl);//需要更新回调
}
