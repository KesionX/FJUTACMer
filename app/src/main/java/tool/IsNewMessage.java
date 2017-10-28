package tool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/11.
 */
public class IsNewMessage {
    public static SharedPreferences sharedPreferences=null;
    private static IsNewMessage isNewMessage = null;
    public static final String IS_NEW_MESSAGE_PRE = "is_new_message_pre";
    public static final String HAVE_MESSAGE = "have_message";

    public IsNewMessage(Context context, String preName){
        this.sharedPreferences = context.getSharedPreferences(preName,Context.MODE_WORLD_READABLE);
    }

    public static IsNewMessage getIsNewMessage(Context context, String preName){
        if(isNewMessage==null){
            isNewMessage = new IsNewMessage(context,preName);
        }
        return isNewMessage;
    }

    public static void setPreNewMessage(boolean isNew){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(HAVE_MESSAGE,isNew);
        editor.commit();
    }

    public  boolean getIsNew(){
        return sharedPreferences.getBoolean(HAVE_MESSAGE,false);
    }

}
