
import java.util.LinkedList; //Importing LinkedLists

/**
 * This cell class is responsible for holding the main properties of a cell in a
 * spreadsheet.
 * @version 03/07/2022
 * @author Varun Parbhakar, Andrew Dibble, and Minh Trung Le.
 */
public class Cell {
    private final LinkedList<Cell> dependsOn;       //the cells that this cell depends on
    private final LinkedList<Cell> feedsInto;       //the cells that depend on this cell
    private String formula;
    private int value;
    private int topNum;                             // Topological rank
    private int indegree;
    // the expression tree below represents the formula
    private ExpressionTree expressionTree;

    /**
     * Constructor for Cell.
     * @param theValue
     */
    public Cell(String theValue) {
        formula = theValue;
        dependsOn = new LinkedList<>();
        feedsInto = new LinkedList<>();
    }

    /**
     * This method should only be called whenever there are multiple tokens to a cell.
     * A single literal or a reference should not be evaluated with this method.
     *
     * @param theStack (The stack containing the post fix version of the expression)
     * @return (Double, the finalSolution)
     */
    public static boolean validateInputFormula(Stack theStack, String theString) throws IllegalAccessException {
        //Checking if an operator appears back to back in the formula
        String forumla = theString;
        boolean seenOperator = false;
        for (int i = 0; i < forumla.length(); i++) {
            if (isOperator(String.valueOf(forumla.charAt(i))) && seenOperator) {
                throw new IllegalArgumentException("Operator appears twice");
            } else seenOperator = isOperator(String.valueOf(forumla.charAt(i)));
        }
        Object[] myArray = theStack.toArray();
        if (myArray.length == 0) {
            throw new IllegalArgumentException("Stack is empty");
        }

        //Checking if the elements in the user's formula are inputted correctly
        for (int i = 0; i < myArray.length; i++) {
            if (isLiteral(myArray[i].toString())
                    || isOperator(myArray[i].toString())) {
            } else {
                if (!(myArray[i] instanceof CellToken)) {
                    throw new IllegalArgumentException("Invalid Input");
                }
            }
        }

        // Checking if the formula is in the correct postFix form
        for (int i = 0; i < myArray.length; i++) {
            if (isOperator(String.valueOf(myArray[i]))) {
                if (!Stack.fetchLast2Values(myArray, i)) {
                    throw new IllegalArgumentException("Two Values were not found");
                }
            }
        }

        return true;
    }

    /**
     * Checks to see if the string is a literal.
     *
     * @param theLiteral (String, Value)
     * @return (Boolean, literal or not)
     */
    public static boolean isLiteral(String theLiteral) {
        int asciiValue = theLiteral.charAt(0);
        if (asciiValue == 45) {
            for (int i = 1; i < theLiteral.length(); i++) {
                asciiValue = theLiteral.charAt(i);
                // If ASCII value < 0 || > 9 // Then return false;
                if (asciiValue < 48 || asciiValue > 57) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < theLiteral.length(); i++) {
                asciiValue = theLiteral.charAt(i);
                // If ASCII value < 0 || > 9 // Then return false;
                if (asciiValue < 48 || asciiValue > 57) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks to see if the string is a reference.
     *
     * @param theReference (String, Value)
     * @return (Boolean, reference or not)
     */
    public static boolean isCellReference(String theReference) {
        int asciiValue = theReference.charAt(0);
        //Checking for the first alphabetCharacter
        if ((asciiValue >= 65 && asciiValue <= 90) || (asciiValue >= 97 && asciiValue <= 122)) {
            //Checking for the numbers that follow the Alphabet reference
            for (int i = 1; i < theReference.length(); i++) {
                asciiValue = theReference.charAt(i);
                // If ASCII value < 0 || > 9 // Then return false;
                if (asciiValue < 48 || asciiValue > 57) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    /**
     * Checks to see if the string is a Operator.
     *
     * @param theReference (String, Value)
     * @return (Boolean, operator or not)
     */
    public static boolean isOperator(String theReference) {
        int asciiValue = theReference.charAt(0);
        if (theReference.length() == 1) {
            return asciiValue == 42 || asciiValue == 43 || asciiValue == 45 || asciiValue == 47;
        }
        return false;
    }

    /**
     * This method returns topological ordering number.
     * @return
     */
    public int getTopNum() {
        return topNum;
    }

    /**
     * This method sets topological ordering number.
     * @return
     */
    public void setTopNum(int topNum) {
        this.topNum = topNum;
    }

    /**
     * This method returns indegree.
     * @return
     */
    public int getIndegree() {
        return indegree;
    }

    /**
     * This method sets indegree.
     * @return
     */
    public void setIndegree(int indegree) {
        this.indegree = indegree;
    }

    /**
     * This method returns expressionTree.
     * @return
     */
    public ExpressionTree getExpressionTree() {
        return expressionTree;
    }

    /**
     * This method sets expressionTree.
     * @return
     */
    public void setExpressionTree(ExpressionTree expressionTree) {
        this.expressionTree = expressionTree;
    }


    /**
     * adds a cell to the dependsOn LinkedList
     *
     * @param cell
     */
    public void addDependency(Cell cell) {
        if (cell != null) {
            dependsOn.add(cell);
        }
        return;
    }

    /**
     * adds a cell the feedsInto LinkedList
     */
    public void addFeedInto(Cell cell) {
        if (cell != null) {
            feedsInto.add(cell);
        }
        return;
    }

    /**
     * This method clears the dependencies .
     * @return
     */
    public void clearDependencies() {
        dependsOn.removeAll(dependsOn);
    }

    /**
     * This method returns the value of the cell.
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     * Set value for cell
     *
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * This method returns number of dependencies.
     * @return
     */
    public int getNumDependencies() {
        return dependsOn.size();
    }

    /**
     * This method returns the linkedlist dependsOn.
     *
     * @return dependsOn
     */
    public LinkedList<Cell> getDependsOn() {
        return dependsOn;
    }

    /**
     * This method returns the linkedlist feedsInto.
     *
     * @return feedsInto
     */
    public LinkedList<Cell> getFeedsInto() {
        return feedsInto;
    }

    /**
     * This method returns the formula of the cell.
     * @return
     */
    public String getFormula() {
        return formula;
    }

    /**
     * Set the formula for cell
     *
     * @param formula
     */
    public void setFormula(String formula) {
        this.formula = formula;
    }

    /**
     * To String method that prints the formula
     * @return
     */
    @Override
    public String toString() {
        return formula;
    }
}

