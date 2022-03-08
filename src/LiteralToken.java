/**
 * The LiteralToken class manages the literal object.
 * @version 03/07/2022
 * @author Varun Parbhakar, Andrew Dibble, and Minh Trung Le.
 */
public class LiteralToken extends Token {
    private final int value; //int values of the LiteralToken

    /**
     * This is constructor for LiteralToken
     * @param value
     */
    public LiteralToken(int value) {
        this.value = value;
    }

    /**
     * This method returns value of the literal token.
     * @param litToken
     * @return
     */
    public static int getValue(LiteralToken litToken) {
        return litToken.value;
    }

    /**
     * toString method for literal token.
     * @return
     */
    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
