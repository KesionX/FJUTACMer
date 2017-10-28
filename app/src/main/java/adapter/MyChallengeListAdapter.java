package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bean.Block;
import bean.BlockDec;
import bean.ChallengeProblem;
import www.fjutoj.com.fjutacmer.R;

/**
 * Created by Administrator on 2016/10/27.
 */
public class MyChallengeListAdapter extends BaseAdapter{
    public static final int TYPE_ROOT = 0;
    public static final int TYPE_DEC = 1;
    public static final int TYPE_PRO = 2;
    public static final int TYPES = 3;
    private ArrayList list;
    private LayoutInflater layoutInflater;

    public MyChallengeListAdapter(ArrayList list,LayoutInflater layoutInflater){
        this.layoutInflater = layoutInflater;
        this.list = list;
    };


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
        int type = getType(list.get(position));
        return type;
    }

    @Override
    public int getViewTypeCount() {
        return TYPES;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Object o = list.get(position);
        int type = getItemViewType(position);
        RootHolderView rootHolderView = null;
        ProHolderView proHolderView = null;
        DecHolderView decHolderView= null;
        if(convertView==null){
            if(type==TYPE_ROOT){
                rootHolderView = new RootHolderView();
                convertView = layoutInflater.inflate(R.layout.challenge_block_item,null);
                rootHolderView.imageJiao = (ImageView) convertView.findViewById(R.id.root_jiaobiao);
                rootHolderView.txBlockTitle = (TextView) convertView.findViewById(R.id.title);
                rootHolderView.txUserScore = (TextView) convertView.findViewById(R.id.userScore);
                rootHolderView.txScore = (TextView) convertView.findViewById(R.id.Score);
               convertView.setTag(rootHolderView);

            }else if(type == TYPE_DEC){
                decHolderView = new DecHolderView();
                convertView =  layoutInflater.inflate(R.layout.challenge_dec_item,null);
                decHolderView.dec = (TextView) convertView.findViewById(R.id.challenge_title_dec);
                convertView.setTag(decHolderView);

            }else if(type == TYPE_PRO){
                proHolderView = new ProHolderView();
                convertView = layoutInflater.inflate(R.layout.challenge_pro_item,null);
                proHolderView.txTpid = (TextView) convertView.findViewById(R.id.tpid);
                proHolderView.txProName = (TextView) convertView.findViewById(R.id.problem_challenge_title);
                proHolderView.txProScore = (TextView) convertView.findViewById(R.id.challenge_score);
                proHolderView.imagRoE = (ImageView) convertView.findViewById(R.id.challenge_r_or_e);
                convertView.setTag(proHolderView);

            }
        }else{
            if(type==TYPE_ROOT){
                rootHolderView = (RootHolderView) convertView.getTag();
            }else if(type == TYPE_DEC){
                decHolderView = (DecHolderView) convertView.getTag();
            }else if(type == TYPE_PRO){
                proHolderView = (ProHolderView) convertView.getTag();
            }

        }

        if(type==TYPE_ROOT){
            Block block = (Block) o;
            rootHolderView.txBlockTitle.setText(block.getName());
            rootHolderView.txUserScore.setText(block.getUserScore()+"");
            rootHolderView.txScore.setText(block.getScore()+"");
            boolean isRootOpen = ((Block) o).isRootIsOpen();
            int isOpen = ((Block) o).getIsOpen();
            if(isRootOpen){
                if(isOpen==3){
                    rootHolderView.imageJiao.setBackgroundResource(R.mipmap.gre_down_jiaobiao);
                }else if(isOpen==1){
                    rootHolderView.imageJiao.setBackgroundResource(R.mipmap.red_down_jiaobiao);
                }

            }else{
                if(isOpen==3){
                    rootHolderView.imageJiao.setBackgroundResource(R.mipmap.gre_up_jiaobiao);
                }else if(isOpen==1){
                    rootHolderView.imageJiao.setBackgroundResource(R.mipmap.red_up_jiaobiao);
                }

            }

        }else if(type == TYPE_DEC){
            BlockDec blockDec = (BlockDec) o;
            decHolderView.dec.setText(blockDec.getName());
        }else if(type == TYPE_PRO){
            ChallengeProblem  challengeProblem = (ChallengeProblem) o;
            proHolderView.txTpid.setText(challengeProblem.getTpid()+"");
            proHolderView.txProName.setText(challengeProblem.getTitle());
            proHolderView.txProScore.setText(challengeProblem.getScore()+"");
            int scoved = challengeProblem.getSolved();
            if(scoved==2){
                proHolderView.imagRoE.setBackgroundResource(R.mipmap.right_icon2);
            }else if(scoved == 1){
                proHolderView.imagRoE.setBackgroundResource(R.mipmap.error_icon2);
            }else {
                proHolderView.imagRoE.setBackgroundColor(0xffffff);
            }

        }

        return convertView;
    }

    private int getType(Object o){
        int type = 0;
        if(o instanceof Block){
            type = TYPE_ROOT;
        }else if(o instanceof BlockDec){
            type = TYPE_DEC;
        }else if (o instanceof ChallengeProblem){
            type = TYPE_PRO;
        }
        return type;
    }

    static class RootHolderView{
        ImageView imageJiao;
        TextView txBlockTitle;
        TextView txUserScore;
        TextView txScore;
    }

    static class DecHolderView{
        TextView dec;
    }

    static class ProHolderView{
        TextView txTpid;
        TextView txProName;
        TextView txProScore;
        ImageView imagRoE;
    }
}
