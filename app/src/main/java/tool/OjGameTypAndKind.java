package tool;

/**
 * Created by Administrator on 2016/10/6.
 */
public class OjGameTypAndKind {

    public static String getAccess(String s){
        if("TEAM_OFFICIAL".equals(s)) return "公开";
        if("PASSWORD".equals(s)) return "密码";
        if("PRIVATE".equals(s)) return "私有";
        if("REGISTER".equals(s)) return "注册";
        if("REGISTER2".equals(s)) return "正式";
        return "公开";
    }

    public static String getKind(int i){
        if(i==1) return "积分";
        if(i==2) return "趣味";
        if(i==3) return "正式";
        return "练习";
    }
}
