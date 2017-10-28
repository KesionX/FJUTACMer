package www.fjutoj.com.fjutacmer;

import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import fg.ChallengeFragment;
import fg.HomeFragment;
import fg.MenuFragment;
import fg.PaimingFragment;
import fg.ProblemFragment;
import persenter.hostGetMegSelfImlp;
import persenter.persenterInterface.hostGetMegSelf;
import tool.StatusColor;
import ui.MyToast;
import www.fjutoj.com.fjutacmer.view.HostView;

public class TabHostActivity extends SlidingFragmentActivity implements HostView{

    private FragmentTabHost fragmentTabHost;
    //底部标题
    private String TabStr[]= {"首页","题目","挑战","排名"};
    private int DrawableId[] = {R.drawable.tab_home_icon_selector,R.drawable.tab_problem_icon_selector,
            R.drawable.tab_change_icon_selector,R.drawable.tab_paiming_icon_selector};
    private Class FragmentArray[] = {HomeFragment.class, ProblemFragment.class, ChallengeFragment.class,
            PaimingFragment.class};

    private hostGetMegSelf getMrgSelf;
    Handler handler ;
    public static SlidingMenu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);
        StatusColor.setTranslucentStatus(this,R.color.colorblue);
        initMenu();
        InitTabHost();
        InitData();
    }

    private void InitData() {
        handler = new Handler();
        getMrgSelf = new hostGetMegSelfImlp(this,handler);
      //  getMrgSelf.GetMegSelf();

    }


    private void initMenu() {

        setBehindContentView(R.layout.menu_framelayout);
        menu = getSlidingMenu();
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffset(300);
        menu.setShadowWidth(getWindowManager().getDefaultDisplay().getWidth() / 40);
        menu.setShadowDrawable(R.drawable.shadow0);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setBehindScrollScale(0);
        menu.setFadeDegree(0.35f);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.menu_frame,new MenuFragment()).commit();
    }


    /**
     *初始化fragmentTabHost
     **/
    private void InitTabHost() {
        fragmentTabHost= (FragmentTabHost) findViewById(R.id.tab_host);
        fragmentTabHost.setup(this,getSupportFragmentManager(),R.id.main_content);
        for(int i=0,n = TabStr.length;i<n;++i){
            fragmentTabHost.addTab(fragmentTabHost.newTabSpec(TabStr[i]).setIndicator(getIndicatorView(i)),
                    FragmentArray[i],null);

        }

    }

    private View getIndicatorView(int i) {
        View view= getLayoutInflater().inflate(R.layout.tabcontent,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.content_image);
        TextView textView  = (TextView) view.findViewById(R.id.content_tx);

        imageView.setBackgroundResource(DrawableId[i]);
        textView.setText(TabStr[i]);

        return view;
    }


    @Override
    public void getMegSelfSuccess() {
        MyToast.ShowToast(this,"获取个人信息成功", Toast.LENGTH_LONG);
    }

    @Override
    public void getMegSelfFall() {
        MyToast.ShowToast(this,"获取个人信息失败", Toast.LENGTH_LONG);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getMrgSelf.destory();
    }
}
