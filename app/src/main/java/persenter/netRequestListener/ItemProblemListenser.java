package persenter.netRequestListener;

import bean.ItemProblem;

/**
 * Created by Administrator on 2016/10/3.
 */
public interface ItemProblemListenser {

    public void getProblemDataSuccess(ItemProblem itemProblem);
    public void getProblemDataFall();
    public void submitCodeSucccess();
    public void submitCodeFall(String error);
}
