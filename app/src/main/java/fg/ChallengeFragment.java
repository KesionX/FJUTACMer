package fg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.MyChallengeListAdapter;
import bean.Block;
import bean.BlockCondition;
import bean.BlockDec;
import bean.ChallengeProblem;
import fg.FragmentView.ChallengeView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import persenter.ChallengePersenterImpl;
import persenter.persenterInterface.ChallengePersonter;
import tool.LogPreferences;
import ui.MyListView;
import ui.MyToast;
import www.fjutoj.com.fjutacmer.BlockDecActivity;
import www.fjutoj.com.fjutacmer.MyProblemActivity;
import www.fjutoj.com.fjutacmer.R;
import www.fjutoj.com.fjutacmer.TabHostActivity;

/**
 * Created by Administrator on 2016/9/24.
 */
public class ChallengeFragment extends BaseFragment implements PtrHandler ,ChallengeView,AdapterView.OnItemClickListener,View.OnClickListener{

    private static final String PID = "pid";
    private static final String TITLE = "title";
    private static final String TEXT_DEC = "dec";
    private Button menu;
    private Handler handler;
    private MyListView listView;
    private PtrFrameLayout ptrFrameLayout;
    private ChallengePersonter challengePersonter;
    private MyChallengeListAdapter myChallengeListAdapter;
    String account;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitData();
    }

    @Override
    public View InitView(LayoutInflater inflate) {
        return inflate.inflate(R.layout.change_fragment,null);
    }

    @Override
    public void InitFindView() {
        listView = (MyListView) rootView.findViewById(R.id.refresh_listview);
        ptrFrameLayout = (PtrFrameLayout) rootView.findViewById(R.id.ptr);
        menu = (Button) rootView.findViewById(R.id.home_menu_button);
        menu.setOnClickListener(this);
        initRefreshHead(ptrFrameLayout);
        listView.setDividerHeight(0);
        listView.setptrFrameLayout(ptrFrameLayout);
        listView.setOnItemClickListener(this);
        ptrFrameLayout.setPtrHandler(this);
        LogPreferences logPreferences = LogPreferences.getLogPreferences(getActivity(),LogPreferences.AP_PRE);
        account = logPreferences.getAt();
        challengePersonter.getChallengeData(account);
    }

    @Override
    public void InitData() {
        handler = new Handler();
        challengePersonter = new ChallengePersenterImpl(this,handler);
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
                challengePersonter.freshChallengeData(account);
                ptrFrameLayout.refreshComplete();
            }
        },2000);
    }

    @Override
    public void getChallengeDataSuccess() {
        if(myChallengeListAdapter==null){
            myChallengeListAdapter = new MyChallengeListAdapter(challengePersonter.getChallengeVector()
                    .getVisiableList(),getActivity().getLayoutInflater());
            listView.setAdapter(myChallengeListAdapter);
        }else {
            myChallengeListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void getChallengeDataFall() {
        MyToast.ShowToast(getActivity(),"网络获取错误", Toast.LENGTH_LONG);
    }

    @Override
    public void freshChallengeDataSuccess() {
        if(myChallengeListAdapter==null){
            myChallengeListAdapter = new MyChallengeListAdapter(challengePersonter.getChallengeVector()
                    .getVisiableList(),getActivity().getLayoutInflater());
            listView.setAdapter(myChallengeListAdapter);
        }else {
            myChallengeListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void freshChallengeDatafall() {
        MyToast.ShowToast(getActivity(),"网络获取错误", Toast.LENGTH_LONG);
    }

    @Override
    public void getBlockDataSuccess(int position) {
        challengePersonter.getChallengeVector().setOpenOrClose(position);
        myChallengeListAdapter.notifyDataSetChanged();
    }

    @Override
    public void getBlockDataFall() {
        MyToast.ShowToast(getActivity(),"网络获取错误", Toast.LENGTH_LONG);
    }

    @Override
    public void getConditionSuccess(ArrayList<BlockCondition> blockDecs) {
        String msg = "请在以下模块:\n";
        for(int i=0,n = blockDecs.size();i<n;++i){
            msg +=blockDecs.get(i).getBlockName()+"中获得:";
            msg +=blockDecs.get(i).getNum()+"积分\n";
        }
        MyToast.ShowToast(getActivity(),msg, Toast.LENGTH_LONG);
    }

    @Override
    public void getConditionFall() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Object o = challengePersonter.getChallengeVector().getVisiableList().get(position);
        if(o instanceof Block){
            Block b = (Block) o;
            int isOpen =( (Block) o).getIsOpen();
            if(isOpen==1){
                int wid = b.getId();
                Log.d("qq","condition");
                challengePersonter.getCondition(wid);
                return ;
            }
            if(challengePersonter.getChallengeVector().isFirstUp(position)){
                Log.d("kej","request first");//请求成功后要challengePersonter.getChallengeVector().setOpenOrClose(position);
                // myChallengeListAdapter.notifyDataSetChanged();
               // Block b = (Block) o;
                int wid = b.getId();
                challengePersonter.getBlockData(wid,account,position);

            }else{
                Log.d("kej","isRootOpen");
                challengePersonter.getChallengeVector().setOpenOrClose(position);
                myChallengeListAdapter.notifyDataSetChanged();
            }

        }else if(o instanceof BlockDec){
            Intent intent = new Intent(getActivity(), BlockDecActivity.class);
            BlockDec blockDec = (BlockDec) o;
            intent.putExtra(TITLE,((BlockDec) o).getName());
            intent.putExtra(TEXT_DEC,((BlockDec) o).getText());
            startActivity(intent);
        }else if (o instanceof ChallengeProblem){
            ChallengeProblem challengeProblem = (ChallengeProblem) o;
            int pid = ((ChallengeProblem) o).getTpid();
            Intent intent = new Intent(getActivity(), MyProblemActivity.class);
            intent.putExtra(PID,pid);
            startActivity(intent);
        }

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
    public void onDestroy() {
        super.onDestroy();
        menu = null;
        listView = null;
        challengePersonter.Destory();
        ptrFrameLayout = null;
        myChallengeListAdapter = null;
        handler = null;

    }
}
