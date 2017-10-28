package fg;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import adapter.MyFragmentAdapter;
import fg.FragmentView.ProblemView;
import persenter.ProblemPersenterImlp;
import persenter.persenterInterface.ProblemPersenter;
import ui.TabViewPager;
import www.fjutoj.com.fjutacmer.R;
import www.fjutoj.com.fjutacmer.TabHostActivity;

/**
 * Created by Administrator on 2016/9/24.
 */
public class ProblemFragment extends BaseFragment implements ProblemView,ViewPager.OnPageChangeListener,View.OnClickListener{

    private TabViewPager viewPager;
    private ProblemPersenter problemPersenter;
    private MyFragmentAdapter myFragmentAdapter;
    private static final int PROBLEM = 0;
    private static final int STATUS = 1;
    private TextView problemtx;
    private TextView statustx;
    private Button menuShow;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitData();
    }

    @Override
    public View InitView(LayoutInflater inflate) {
        return inflate.inflate(R.layout.problem_fragment,null);
    }

    @Override
    public void InitFindView() {
        viewPager = (TabViewPager) rootView.findViewById(R.id.tab_pro_and_status);
        problemtx = (TextView) rootView.findViewById(R.id.problem_pb_title);
        statustx = (TextView) rootView.findViewById(R.id.problem_status_title);
        menuShow = (Button) rootView.findViewById(R.id.menu_show);
        menuShow.setOnClickListener(this);
        problemtx.setOnClickListener(this);
        statustx.setOnClickListener(this);
        viewPager.setOnPageChangeListener(this);
        viewPager.setOffscreenPageLimit(0);
        problemPersenter.SetAdapter();
    }

    @Override
    public void InitData() {
        problemPersenter = new ProblemPersenterImlp(this);


    }

    @Override
    public void setAdapter() {
        if(myFragmentAdapter==null){
            myFragmentAdapter = new MyFragmentAdapter(getFragmentManager(),problemPersenter.getFagmentList());
            viewPager.setAdapter(myFragmentAdapter);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        problemPersenter.destory();
        myFragmentAdapter =null;
        problemtx = null;
        statustx = null;
        viewPager = null;
        problemPersenter = null;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        SetTabText( position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void SetTabText(int position){
        if(position==PROBLEM){
            problemtx.setBackgroundResource(R.drawable.rectangle_shape3_blue);
            problemtx.setTextColor(getActivity().getResources().getColor(R.color.colorwhite));
            statustx.setBackgroundResource(R.drawable.rectangle_shape4);
            statustx.setTextColor(getActivity().getResources().getColor(R.color.colorblue));
        }else if (position==STATUS){
            problemtx.setBackgroundResource(R.drawable.rectangle_shape3);
            problemtx.setTextColor(getActivity().getResources().getColor(R.color.colorblue));
            statustx.setBackgroundResource(R.drawable.rectangle_shape4_blue);
            statustx.setTextColor(getActivity().getResources().getColor(R.color.colorwhite));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.problem_pb_title:
                viewPager.setCurrentItem(PROBLEM);
                break;
            case R.id.problem_status_title:
                viewPager.setCurrentItem(STATUS);
                break;
            case R.id.menu_show:
                TabHostActivity.menu.showMenu();
                break;
        }
    }
}
