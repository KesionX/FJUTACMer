package bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/3.
 */
public class ItemProblem {

    private int pid;
    private String title;
    private int type;
    private String  oj;
    private String ojid;
    private String Int64;
    private String timelimit;
    private String memoryLimit;
    private String des;
    private String input;
    private String output;
    private ArrayList<String > sampleinput;
    private ArrayList<String > sampleoutput;

    public ItemProblem(int pid, String title, String oj, int type,
                       String ojid, String int64, String timelimit, String memoryLimit,
                       String des, String input, String output, ArrayList<String> sampleinput,
                       ArrayList<String> sampleoutput) {
        this.pid = pid;
        this.title = title;
        this.oj = oj;
        this.type = type;
        this.ojid = ojid;
        Int64 = int64;
        this.timelimit = timelimit;
        this.memoryLimit = memoryLimit;
        this.des = des;
        this.input = input;
        this.output = output;
        this.sampleinput = sampleinput;
        this.sampleoutput = sampleoutput;
    }

    @Override
    public String toString() {
        return "ItemProblem{" +
                "pid=" + pid +
                ", title='" + title + '\'' +
                ", type=" + type +
                ", oj='" + oj + '\'' +
                ", ojid=" + ojid +
                ", Int64='" + Int64 + '\'' +
                ", timelimit='" + timelimit + '\'' +
                ", memoryLimit='" + memoryLimit + '\'' +
                ", des='" + des + '\'' +
                ", input='" + input + '\'' +
                ", output='" + output + '\'' +
                ", sampleinput=" + sampleinput +
                ", sampleoutput=" + sampleoutput +
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getOj() {
        return oj;
    }

    public void setOj(String oj) {
        this.oj = oj;
    }

    public String getOjid() {
        return ojid;
    }

    public void setOjid(String ojid) {
        this.ojid = ojid;
    }

    public String getInt64() {
        return Int64;
    }

    public void setInt64(String int64) {
        Int64 = int64;
    }

    public String getTimelimit() {
        return timelimit;
    }

    public void setTimelimit(String timelimit) {
        this.timelimit = timelimit;
    }

    public String getMemoryLimit() {
        return memoryLimit;
    }

    public void setMemoryLimit(String memoryLimit) {
        this.memoryLimit = memoryLimit;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public ArrayList<String> getSampleinput() {
        return sampleinput;
    }

    public void setSampleinput(ArrayList<String> sampleinput) {
        this.sampleinput = sampleinput;
    }

    public ArrayList<String> getSampleoutput() {
        return sampleoutput;
    }

    public void setSampleoutput(ArrayList<String> sampleoutput) {
        this.sampleoutput = sampleoutput;
    }
}
