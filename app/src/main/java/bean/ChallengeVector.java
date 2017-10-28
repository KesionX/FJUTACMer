package bean;

import android.view.View;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Administrator on 2016/10/27.
 */
public class ChallengeVector {
    private ArrayList totalList;//总数据
    private ArrayList visiableList ;//可见数据

    public ChallengeVector(){
        this.totalList = new ArrayList();
        this.visiableList = new ArrayList();
    }

    public void add(Object o){
        this.totalList.add(o);
    }

    public Object get(int index){
        return this.totalList.get(index);
    }

    public void initVisibleList(){
        visiableList.clear();
        for (int i=0,n=totalList.size();i<n; ++i){
            Object o = totalList.get(i);
            if(o instanceof Block){
                visiableList.add(o);
            }
        }
    }

    /**
     * root的
     * @param index
     */
    public void setOpenOrClose(int index){
        Block b = (Block)visiableList.get(index);
        b.setRootIsOpen(!b.isRootIsOpen());
        changeVisiBleList();
    }

    public boolean isFirstUp(int index){
        Block b = (Block)visiableList.get(index);
        if(!b.isFirstAnter()){
            b.setFirstAnter(true);
            return true;
        }
        return false;
    }

    public void changeVisiBleList(){
        this.visiableList.clear();
        for(int i=0,n =totalList.size() ;i<n;++i){
            Object b = totalList.get(i);
            if(b instanceof  Block){
                Block block = (Block) b;
                visiableList.add(b);
                boolean isStatusOpen = block.isRootIsOpen();
                if(!isStatusOpen)
                    continue;
                int id = block.getId();
                for(int j=0 ; j< n;++j){
                    Object obj = totalList.get(j);
                    if(obj instanceof  BlockDec ){
                        BlockDec blockDec = (BlockDec) obj;
                        int parent = blockDec.getRootParent();
                        if(parent==id){
                            visiableList.add(obj);
                        }

                    }else if(obj instanceof ChallengeProblem){
                        ChallengeProblem challengeProblem = (ChallengeProblem) obj;
                        int parent = challengeProblem.getRootParent();
                        if(parent==id){
                            visiableList.add(obj);
                        }
                    }

                }

            }else{
                break;
            }
        }


      /*  boolean isopen = false;
        for(int i=0,n = totalList.size();i<n;++i){
            Object o= this.totalList.get(i);
            if(o instanceof Block){
                Block b = (Block) totalList.get(i);
                isopen = b.isRootIsOpen();
                totalList.add(o);
            }else if(o instanceof BlockDec && isopen){
                totalList.add(o);
            }else if(o instanceof ChallengeProblem && isopen){
                totalList.add(o);
            }
        }*/

    }

    public ArrayList getVisiableList(){
        return this.visiableList;
    }
    public ArrayList getTotalList(){return this.totalList;}

}
