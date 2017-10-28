package persenter;

import android.os.Handler;

import net.NetInterface.RequestItemProblem;
import net.RequestItemProblemImpl;

import bean.ItemProblem;
import persenter.netRequestListener.ItemProblemListenser;
import persenter.persenterInterface.ItemProblemPersenter;
import www.fjutoj.com.fjutacmer.view.ItemProblemView;

/**
 * Created by Administrator on 2016/10/3.
 */
public class ItemProblemPersenterImpl implements ItemProblemPersenter ,ItemProblemListenser{

    private ItemProblemView itemProblemView;
    private RequestItemProblem requestItemProblem;
    private Handler handler;

    public ItemProblemPersenterImpl(ItemProblemView itemProblemView,Handler handler){
        this.itemProblemView = itemProblemView;
        this.handler = handler;
        requestItemProblem = new RequestItemProblemImpl(this,handler);
    }
    @Override
    public void getProblemData(final int ojpid) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                requestItemProblem.getProblemData(ojpid);
            }
        };
        thread.start();

    }

    @Override
    public void submitCode(final int pidnum, final String code, final int lang, final int cid) {
        Thread thread = new Thread(){
            @Override
            public void run() {
                requestItemProblem.subimitProblemCode(pidnum,code,lang,cid);
            }
        };
        thread.start();

    }

    @Override
    public void destory() {
        requestItemProblem.destory();
    }

    @Override
    public void getProblemDataSuccess(ItemProblem itemProblem) {
        itemProblemView.getProblemDataSuccess(itemProblem);
    }

    @Override
    public void getProblemDataFall() {
        itemProblemView.getProblemDataFall();
    }

    @Override
    public void submitCodeSucccess() {
        itemProblemView.submitSuccess();
    }

    @Override
    public void submitCodeFall(String error) {
        itemProblemView.submitFall(error);
    }
}
