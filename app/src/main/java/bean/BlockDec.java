package bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/10/27.
 */
public class BlockDec {

    private int rootParent;
    private int id;
    private String name;
    private int group;
    private String text;
    private int score;
    private int userScore;
    private ArrayList<BlockCondition> list;
    private int myType;

    public BlockDec(int rootParent, int id, int group, String name, int score, String text, int userScore, ArrayList<BlockCondition> list, int myType) {
        this.rootParent = rootParent;
        this.id = id;
        this.group = group;
        this.name = name;
        this.score = score;
        this.text = text;
        this.userScore = userScore;
        this.list = list;
        this.myType = myType;
    }

    public int getRootParent() {
        return rootParent;
    }

    public void setRootParent(int rootParent) {
        this.rootParent = rootParent;
    }

    public int getMyType() {
        return myType;
    }

    public void setMyType(int myType) {
        this.myType = myType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public ArrayList<BlockCondition> getList() {
        return list;
    }

    public void setList(ArrayList<BlockCondition> list) {
        this.list = list;
    }
}
