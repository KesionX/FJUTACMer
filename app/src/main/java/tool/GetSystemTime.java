package tool;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2016/10/10.
 */
public class GetSystemTime {

    public static String getSystemTime(){
        SimpleDateFormat    sDateFormat    =   new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String    date    =    sDateFormat.format(new    java.util.Date());
        return date;
    };
}
