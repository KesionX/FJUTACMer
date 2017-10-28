package www.fjutoj.com.fjutacmer;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import db.DbHelper;
import db.SystemMessageDAOImpl;
import db.dbDAO.SystemMessageDAO;
import tool.GetSystemTime;
import tool.IsNewMessage;
import tool.LogPreferences;

/**
 * Created by Administrator on 2016/10/10.
 */
public class SystemMessageReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        // Log.d(TAG, "onReceive - " + intent.getAction() + ", extras: " + AndroidUtil.printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            Log.d(TAG, "JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "接受到推送下来的通知");

            receivingNotification(context,bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "用户点击打开了通知");

          //  openNotification(context,bundle);

        } else {
            Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle){
        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
        Log.d(TAG, " title : " + title);
        String message = bundle.getString(JPushInterface.EXTRA_ALERT);
        Log.d(TAG, "message : " + message);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        Log.d(TAG, "extras : " + extras);

        LogPreferences logPreferences = LogPreferences.getLogPreferences(context,LogPreferences.AP_PRE);
        String account = logPreferences.getAt();
        String datetime = GetSystemTime.getSystemTime();
        SystemMessageDAO systemMessageDAO = new SystemMessageDAOImpl(context, DbHelper.VERSION);
        systemMessageDAO.insertSystemMessage(account,message,datetime);
        Log.d("main_receiver",account+" "+message+" "+datetime);

        IsNewMessage isNewMessage = IsNewMessage.getIsNewMessage(context,IsNewMessage.IS_NEW_MESSAGE_PRE);
        isNewMessage.setPreNewMessage(true);
    }

    private void openNotification(Context context, Bundle bundle){
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            Log.w(TAG, "Unexpected: extras is not a valid json", e);
            return;
        }
/*        if (TYPE_THIS.equals(myValue)) {
            Intent mIntent = new Intent(context, ThisActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        } else if (TYPE_ANOTHER.equals(myValue)){
            Intent mIntent = new Intent(context, AnotherActivity.class);
            mIntent.putExtras(bundle);
            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mIntent);
        }*/
    }
}
