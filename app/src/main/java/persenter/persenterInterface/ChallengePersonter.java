package persenter.persenterInterface;

import java.util.ArrayList;

import bean.ChallengeVector;

/**
 * Created by Administrator on 2016/10/27.
 */
public interface ChallengePersonter {

    public void getChallengeData(final String user);
    public void freshChallengeData(final String user);
    public ChallengeVector getChallengeVector();
    public void getBlockData(final int id,final String user,int position);
    public void getCondition(int id);
    public void Destory();

}
