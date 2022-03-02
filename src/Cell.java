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
     * Set value for cell
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Set the formula for cell
     * @param formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
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

    /**
     * This method evaluate the expression in the cell
     * for example 5+17*3 = 56
     * @param theStack (The stack containing the post fix version of the expression)
     * @return (Double, the finalSolution)
     */
    public static double evaluateCell(Stack theStack) {
        String[] myArray = theStack.toArray();
        //Dummy array to store the last 2 number in the array
        double[] lastTwoNumbers;

        double solution = 0;

        for (int i = 0; i < myArray.length ; i++) {
            switch (myArray[i]) {
                case ("/"):
                    lastTwoNumbers = Stack.fetchLast2Values(myArray, i);
                    solution = lastTwoNumbers[0] / lastTwoNumbers[1];
                    myArray[i] = Double.toString(solution);
                    break;

                case ("*"):
                    lastTwoNumbers = Stack.fetchLast2Values(myArray, i);
                    solution = lastTwoNumbers[0] * lastTwoNumbers[1];
                    myArray[i] = Double.toString(solution);
                    break;

                case ("+"):
                    lastTwoNumbers = Stack.fetchLast2Values(myArray, i);
                    solution = lastTwoNumbers[0] + lastTwoNumbers[1];
                    myArray[i] = Double.toString(solution);
                    break;

                case ("-"):
                    lastTwoNumbers = Stack.fetchLast2Values(myArray, i);
                    solution = lastTwoNumbers[0] - lastTwoNumbers[1];
                    myArray[i] = Double.toString(solution);
                    break;
            }
        }


        return solution;
    }








}
