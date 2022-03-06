import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTreeTest {

    @Test
    void makeEmpty() {
    }

    @Test
    void printTree() throws IllegalAccessException {
        String myTestFormula = "920/32+52/1-788*43";
        Spreadsheet myTest = new Spreadsheet(4,4);
        CellToken myToken = new CellToken();
        //Create cell from cell token
        Cell currentCell = myTest.getCellValue(myToken);

        Stack myTestStack = Token.getFormula(myTestFormula, myTest, currentCell);
        System.out.println(myTestStack);
        System.out.println(Cell.validateInputFormula(myTestStack));

        ExpressionTree myTestTree = new ExpressionTree(new ExpressionTreeNode());
        myTestTree.BuildExpressionTree(myTestStack);
        myTestTree.printTree();

    }

    @Test
    void evaluate1() {
        Stack myStack = evaluateHelperMethod("24+23-12");

        ExpressionTree myTestTree = new ExpressionTree(new ExpressionTreeNode());
        myTestTree.BuildExpressionTree(myStack);

        assertEquals( 35, myTestTree.Evaluate(new Spreadsheet(4)));
    }

    @Test
    void evaluate2() {
        Stack myStack = evaluateHelperMethod("24-23-12");

        ExpressionTree myTestTree = new ExpressionTree(new ExpressionTreeNode());
        myTestTree.BuildExpressionTree(myStack);

        assertEquals( -11, myTestTree.Evaluate(new Spreadsheet(4)));
    }

    @Test
    void evaluate3() {
        Stack myStack = evaluateHelperMethod("100/2/2/2/2");

        ExpressionTree myTestTree = new ExpressionTree(new ExpressionTreeNode());
        myTestTree.BuildExpressionTree(myStack);

        assertEquals( 6, myTestTree.Evaluate(new Spreadsheet(4)));
    }

    @Test
    void buildExpressionTree() {
        String myTestFormula = "15+4*2/43-2";
        Spreadsheet myTest = new Spreadsheet(4,4);
        CellToken myToken = new CellToken();

        //Create cell from cell token
        Cell currentCell = myTest.getCellValue(myToken);
        Stack myTestStack = Token.getFormula(myTestFormula, myTest, currentCell);
        System.out.println(myTestStack);

        ExpressionTree myTestTree = new ExpressionTree(new ExpressionTreeNode());
        myTestTree.BuildExpressionTree(myTestStack);
        myTestTree.printTree();

    }

    @Test
    void getExpressionTree() {
    }

    public static Stack evaluateHelperMethod(String theFormula) {
        String myTestFormula = theFormula;
        Spreadsheet myTest = new Spreadsheet(4,4);
        CellToken myToken = new CellToken();
        //Create cell from cell token
        Cell currentCell = myTest.getCellValue(myToken);
        Stack myTestStack = Token.getFormula(myTestFormula, myTest, currentCell);
        return myTestStack;
    }
}