import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    Cell myTestCell = new Cell("45");

    @Test
    void addDependency() {
        myTestCell = new Cell("23");
    }

    @Test
    void addFeedInto() {

    }

    @Test
    void getValue() {
    }

    @Test
    void getNumDependencies() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"A0"} )
    void evaluate(String values) throws IllegalArgumentException, IllegalAccessException {
        System.out.println(Cell.validateInputFormula(Token.getFormula(values, new Spreadsheet(9), new Cell("2")), values));


    }

    @Test
    void getFormula() {
    }

    @Test
    void testToString() {
    }
    @Test
    void test() {

    }

    @Test
    void isLiteralTest() {
        String literalString = "57";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isLiteral(literalString));
    }

    @Test
    void isLiteralTest2() {
        String literalString = "0";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isLiteral(literalString));
    }
    @Test
    void isLiteralTest3() {
        String literalString = "9";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isLiteral(literalString));
    }

    @Test
    void isLiteralTest4() {
        String literalString = "/";
        Cell myTestCell = new Cell("00");
        assertFalse(Cell.isLiteral(literalString));
    }
    @Test
    void isLiteralTest5() {
        String literalString = ":";
        Cell myTestCell = new Cell("00");
        assertFalse(Cell.isLiteral(literalString));
    }
    @Test
    void isLiteralTest6() {
        String literalString = "-9";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isLiteral(literalString));
    }
    @Test
    void isLiteralTest7() {
        String literalString = "-0";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isLiteral(literalString));
    }
    @Test
    void isReferenceTest() {
        String literalString = "A1";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isCellReference(literalString));
    }
    @Test
    void isReferenceTest2() {
        String literalString = "A0";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isCellReference(literalString));
    }
    @Test
    void isReferenceTest3() {
        String literalString = "A79";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isCellReference(literalString));
    }

    @Test
    void isReferenceTest4() {
        String literalString = "d82";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isCellReference(literalString));
    }
    @Test
    void isReferenceTest5() {
        String literalString = "z82";
        Cell myTestCell = new Cell("00");
        assertTrue(Cell.isCellReference(literalString));
    }
    @Test
    void isReferenceTest6() {
        String literalString = "-z82";
        Cell myTestCell = new Cell("00");
        assertFalse(Cell.isCellReference(literalString));
    }
    @Test
    void isReferenceTest7() {
        String literalString = "A-82";
        Cell myTestCell = new Cell("00");
        assertFalse(Cell.isCellReference(literalString));
    }

    @Test
    void isOperator() {
        String operatorString = "+";
        assertTrue(Cell.isOperator(operatorString));
    }
    @Test
    void isOperator2() {
        String operatorString = "-";
        assertTrue(Cell.isOperator(operatorString));
    }
    @Test
    void isOperator3() {
        String operatorString = "*";
        assertTrue(Cell.isOperator(operatorString));
    }
    @Test
    void isOperator4() {
        String operatorString = "/";
        assertTrue(Cell.isOperator(operatorString));
    }
    @Test
    void isOperator5() {
        String operatorString = ")";
        assertFalse(Cell.isOperator(operatorString));
    }
    @Test
    void isOperator6() {
        String operatorString = ".";
        assertFalse(Cell.isOperator(operatorString));
    }
    @Test
    void isOperator7() {
        String operatorString = "'";
        assertFalse(Cell.isOperator(operatorString));
    }
    @Test
    void isOperator8() {
        String operatorString = "_";
        assertFalse(Cell.isOperator(operatorString));
    }




}