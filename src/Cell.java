import java.util.LinkedList;

public class Cell {
    private String formula;
    private int value;
    private LinkedList<Cell> dependsOn;       //the cells that this cell depends on
    private LinkedList<Cell> feedsInto;       //the cells that depend on this cell
    private int topNum;
    private int indegree;
    // the expression tree below represents the formula
    private ExpressionTree expressionTree;

    public Cell(String theValue) {
        formula = theValue;
        dependsOn = new LinkedList<>();
        feedsInto = new LinkedList<>();
    }

    /**
     * Set value for cell
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

    public void setTopNum(int topNum){this.topNum = topNum;}

    public int getTopNum(){return topNum;}

    public int getIndegree(){return indegree;}

    public void setIndegree(int indegree){this.indegree = indegree;}
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
        if(cell != null) {
            dependsOn.add(cell);
        }return;
    }

    /**
     * adds a cell the feedsInto LinkedList
     */
    public void addFeedInto (Cell cell) {
        if(cell != null)  {
            feedsInto.add(cell);
        } return;
    }

    public int getValue(){
        return value;
    }

    public int getNumDependencies(){
        return dependsOn.size();
    }

    /**
     * Get dependsOn
     * @return dependsOn
     */
    public LinkedList<Cell> getDependsOn() {
        return dependsOn;
    }

    /**
     * Get getFeedsInto
     * @return feedsInto
     */
    public LinkedList<Cell> getFeedsInto() {
        return feedsInto;
    }


    /*public void Evaluate (Spreadsheet spreadsheet) { //TODO DO WE NEED THIS???

    }*/


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
