public class ExpressionTree {
    private ExpressionTreeNode root;

    public void makeEmpty(){}
    public void printTree(){}

    
    public ExpressionTree(ExpressionTreeNode root){
        this.root = root;
    }
  
    public ExpressionTree() {
        root = new ExpressionTreeNode();
    }

    public int Evaluate(Spreadsheet spreadsheet) {return 0;}

}
