import org.junit.jupiter.api.Test;

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

    @Test
    void evaluate() {
    }

    @Test
    void getFormula() {
    }

    @Test
    void testToString() {
    }
    @Test
    void test() {
        boolean s = myTestCell.isLiteral("42");
        System.out.println(s);
    }

    @Test
    void isLiteralTest() {
        String literalString = "57";
        Cell myTestCell = new Cell("00");
        assertTrue(myTestCell.isLiteral(literalString));
    }

    @Test
    void isLiteralTest2() {
        String literalString = "0";
        Cell myTestCell = new Cell("00");
        assertTrue(myTestCell.isLiteral(literalString));
    }
    @Test
    void isLiteralTest3() {
        String literalString = "9";
        Cell myTestCell = new Cell("00");
        assertTrue(myTestCell.isLiteral(literalString));
    }

    @Test
    void isLiteralTest4() {
        String literalString = "/";
        Cell myTestCell = new Cell("00");
        assertFalse(myTestCell.isLiteral(literalString));
    }
    @Test
    void isLiteralTest5() {
        String literalString = ":";
        Cell myTestCell = new Cell("00");
        boolean s = myTestCell.isLiteral("45");
        System.out.println(s);
        assertFalse(myTestCell.isLiteral(literalString));
    }
    @Test
    void isLiteralTest6() {
        String literalString = "-9";
        Cell myTestCell = new Cell("00");
        assertFalse(myTestCell.isLiteral(literalString));
    }
}