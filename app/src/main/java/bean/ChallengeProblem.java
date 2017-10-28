package bean;

/**
 * Created by Administrator on 2016/10/27.
 */
public class ChallengeProblem {

    private int rootParent;
    private int solved;
    private int pid;
    private int tpid;//oj题目id
    private String title;
    private int score;
    private int myType;

    public ChallengeProblem(int rootParent, int solved, int pid, int tpid, String title, int score, int myType) {
        this.rootParent = rootParent;
        this.solved = solved;
        this.pid = pid;
        this.tpid = tpid;
        this.title = title;
        this.score = score;
        this.myType = myType;
    }

    public int getRootParent() {
        return rootParent;
    }

    public void setRootParent(int rootParent) {
        this.rootParent = rootParent;
    }

    public int getSolved() {
        return solved;
    }

    public void setSolved(int solved) {
        this.solved = solved;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTpid() {
        return tpid;
    }

    public void setTpid(int tpid) {
        this.tpid = tpid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMyType() {
        return myType;
    }

    public void setMyType(int myType) {
        this.myType = myType;
    }


}
