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

    public void clearDependencies(){
        dependsOn.removeAll(dependsOn);
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
    public static boolean validateInputFormula(Stack theStack) throws IllegalAccessException {
        String[] myArray = theStack.toArray();

//        Stack myStack = new Stack();
//        myStack.push("2");
//        myStack.push("8");
//        myStack.push("/");
//        myStack.push("9");
//        myStack.push("/");
//        myStack.push("3");
//        myStack.push("*");
//        myStack.push("4");
//        myStack.push("-");
//        String[] myArray = myStack.toArray();

        //Dummy array to store the last 2 number in the array
        for (int i = 0; i < myArray.length; i++) {
            if(isLiteral(myArray[i].toString())
                    || isOperator(myArray[i].toString())
                    || isCellReference(myArray[i].toString())) {
            }else {
                System.out.println("Something wrong");
                return false;
            }
        }



        for (int i = 0; i < myArray.length ; i++) {
            if(isOperator(myArray[i])) {
                if(!Stack.fetchLast2Values(myArray, i)) {
                    throw new IllegalArgumentException("Two Values were not found");
                }
            }
        }

        return true;
    }

    /**
     * Checks to see if the string is a literal.
     * @param theLiteral (String, Value)
     * @return (Boolean, literal or not)
     */
    public static boolean isLiteral(String theLiteral) {
        int asciiValue = theLiteral.charAt(0);
        if(asciiValue == 45) {
            for (int i = 1; i < theLiteral.length(); i++) {
                asciiValue = theLiteral.charAt(i);
                // If ASCII value < 0 || > 9 // Then return false;
                if(asciiValue < 48 || asciiValue > 57) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < theLiteral.length(); i++) {
                asciiValue = theLiteral.charAt(i);
                // If ASCII value < 0 || > 9 // Then return false;
                if(asciiValue < 48 || asciiValue > 57) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks to see if the string is a reference.
     * @param theReference (String, Value)
     * @return (Boolean, reference or not)
     */
    public static boolean isCellReference(String theReference) {
        int asciiValue = theReference.charAt(0);
        //Checking for the first alphabetCharacter
        if((asciiValue >= 65 && asciiValue <= 90) || (asciiValue >= 97 && asciiValue <= 122) ){
            //Checking for the numbers that follow the Alphabet reference
            for (int i = 1; i < theReference.length(); i++) {
                asciiValue = theReference.charAt(i);
                // If ASCII value < 0 || > 9 // Then return false;
                if(asciiValue < 48 || asciiValue > 57) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }
    /**
     * Checks to see if the string is a Operator.
     * @param theReference (String, Value)
     * @return (Boolean, operator or not)
     */
    public static boolean isOperator(String theReference) {
        int asciiValue = theReference.charAt(0);
        if(theReference.length() == 1) {
            if(asciiValue == 42 || asciiValue == 43 || asciiValue == 45 ||asciiValue == 47) {
                return true;
            }
        }


        return false;
    }
}

