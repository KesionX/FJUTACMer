package adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import bean.recentlyGame;
import tool.OjGameTypAndKind;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/9/28.
 */
public class OjGameAdapter extends BaseAdapter{
    private  LayoutInflater layoutInflater;
    private ArrayList<recentlyGame> list;
    private static final int TYPES = 3;
    public static final int TYPE_ITEM  = 0;
    public   static final int TYPE_TITLE = 1;
    public static final int TYPE_ITEM2 = 2;
   public OjGameAdapter(LayoutInflater layoutInflater,ArrayList<recentlyGame> list){
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
    public int getItemViewType(int position) {
       int type = 1;
        if(list.get(position).getType()==TYPE_ITEM){
           type  = TYPE_ITEM;
       }else if(list.get(position).getType()==TYPE_TITLE){
            type = TYPE_TITLE;
        }else if(list.get(position).getType()==TYPE_ITEM2){
            type = TYPE_ITEM2;
        }
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return TYPES;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        recentlyGame mrecentlyGame = list.get(position);
        GameItemHoldView gameItemHoldView = null;
        GameTitle gameTitle = null;
        GameIitem2ViewHolder gameIitem2ViewHolder = null;
        int type = getItemViewType(position);

        if(convertView==null){
            if(type==TYPE_ITEM){
                gameItemHoldView = new GameItemHoldView();
                convertView = layoutInflater.inflate(R.layout.recently_game_item,null);
                gameItemHoldView.ojname = (TextView) convertView.findViewById(R.id.ojgame_ojname);
                gameItemHoldView.ojgameName = (TextView) convertView.findViewById(R.id.ojgame_name);
                gameItemHoldView.gameTime = (TextView) convertView.findViewById(R.id.ojgame_time);
                gameItemHoldView.gamgeWeek = (TextView) convertView.findViewById(R.id.ojgame_week);
                convertView.setTag(gameItemHoldView);
            }else if(type==TYPE_TITLE) {
                gameTitle = new GameTitle();
                convertView = layoutInflater.inflate(R.layout.ojgame_title, null);
                gameTitle.gameTitlename = (TextView) convertView.findViewById(R.id.ojgame_titlename);
                convertView.setTag(gameTitle);
            }else if(type==TYPE_ITEM2){
                gameIitem2ViewHolder = new GameIitem2ViewHolder();
                convertView = layoutInflater.inflate(R.layout.recently_game_item2,null);
                gameIitem2ViewHolder.ojname = (WebView) convertView.findViewById(R.id.recently_game_ben_oj_title);
                gameIitem2ViewHolder.access = (TextView) convertView.findViewById(R.id.recently_game_ben_oj_access);
                gameIitem2ViewHolder.begin = (TextView) convertView.findViewById(R.id.recently_game_ben_oj_begin);
                gameIitem2ViewHolder.end = (TextView) convertView.findViewById(R.id.recently_game_ben_oj_end);
                gameIitem2ViewHolder.kind = (TextView) convertView.findViewById(R.id.recently_game_ben_oj_kind);
                convertView.setTag(gameIitem2ViewHolder);
            }

        }else{
            if(type==TYPE_ITEM){
                gameItemHoldView = (GameItemHoldView) convertView.getTag();
            }else if(type==TYPE_TITLE){
                gameTitle = (GameTitle) convertView.getTag();
            }else if(type==TYPE_ITEM2){
                gameIitem2ViewHolder = (GameIitem2ViewHolder) convertView.getTag();
            }
        }
        if(type==TYPE_ITEM){
            gameItemHoldView.ojname.setText(mrecentlyGame.getOj());
            gameItemHoldView.ojgameName.setText(mrecentlyGame.getName());
            gameItemHoldView.gameTime.setText(mrecentlyGame.getStart_time());
            gameItemHoldView.gamgeWeek.setText(mrecentlyGame.getWeek());
        }else if(type==TYPE_TITLE){
            gameTitle.gameTitlename.setText(mrecentlyGame.getName());
        }else if(type == TYPE_ITEM2){
            gameIitem2ViewHolder.ojname.loadDataWithBaseURL(null,mrecentlyGame.getName(),"text/html","UTF-8",null);
            gameIitem2ViewHolder.kind.setText(OjGameTypAndKind.getKind(mrecentlyGame.getKind()));
            gameIitem2ViewHolder.access.setText(OjGameTypAndKind.getAccess(mrecentlyGame.getAccess()));
            gameIitem2ViewHolder.begin.setText(mrecentlyGame.getBegin());
            gameIitem2ViewHolder.end.setText(mrecentlyGame.getEnd());
        }
        return convertView;
    }

    static class GameItemHoldView{
        TextView ojname;
        TextView ojgameName;
        TextView gameTime;
        TextView gamgeWeek;
    }

    static class GameTitle{
        TextView gameTitlename;
    }

    static class GameIitem2ViewHolder{
        WebView ojname;
        TextView access;
        TextView kind;
        TextView begin;
        TextView end;
    }
}
