package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bean.SystemMessage;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/10/10.
 */
public class MySystemMessageAdapter extends BaseAdapter{
    private ArrayList<SystemMessage> list;
    private LayoutInflater layoutInflater;

    public MySystemMessageAdapter(LayoutInflater layoutInflater,ArrayList<SystemMessage> list){
        this.layoutInflater = layoutInflater;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SystemMessage systemMessage = list.get(position);
        ViewHolder viewHolder =null;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.system_message_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.message = (TextView) convertView.findViewById(R.id.system_message_msg);
            viewHolder.datetime = (TextView) convertView.findViewById(R.id.system_message_datetime);
            convertView.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.message.setText(systemMessage.getMessage());
        viewHolder.datetime.setText(systemMessage.getDatetime());

        return convertView;
    }

    static class ViewHolder{
        TextView message;
        TextView datetime;
    }

}
