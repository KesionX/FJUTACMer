package bean;

/**
 * Created by Administrator on 2016/10/10.
 */
public class SystemMessage {

    private String account;
    private String message;
    private String datetime;

    public SystemMessage(String account, String message, String datetime) {
        this.account = account;
        this.message = message;
        this.datetime = datetime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
