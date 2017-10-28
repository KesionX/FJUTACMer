package bean;

/**
 * Created by Administrator on 2017/2/23.
 */
public class RankingAcmer {


    private String userName;
    private String nick;
    private String motto;
    private int submission;
    private int acNum;
    private int acb;
    private int rating;
    private int ratingNum;
    private int rank;

    public RankingAcmer(String userName, String nick, String motto, int submission, int acNum, int acb, int rating, int ratingNum, int rank) {
        this.userName = userName;
        this.nick = nick;
        this.motto = motto;
        this.submission = submission;
        this.acNum = acNum;
        this.acb = acb;
        this.rating = rating;
        this.ratingNum = ratingNum;
        this.rank = rank;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getSubmission() {
        return submission;
    }

    public void setSubmission(int submission) {
        this.submission = submission;
    }

    public int getAcNum() {
        return acNum;
    }

    public void setAcNum(int acNum) {
        this.acNum = acNum;
    }

    public int getAcb() {
        return acb;
    }

    public void setAcb(int acb) {
        this.acb = acb;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRatingNum() {
        return ratingNum;
    }

    public void setRatingNum(int ratingNum) {
        this.ratingNum = ratingNum;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
