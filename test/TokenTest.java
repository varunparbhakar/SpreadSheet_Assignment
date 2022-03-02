import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {

    @Test
    void printExpressionTreeToken() {
        String myTestFormula = "5+17*3";
        Stack myTestStack = Token.getFormula(myTestFormula);
        System.out.println(myTestStack.toString());
    }

    @Test
    void getFormula() {
    }
}