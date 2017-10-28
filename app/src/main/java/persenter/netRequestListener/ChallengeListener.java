package persenter.netRequestListener;

import java.util.ArrayList;

import bean.BlockCondition;
import bean.BlockDec;

/**
 * Created by Administrator on 2016/10/27.
 */
public interface ChallengeListener {
    public void getChallengeDataSuccess();
    public void getChallengeDataFall();
    public void freshChallengeDataSuccess();
    public void freshChallengeDatafall();
    public void getBlockDataSuccess(int position);
    public void getBlockDataFall();
    public void getConditionSuccess(ArrayList<BlockCondition> blockDecs);
    public void getConditionFall();
}
