import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void printExpressionTreeToken() {

    }

    @Test
    void getFormula() {
        String myTestFormula = "15+75*7855";
        Spreadsheet myTest = new Spreadsheet(4,4);
        CellToken myToken = new CellToken();
        //Create cell from cell token
        Cell currentCell = myTest.getCellValue(myToken);
        Stack myTestStack = Token.getFormula(myTestFormula, myTest, currentCell);
        System.out.println(myTestStack);
        assertEquals("[15, 75, 7855, *, +]", myTestStack.toString());


    }
}