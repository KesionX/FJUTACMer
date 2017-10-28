package fg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import adapter.MyProblemListAdapter;
import bean.MyProblemBean;
import fg.FragmentView.MyProblemView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import persenter.MyProblemPersenterImpl;
import persenter.persenterInterface.MyProblemPersenter;
import ui.MyListView;
import ui.MyToast;
import www.fjutoj.com.fjutacmer.MyProblemActivity;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/10/1.
 */
public class MyProblemFragment extends BaseFragment implements MyProblemView,MyListView.LoadListener,PtrHandler,AdapterView.OnItemClickListener {

    private static final String PID = "pid";
    public final int  page;
    public MyProblemFragment(int page){
        this.page = page;
    }
    private MyProblemPersenter  myProblemPersenter;
    private Handler hander;
    private PtrFrameLayout ptrFrameLayout;
    private MyListView myListView;
    private MyProblemListAdapter myProblemListAdapter;
    private boolean isok=false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitData();
    }

    @Override
    public View InitView(LayoutInflater inflate) {
        return inflate.inflate(R.layout.myproblem_fragment,null);
    }

    @Override
    public void InitFindView() {
        ptrFrameLayout = (PtrFrameLayout) rootView.findViewById(R.id.ptr);
        myListView = (MyListView) rootView.findViewById(R.id.refresh_listview);
        myListView.setDividerHeight(0);
        initRefreshHead(ptrFrameLayout);
        myListView.setptrFrameLayout(ptrFrameLayout);
        myListView.setInterface(this);
        myListView.setOnItemClickListener(this);
        ptrFrameLayout.setPtrHandler(this);
        myProblemPersenter.getMyProblemMoreData();
        Log.d("pagemain","page:"+page);
    }

    @Override
    public void InitData() {
        hander = new Handler();
        myProblemPersenter = new MyProblemPersenterImpl(this,hander);
    }
    @Override
    public void getMyProblemMoreSuccess() {
        if(myProblemListAdapter==null){
            myProblemListAdapter = new MyProblemListAdapter(getActivity().getLayoutInflater(),myProblemPersenter.getMyProblemList());
            myListView.setAdapter(myProblemListAdapter);
        }else{
            myProblemListAdapter.notifyDataSetChanged();
        }
        myListView.loadcomplete();
    }

    @Override
    public void getMyProblemMoreFall() {
        MyToast.ShowToast(getActivity(),"加载失败", Toast.LENGTH_LONG);
        myListView.loadcomplete();
    }

    @Override
    public void getRefreshMyProblemSuccess() {
        if(myProblemListAdapter==null){
            myProblemListAdapter = new MyProblemListAdapter(getActivity().getLayoutInflater(),myProblemPersenter.getMyProblemList());
            myListView.setAdapter(myProblemListAdapter);
        }else{
            myProblemListAdapter.notifyDataSetChanged();
        }
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void getRefreshMyProblemFall() {
        MyToast.ShowToast(getActivity(),"加载失败", Toast.LENGTH_LONG);
        ptrFrameLayout.refreshComplete();
    }

    public int getPage() {
        return page;
    }


    @Override
    public void onLoad() {
        hander.postDelayed(new Runnable() {
            @Override
            public void run() {
                myProblemPersenter.getMyProblemMoreData();
            }
        },1000);
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return true;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        frame.postDelayed(new Runnable() {
            @Override
            public void run() {
                myProblemPersenter.getRefreshProblemData();
            }
        },1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myProblemPersenter.destory();
        hander = null;
        ptrFrameLayout = null;
        myListView = null;
        myProblemListAdapter = null;
        myProblemPersenter = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int pid = myProblemPersenter.getMyProblemList().get(position).getPid();
        Intent intent = new Intent(getActivity(), MyProblemActivity.class);
        intent.putExtra(PID,pid);
        startActivity(intent);
    }
}
