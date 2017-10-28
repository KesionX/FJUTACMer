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
import adapter.StatusProblemAdapter;
import bean.StatusProblem;
import fg.FragmentView.StatusProblemView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import persenter.StatusProblemPersenterImpl;
import persenter.persenterInterface.StatusProblemPersenter;
import ui.MyListView;
import ui.MyToast;
import www.fjutoj.com.fjutacmer.MyProblemActivity;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/9/30.
 */
public class TabProblemStatusFragment extends BaseFragment implements StatusProblemView,MyListView.LoadListener,PtrHandler,AdapterView.OnItemClickListener{

    private static final String PID = "pid";
    private Handler hander;
    private PtrFrameLayout ptrFrameLayout;
    private MyListView myListView;
    private StatusProblemAdapter statusProblemAdapter;
    private StatusProblemPersenter statusProblemPersenter;
    private boolean isInitRefresh = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitData();
        Log.d("mainkk","tab_status");
    }

    @Override
    public View InitView(LayoutInflater inflate) {
        return inflate.inflate(R.layout.tab_problem_status_layout,null);
    }
    @Override
    public void ActivityCreated() {
     /*   Log.d("mainkk","res_status");
        if(!isInitRefresh){
            statusProblemPersenter.getStatusDataRefresh();
            isInitRefresh = true;
        }*/
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
        statusProblemPersenter.getStatusDataMore();
    }

    @Override
    public void InitData() {
        hander = new Handler();
        statusProblemPersenter = new StatusProblemPersenterImpl(this,hander);
    }

    @Override
    public void getStatusMoreSuccess() {
        Log.d("mainkk","success "+statusProblemPersenter.getStatusDataList().size());
        if(statusProblemAdapter==null){
         //   statusProblemAdapter = new StatusProblemAdapter(getActivity(), getActivity().getLayoutInflater(),statusProblemPersenter.getStatusDataList());
            statusProblemAdapter = new StatusProblemAdapter(getActivity(),
                    getActivity().getLayoutInflater(),statusProblemPersenter.getCloneDataList());
            myListView.setAdapter(statusProblemAdapter);
        }else{
            statusProblemAdapter.setList(statusProblemPersenter.getCloneDataList());
           statusProblemAdapter.notifyDataSetChanged();
        }
        myListView.loadcomplete();
    }

    @Override
    public void getStatusMoreFall() {
        myListView.loadcomplete();
        MyToast.ShowToast(getActivity(),"加载失败", Toast.LENGTH_LONG);
        Log.d("mainkk","fall");
    }

    @Override
    public void getStatusRefreshSuccess() {
        if(statusProblemAdapter==null){
            //   statusProblemAdapter = new StatusProblemAdapter(getActivity(), getActivity().getLayoutInflater(),statusProblemPersenter.getStatusDataList());
            statusProblemAdapter = new StatusProblemAdapter(getActivity(),
                    getActivity().getLayoutInflater(),statusProblemPersenter.getCloneDataList());
            myListView.setAdapter(statusProblemAdapter);
        }else{
            statusProblemAdapter.setList(statusProblemPersenter.getCloneDataList());
            statusProblemAdapter.notifyDataSetChanged();
        }
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void getStatusRefreshFall() {
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void onLoad() {
        hander.postDelayed(new Runnable() {
            @Override
            public void run() {
                statusProblemPersenter.getStatusDataMore();
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
                statusProblemPersenter.getStatusDataRefresh();
                isInitRefresh = true;
            }
        },1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        statusProblemPersenter.destory();
        hander =  null;
        ptrFrameLayout  = null;
        myListView = null;
        statusProblemAdapter = null;
        statusProblemPersenter = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int pid = statusProblemPersenter.getStatusDataList().get(position).getPid();
        Intent intent = new Intent(getActivity(), MyProblemActivity.class);
        intent.putExtra(PID,pid);
        startActivity(intent);
    }
}
