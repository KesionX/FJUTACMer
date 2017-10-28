package ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/10/1.
 */
public class TabViewPager extends ViewPager{
    public TabViewPager(Context context) {
        this(context,null);
    }

    public TabViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
