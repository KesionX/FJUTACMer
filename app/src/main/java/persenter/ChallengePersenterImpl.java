package persenter;

import android.os.Handler;
import android.util.Log;

import net.NetInterface.RequestChallenge;
import net.RequestChallengeImpl;

import java.util.ArrayList;

import bean.BlockCondition;
import bean.BlockDec;
import bean.ChallengeVector;
import fg.FragmentView.ChallengeView;
import persenter.netRequestListener.ChallengeListener;
import persenter.persenterInterface.ChallengePersonter;

/**
 * Created by Administrator on 2016/10/27.
 */
public class ChallengePersenterImpl implements ChallengePersonter,ChallengeListener{

    private ChallengeView challengeView;
    private RequestChallenge requestChallenge;
    private ChallengeVector challengeVector;

    public ChallengePersenterImpl(ChallengeView challengeView, Handler handler){
        this.challengeView = challengeView;
        this.requestChallenge = new RequestChallengeImpl(this,handler);
        challengeVector = new ChallengeVector();
    }


    @Override
    public void getChallengeData(final String user) {
        Thread t = new Thread(){
            @Override
            public void run() {
                requestChallenge.getChallengeData(challengeVector,user);
            }
        };
        t.start();
    }

    @Override
    public void freshChallengeData(final String user) {
        challengeVector.getTotalList().clear();
        Thread t = new Thread(){
            @Override
            public void run() {
                requestChallenge.freshChallengeData(challengeVector,user);
            }
        };
        t.start();
    }

    @Override
    public ChallengeVector getChallengeVector() {
        return challengeVector;
    }

    @Override
    public void getBlockData(final int id, final String user, final int position) {
        Thread t = new Thread(){
            @Override
            public void run() {
                requestChallenge.getBlockData(challengeVector,id,user,position);
            }
        };
        t.start();

    }

    @Override
    public void getCondition(final int id) {
        Thread t = new Thread(){
            @Override
            public void run() {
                requestChallenge.getCondition(id);
            }
        };
        t.start();
    }

    @Override
    public void Destory() {
        challengeView = null;
        requestChallenge = null;
        challengeVector  = null;
    }

    @Override
    public void getChallengeDataSuccess() {
        challengeVector.initVisibleList();
        Log.d("kkkkk","size:"+challengeVector.getVisiableList().size()+"");
        challengeView.getChallengeDataSuccess();
    }

    @Override
    public void getChallengeDataFall() {
        challengeView.getChallengeDataFall();
    }

    @Override
    public void freshChallengeDataSuccess() {
        challengeVector.initVisibleList();
        challengeView.freshChallengeDataSuccess();
    }

    @Override
    public void freshChallengeDatafall() {
        challengeView.freshChallengeDatafall();
    }

    @Override
    public void getBlockDataSuccess(int position) {
        challengeView.getBlockDataSuccess(position);
    }

    @Override
    public void getBlockDataFall() {
        challengeView.getBlockDataFall();
    }

    @Override
    public void getConditionSuccess(ArrayList<BlockCondition> blockDecs) {
        challengeView.getConditionSuccess(blockDecs);
    }

    @Override
    public void getConditionFall() {
        challengeView.getConditionFall();
    }
}
