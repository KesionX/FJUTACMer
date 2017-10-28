package persenter.persenterInterface;

import android.app.Fragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/30.
 */
public interface ProblemPersenter {
    public void SetAdapter();
    public ArrayList<Fragment> getFagmentList();
    public void destory();
}
