public class Cell {
    private String formula;
    private int value;
    public Cell(String theValue) {
        formula = theValue;
    }
    // the expression tree below represents the formula
    private ExpressionTree expressionTree;


    public String getFormula(){
        return formula;
    }

    public int getValue(){
        return value;
    }
    public void Evaluate (Spreadsheet spreadsheet) { //TODO

    }



    @Override
    public String toString() {
        return formula;
    }
}
