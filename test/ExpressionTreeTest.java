import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTreeTest {

    @Test
    void makeEmpty() {
    }

    @Test
    void printTree() {
    }

    @Test
    void evaluate() {
    }

    @Test
    void buildExpressionTree() {
        String myTestFormula = "15+75*7855";
        Spreadsheet myTest = new Spreadsheet(4,4);
        CellToken myToken = new CellToken();
        Stack myTestStack = Token.getFormula(myTestFormula, myTest, myToken);

        ExpressionTree myTestTree = new ExpressionTree(new ExpressionTreeNode());


    }

    @Test
    void getExpressionTree() {
    }
}