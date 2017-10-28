package net.NetInterface;

import bean.ChallengeVector;

/**
 * Created by Administrator on 2016/10/27.
 */
public interface RequestChallenge {

    public void getChallengeData(ChallengeVector challengeVector, String user);
    public void freshChallengeData(ChallengeVector challengeVector, String user);
    public void getBlockData(ChallengeVector challengeVector, int id, String user, int position);
    public void getCondition(int id);
    public void Destory();

}
