package tool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/9.
 */
public class LogPreferences {

    public static LogPreferences logPreferences= null;
    public  SharedPreferences sharedPreferences=null;
    public static final String AP_PRE = "ap_pre";
    public static final String AT = "at";
    public static final String PS = "ps";
    public static final String ISOK="isok";

    public LogPreferences(Context context,String preName){
        this.sharedPreferences = context.getSharedPreferences(preName,Context.MODE_WORLD_READABLE);
    }

    public static LogPreferences getLogPreferences(Context context,String preName){
        if(logPreferences==null){
            logPreferences = new LogPreferences(context,preName);
        }
        return logPreferences;
    }

    public SharedPreferences getSharedPreferences(){
        return sharedPreferences;
    }

    public void putSharedPreferences(String at,String ps,boolean isok){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(AT,at);
        editor.putString(PS,ps);
        editor.putBoolean(ISOK,isok);
        editor.commit();
    }

    public String getAt(){
        String at = sharedPreferences.getString(AT,"aaaaaaaa");
        return at;
    }

    public  String getPs(){
        String ps = sharedPreferences.getString(PS,"aaaaaaaa");
        return ps;
    }

    public boolean getIsok(){
        boolean isok = sharedPreferences.getBoolean(ISOK,false);
        return isok;
    }

}
