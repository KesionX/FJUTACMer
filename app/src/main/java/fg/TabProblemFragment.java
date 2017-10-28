package fg;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import java.util.ArrayList;

import adapter.MyProblemFgAdapter;
import fg.BaseFragment;
import fg.FragmentView.TabProblemView;
import persenter.TabProblemPersenterImpl;
import persenter.persenterInterface.TabProblemPersenter;
import ui.ViewIndicatorLayout;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/9/30.
 */
public class TabProblemFragment extends BaseFragment implements TabProblemView {


    private TabProblemPersenter tabProblemPersenter;
    private ViewIndicatorLayout viewIndicatorLayout;
    private Handler handler;
    private ViewPager viewPager;
    private MyProblemFgAdapter myProblemFgAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitData();
    }

    @Override
    public View InitView(LayoutInflater inflate) {
        return inflate.inflate(R.layout.tab_problem_layout,null);
    }


    @Override
    public void InitFindView() {
       viewIndicatorLayout = (ViewIndicatorLayout) rootView.findViewById(R.id.viewpageIndecator);
        viewPager = (ViewPager) rootView.findViewById(R.id.my_problem_vp);
        tabProblemPersenter.getViewPageData();

    }

    @Override
    public void InitData() {

        handler = new Handler();
        tabProblemPersenter = new TabProblemPersenterImpl(this,handler);

    }

    @Override
    public void getViewPageSuccess() {
        viewIndicatorLayout.setInitTitle(tabProblemPersenter.getStringList());
        if(myProblemFgAdapter==null){
            myProblemFgAdapter = new MyProblemFgAdapter(getFragmentManager(),tabProblemPersenter.getMyProblemFragmentVp());
            viewPager.setAdapter(myProblemFgAdapter);
            viewIndicatorLayout.setViewPage(viewPager);
        }
    }

    @Override
    public void getViewPageFall() {

    }
}
