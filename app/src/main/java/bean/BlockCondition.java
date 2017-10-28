package bean;

/**
 * Created by Administrator on 2016/10/27.
 */
public class BlockCondition {
    private int type;
    private int par;
    private String blockName;
    private int num;//需要完成的分数

    public BlockCondition(int type, int par, String blockName, int num) {
        this.type = type;
        this.par = par;
        this.blockName = blockName;
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPar() {
        return par;
    }

    public void setPar(int par) {
        this.par = par;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
