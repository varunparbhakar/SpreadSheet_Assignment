import java.util.LinkedList;

public class Cell {
    private String formula;
    private int value;
    private LinkedList<Cell> dependsOn;       //the cells that this cell depends on
    private LinkedList<Cell> feedsInto;       //the cells that depend on this cell
    private int dependencies;

    // the expression tree below represents the formula
    private ExpressionTree expressionTree;

    public Cell(String theValue) {
        formula = theValue;
        dependsOn = new LinkedList<>();
        feedsInto = new LinkedList<>();
        dependencies = 0;
    }


    /**
     * adds a cell to the dependsOn LinkedList
     * @param cell
     */
    public void addDependency (Cell cell)	{
        dependsOn.add(cell);
    }

    /**
     * adds a cell the feedsInto LinkedList
     */
    public void addFeedInto (Cell cell) {
        feedsInto.add(cell);
    }

    public int getValue(){
        return value;
    }
    public int getNumDependencies(){
        return dependencies;
    }
    public void Evaluate (Spreadsheet spreadsheet) { //TODO

    }


    public String getFormula() {
        return formula;
    }

    @Override
    public String toString() {
        return formula;
    }








}
