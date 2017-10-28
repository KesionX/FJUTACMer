package bean;

/**
 * Created by Administrator on 2016/10/4.
 */
public class StatusProblem {
    int rid;
    int pid;
    String username;
    int cid;
    int lang;
    String submitTime;
    int result;
    int score;
    String timeUsed;
    String memoryUsed;
    String codeLength;
    String nick;

    public StatusProblem(int rid, int pid, String username, int cid, String submitTime, int lang, int result, int score, String timeUsed, String memoryUsed, String codeLength, String nick) {
        this.rid = rid;
        this.pid = pid;
        this.username = username;
        this.cid = cid;
        this.submitTime = submitTime;
        this.lang = lang;
        this.result = result;
        this.score = score;
        this.timeUsed = timeUsed;
        this.memoryUsed = memoryUsed;
        this.codeLength = codeLength;
        this.nick = nick;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(int lang) {
        this.lang = lang;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(String timeUsed) {
        this.timeUsed = timeUsed;
    }

    public String getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(String memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public String getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(String codeLength) {
        this.codeLength = codeLength;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
