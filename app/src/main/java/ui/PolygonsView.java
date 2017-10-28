package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;



import java.util.ArrayList;

import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2017/3/9.
 */
public class PolygonsView extends View {

    //view的宽
    private int viewWidth;
    //view的高
    private int viewHeight;

    //默认宽
    private int defaultWidth = 200;
    //默认高
    private int defaultHeight = 200;

    //中心平均角度
    private double angle ;
    //半径长度
    private int r ;

    //边数
    private int polygonsEdgeCount;
    //中心点
    private Point center;
    //画笔
    private Paint mpaint;
    //字体颜色
    private int textColor;
    //能力线条颜色
    private int dataPolygonsColor;

    //多边形颜色
    private int polygonsColor;
    //默认字体颜色
    private static int DEFAULT_TEXT_COLOR = 0xff00bfff;
    //默认能力线颜色
    private static int DEFAULT_DATA_POLYGONS_COLOR = 0xff00bfff;
    //默认多变形颜色
    private static int DEFAULT_POLYGONS_COLOR = 0x00bfff;
    //默认边数
    private static int DEFAULT_POLYGONS_EDGE_COUNT = 3;

    private ArrayList<String> attrStr;
    private ArrayList<Double> percentageS;

    public PolygonsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public PolygonsView(Context context) {
        this(context,null);

    }

    public PolygonsView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PolygonsView);
        textColor = a.getColor(R.styleable.PolygonsView_textColor,DEFAULT_TEXT_COLOR);
        dataPolygonsColor =  a.getColor(R.styleable.PolygonsView_dataPolygonsColor,DEFAULT_DATA_POLYGONS_COLOR);
        polygonsColor = a.getColor(R.styleable.PolygonsView_polygonsColor,DEFAULT_POLYGONS_COLOR);
        polygonsEdgeCount = a.getInt(R.styleable.PolygonsView_polygonsEdgeCount,DEFAULT_POLYGONS_EDGE_COUNT);
        polygonsEdgeCount = polygonsEdgeCount<3 ? 3:polygonsEdgeCount;
        angle = (double)Math.PI*2/polygonsEdgeCount;
        center = new Point();
        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        defaultHeight = dip2px(context,defaultHeight);
        defaultWidth = dip2px(context,defaultWidth);
        //去锯齿
        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.BLUE);
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(1);

        attrStr = new ArrayList<>();
        initArreStr();
        percentageS = new ArrayList<>();
        initPoints();

    }

    private void initArreStr() {
        for(int i=0;i<polygonsEdgeCount;++i ){
        String s = "a";
        s = String.valueOf((char)(s.charAt(0)+i));
        attrStr.add(s);
        //  Log.d("ss",s);
    }
    }

    private   int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widrhSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);


        if(widthSpecMode==MeasureSpec.AT_MOST&&heightSpecMode==MeasureSpec.AT_MOST){

            setMeasuredDimension(defaultWidth,defaultHeight);
        }else if(widthSpecMode == MeasureSpec.AT_MOST){
            int w = defaultWidth<heightSpecSize ? defaultWidth:heightSpecSize;
            setMeasuredDimension(w,w);
        }else if(heightSpecMode == MeasureSpec.AT_MOST){
            int w = defaultHeight<widrhSpecSize ? defaultHeight:widrhSpecSize;
            setMeasuredDimension(w,w);
        }else{
            int w = widrhSpecSize<heightSpecSize ? widrhSpecSize:heightSpecSize;
            setMeasuredDimension( w, w);
        }

    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initViewPamrs();
    }

    private void initViewPamrs() {
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        viewWidth = getWidth();
        viewHeight = getHeight();
        center.set(viewWidth/2,viewHeight/2);
        viewWidth = viewWidth - paddingLeft - paddingRight;
        viewHeight = viewHeight - paddingBottom - paddingTop;
        r = viewWidth/3;


    }

    private void initPoints() {

       // int mr[]={(int)(0.8*r),(int)(0.3*r),(int)(0.5*r),(int) (0.2*r),(int)(0.6*r),(int)(0.1*r),(int)(0.44*r)};

        percentageS.clear();


        for(int i=0;i<=polygonsEdgeCount-1;++i ){
         /*   double tx = mr * Math.cos(i*angle);
            double ty = mr * Math.sin(i*angle);*/
           // Point tt = new Point((int)(tx+0.5),(int) (ty+0.5));
            percentageS.add((double)2/3);
        }

    }


    @Override
    protected void onDraw(Canvas canvas) {
        initViewPamrs();
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(center.x,center.y);
        Polygons( canvas);
        towerPolygons(canvas);
        threePolygons(canvas);
        drawCenterLine(canvas);
        dataPolygons(canvas);
        dataText(canvas);
        canvas.restore();
    }

    private void dataText(Canvas canvas) {
        int mr =(int) (r+ 8*((float)viewWidth/defaultWidth));
     //   String s[]={"几何","数据结构","基础","搜索","图论","数论","动态规划"};
        mpaint.setAntiAlias(true);
        mpaint.setColor(textColor);
        mpaint.setStyle(Paint.Style.STROKE);
       mpaint.setStrokeWidth(0);
        canvas.drawText(attrStr.get(0),r+10,0,mpaint);
       for(int i=1;i<=polygonsEdgeCount-1;++i ){
            double tx = mr * Math.cos(i*angle);
            double ty = mr * Math.sin(i*angle);
           float size = 10*((float)viewWidth/defaultWidth);
           mpaint.setTextSize(size);
           if(tx<0)
               tx-=attrStr.get(i).length()*size;
           canvas.drawText(attrStr.get(i),(float) tx,(float)ty,mpaint);

        }
    }

    private void drawCenterLine(Canvas canvas) {

        int mr = r;
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(0x66b0c4de);
        for(int i=0;i<=polygonsEdgeCount-1;++i ){
            double tx = mr * Math.cos(i*angle);
            double ty = mr * Math.sin(i*angle);
          canvas.drawLine(0,0,(float) tx,(float) ty,mpaint);
        }

    }

    private void threePolygons(Canvas canvas) {
        Path path = new Path();

        canvas.drawPoint(0,0,mpaint);
        int mr = r*1/3;
        path.moveTo(mr,0);

        for(int i=1;i<=polygonsEdgeCount-1;++i ){
            double tx = mr * Math.cos(i*angle);
            double ty = mr * Math.sin(i*angle);
            path.lineTo((int)(tx+0.5),(int)(ty+0.5));
        }
        path.close();
        mpaint.setStyle(Paint.Style.FILL);
        int color = polygonsColor&0x00ffffff;
        mpaint.setColor(0x4a000000|color);
        canvas.drawPath(path,mpaint);
    }

    private void towerPolygons(Canvas canvas) {
        Path path = new Path();

        canvas.drawPoint(0,0,mpaint);
        int mr = r*2/3;
        path.moveTo(mr,0);
        //  canvas.drawText((int)(x+centerX+0.5)+" "+(int)(y+centerY+0.5),(int)(x+centerX+0.5),(int)(y+centerY+0.5),mpaint);
        for(int i=1;i<=polygonsEdgeCount-1;++i ){
            double tx = mr * Math.cos(i*angle);
            double ty = mr * Math.sin(i*angle);
            path.lineTo((int)(tx+0.5),(int)(ty+0.5));
        }


        path.close();
        mpaint.setStyle(Paint.Style.FILL);
        int color = polygonsColor&0x00ffffff;
        mpaint.setColor(0x40000000|color);
        canvas.drawPath(path,mpaint);
    }

    private void dataPolygons(Canvas canvas) {
        //测试数据
        int mr[]={(int)(0.8*r),(int)(0.3*r),(int)(0.5*r),(int) (0.2*r),(int)(0.6*r),(int)(0.1*r),(int)(0.44*r)};
        Path path = new Path();
        canvas.drawPoint(0,0,mpaint);

        path.moveTo((int)(r*percentageS.get(0)+0.5),0);
        for(int i=1;i<=polygonsEdgeCount-1;++i ){

            double tx = (int)(r*percentageS.get(i)+0.5) * Math.cos(i*angle);
            double ty = (int)(r*percentageS.get(i)+0.5) * Math.sin(i*angle);;
            path.lineTo((int)(tx+0.5),(int) (ty+0.5));
        }
        path.close();
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setColor(dataPolygonsColor);
        canvas.drawPath(path,mpaint);
    }

    private void Polygons(Canvas canvas) {
        Path path = new Path();
        canvas.drawPoint(0,0,mpaint);
        path.moveTo(r,0);
        for(int i=1;i<=polygonsEdgeCount-1;++i ){
            double tx = r * Math.cos(i*angle);
            double ty = r * Math.sin(i*angle);
            path.lineTo((int)(tx+0.5),(int)(ty+0.5));

        }
        path.close();
        mpaint.setStyle(Paint.Style.FILL);
        int color = polygonsColor&0x00ffffff;
        mpaint.setColor(0x33000000|color);
        canvas.drawPath(path,mpaint);
    }

    public void setArrayStr(ArrayList<String> list){
        attrStr.clear();
        for(int i=0;i<list.size();++i){
            String s = new String();
            s = list.get(i);
            attrStr.add(s);
        }
        postInvalidate();
    }

    public void setPercentageS(ArrayList<Double> list){
        percentageS.clear();
        for(int i=0;i<list.size();++i){
            //Double a = new Double(list.get(i));
            percentageS.add(list.get(i));
        }
        postInvalidate();
    }


}
