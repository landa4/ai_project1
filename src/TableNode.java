public class TableNode {

    private NodeFlag flag;
    private int depth;
    private float value;


    public TableNode(float v, NodeFlag n, int d){
        flag = n;
        depth = d;
        value = v;

    }

    @Override
    public String toString() {
        return "TableNode{" +
                "flag=" + flag +
                ", depth=" + depth +
                ", value=" + value +
                '}';
    }

    public float getValue() {
        return value;
    }

    public NodeFlag getFlag() {
        return flag;
    }

    public int getDepth() {
        return depth;
    }
}

