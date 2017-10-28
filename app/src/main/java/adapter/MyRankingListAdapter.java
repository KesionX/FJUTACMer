package adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import Catch.MyImageLoader;
import bean.RankingAcmer;
import ui.CircleImageView;
import ui.ColorTextView;
import url.MYURL;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2017/2/23.
 */
public class MyRankingListAdapter extends BaseAdapter{

    private ArrayList<RankingAcmer> list;
    private LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    public MyRankingListAdapter(Context context,LayoutInflater layoutInflater, ArrayList<RankingAcmer> list){
        this.layoutInflater= layoutInflater;
        this.list = list;
        imageLoader = MyImageLoader.getMyImageLoader(context);
    }


    public void setList(ArrayList<RankingAcmer> list){
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

        try {
            RankingAcmer rankingAcmer = list.get(position);
            ViewHorld viewHorld = null;

            if(convertView==null){
                viewHorld = new ViewHorld();
                convertView = layoutInflater.inflate(R.layout.ranking_listview_item,null);
                viewHorld.acb = (TextView) convertView.findViewById(R.id.ranking_acb);
                viewHorld.acmerTx = (CircleImageView) convertView.findViewById(R.id.ranking_tx);
                viewHorld.nick = (ColorTextView) convertView.findViewById(R.id.ranking_nick);
                viewHorld.acNum = (TextView) convertView.findViewById(R.id.ranking_acnum);
                viewHorld.motto = (TextView) convertView.findViewById(R.id.ranking_motto);
                viewHorld.rankingRank = (ColorTextView) convertView.findViewById(R.id.ranking_rank);
                viewHorld.rating = (ColorTextView) convertView.findViewById(R.id.ranking_rating);
                viewHorld.ratingNum = (TextView) convertView.findViewById(R.id.ranking_ratingnum);
                convertView.setTag(viewHorld);

            }else{
                viewHorld = (ViewHorld) convertView.getTag();
            }

            ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(viewHorld.acmerTx,
                    R.mipmap.default_touxiang,R.mipmap.default_touxiang);
            imageLoader.get(MYURL.ROOT_ICON_URL+ MYURL.HEADURL+
                    rankingAcmer.getUserName().trim()+".jpg",imageListener);

            int rating = rankingAcmer.getRating();

            viewHorld.rating.setText(rating<0?"-":rating+"");

            //viewHorld.rating.setTextColor(0x40C040);


            //  viewHorld.nick.setColor(rating);
            viewHorld.nick.setText(rankingAcmer.getNick());

            viewHorld.rankingRank.setBackColor(rating);
            viewHorld.rankingRank.setText(list.indexOf(rankingAcmer)+1+"");

            viewHorld.acb.setText(rankingAcmer.getAcb()+"");
            viewHorld.motto.setText(rankingAcmer.getMotto());
            viewHorld.acNum.setText(rankingAcmer.getAcNum()+"");

            viewHorld.ratingNum.setText(rankingAcmer.getRatingNum()==0 ? "-":rankingAcmer.getRatingNum()+"");


        }catch (Exception e){

            Log.d("keerrorke",e.getMessage());
        }

        return convertView;
    }


    static class ViewHorld{
        ColorTextView rankingRank;
        ColorTextView nick;
        CircleImageView acmerTx;
        TextView acNum;
        TextView acb;
        TextView motto;
        ColorTextView rating;
        TextView ratingNum;
    }
}
