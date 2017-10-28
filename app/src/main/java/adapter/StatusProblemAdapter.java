package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Catch.MyImageLoader;
import bean.SingleMegSelf;
import bean.StatusProblem;
import tool.ProblemStatusDec;
import ui.CircleImageView;
import url.MYURL;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/10/4.
 */
public class StatusProblemAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<StatusProblem> list;
    private ImageLoader imageLoader;
    public StatusProblemAdapter(Context context,LayoutInflater layoutInflater, ArrayList<StatusProblem> list) {
        this.layoutInflater = layoutInflater;
        this.list = list;
        imageLoader = MyImageLoader.getMyImageLoader(context);
    }


    public void setList(ArrayList<StatusProblem> list){
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
        StatusProblem statusProblem = list.get(position);
        ViewHolder viewHolder = null;
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.problem_status_item,null);
            viewHolder =  new ViewHolder();
            viewHolder.pid = (TextView) convertView.findViewById(R.id.status_problem_pid);
            viewHolder.lang = (TextView) convertView.findViewById(R.id.status_problem_lang);
            viewHolder.touxiang = (CircleImageView) convertView.findViewById(R.id.status_problem_tx);
            viewHolder.nick = (TextView) convertView.findViewById(R.id.status_problem_nick);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status_problem_status);
            viewHolder.timeUsed = (TextView) convertView.findViewById(R.id.status_problem_timeUsed);
            viewHolder.memoryUsed = (TextView) convertView.findViewById(R.id.status_problem_memoryUsed);
            viewHolder.codeLength = (TextView) convertView.findViewById(R.id.status_problem_codeLength);
            viewHolder.submitTime  = (TextView) convertView.findViewById(R.id.status_problem_submittime);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.pid.setText(statusProblem.getPid()+"");
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHolder.touxiang,
                R.mipmap.default_touxiang,R.mipmap.default_touxiang);
        imageLoader.get(MYURL.ROOT_ICON_URL+ MYURL.HEADURL+
                statusProblem.getUsername()+".jpg",imageListener);
        viewHolder.nick.setText(statusProblem.getNick());
        viewHolder.status.setText(ProblemStatusDec.getResultString(statusProblem.getResult()));
        if(statusProblem.getResult()!=1)
           viewHolder.status.setBackgroundResource(R.drawable.text_back_shape);
        else
            viewHolder.status.setBackgroundResource(R.drawable.text_back_shape5);
        //viewHolder.lang.setText(statusProblem.get);
        viewHolder.lang.setText(ProblemStatusDec.getLangString(statusProblem.getLang()));
        viewHolder.timeUsed.setText(statusProblem.getTimeUsed());
        viewHolder.memoryUsed.setText(statusProblem.getMemoryUsed());
        viewHolder.codeLength.setText(statusProblem.getCodeLength());
        viewHolder.submitTime.setText(statusProblem.getSubmitTime());
        return convertView;
    }


    static class ViewHolder {
        TextView pid;
        TextView lang;
        CircleImageView touxiang;
        TextView nick;
        TextView status;
        TextView timeUsed;
        TextView memoryUsed;
        TextView codeLength;
        TextView submitTime;

    }
}
