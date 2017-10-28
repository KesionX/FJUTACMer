package ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 */
public class ViewIndicatorLayout extends LinearLayout {

    //标题颜色（默认与高光）
    private static final int DEFAULT_COLOR = 0x7711b7f3;
    private static final int HIGLIGTH_COLOR = 0xFF11b7f3;
    private static final int SREAM_HEIGHT = 40;
    //画笔，用来绘制三角形角标
    private Paint paint;
    //线路
    private Path path;
    //三角形宽
    private int TragWidth;
    //三角形高
    private int TragHigth;
    //角标偏移量
    private int TranslationX;
    //角标初始偏移量
    private int InitTranslationX;
    //子View个数
    private int cCount;
    //可见Tab个数
    private  int Tabcount;
    //当tabcount>4默认为4个
    private final int TABCOUNTS = 5;
    //tab标题
    private ArrayList<String> listTitle;
    private ViewPager mViewPage;

    //控制indicator滑动
    private int goX;
    private int traX;
    private int llX;

    int SreamWidth;

    public ViewIndicatorLayout(Context context) {
        this(context,null);
    }

    public ViewIndicatorLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public ViewIndicatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitPaint();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        cCount = getChildCount();
        Tabcount = cCount;
        if(Tabcount >= TABCOUNTS){
            Tabcount = TABCOUNTS;
        }
    }

    /**
     * 设置子view的宽
     * */
    public  void setChildWith(){
        if(cCount<=0) return;
        if(cCount > TABCOUNTS)
        {
            for(int i=0;i<cCount;++i)
            {
                View view  = getChildAt(i);
                LayoutParams param = (LayoutParams) view.getLayoutParams();
                param.weight=0.0f;

                //           Log.d("main","getW"+getWidth());
                param.width = getWidth()/Tabcount;
            }
        }else{
            for(int i=0;i<Tabcount;++i)
            {
                View view  = getChildAt(i);
                LayoutParams param = (LayoutParams) view.getLayoutParams();
                param.weight=0.0f;
                param.width = getWidth()/Tabcount;
             //  Log.d("main","width"+"");
            }

        }
        setOnClickListener();
    };

    /**
     * 初始化画笔
     * */
    private void InitPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setPathEffect(new CornerPathEffect(5));
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        SreamWidth = wm.getDefaultDisplay().getWidth();
    }

    /**
     * 三角形绘制
     * */
    @Override
    protected void dispatchDraw(Canvas canvas) {
       // Log.d("main","dispath");
      //  initTrag();
        if(Tabcount==0)
            return;
        canvas.save();
        canvas.translate(InitTranslationX+TranslationX,getHeight());
         //   Log.d("main","getHight"+getHeight());
        //if(path!=null)
        canvas.drawPath(path,paint);
        canvas.restore();
        super.dispatchDraw(canvas);

    }

/*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // int mode = MeasureSpec.getMode(widthMeasureSpec);

        int width ;
        width =  MeasureSpec.getSize(widthMeasureSpec);
        width = width/Tabcount*cCount;

        int hight =  MeasureSpec.getSize(heightMeasureSpec);
        Log.d("main","t"+Tabcount+"c"+cCount+" "+width+" "+hight+" "+widthMeasureSpec+" "+heightMeasureSpec);
        setMeasuredDimension(width,hight);
      //  super.onMeasure(widthMeasureSpec, heightMeasureSpec);


    }*/

    /**
     * 设置三角形宽及初始位置
     * */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
            Log.d("main",w+" "+h+" "+oldh+ " "+oldw+" "+Tabcount+" "+getChildCount());
        setChildWith();

        if(Tabcount==0)
            return;
        TragWidth = (int)w/Tabcount*1/6;
        //   Log.d("main",TragWidth+"");
        InitTranslationX = w/Tabcount/2-TragWidth/2;
        //   Log.d("main",TragWidth+" "+InitTranslationX);
        initTrag();
       // setTitleHighlight(0);
    }

    /**
     * 设置path，绘制三角形
     * */
    private void initTrag() {
        TragHigth = TragWidth/2;
        path = new Path();
        path.moveTo(0,0);
        path.lineTo(TragWidth,0);
        path.lineTo(TragWidth/2,-TragHigth);
        path.close();
}

    /**
     * ViewPage移动时进行角标重绘
     * */
    public void InDraw(int position, float positionoffset) {
        int tabWith = getWidth()/Tabcount;
        TranslationX = position*getWidth()/Tabcount+(int)(positionoffset*getWidth()/Tabcount);
        if(cCount>TABCOUNTS&&position>(TABCOUNTS-3)&&positionoffset>0&&(cCount-position>2)){

            this.scrollTo((position-(TABCOUNTS-2))*tabWith+(int)(tabWith*positionoffset),0);
            //       Log.d("main","scroll");
        }

        invalidate();
    }

    /**
     * 重写onTouchEven，用来滑动
     * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(llX<0) {
            llX=0;
            scrollTo(0,0);
        }else if( llX >(cCount-TABCOUNTS)*getWidth()/Tabcount){
            llX = (cCount-TABCOUNTS)*getWidth()/Tabcount;
            scrollTo((cCount-TABCOUNTS)*getWidth()/Tabcount,0);
        }

        if(event.getAction()==MotionEvent.ACTION_MOVE)
        {

            traX = (int)event.getX();
            this.scrollBy(-(traX-goX),0);
            llX =getScrollX();
          //      Log.d("main","xx"+traX+" "+goX+" "+getScrollX()+" "+llX);
            goX = traX;
        }else if(event.getAction()==MotionEvent.ACTION_DOWN){
            goX = (int)event.getX();
        }
        return true;
    }

    /**
     * 动态添加标题时使用
     * */
    public void setInitTitle(ArrayList<String> Titles){
       // Log.d("main","tttt"+Tabcount);
        if(Titles==null||Titles.size()==0){
            return;
        }
        Tabcount = Titles.size();

        cCount = Tabcount;
        if(Tabcount>TABCOUNTS)
        {
            Tabcount = TABCOUNTS;
        }
        listTitle = Titles;
        this.removeAllViews();
        for(String title:Titles){
            addView(addTitleView(title));
          //  Log.d("main",title);
        }
        setOnClickListener();
        setTitleHighlight(0);
        onSizeChanged(SreamWidth,SREAM_HEIGHT,0,0);
        invalidate();
    }

    private TextView addTitleView(String s) {
        TextView view = new TextView(getContext());
        view.setText(s);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.width=SreamWidth/Tabcount;
        params.weight = 0.0f ;
        //  Log.d("main","widthhh"+);
       // params.height = getHeight();
        view.setGravity(Gravity.CENTER);
        view.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
        view.setTextColor(DEFAULT_COLOR);
        view.setLayoutParams(params);
        //view.setWidth();
        return view;
    }

    /**
     * 设置标题颜色
     * */
    private void setTitleHighlight(int pos){
        for(int i=0;i<cCount;++i){

            TextView textView= (TextView) getChildAt(i);
          // Log.d("main","light"+textView.getWidth()+" "+textView.getHeight());
            if(i==pos)
            {
                textView.setTextColor(HIGLIGTH_COLOR);
            }else{
                textView.setTextColor(DEFAULT_COLOR);
            }
        }
    }

    /**
     * 拦截子view点击事件
     * */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(cCount>TABCOUNTS)
        {
            if(ev.getAction()==MotionEvent.ACTION_MOVE)
            {
                return true;
            }else if(ev.getAction()==MotionEvent.ACTION_DOWN)
            {
                goX = (int)ev.getX();
                return false;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     *设置子view点击监听
     */
    public void setOnClickListener(){
        for(int i=0;i<cCount;++i){
            final int j=i;
            getChildAt(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPage.setCurrentItem(j);
                }
            });

        }

    }

    public PageOnChangeListener mLisstenner;
    /**
     * 外部接口回调监听ViewPage
     */
    public void setOnPageChangeListener(PageOnChangeListener listener){
        mLisstenner = listener;
    }
    public interface PageOnChangeListener{

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);
        public void onPageSelected(int position);

        public void onPageScrollStateChanged(int state);

    }

    public void setViewPage(ViewPager viewPage){
        mViewPage = viewPage;
        mViewPage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                InDraw(position, positionOffset);
                if(mLisstenner!=null){
                    mLisstenner.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }
            }

            @Override
            public void onPageSelected(int position) {
                setTitleHighlight(position);
                if(mLisstenner!=null){
                    mLisstenner.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(mLisstenner!=null){
                    mLisstenner.onPageScrollStateChanged(state);
                }
            }
        });
        mViewPage.setCurrentItem(0);
    }
}
