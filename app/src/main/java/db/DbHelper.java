package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/10/10.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static DbHelper sDbHelper = null;
    public static final String DB_NAME = "fjutacmer_database.db";
    public static final int VERSION = 6;
    public static final int VERSION_5 = 5;
    public static final int VERSION_4 = 4;
    public static final int VERSION_3 = 3;
    public static final int VERSION_2 = 2;
    public static final int VERSION_1 = 1;
    public static DbHelper dbHelper = null;
   /**
    *创建表
    ***/
    public static final String ACCOUNT = "account";
    public static final String MESSAGE = "message";
    public static final String DATE_TIME = "datetime";
    public static final String TABLE_SYSTEM_MESSAGE = "system_message";
    private static final String CREATE_SYSTEM_MESSAGE = "create table "+TABLE_SYSTEM_MESSAGE+ "("+
           ACCOUNT+" text,"+
           MESSAGE+" text,"+DATE_TIME+" text )";


    public DbHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    public static DbHelper getDbHelper(Context context, int version){
        if(dbHelper==null){
            dbHelper = new DbHelper(context,version);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SYSTEM_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
