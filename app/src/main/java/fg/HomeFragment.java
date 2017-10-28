package fg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Set;

import adapter.OjGameAdapter;
import bean.SingleMegSelf;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import fg.FragmentView.OjGameView;
import fg.FragmentView.UpLoadView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import persenter.OjGameImpl;
import persenter.UpLoadPersenterImpl;
import persenter.persenterInterface.OjGamePersenter;
import persenter.persenterInterface.UploadPersenter;
import ui.MyListView;
import ui.MyToast;
import url.MYURL;
import www.fjutoj.com.fjutacmer.R;
import www.fjutoj.com.fjutacmer.TabHostActivity;
import www.fjutoj.com.fjutacmer.UpLoadAppActivity;

/**
 * Created by Administrator on 2016/9/24.
 */
public class HomeFragment extends BaseFragment implements OjGameView,MyListView.LoadListener,PtrHandler ,View.OnClickListener,UpLoadView{

    private Button  menuButton;
    private OjGameAdapter ojGameAdapter;
    private OjGamePersenter ojGamePersenter;
    private Handler handler;
    private MyListView listView;
    private PtrFrameLayout ptrFrameLayout;
    private UploadPersenter uploadPersenter;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitData();
        InitJush();
    }

    private void InitJush() {
        JPushInterface.setAlias(getActivity(), SingleMegSelf.getSingleMegSelf().getUsername(), new TagAliasCallback() {
            @Override
            public void  gotResult(int i, String s, Set<String> set) {

            }
        });

    }

    @Override
    public View InitView(LayoutInflater inflate) {

        return inflate.inflate(R.layout.home_fragment,null);
    }

    @Override
    public void InitFindView() {
        listView = (MyListView) rootView.findViewById(R.id.refresh_listview);
        menuButton = (Button) rootView.findViewById(R.id.home_menu_button);
        ptrFrameLayout = (PtrFrameLayout) rootView.findViewById(R.id.ptr);
        initRefreshHead(ptrFrameLayout);
        listView.setDividerHeight(0);
        listView.setInterface(this);
        listView.setptrFrameLayout(ptrFrameLayout);
        ptrFrameLayout.setPtrHandler(this);
        ojGamePersenter.requestOjGameListData();
        menuButton.setOnClickListener(this);
    }

    @Override
    public void InitData() {
        handler = new Handler();
        ojGamePersenter = new OjGameImpl(this,handler);
        uploadPersenter = new UpLoadPersenterImpl(this,handler);
        uploadPersenter.getUpLoad();
    }

    private void setAdapter() {
        if(ojGameAdapter==null){
            ojGameAdapter = new OjGameAdapter(getActivity().getLayoutInflater(),ojGamePersenter.getOjGameList());
            listView.setAdapter(ojGameAdapter);
        }else {
            ojGameAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        menuButton = null;
        listView = null;
        ojGamePersenter = null;
        handler = null;
    }

    @Override
    public void ojGameRefreshSuccess() {
        setAdapter();
    }

    @Override
    public void ojGameRefreshFall() {
        MyToast.ShowToast(getActivity(),"加载失败", Toast.LENGTH_LONG);
    }

    @Override
    public void ojGameListSuccess() {
        setAdapter();
    }

    @Override
    public void ojGameListFall() {
        MyToast.ShowToast(getActivity(),"加载失败", Toast.LENGTH_LONG);
    }

    @Override
    public void onLoad() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.loadcomplete();
            }
        },3000);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return true;
    }

    @Override
    public void onRefreshBegin(final PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("main","refresh----");
                ptrFrameLayout.refreshComplete();
            }
        },2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_menu_button:
                TabHostActivity.menu.showMenu();
                break;
        }
    }

    @Override
    public void Upload(int versionCode, String versionName, String versioninfo, String apkUrl) {
      //  Toast.makeText(getContext(),versionCode+" "+versionName+" "+versioninfo+" "+apkUrl,Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getActivity(), UpLoadAppActivity.class);
        intent.putExtra(MYURL.VERSION_CODE,versionCode);
        intent.putExtra(MYURL.VERSION_NAME,versionName);
        intent.putExtra(MYURL.VERSION_INFO,versioninfo);
        intent.putExtra(MYURL.APK_URL,apkUrl);
        startActivity(intent);
    }
}
