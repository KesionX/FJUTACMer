package bean;

/**
 * Created by Administrator on 2016/10/2.
 */
public class MyProblemBean {
    private int pid ;
    private String title;
    private int ac;
    private int submit;
    private int status;

    public MyProblemBean(int pid, int ac, String title, int submit) {
        this.pid = pid;
        this.ac = ac;
        this.title = title;
        this.submit = submit;
    }

    public MyProblemBean(int pid, int ac, String title, int submit,int status) {
        this.pid = pid;
        this.ac = ac;
        this.title = title;
        this.submit = submit;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "MyProblemBean{" +
                "pid=" + pid +
                ", title='" + title + '\'' +
                ", ac=" + ac +
                ", submit=" + submit +
                '}';
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

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public int getSubmit() {
        return submit;
    }

    public void setSubmit(int submit) {
        this.submit = submit;
    }
}
