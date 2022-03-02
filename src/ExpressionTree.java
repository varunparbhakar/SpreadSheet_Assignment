public class ExpressionTree {
    private ExpressionTreeNode root;

    public void makeEmpty(){}
    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( root == null )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param node the node that roots the tree.
     */
    private void printTree( ExpressionTreeNode node )
    {
        if( node != null )
        {
            printTree( node.left );
            System.out.println( node.getToken().printExpressionTreeToken(node.getToken()) );
            printTree( node.right );
        }
    }

    
    public ExpressionTree(ExpressionTreeNode root){
        this.root = root;
    }
  
    /*public ExpressionTree() {
        this.root = new ExpressionTreeNode();
    }*/

    //public int Evaluate(Spreadsheet spreadsheet){}

    /**
     * Evaluate the expression of formula
     * @return value after calculation
     */
    public int Evaluate(){

        if( root == null ) {
            return 0;
        }
        else {
            return Evaluate(root);
        }
    }

    /**
     * Internal method for evaluate the formula
     * @param node Node of Expression Tree
     * @return value after calculation
     */
    private int Evaluate(ExpressionTreeNode node) {
        int result = 0;
        char operator = '0';
        int literalToken = 0;

        if(node != null) {
            Token token = node.getToken();
            if(token instanceof LiteralToken){
                literalToken = LiteralToken.getValue((LiteralToken) token);
                return literalToken;
            }

            if (token instanceof OperatorToken) {
                operator = ((OperatorToken) token).getOperatorToken();

                if (operator == '+') {
                    result = Evaluate(node.left) + Evaluate(node.right);
                } else if (operator == '-') {
                    result = Evaluate(node.left) - Evaluate(node.right);
                } else if (operator == '*') {
                    result = Evaluate(node.left) * Evaluate(node.right);
                } else if (operator == '/') {
                    result = Evaluate(node.left) / Evaluate(node.right);
                }
            }
        }
        return result;

    }  //TODO

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
