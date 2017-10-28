package ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/2/23.
 */
public class ColorTextView extends TextView{
    public ColorTextView(Context context) {
        super(context);
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setColor(int rating){
       int color = getMyTextColor(rating);
       // int color = 0x00C0FF;

        this.setTextColor(color);

    }

    private int getMyTextColor(int rating){
        if(rating==-100000) return 0x000000;
        if(rating<1000) return 0x808080;
        if(rating<1200) return 0x40C040;
        if(rating<1400) return 0x00FF00;
        if(rating<1500) return 0x00C0FF;
        if(rating<1600) return 0x0000FF;
        if(rating<1700) return 0xC000FF;
        if(rating<1900) return 0xFF00FF;
        if(rating<2000) return 0xFF0080;
        if(rating<2200) return 0xFF0000;
        if(rating<2600) return 0xFF8000;
        return 0xFFD700;
    }

    public void setBackColor(int rating){
        int color = getMyBackColor(rating);
       // int color = 0x00C0FF;
        this.setBackgroundColor(color);
    }

    private int getMyBackColor(int rating){
        if(rating==-100000) return 0x33000000;
        if(rating<1000) return 0x33808080;
        if(rating<1200) return 0x3340C040;
        if(rating<1400) return 0x3300FF00;
        if(rating<1500) return 0x3300C0FF;
        if(rating<1600) return 0x330000FF;
        if(rating<1700) return 0x33C000FF;
        if(rating<1900) return 0x33FF00FF;
        if(rating<2000) return 0x33FF0080;
        if(rating<2200) return 0x33FF0000;
        if(rating<2600) return 0x33FF8000;
        return 0x33FFD700;
    }




}
