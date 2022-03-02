public class LiteralToken extends Token {
    private int value;

    public LiteralToken(int value){this.value = value;};

    public static int getValue(LiteralToken litToken){
        return litToken.value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
