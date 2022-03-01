public class ExpressionTree {
    private ExpressionTreeNode root;

    public void makeEmpty(){}
    public void printTree(){}

    
    public ExpressionTree(ExpressionTreeNode root){
        this.root = root;
    }
  
    /*public ExpressionTree() {
        this.root = new ExpressionTreeNode();
    }*/

    public int Evaluate(Spreadsheet spreadsheet) {return 0;}  //TODO

    // Build an expression tree from a stack of ExpressionTreeTokens
    void BuildExpressionTree (Stack s) {
        root = GetExpressionTree(s);
        if (!s.isEmpty()) {
            System.out.println("Error in BuildExpressionTree.");
        }
    }
    public ExpressionTreeNode GetExpressionTree(Stack s) {
        ExpressionTreeNode returnTree;
        Token token;
        if (s.isEmpty())
            return null;
        token = (Token) s.topAndPop(); // need to handle stack underflow
        if ((token instanceof LiteralToken) ||
                (token instanceof CellToken) ) {
            // Literals and Cells are leaves in the expression tree
            returnTree = new ExpressionTreeNode(token, null, null);
            return returnTree;
        } else if (token instanceof OperatorToken) {
            // Continue finding tokens that will form the
            // right subtree and left subtree.
            ExpressionTreeNode rightSubtree = GetExpressionTree (s);
            ExpressionTreeNode leftSubtree = GetExpressionTree (s);
            returnTree = new ExpressionTreeNode(token, leftSubtree, rightSubtree);
            return returnTree;
        }else{
            return null;
        }
    }


}
