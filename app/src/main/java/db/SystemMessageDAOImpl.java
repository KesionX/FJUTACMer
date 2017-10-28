package db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import bean.SystemMessage;
import db.dbDAO.SystemMessageDAO;

/**
 * Created by Administrator on 2016/10/10.
 */
public class SystemMessageDAOImpl implements SystemMessageDAO{

    private DbHelper dbHelper = null;
    public static final String INSERT ="insert into "+DbHelper.TABLE_SYSTEM_MESSAGE+"("+DbHelper.ACCOUNT+","+DbHelper.MESSAGE+","+DbHelper.DATE_TIME+") values(?,?,?)";
    public static final String SELECT_MESSAGE = "select * from "+DbHelper.TABLE_SYSTEM_MESSAGE+" where "+DbHelper.ACCOUNT+" = ?";

    public SystemMessageDAOImpl(Context context, int version){
        dbHelper = DbHelper.getDbHelper(context,version);
    }



    @Override
    public synchronized void insertSystemMessage(String account,String message, String datetime) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(INSERT,new String[]{account,message,datetime});
        db.close();
    }

    @Override
    public ArrayList<SystemMessage> getSystemMessageList(String account) {
        ArrayList<SystemMessage> messageArrayList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_MESSAGE,new String[]{account});
        while (cursor.moveToNext()){
            String maccount = cursor.getString(cursor.getColumnIndex(DbHelper.ACCOUNT));
            String mmessage = cursor.getString(cursor.getColumnIndex(DbHelper.MESSAGE));
            String datetime = cursor.getString(cursor.getColumnIndex(DbHelper.DATE_TIME));
            messageArrayList.add(new SystemMessage(maccount,mmessage,datetime));
        }
       cursor.close();
        db.close();
        ArrayList<SystemMessage> list = new ArrayList<>();
        for(int i=messageArrayList.size()-1;i>=0;--i){
            SystemMessage systemMessage = messageArrayList.get(i);
            list.add(systemMessage);
        }

        return list;
    }
}
