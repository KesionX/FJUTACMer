package db.dbDAO;

import java.util.ArrayList;

import bean.SystemMessage;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface SystemMessageDAO {

    public void insertSystemMessage(String account,String message,String datetime);

    public ArrayList<SystemMessage> getSystemMessageList(String account);
}
