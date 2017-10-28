package fg;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;

import Catch.MyImageLoader;
import adapter.MyRankingListAdapter;
import bean.SingleMegSelf;
import fg.FragmentView.RankingView;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import persenter.RankingPersenterImpl;
import persenter.persenterInterface.RankingPersenter;
import ui.CircleImageView;
import ui.ColorTextView;
import ui.MyListView;
import ui.MyToast;
import ui.RankingMenuPopupWindow;
import url.MYURL;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/9/24.
 */
public class PaimingFragment extends BaseFragment  implements View.OnClickListener,MyListView.LoadListener,PtrHandler,RankingView ,MyListView.MyScrollListener{

    private Button menuBt;
    private RankingMenuPopupWindow mRankingMenuPopupWindow;
    private PtrFrameLayout ptrFrameLayout;
    private MyListView myListView;

    private Handler handler;
    private RankingPersenter rankingPersenter;
    private MyRankingListAdapter myRankingListAdapter;

    private View User;

    private ViewHorld viewHorld;
    private ImageLoader imageLoader;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InitData();
    }

    @Override
    public View InitView(LayoutInflater inflate) {
        return inflate.inflate(R.layout.paiming_fragment,null);
    }

    @Override
    public void InitFindView() {
        menuBt = (Button) rootView.findViewById(R.id.paiming_menu_button);
        menuBt.setOnClickListener(this);

        ptrFrameLayout = (PtrFrameLayout) rootView.findViewById(R.id.ptr);
        myListView = (MyListView) rootView.findViewById(R.id.refresh_listview);
        initRefreshHead(ptrFrameLayout);
        myListView.setptrFrameLayout(ptrFrameLayout);
        myListView.setInterface(this);
        ptrFrameLayout.setPtrHandler(this);

        findUserView();
    }

    private void findUserView() {
        imageLoader = MyImageLoader.getMyImageLoader(getContext());
        User = rootView.findViewById(R.id.user);
        myListView.setMyScrollListener(this);
        View convertView = User;
        if(viewHorld == null)
            viewHorld = new ViewHorld();

        viewHorld.acb = (TextView) convertView.findViewById(R.id.ranking_acb);
        viewHorld.acmerTx = (CircleImageView) convertView.findViewById(R.id.ranking_tx);
        viewHorld.nick = (ColorTextView) convertView.findViewById(R.id.ranking_nick);
        viewHorld.acNum = (TextView) convertView.findViewById(R.id.ranking_acnum);
        viewHorld.motto = (TextView) convertView.findViewById(R.id.ranking_motto);
        viewHorld.rankingRank = (ColorTextView) convertView.findViewById(R.id.ranking_rank);
        viewHorld.rating = (ColorTextView) convertView.findViewById(R.id.ranking_rating);
        viewHorld.ratingNum = (TextView) convertView.findViewById(R.id.ranking_ratingnum);

        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHorld.acmerTx,
                R.mipmap.default_touxiang,R.mipmap.default_touxiang);
        imageLoader.get(MYURL.ROOT_ICON_URL+ MYURL.HEADURL+
                SingleMegSelf.getSingleMegSelf().getUsername().trim()+".jpg",imageListener);

        int rating = SingleMegSelf.getSingleMegSelf().getRating();

        viewHorld.rating.setText(rating<0?"-":rating+"");

        viewHorld.nick.setText(SingleMegSelf.getSingleMegSelf().getNick());

        viewHorld.rankingRank.setBackColor(rating);
        viewHorld.rankingRank.setText(SingleMegSelf.getSingleMegSelf().getRank()+"");

        viewHorld.acb.setText(SingleMegSelf.getSingleMegSelf().getAcb()+"");
        viewHorld.motto.setText(SingleMegSelf.getSingleMegSelf().getMotto());
        viewHorld.acNum.setText(SingleMegSelf.getSingleMegSelf().getAcnum()+"");

        viewHorld.ratingNum.setText(SingleMegSelf.getSingleMegSelf().getRatingnum()==0 ? "-":SingleMegSelf.getSingleMegSelf().getRatingnum()+"");

    }



    static class ViewHorld{
        ColorTextView rankingRank;
        ColorTextView nick;
        CircleImageView acmerTx;
        TextView acNum;
        TextView acb;
        TextView motto;
        ColorTextView rating;
        TextView ratingNum;
    }

    @Override
    public void InitData() {
        mRankingMenuPopupWindow = new RankingMenuPopupWindow(getActivity());
        this.handler = new Handler();
        this.rankingPersenter  = new RankingPersenterImpl(this,handler);
        this.rankingPersenter.getRankingData(MYURL.RANK);
        this.mRankingMenuPopupWindow.setRankingData(rankingPersenter);
        this.mRankingMenuPopupWindow.setHandler(handler);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.paiming_menu_button:
                mRankingMenuPopupWindow.showPopupWindow(menuBt);
                break;
        }

    }


    @Override
    public void onLoad() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                 rankingPersenter.getMoreRankingData();
            }
        },1000);

      //  myListView.loadcomplete();
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
                     rankingPersenter.freshRankingData();
                }
            },1000);

       // ptrFrameLayout.refreshComplete();
    }

    @Override
    public void getRankingDataMoreSuccess() {
        Log.d("ddd","sss"+rankingPersenter.getArrayListRankingAcmer().size());
        if(myRankingListAdapter == null){
            //myRankingListAdapter = new MyRankingListAdapter(getContext(),getActivity().getLayoutInflater(),rankingPersenter.getArrayListRankingAcmer());
            myRankingListAdapter = new MyRankingListAdapter(getContext(),getActivity().getLayoutInflater(),rankingPersenter.getCloneList());
            myListView.setAdapter(myRankingListAdapter);
        }else{
            myRankingListAdapter.setList(rankingPersenter.getCloneList());
            myRankingListAdapter.notifyDataSetChanged();
        }
        myListView.loadcomplete();
    }

    @Override
    public void getRankingDataMoreFall() {
        MyToast.ShowToast(getActivity(),"加载失败", Toast.LENGTH_LONG);
        myListView.loadcomplete();
    }

    @Override
    public void freshRankingDataSuccess() {
        if(myRankingListAdapter == null){
            //myRankingListAdapter = new MyRankingListAdapter(getContext(),getActivity().getLayoutInflater(),rankingPersenter.getArrayListRankingAcmer());
            myRankingListAdapter = new MyRankingListAdapter(getContext(),getActivity().getLayoutInflater(),rankingPersenter.getCloneList());
            myListView.setAdapter(myRankingListAdapter);
        }else{
            myRankingListAdapter.setList(rankingPersenter.getCloneList());
            myRankingListAdapter.notifyDataSetChanged();
        }
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void freshRankingDataFall() {
        MyToast.ShowToast(getActivity(),"加载失败", Toast.LENGTH_LONG);
        ptrFrameLayout.refreshComplete();
    }

    @Override
    public void show() {
      //  User.startAnimation(animationEnd);
        User.setVisibility(View.VISIBLE);
    }

    @Override
    public void miss() {
       // User.startAnimation(animationBegin);
        User.setVisibility(View.INVISIBLE);
    }
}
