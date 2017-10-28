package www.fjutoj.com.fjutacmer;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;


import java.util.ArrayList;

import Catch.MyImageLoader;
import bean.SingleMegSelf;
import bean.StaticUserPolygons;
import persenter.MessageLinePersonterImpl;
import persenter.UserPolysonsPersonterImpl;
import persenter.persenterInterface.MessageLinePersonter;
import persenter.persenterInterface.UserPolygonsPersonter;
import tool.StatusColor;
import ui.CircleImageView;
import ui.LineChartView;
import ui.MyToast;
import ui.PolygonsView;
import url.MYURL;
import www.fjutoj.com.fjutacmer.view.MessageLineView;
import www.fjutoj.com.fjutacmer.view.UserPolygonsView;

public class MegSelfActivity extends FragmentActivity implements View.OnClickListener,MessageLineView,UserPolygonsView{

    private Button back ;
    private CircleImageView circleImageView;
    private ImageView grander;
    private TextView nick;
    private TextView motto;
    private TextView email;
    private TextView school;
    private TextView acb;
    private TextView acnum;
    private TextView submitnum;
    private TextView rating;
    private  TextView ratingnum;
    View rootView;
    Animation animationBegin;
    Animation animationEnd;
    private ImageLoader imageLoader;
    private Activity context;
    private LineChartView mLineChartView;
    private MessageLinePersonter messageLinePersonter;
    private Handler handler;

    private ui.PolygonsView polygonView;
    private UserPolygonsPersonter mUserPolygonsPersonter;
    private ArrayList<String> polygonsStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.megself_layout);
        rootView = findViewById(R.id.my_megself_layout);
        InitFind();
        Initdata();
        StatusColor.setTranslucentStatus(this,R.color.colortransparent);
        startRootAnimation(savedInstanceState);

    }

    private void InitFind() {
        circleImageView = (CircleImageView) findViewById(R.id.megself_touxiang);
        back = (Button) findViewById(R.id.megself_back);
        back.setOnClickListener(this);
        grander = (ImageView) findViewById(R.id.megself_grander);
        nick = (TextView) findViewById(R.id.megself_nick);
        motto = (TextView) findViewById(R.id.megself_motto);
        email = (TextView) findViewById(R.id.megself_email);
        school = (TextView) findViewById(R.id.megself_school);
        acb = (TextView) findViewById(R.id.megself_acb);
        acnum = (TextView) findViewById(R.id.megself_acnum);
        submitnum = (TextView) findViewById(R.id.megself_submitnum);
        rating = (TextView) findViewById(R.id.megself_rating);
        ratingnum = (TextView) findViewById(R.id.megself_ratingnum);
        mLineChartView = (LineChartView) findViewById(R.id.messageLineView);
        polygonView = (PolygonsView) findViewById(R.id.polygons);
    }

    private void Initdata() {
        context = this;
        animationBegin = AnimationUtils.loadAnimation(this,R.anim.tran_in_amian2);
        animationEnd = AnimationUtils.loadAnimation(this,R.anim.tran_out_amian2);
        imageLoader = MyImageLoader.getMyImageLoader(this);
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(circleImageView,
                R.mipmap.default_touxiang,R.mipmap.default_touxiang);
        imageLoader.get(MYURL.ROOT_ICON_URL+ MYURL.HEADURL+
                SingleMegSelf.getSingleMegSelf().getUsername()+".jpg",imageListener);
        nick.setText(SingleMegSelf.getSingleMegSelf().getNick());
        setGranderImage(SingleMegSelf.getSingleMegSelf().getGender());
        motto.setText(SingleMegSelf.getSingleMegSelf().getMotto());
        email.setText(SingleMegSelf.getSingleMegSelf().getEmail());
        school.setText(SingleMegSelf.getSingleMegSelf().getSchool());
        acb.setText(SingleMegSelf.getSingleMegSelf().getAcb()+"");
        acnum.setText(SingleMegSelf.getSingleMegSelf().getAcnum()+"");
        submitnum.setText(SingleMegSelf.getSingleMegSelf().getSubmitnum()+"");
        rating.setText(SingleMegSelf.getSingleMegSelf().getRating()<0? "-":SingleMegSelf.getSingleMegSelf().getRating()+"");
        ratingnum.setText(SingleMegSelf.getSingleMegSelf().getRatingnum()==0? "-":SingleMegSelf.getSingleMegSelf().getRatingnum()+"");

        handler = new Handler();
        messageLinePersonter = new MessageLinePersonterImpl(this,handler);
        messageLinePersonter.getLineData(SingleMegSelf.getSingleMegSelf().getUsername());


        PolygonsStr();
        mUserPolygonsPersonter = new UserPolysonsPersonterImpl(this,handler);
        getPolygonsData(SingleMegSelf.getSingleMegSelf().getUsername());

    }

    private void PolygonsStr(){
        polygonsStr = new ArrayList<>();
        polygonsStr.add("基础");
        polygonsStr.add("数据结构");
        polygonsStr.add("数学");
        polygonsStr.add("几何");
        polygonsStr.add("图论");
        polygonsStr.add("搜索");
        polygonsStr.add("动态规划");
        polygonView.setArrayStr(polygonsStr);
    }

    private void setGranderImage(int i) {
        if (i==1){
            grander.setImageResource(R.mipmap.men_icon);
        }else {
            grander.setImageResource(R.mipmap.girl_icon);
        }
    }

    private void startRootAnimation(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    rootView.getViewTreeObserver().removeOnPreDrawListener(this);
                    rootView.setAnimation(animationBegin);
                    return true;
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.megself_back:
                endRootAnimation();
                break;
        }
    }

    private void endRootAnimation() {
        animationEnd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                context.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        rootView.startAnimation(animationEnd);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        endRootAnimation();

        return false;
       // return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        back =null;
        circleImageView = null;
        grander = null;
        nick = null;
        motto = null;
        email = null;
        school = null;
        acb = null;
        acnum  = null;
        submitnum = null;
        rating = null;
        ratingnum = null;
        rootView = null;
        context = null;
        animationBegin = null;
        animationEnd = null;

        messageLinePersonter.destory();
        mUserPolygonsPersonter.destory();
        mUserPolygonsPersonter = null;
        handler = null;
        mLineChartView = null;
        polygonView = null;


    }

    @Override
    public void getLineDataSuccess() {
        Log.d("ddd",messageLinePersonter.getRatingPointList().toString());
        mLineChartView.setRankPointList(messageLinePersonter.getRatingPointList());
    }

    @Override
    public void getLineDataFall() {
        MyToast.ShowToast(this,"数据请求出错", Toast.LENGTH_LONG);
    }

    @Override
    public void getPolygonsDataSuccess() {
        StaticUserPolygons ms = StaticUserPolygons.getMs();
        ArrayList<Double> list = mUserPolygonsPersonter.getPolygonsList();
        ArrayList<Double> ml = new ArrayList();
        for (int i=0,n=list.size();i<n;++i){
            Double d = new Double(list.get(i));
            Log.d("polygons",d+"");
            ml.add(d);
        }
        ms.pushUserPolygons(SingleMegSelf.getSingleMegSelf().getUsername(),ml);
        polygonView.setPercentageS(ms.getUserPolygons(SingleMegSelf.getSingleMegSelf().getUsername()));
    }

    @Override
    public void getPolygonsDataFall() {
        MyToast.ShowToast(this,"数据请求出错", Toast.LENGTH_LONG);
    }

    public void getPolygonsData(String user) {
        StaticUserPolygons ms = StaticUserPolygons.getMs();
        if(ms.isHave(user)){
            polygonView.setPercentageS(ms.getUserPolygons(user));
        }else{
            mUserPolygonsPersonter.getUserPolygonsData(user);
        }

    }
}
