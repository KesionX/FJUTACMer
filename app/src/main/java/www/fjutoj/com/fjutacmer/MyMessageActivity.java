package www.fjutoj.com.fjutacmer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.MySystemMessageAdapter;
import bean.SystemMessage;
import db.DbHelper;
import db.SystemMessageDAOImpl;
import db.dbDAO.SystemMessageDAO;
import tool.LogPreferences;
import tool.StatusColor;

public class MyMessageActivity extends BaseFragmentActivity implements View.OnClickListener{

    private ListView listView;
    private MySystemMessageAdapter mySystemMessageAdapter;
    private ArrayList<SystemMessage> messageArrayList;
   private SystemMessageDAO systemMessageDAO;
    private Button backbt;
    @Override
    protected void rootViewFind() {
        rootView = findViewById(R.id.mymessage);
    }

    @Override
    public void onBaseCreate(Bundle savedInstanceState) {
        StatusColor.setTranslucentStatus(this,R.color.colortransparent);
        setContentView(R.layout.mymessage_layout);
        InitfindView();
        initData();

    }

    private void InitfindView() {
        listView = (ListView) findViewById(R.id.system_message_listview);
        listView.setDividerHeight(0);
        backbt = (Button) findViewById(R.id.back_bt);
        backbt.setOnClickListener(this);
    }

    private void initData() {
        messageArrayList = new ArrayList<>();
        systemMessageDAO = new SystemMessageDAOImpl(this, DbHelper.VERSION);
        messageArrayList = systemMessageDAO.getSystemMessageList(LogPreferences.getLogPreferences(this,LogPreferences.AP_PRE).getAt());
        mySystemMessageAdapter = new MySystemMessageAdapter(getLayoutInflater(),messageArrayList);
        listView.setAdapter(mySystemMessageAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        listView = null;
        mySystemMessageAdapter = null;
        messageArrayList = null;
        systemMessageDAO = null;
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.back_bt:
             endRootAnimation();
             break;
     }
    }
}
