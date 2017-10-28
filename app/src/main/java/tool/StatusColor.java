package tool;

import android.app.Activity;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

/**
 * Created by Administrator on 2016/9/20.
 */
public class StatusColor {

    public static void setTranslucentStatus(Activity context , int R_color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            context.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            context.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemStatusManager tintManager = new SystemStatusManager(context);
            tintManager.setStatusBarTintEnabled(true);
            // 设置状态栏的颜色
            tintManager.setStatusBarTintResource(R_color);
            context.getWindow().getDecorView().setFitsSystemWindows(true);
        }
    }
}
