package bean;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import tool.SingleHttpClick;
import url.MYURL;

/**
 * Created by Administrator on 2016/9/25.
 */
public class SingleMegSelf {

    String username;
    String nick;
    String motto;
    String school;
    String email;
    int gender;
    int acb;
    int acnum;
    int submitnum;
    int rating;
    int ratingnum;
    int rank;
    private static SingleMegSelf singleMegSelf = null;

    public SingleMegSelf(String username, String nick, String motto, String email, String school,
                         int gender, int acb, int submitnum, int acnum, int ratingnum, int rating,int rank) {
        this.username = username;
        this.nick = nick;
        this.motto = motto;
        this.email = email;
        this.school = school;
        this.gender = gender;
        this.acb = acb;
        this.submitnum = submitnum;
        this.acnum = acnum;
        this.ratingnum = ratingnum;
        this.rating = rating;
        this.rank = rank;
    }

    public SingleMegSelf(String username, String nick, String motto, String email, String school,
                         int gender, int acb, int submitnum, int acnum, int ratingnum, int rating) {
        this.username = username;
        this.nick = nick;
        this.motto = motto;
        this.email = email;
        this.school = school;
        this.gender = gender;
        this.acb = acb;
        this.submitnum = submitnum;
        this.acnum = acnum;
        this.ratingnum = ratingnum;
        this.rating = rating;

    }

    public static SingleMegSelf getSingleMegSelf(String username, String nick, String motto, String email, String school,
                                                 int gender, int acb, int submitnum, int acnum, int ratingnum, int rating,int rank){
        if(singleMegSelf==null){
            singleMegSelf = new SingleMegSelf(username, nick, motto, email, school, gender, acb, submitnum, acnum, ratingnum, rating,rank);
        }
        return singleMegSelf;
    }
    public static void setSingleMegSelf(String username, String nick, String motto, String email, String school,
                                        int gender, int acb, int submitnum, int acnum, int ratingnum, int rating,int rank){
        if(singleMegSelf==null){
            singleMegSelf = new SingleMegSelf(username, nick, motto, email, school, gender, acb, submitnum, acnum, ratingnum, rating,rank);
        }else {
            singleMegSelf.username = username;
            singleMegSelf.nick = nick;
            singleMegSelf.motto = motto;
            singleMegSelf.email = email;
            singleMegSelf.school = school;
            singleMegSelf.gender = gender;
            singleMegSelf.acb = acb;
            singleMegSelf.submitnum = submitnum;
            singleMegSelf.acnum = acnum;
            singleMegSelf.ratingnum = ratingnum;
            singleMegSelf.rating = rating;
            singleMegSelf.rank = rank;
        }

    }
    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    public static SingleMegSelf getSingleMegSelf(){
        if(singleMegSelf==null){
            /*Thread thread  = new Thread(){
                @Override
                public void run() {
                    getMeg();

                }
            };
            thread.start();*/
            singleMegSelf = new SingleMegSelf("username", "nick", "motto", "email", "school", 1, 0, 0, 0, 0, 0,0);
        }
        return singleMegSelf;
    }

    private static void getMeg(){
        final String meg;
        SingleHttpClick singleHttpClick = SingleHttpClick.getSingleHttpClick();
        meg = singleHttpClick.getHttpClickMeg(MYURL.ROOT_URL+MYURL.SINGLE_MEG_SELF);
        AxJSON(meg);
    }

    private static void  AxJSON(String s){
        try {
            JSONObject jsonObject = new JSONObject(s);
            String ret = jsonObject.getString(MYURL.RET);
            if(MYURL.SUCCESS.equals(ret)){
                String username = jsonObject.getString(MYURL.USERNAME);
                String nick = jsonObject.getString(MYURL.NICK);
                String motto = jsonObject.getString(MYURL.MOTTO);
                String school = jsonObject.getString(MYURL.SCHOOL);
                String email = jsonObject.getString(MYURL.EMAIL);
                int gender = jsonObject.getInt(MYURL.GENDER);
                int acb = jsonObject.getInt(MYURL.ACB);
                int acnum = jsonObject.getInt(MYURL.ACNUM);
                int submitnum = jsonObject.getInt(MYURL.SUBMITNUM);
                int rating = jsonObject.getInt(MYURL.RATING);
                int ratingnum = jsonObject.getInt(MYURL.RATINGNUM);
                int rank = jsonObject.getInt(MYURL.RANK);
                Log.d("maink","use"+username);
                singleMegSelf = SingleMegSelf.getSingleMegSelf(username,nick,motto,email,school,gender,acb,submitnum,acnum,ratingnum,rating,rank);
                Log.d("maink","ss"+singleMegSelf.toString());
            }else{

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "SingleMegSelf{" +
                "username='" + username + '\'' +
                ", nick='" + nick + '\'' +
                ", motto='" + motto + '\'' +
                ", school='" + school + '\'' +
                ", email='" + email + '\'' +
                ", gender=" + gender +
                ", acb=" + acb +
                ", acnum=" + acnum +
                ", submitnum=" + submitnum +
                ", rating=" + rating +
                ", ratingnum=" + ratingnum +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAcb() {
        return acb;
    }

    public void setAcb(int acb) {
        this.acb = acb;
    }

    public int getAcnum() {
        return acnum;
    }

    public void setAcnum(int acnum) {
        this.acnum = acnum;
    }

    public int getSubmitnum() {
        return submitnum;
    }

    public void setSubmitnum(int submitnum) {
        this.submitnum = submitnum;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getRatingnum() {
        return ratingnum;
    }

    public void setRatingnum(int ratingnum) {
        this.ratingnum = ratingnum;
    }
}
