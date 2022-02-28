public class Cell {
    private String formula;
    private int value;
    public Cell(String theValue) {
        formula = theValue;
    }
    // the expression tree below represents the formula
    private ExpressionTree expressionTree;
    public void Evaluate (Spreadsheet spreadsheet) {

    }



    @Override
    public String toString() {
        return formula;
    }
}
