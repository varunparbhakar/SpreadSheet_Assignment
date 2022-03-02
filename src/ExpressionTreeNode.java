public class ExpressionTreeNode {
    private Token token;
    ExpressionTreeNode left;
    ExpressionTreeNode right;
  

    public ExpressionTreeNode(){
        token = new Token();
        this.left = null;
        this.right = null;
    }

  public ExpressionTreeNode(Token token, ExpressionTreeNode left, ExpressionTreeNode right) {
      this.token = token;
      this.left = left;
      this.right = right;
  }


    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    public ExpressionTreeNode getLeft() {
        return left;
    }

    public void setLeft(ExpressionTreeNode left) {
        this.left = left;
    }

    public ExpressionTreeNode getRight() {
        return right;
    }

    public void setRight(ExpressionTreeNode right) {
        this.right = right;
    }
}
