package ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import persenter.persenterInterface.RankingPersenter;
import url.MYURL;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2017/2/21.
 */
public class RankingMenuPopupWindow  extends PopupWindow implements View.OnClickListener{

    private View contentView;
    private TextView mDefault;
    private TextView acNum;
    private TextView acb;
    private TextView rating;
    private TextView ratingNum;
    private RankingPersenter rankingPersenter;
    private Handler handler;
    public RankingMenuPopupWindow(Activity context){

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        contentView = inflater.inflate(R.layout.ranking_menu_popupwindow,null);
        mDefault = (TextView) contentView.findViewById(R.id.ranking_default);
        mDefault.setOnClickListener(this);
        acNum = (TextView) contentView.findViewById(R.id.ranking_acnum);
        acNum.setOnClickListener(this);
        acb = (TextView) contentView.findViewById(R.id.ranking_acb);
        acb.setOnClickListener(this);
        rating = (TextView) contentView.findViewById(R.id.ranking_rating);
        rating.setOnClickListener(this);
        ratingNum = (TextView) contentView.findViewById(R.id.ranking_ratingnum);
        ratingNum.setOnClickListener(this);



        int height = context.getWindowManager().getDefaultDisplay().getHeight();
        int width = context.getWindowManager().getDefaultDisplay().getWidth();
        //设置popupWindow的View
        this.setContentView(contentView);
        //设置宽
        this.setWidth(width/3);
        //设置高
        int heightDp =  dip2px(context, 210);
        this.setHeight(heightDp);
        //设置弹出窗体可点击
        this.setFocusable(true);
        //设置外面可点击
        this.setOutsideTouchable(true);
        //刷新状态
        this.update();

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview2);

    }
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }

    public static int dip2px(Context context, float dipValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale +0.5f); }

    public void setRankingData(RankingPersenter rankingPersenter){
        this.rankingPersenter = rankingPersenter;
    }


    public void setHandler(Handler handler){
        this.handler = handler;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ranking_default:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankingPersenter.getRankingData(MYURL.RANK);
                    }
                },200);

                this.dismiss();
                break;

            case R.id.ranking_acnum:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankingPersenter.getRankingData(MYURL.ACNUM);
                    }
                },200);

                this.dismiss();
                break;
            case R.id.ranking_acb:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankingPersenter.getRankingData(MYURL.ACB);
                    }
                },200);

                this.dismiss();
                break;
            case R.id.ranking_rating:

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankingPersenter.getRankingData(MYURL.RATING);
                    }
                },200);

                this.dismiss();
                break;
            case R.id.ranking_ratingnum:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rankingPersenter.getRankingData(MYURL.RATINGNUM);
                    }
                },200);

                this.dismiss();
                break;

        }
    }
}
