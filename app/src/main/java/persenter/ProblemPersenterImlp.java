package persenter;

import android.app.Fragment;

import java.util.ArrayList;

import fg.FragmentView.ProblemView;
import fg.TabProblemFragment;
import fg.TabProblemStatusFragment;
import persenter.persenterInterface.ProblemPersenter;

/**
 * Created by Administrator on 2016/9/30.
 */
public class ProblemPersenterImlp implements ProblemPersenter{

    private ProblemView problemView;
    private ArrayList list;

    public ProblemPersenterImlp(ProblemView problemView){
        this.problemView = problemView;
        list = new ArrayList<>();
        InitFagmentList();
    }

    private void InitFagmentList() {
        list.add(new TabProblemFragment());
        list.add(new TabProblemStatusFragment());
    }


    @Override
    public void SetAdapter() {
        problemView.setAdapter();
    }

    @Override
    public ArrayList<Fragment> getFagmentList() {
        return list;
    }

    @Override
    public void destory() {
        problemView = null;
        list = null;
    }
}
