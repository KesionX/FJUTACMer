package ui;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyToast {

    private static Toast toast  = null;

    public static void ShowToast(Activity context, String meg, int time){

        if(toast==null){
            toast = new Toast(context);
        }
        try {
            LayoutInflater layoutInflater = context.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.toast_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.toast_text);
            textView.setText(meg + "");
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 80);
            toast.setDuration(time);
            toast.setView(view);
            toast.show();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
