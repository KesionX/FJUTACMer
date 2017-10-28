package ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2017/3/12.
 */
public class LineChartView extends View {


    private int viewWidth;
    private int viewHeight;
    private Paint mpaint;

    //矩阵
    private Matrix mMatrix;

    //x坐标偏移值
    private int xTranslate;
    //y坐标偏移值
    private int yTranslate;
    //x轴颜色
    private int xColor;
    private static int X_DEFAULT_COLOR = 0xff000000;
    //y轴颜色
    private  int yColor;
    private static int Y_DEFAULT_COLOR = 0xff000000;
    //字体大小
    private int size = 8;
    //x轴单位值
    private double xCell;
    //x轴最低值
    private int xMin;
    //x轴最大值
    private int xMax;
    //x轴分段值
    private int xArrat[] = {0,1000,1200,1400,1500,1600,1700,1900,2000,2200,2600};
    //xdata最低值
    private int xDataMin;
    //xdata最大值
    private int xDataMax;
    //xData颜色
    private int xDataTextColor;
    private static int X_DATE_TEXT_DEFAULT_COLOR = 0xffff0000;

    //y轴最低值
    private double yMin;
    //y轴最高值
    private double yMax;

    //y日期最小值
    private double  yDateMin;
    //y日期最大值
    private double yDateMax;
    //y单元位值
    private double yCell;
    //y等分数
    private int yCount = 10;
    //yDate颜色
    private int yDateTextColor;
    private static int Y_DATE_TEXT_DEFAULT_COLOR = 0xff00ffff;
    //r半径
    private int rCircle;

    //小圆颜色
    private int circleColor;
    private static int CIRCLE_DEFAULT_COLOR = 0xff00ffff;
    //小小圆半径
    private int rLittleCircle;
    //小小圆颜色
    private int littleCircleColor;
    private static int LITTLE_CIRCLE_DEFAULT_COLOR = 0xffffffff;
    //折线颜色
    private int lineChartColor;
    private static int LINE_CHART_DEFAULT_COLOR = 0xffff0000;

    private ArrayList<RatingPoint> list;
    //按下的点
    private int downIndex;
    //判断是否抬起手
    private boolean isUp = true;
    //比赛数据颜色
    private int dataMessageColor;
    private static int DATA_MESSAGE_DEFAULT_COLOR =  0xffffffff;

    private int downCircleColor;
    private static int DOWN_CIRCLE_DEFAULT_COLOR = 0xFFFF0000;

    private SimpleDateFormat sdf ;
    public LineChartView(Context context) {
        this(context,null);
    }

    public LineChartView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        initNew();
        initPaint();
       // initMatrix();
        initXyColor(context,attrs);//
        initCircle(context,attrs);//
        initData();
        initLine(context,attrs);//
        initDataMessageColor(context,attrs);//

    }

    private void initDataMessageColor(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);

        dataMessageColor = a.getColor(R.styleable.LineChartView_dataMessageColor,DATA_MESSAGE_DEFAULT_COLOR);
    }

    private void initLine(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        lineChartColor = a.getColor(R.styleable.LineChartView_lineChartColor,LINE_CHART_DEFAULT_COLOR);
    }

    private void initData() {

       // xDataMin = 100;
        //xDataMax = 100;
       // yDateMin = System.currentTimeMillis();
        //yDateMax = System.currentTimeMillis();

    }

    private void initNew() {
        sdf = new SimpleDateFormat("yyyy/MM/dd");
        list = new ArrayList<>();
    }

    private void initCircle(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);

        circleColor = a.getColor(R.styleable.LineChartView_circleColor,CIRCLE_DEFAULT_COLOR);
        rCircle = 4;

        littleCircleColor = a.getColor(R.styleable.LineChartView_littleCircleColor,LITTLE_CIRCLE_DEFAULT_COLOR);
        Log.d("little",littleCircleColor+"");
        rLittleCircle = 2;

        downCircleColor = a.getColor(R.styleable.LineChartView_downCircleColor,DOWN_CIRCLE_DEFAULT_COLOR);
    }

    private void initXyColor(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        xColor = a.getColor(R.styleable.LineChartView_xColor,X_DEFAULT_COLOR);
        yColor = a.getColor(R.styleable.LineChartView_yColor,Y_DEFAULT_COLOR);
        xDataTextColor = a.getColor(R.styleable.LineChartView_xDateTextColor,X_DATE_TEXT_DEFAULT_COLOR);
        yDateTextColor = a.getColor(R.styleable.LineChartView_yDateTextColor,Y_DATE_TEXT_DEFAULT_COLOR);
    }

    private void initMatrix() {
        mMatrix = new Matrix();
        mMatrix.setValues(new float[]{
                1,0,0,
                0,-1,0,
                0,0,1
        });
    }

    private void initPaint() {
        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setStyle(Paint.Style.STROKE);
        //mpaint.setColor(0xffff0000);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        viewWidth = getWidth();
        viewHeight = getHeight();

        viewWidth = viewWidth - getPaddingLeft()-getPaddingRight()-size*5;
        viewHeight = viewHeight - getPaddingBottom() - getPaddingTop()-size*3;
        xTranslate = getPaddingLeft()+size*4;
        yTranslate = getHeight()-getPaddingBottom()-size*2;


       // initXDate();
       // initYDate();
        Log.d("draw","xm:"+xDataMin+"ym:"+xDataMax);
    }

    private void initYDate() {

        double datel = (yDateMax-yDateMin)/15;
        if(yDateMax == yDateMin)
            datel = 200000000;
        yMin = yDateMin - datel;
        yMax = yDateMax + datel;
        yCell = (double)viewWidth/(yMax - yMin);
    }

    private void initXDate() {

        xMin = getMinRange(xDataMin);
        xMax = getMaxRange(xDataMax);
        if(xMin==750){
            xArrat[0]=750;
        }

        if(xDataMin>=20&&xDataMin<1000)
        {
            xArrat[0]=xDataMin-50;
            xMin = xDataMin-50;
        }

        if(xMax>2600){
            xArrat[xArrat.length-1]=xDataMax+100;
            xMax = xDataMax+100;
        }
        xCell = (double)viewHeight/(xMax-xMin);
    }


    @Override
    public void draw(Canvas canvas) {
        initXDate();
        initYDate();
        Log.d("draw","draw"+xMin+" "+xMax);
        super.draw(canvas);
        canvas.save();
        initXY(canvas);
        drawX(canvas);
        drawY(canvas);
        drawXAxis(canvas);
        drawYAxis(canvas);
        drawBackColor(canvas);
        drawLineChart(canvas);

        drawText(canvas);
        canvas.restore();

    }

    private void drawText(Canvas canvas) {

        if(this.list!=null&&this.list.size()!=0&&isUp==false){
            mpaint.setColor(dataMessageColor);
            mpaint.setTextSize(14);
            canvas.drawText(this.list.get(downIndex).getDataMessage()+" rank:"+this.list.get(downIndex).getRank(),
                    50,-viewHeight+50,mpaint);


            mpaint.setStyle(Paint.Style.FILL);
            for(int i=0;i<this.list.size();++i){
                if(i==downIndex){
                    mpaint.setColor(downCircleColor);
                    canvas.drawCircle((int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5),-(int)((this.list.get(i).getRating()-xMin)*xCell+0.5),rCircle,mpaint);

                    mpaint.setColor(littleCircleColor);
                    canvas.drawCircle((int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5),-(int)((this.list.get(i).getRating()-xMin)*xCell+0.5),rLittleCircle,mpaint);
                    break;
                }
            ;
                //  Log.d("kkk",(int)((this.list.get(i).getRating()-xMin)*xCell+0.5)+" "+(int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5));
            }

        }
        //canvast
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
              //  event.getX();
                int x =  (int)(event.getX()+0.5)-xTranslate;
                int y = -(getHeight()-(int)(event.getY()+0.5)-getPaddingBottom()-size*2);

                downIndex = getDownIndex(x,y);
                isUp = false;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isUp = true;
                break;
        }

        invalidate();

        return super.onTouchEvent(event);
    }

    private int getDownIndex(int x, int y) {
        int index=0;
        Log.d("point","down："+x+" "+y);
        if(this.list!=null&&this.list.size()!=0){
            for(int i=0;i<this.list.size();++i){
                int tx = (int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5);
                int ty = -(int)((this.list.get(i).getRating()-xMin)*xCell+0.5);
                //canvas.drawCircle((int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5),-(int)((this.list.get(i).getRating()-xMin)*xCell+0.5),rCircle,mpaint);
             //   Log.d("kkk",(int)((this.list.get(i).getRating()-xMin)*xCell+0.5)+" "+(int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5));
                Log.d("point",tx+" "+ty);
                if(tx-30<x&&x<tx+30&&ty-30<y&&y<ty+30){
                    index = i;
                    break;
                }
            }

        }

        return index;
    }

    private void drawLineChart(Canvas canvas) {

        //canvas.drawCircle((float) yCell*2000000000,-(float)((1304-xMin)*xCell+0.5),rCircle,mpaint);

        if(this.list!=null&&this.list.size()!=0){

            mpaint.setColor(lineChartColor);
            for(int i=0;i<this.list.size()-1;++i ){

                canvas.drawLine((int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5),-(int)((this.list.get(i).getRating()-xMin)*xCell+0.5),
                        (int)((this.list.get(i+1).getDateTime()-yMin)*yCell+0.5),-(int)((this.list.get(i+1).getRating()-xMin)*xCell+0.5),mpaint);

            }



            mpaint.setStyle(Paint.Style.FILL);
            for(int i=0;i<this.list.size();++i){
                mpaint.setColor(circleColor);
                canvas.drawCircle((int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5),-(int)((this.list.get(i).getRating()-xMin)*xCell+0.5),rCircle,mpaint);

                mpaint.setColor(littleCircleColor);
                canvas.drawCircle((int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5),-(int)((this.list.get(i).getRating()-xMin)*xCell+0.5),rLittleCircle,mpaint);
              //  Log.d("kkk",(int)((this.list.get(i).getRating()-xMin)*xCell+0.5)+" "+(int)((this.list.get(i).getDateTime()-yMin)*yCell+0.5));
            }

        }

        mpaint.setStyle(Paint.Style.STROKE);
    }

    private void drawBackColor(Canvas canvas) {
        mpaint.setStyle(Paint.Style.FILL);
        int i=0;
        int color;
        for(;i<xArrat.length;++i){
            if (xMin==xArrat[i])
                break;
        }
        for(;i<xArrat.length-1;++i){

            color = getMyBackColor(xArrat[i]);
            mpaint.setColor(color);
            int h = xArrat[i]-xMin;
            int datel = xArrat[i+1];
            if(datel>2600)
                datel = 2600;
            int hX = datel-xMin;
            if(xArrat[i+1]>xMax)
                break;
            canvas.drawRect(0,-(int)(hX*xCell+0.5),viewWidth,-(int)(h*xCell+0.5),mpaint);
          //  canvas.drawLine(0,-(int)(h*xCell+0.5),5,-(int)(h*xCell+0.5),mpaint);
            //

        }
        if(i==xArrat.length-1){
            color = getMyBackColor(xArrat[xArrat.length-1]);
            mpaint.setColor(color);
            int h = 2600-xMin;
            int datel = xArrat[xArrat.length-1];
            int hX = datel-xMin;
            canvas.drawRect(0,-(int)(hX*xCell+0.5),viewWidth,-(int)(h*xCell+0.5),mpaint);
        }
        mpaint.setStyle(Paint.Style.STROKE);
    }
    private int getMyBackColor(int rating){
       // if(rating==0) return 0xFFFFFFFF;
        if(rating<1000) return 0x88808080;
        if(rating<1200) return 0x8840C040;
        if(rating<1400) return 0x8800FF00;
        if(rating<1500) return 0x8800C0FF;
        if(rating<1600) return 0x880000FF;
        if(rating<1700) return 0x88C000FF;
        if(rating<1900) return 0x88FF00FF;
        if(rating<2000) return 0x88FF0080;
        if(rating<2200) return 0x88FF0000;
        if(rating<2600) return 0x88FF8000;
        return 0x88FFD700;
    }


    private void drawYAxis(Canvas canvas) {
        double datel = (yMax-yMin)/yCount;
        mpaint.setTextSize(7);

        for(int i=1;i<=yCount;++i){
            int t = (int)(i*datel*yCell+0.5);
            mpaint.setColor(yColor);
            canvas.drawLine(t,0,t,-5,mpaint);
            mpaint.setColor(yDateTextColor);
            canvas.drawText(TimeToDate((long)(yMin+datel*i)),t-size*2,size*1,mpaint);
        }
    //    long  a = (long)1489152600000.0;
        mpaint.setTextSize(size);
      /*  canvas.drawText(TimeToDate(a),(long)(datel*yCell+0.5)-size*2,size*1,mpaint);
       ;
        canvas.drawText(TimeToDate(a),(long)(datel*yCell+0.5)*2-size*2,size*1,mpaint);*/
    }

    private void drawXAxis(Canvas canvas) {
        mpaint.setTextSize(size);
        float traX = -4*size;
       // canvas.drawText("1000",traX,0,mpaint);
        int index = 0;
        int i=0;
        for(;i<xArrat.length;++i){
            if (xMin==xArrat[i])
                break;
        }
        for(;i<xArrat.length;++i){
            if(xArrat[i]>xMax)
                break;
            int h = xArrat[i]-xMin;
            mpaint.setColor(xDataTextColor);
            canvas.drawText(xArrat[i]+"",traX,-(int)(h*xCell+0.5),mpaint);
            mpaint.setColor(xColor);
            canvas.drawLine(0,-(int)(h*xCell+0.5),5,-(int)(h*xCell+0.5),mpaint);
        }

    }

    private void drawY(Canvas canvas) {
        mpaint.setColor(yColor);
        canvas.drawLine(0,0,viewWidth,0,mpaint);
    }

    private void drawX(Canvas canvas) {
        mpaint.setColor(xColor);
        canvas.drawLine(0,0,0,-viewHeight,mpaint);
    }

    private void initXY(Canvas canvas) {
        canvas.translate(xTranslate,yTranslate);
       // canvas.concat(mMatrix);
    }

    private   int getMinRange(int data){
        int min=0;
        if(data<=-100000) min=0;
        if(data>-100000&&data<1000) min=0;
        if(data>=1000&&data<1200) min=750;
        if(data>=1200&&data<1400) min=1000;
        if(data>=1400&&data<1500) min=1200;
        if(data>=1500&&data<1600) min = 1400;
        if(data>=1600&&data<1700) min = 1500;
        if(data>=1700&&data<1900) min = 1600;
        if(data>=1900&&data<2000) min = 1700;
        if(data>=2000&&data<2200) min = 1900;
        if(data>=2200&&data<2600) min = 2000;
        if(data>=2600) min = 2200;
        return min;
    }


    private   int getMaxRange(int data){
        int max = 0;
        if(data<=-100000) max=1000;
        if(data>-100000&&data<1000) max=1200;
        if(data>=1000&&data<1200) max=1400;
        if(data>=1200&&data<1400) max=1500;
        if(data>=1400&&data<1500) max=1600;
        if(data>=1500&&data<1600) max = 1700;
        if(data>=1600&&data<1700) max = 1900;
        if(data>=1700&&data<1900) max = 2000;
        if(data>=1900&&data<2000) max = 2200;
        if(data>=2000&&data<2200) max = 2600;
        if(data>=2200&&data<2600) max = 2600;
        if(data>=2600) max = data;
        return max;
    }

    private String TimeToDate(long time){

        String date = sdf.format(new Date(time));
        return date;

    }


    public static class RatingPoint{


        private long dateTime;
        private int rating;
        private int rank;
        private String dataMessage;

        public RatingPoint() {
        }

        public RatingPoint(long dateTime, int rating, String dataMessage,int rank) {
            this.dateTime = dateTime;
            this.rating = rating;
            this.rank = rank;
            this.dataMessage = dataMessage;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public double getDateTime() {
            return dateTime;
        }

        public void setDateTime(long dateTime) {
            this.dateTime = dateTime;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getDataMessage() {
            return dataMessage;
        }

        public void setDataMessage(String dataMessage) {
            this.dataMessage = dataMessage;
        }

        @Override
        public String toString() {
            return "RatingPoint{" +
                    "dateTime=" + dateTime +
                    ", rating=" + rating +
                    ", rank=" + rank +
                    ", dataMessage='" + dataMessage + '\'' +
                    '}';
        }
    }

    public void setRankPointList(ArrayList<RatingPoint> list){
        this.list = list;
        setMinX();
        setMaxX();
        setMinY();
        setMaxY();
        Log.d("draw","x:"+xDataMin+"y:"+xDataMax);
        postInvalidate();
    }

    private void setMinX(){
        if(this.list!=null&&this.list.size()!=0){
            int minx = list.get(0).getRating();
            for (int i=1;i<this.list.size();++i) {
                minx = this.list.get(i).getRating() < minx ? this.list.get(i).getRating():minx;
            }
            xDataMin = minx;
        }else{
            xDataMin = 0;
        }
    }


    private void setMaxX(){
        if(this.list!=null&&this.list.size()!=0){
            int maxX = list.get(0).getRating();
            for (int i=1;i<this.list.size();++i) {
                maxX = this.list.get(i).getRating() > maxX ? this.list.get(i).getRating():maxX;
            }
            xDataMax = maxX;
        }else{
            xDataMax = 0;
        }
    }

    private void setMinY(){
        if(this.list!=null&&this.list.size()!=0){
            double miny = list.get(0).getDateTime();
            for (int i=1;i<this.list.size();++i) {
                miny = this.list.get(i).getDateTime() < miny ? this.list.get(i).getDateTime():miny;
            }
            yDateMin = miny;
        }else{
            yDateMin =System.currentTimeMillis();
        }

    }

    private void setMaxY(){
        if(this.list!=null&&this.list.size()!=0){
            double maxy = list.get(0).getDateTime();
            for (int i=1;i<this.list.size();++i) {
                maxy = this.list.get(i).getDateTime() > maxy ? this.list.get(i).getDateTime():maxy;
            }
            yDateMax = maxy;
        }else{
            yDateMax =  System.currentTimeMillis();
        }
    }


}
