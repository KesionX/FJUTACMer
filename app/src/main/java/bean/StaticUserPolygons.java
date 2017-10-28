package bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/19.
 */
public class StaticUserPolygons {

    private static StaticUserPolygons ms;
    private static HashMap<String ,ArrayList<Double>> mp;

    public StaticUserPolygons(){
        mp = new HashMap();
    }

    public static StaticUserPolygons getMs(){
        if(ms==null){
            ms = new StaticUserPolygons();
        }
        return ms;
    }


    public void pushUserPolygons(String user,ArrayList<Double> list){
        mp.put(user,list);
    }

    public ArrayList<Double> getUserPolygons(String user){

        ArrayList list = mp.get(user);
        return list;
    }

    public boolean isHave(String user){
        boolean ishave = false;
        ArrayList s = mp.get(user);
        if(s==null){
            ishave = false;
        }else{
            ishave = true;
        }
        return ishave;
    }

}
