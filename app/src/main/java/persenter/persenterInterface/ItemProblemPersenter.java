package persenter.persenterInterface;

/**
 * Created by Administrator on 2016/10/3.
 */
public interface ItemProblemPersenter {
        public void getProblemData(int ojpid);
        public void submitCode(int pidnum, String code, int lang, int cid);
        public void destory();
}
