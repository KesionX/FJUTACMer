package bean;

/**
 * Created by Administrator on 2016/9/28.
 */
public class recentlyGame {
    private int id;
    private String oj;
    private String Link;
    private String name;
    private String start_time;
    private String week;
    private int type;
    private String access;

    private int cid;
    private String begin;
    private String end;
    private int kind;

    public recentlyGame(String name, String access, int cid, String begin, String end, int kind, int type) {
        this.name = name;
        this.access = access;
        this.cid = cid;
        this.begin = begin;
        this.end = end;
        this.kind = kind;
        this.type = type;
    }

    public recentlyGame(int id, String oj, String link, String name, String start_time, String week,
                        int type, String access) {
        this.id = id;
        this.oj = oj;
        this.Link = link;
        this.name = name;
        this.start_time = start_time;
        this.week = week;
        this.type = type;
        this.access =access;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
