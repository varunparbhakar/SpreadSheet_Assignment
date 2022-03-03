public class ExpressionTree {

    private ExpressionTreeNode root;

    public void makeEmpty(){
        root = new ExpressionTreeNode();
    }
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
  
    public ExpressionTree() {
        this.root = new ExpressionTreeNode();
    }

    //public int Evaluate(Spreadsheet spreadsheet){}

    /**
     * Evaluate the expression of formula
     * @return value after calculation
     */
    public int Evaluate(Spreadsheet spreadsheet, CellToken cellToken){

        if( root == null ) {
            return 0;
        }
        else {
            return Evaluate(spreadsheet, cellToken, root);
        }
    }

    /**
     * Internal method for evaluate the formula
     * @param node Node of Expression Tree
     * @return value after calculation
     */
    private int Evaluate(Spreadsheet spreadsheet, CellToken currentCellToken, ExpressionTreeNode node) {
        int result = 0;
        char operator = '0';
        int literalToken = 0;
        CellToken cellTokenInExpression = null;
        Cell currentCell;
        Cell cellInExpression;

        if(node != null) {
            Token token = node.getToken();

            //For literal Token, return int value of literalToken
            if(token instanceof LiteralToken){
                literalToken = LiteralToken.getValue((LiteralToken) token);
                return literalToken;
            }

            //For Cell Token, return value of Cell in Expression
            //"A1 = A3 + 4"
            //A3 feed into A1 =>
            //A1 depend on A3 => A1 is feed into of A3
            if(token instanceof CellToken){
                cellTokenInExpression = (CellToken) token; // A3 + 4

                currentCell = spreadsheet.getCell(currentCellToken); // A1
                cellInExpression = spreadsheet.getCell(cellTokenInExpression); // A3

                currentCell.addDependency(cellInExpression); //A1 add dependency cell A3
                cellInExpression.addFeedInto(currentCell); // A3 add feed into cell A1

//                System.out.println("cellTokenInExpression " + Token.printExpressionTreeToken(cellTokenInExpression));
//                System.out.println("currentCell " + currentCell);
//                System.out.println("cellInExpression " + cellInExpression);
//                System.out.println("currentCell addDependency " + currentCell.getDependsOn());
//                System.out.println("currentCell addFeedInto " + currentCell.getFeedsInto());
//                System.out.println("cellInExpression getValue " + currentCell.getValue());

                return cellInExpression.getValue();
            }

            //For Operator Token, calculate the left and right node
            if (token instanceof OperatorToken) {
                operator = ((OperatorToken) token).getOperatorToken();

                if (operator == '+') {
                    result = Evaluate(spreadsheet, currentCellToken, node.left) + Evaluate(spreadsheet,currentCellToken, node.right);
                } else if (operator == '-') {
                    result = Evaluate(spreadsheet, currentCellToken, node.left) - Evaluate(spreadsheet, currentCellToken, node.right);
                } else if (operator == '*') {
                    result = Evaluate(spreadsheet, currentCellToken, node.left) * Evaluate(spreadsheet, currentCellToken, node.right);
                } else if (operator == '/') {
                    result = Evaluate(spreadsheet, currentCellToken, node.left) / Evaluate(spreadsheet, currentCellToken, node.right);
                }
            }
        }
        return result;

    }

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
