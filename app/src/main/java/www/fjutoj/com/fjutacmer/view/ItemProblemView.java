package www.fjutoj.com.fjutacmer.view;

import bean.ItemProblem;

/**
 * Created by Administrator on 2016/10/3.
 */
public interface ItemProblemView {
    public void getProblemDataSuccess(ItemProblem itemProblem);
    public void getProblemDataFall();
    public void submitSuccess();
    public void submitFall(String error);

}
