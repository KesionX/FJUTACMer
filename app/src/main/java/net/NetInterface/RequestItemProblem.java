package net.NetInterface;

/**
 * Created by Administrator on 2016/10/3.
 */
public interface RequestItemProblem {

    public void getProblemData(int ojpid);
    public void subimitProblemCode(int pidnum, String code, int lang, int cid);
    public void destory();
}
