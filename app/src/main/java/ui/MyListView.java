package ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/9/29.
 */
public class MyListView extends ListView implements AbsListView.OnScrollListener{
    private View footer;
    public boolean isloading;
    private int TotalItemCount;
    private int lastVisibleItem;
    private LoadListener LoadListener;
    private PtrFrameLayout ptrFrameLayout;
    private MyScrollListener myScrollListener;
    public MyListView(Context context) {
        super(context);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        footer =inflater.inflate(R.layout.listview_foot_layout,null);
        footer.findViewById(R.id.listview_foot_imageview).setVisibility(View.GONE);
        addFooterView(footer);
        this.setOnScrollListener(this);

    }



    public void setptrFrameLayout(PtrFrameLayout ptrFrameLayout){
        this.ptrFrameLayout = ptrFrameLayout;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(this.myScrollListener!=null)
        switch(scrollState){
            case OnScrollListener.SCROLL_STATE_IDLE://空闲状态
                this.myScrollListener.show();
                break;
            case OnScrollListener.SCROLL_STATE_FLING://滚动状态
                this.myScrollListener.miss();
                break;
            case OnScrollListener.SCROLL_STATE_TOUCH_SCROLL://触摸后滚动
                this.myScrollListener.miss();
                break;
        }

        if(lastVisibleItem==TotalItemCount&&
                scrollState ==SCROLL_STATE_IDLE&&LoadListener!=null){
            if(!isloading){
                isloading = true;
                footer.findViewById(R.id.listview_foot_imageview).setVisibility(VISIBLE);
                if(LoadListener!=null)
                    LoadListener.onLoad();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
     //   Log.d("main","firstVisibleItem"+firstVisibleItem+" visibleItemCount"+visibleItemCount+" totalItemCount"+totalItemCount);
        View firstView = view.getChildAt(firstVisibleItem);
        if(ptrFrameLayout!=null)
        {
            if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                ptrFrameLayout.setEnabled(true);
            } else {
                ptrFrameLayout.setEnabled(false);
            }
        }

        lastVisibleItem = firstVisibleItem + visibleItemCount;
        TotalItemCount = totalItemCount;
    }

    public void loadcomplete(){
        this.isloading = false;
        footer.findViewById(R.id.listview_foot_imageview).setVisibility(View.GONE);

    }

    public void setInterface(LoadListener LoadListener)
    {
        this.LoadListener = LoadListener;
    }

    public interface LoadListener{
        public void onLoad();
    }


    public void setMyScrollListener(MyScrollListener myScrollListener){
        this.myScrollListener = myScrollListener;
    }
    public interface MyScrollListener{
        public void show();
        public void miss();
    }
}
