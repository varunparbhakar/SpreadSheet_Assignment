/**
 * The ExpressionTreeNode class manages the ExpressionTree node object.
 * @version 03/07/2022
 * @author Varun Parbhakar, Andrew Dibble, and Minh Trung Le.
 */
public class ExpressionTreeNode {
    ExpressionTreeNode left;
    ExpressionTreeNode right;
    private Token token;


    /**
     * Constructor for Expression Tree Nodes
     */
    public ExpressionTreeNode() {
        token = new Token();
        this.left = null;
        this.right = null;
    }

    /**
     * Constructor for Expression Tree Nodes with parameters, to specifically set the
     * left, right and main node itself.
     */
    public ExpressionTreeNode(Token token, ExpressionTreeNode left, ExpressionTreeNode right) {
        this.token = token;
        this.left = left;
        this.right = right;
    }


    /**
     * This method returns the main token.
     * @return
     */
    public Token getToken() {
        return token;
    }

    /**
     * This method sets the main token.
     * @return
     */
    public void setToken(Token token) {
        this.token = token;
    }

    /**
     * This method returns the left token.
     * @return
     */
    public ExpressionTreeNode getLeft() {
        return left;
    }

    /**
     * This method sets the left token.
     * @return
     */
    public void setLeft(ExpressionTreeNode left) {
        this.left = left;
    }

    /**
     * This method returns the right token.
     * @return
     */
    public ExpressionTreeNode getRight() {
        return right;
    }

    /**
     * This method sets the right token.
     * @return
     */
    public void setRight(ExpressionTreeNode right) {
        this.right = right;
    }
}
