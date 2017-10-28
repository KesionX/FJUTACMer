package bean;

/**
 * Created by Administrator on 2016/10/27.
 */
public class Block {
    private int id ;
    private int group;
    private int score;
    private int isOpen;
    private int userScore;
    private String name;
    private int myType;//ui类型
    private boolean rootIsOpen;//判断ui_root是否打开
    private boolean isFirstAnter;


    public Block(int id, int group, int score, int isOpen, int userScore, String name, int myType) {
        this.id = id;
        this.group = group;
        this.score = score;
        this.isOpen = isOpen;
        this.userScore = userScore;
        this.name = name;
        this.myType = myType;
        this.rootIsOpen = false;
        this.isFirstAnter = false;
    }

    public boolean isFirstAnter() {
        return isFirstAnter;
    }

    public void setFirstAnter(boolean firstAnter) {
        isFirstAnter = firstAnter;
    }

    public int getMyType() {
        return myType;
    }

    public void setMyType(int myType) {
        this.myType = myType;
    }

    public boolean isRootIsOpen() {
        return rootIsOpen;
    }

    public void setRootIsOpen(boolean rootIsOpen) {
        this.rootIsOpen = rootIsOpen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getUserScore() {
        return userScore;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
