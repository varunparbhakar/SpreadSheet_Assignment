/*
 * Driver program of a spreadsheet application.
 * Text-based user interface.
 *
 * @author Donald Chinn
 */

import java.io.*;
import java.util.*;

public class SpreadsheetApp {

    /**
     * Read a string from standard input.
     * All characters up to the first carriage return are read.
     * The return string does not include the carriage return.
     *
     * @return a line of input from standard input
     */

    private static boolean cyclePresent = false;

    public static String readString() {
        BufferedReader inputReader;
        String returnString = "";
        char ch;

        inputReader = new BufferedReader(new InputStreamReader(System.in));

        // read all characters up to a carriage return and append them
        // to the return String
        try {
            returnString = inputReader.readLine();
        } catch (IOException e) {
            System.out.println("Error in reading characters in readString.");
        }
        return returnString;
    }

    /**
     * print out the values in the spreadsheet, calls printValues method in the Spreadsheet class
     *
     * @param theSpreadsheet current spreadsheet
     */
    private static void menuPrintValues(Spreadsheet theSpreadsheet) {
        theSpreadsheet.printValues();
    }

    /**
     * print out a cell's formula
     *
     * @param theSpreadsheet the spreadsheet containing the cell to print out
     */
    private static void menuPrintCellFormula(Spreadsheet theSpreadsheet) {
        CellToken cellToken = new CellToken();
        String inputString;

        //getting user input
        System.out.println("Enter the cell: ");
        inputString = readString();
        CellToken.getCellToken(inputString, 0, cellToken);

        System.out.print(CellToken.printCellToken(cellToken));
        System.out.print(": ");

        //Error check to make sure the row and column are within spreadsheet array bounds.
        checkCellBound(theSpreadsheet, cellToken);

        //printing out the formula associated with the cell token
        theSpreadsheet.printCellFormula(cellToken);
        System.out.println();
    }

    /**
     * calls the spreadsheet class's print all formulas method
     *
     * @param theSpreadsheet current spreadsheet
     */
    private static void menuPrintAllFormulas(Spreadsheet theSpreadsheet) {
        theSpreadsheet.printAllFormulas();
        System.out.println();
    }

    /**
     * change the cell formula
     *
     * @param theSpreadsheet current spreadsheet
     * @throws Spreadsheet.CycleFoundException
     */
    private static void menuChangeCellFormula(Spreadsheet theSpreadsheet) throws Spreadsheet.CycleFoundException {
        String inputCell;
        String inputFormula;
        CellToken cellToken = new CellToken();
        Stack expTreeTokenStack;
        Cell currentCell;
        String previousFormula = "";

        boolean cellFound = false;
        while (!cellFound) {
            System.out.print("\nEnter the cell to change: ");
            inputCell = readString().toUpperCase();
            CellToken.getCellToken(inputCell, 0, cellToken);

            //Error check to make sure the row and column are within spreadsheet array bounds.
            if (checkCellBound(theSpreadsheet, cellToken)) {
                cellFound = true;
            } else {
                System.out.println("ERROR: INVALID CELL");
            }
        }
        boolean correctInput = false;
        int counter = 0;
        while (!correctInput) {
            System.out.print("\nEnter the cell's new formula: ");
            inputFormula = readString().toUpperCase();


//        //Print the value of spreadsheet before reevaluation
//        System.out.println("Spreadsheet before reevaluation ");
//        menuPrintAllFormulas(theSpreadsheet);
//        menuPrintValues(theSpreadsheet);
//        System.out.println();
        currentCell = theSpreadsheet.getCellValue(cellToken);
        previousFormula = currentCell.getFormula();          //getting the old formula

        //reset dependencies if cell token is not null
        if (cellToken != null) {
            resettingDependencies(theSpreadsheet, cellToken, inputFormula);
        }

        //Create cell from cell token
        currentCell = theSpreadsheet.getCellValue(cellToken);

            //Create cell from cell token
            currentCell = theSpreadsheet.getCellValue(cellToken);

            if(currentCell == null){
                currentCell = theSpreadsheet.insertItem(cellToken.getRow(), cellToken.getColumn(), inputFormula);
            }

            //Creat a stack of expression from the formula
            try {
                expTreeTokenStack = Token.getFormula(inputFormula, theSpreadsheet, currentCell);
                Stack expressionTreeCopy = expTreeTokenStack.copy();
                if(expressionTreeCopy.size() != 0) {

                    theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack, inputFormula, theSpreadsheet);

                    if(expressionTreeCopy.size() > 2) {
                        Cell.validateInputFormula(expressionTreeCopy, inputFormula);
                    }
                    correctInput = true;
                } else {
                    throw new IllegalArgumentException("Stack is empty");
                }
            } catch (Exception e) {
                System.out.println("ERROR: Please enter the correct formula");
            }

        }


        //recalculate whole spreadsheet
        recalculateSpreadsheet(theSpreadsheet);

        if (cyclePresent) {
            cyclePresent = false;
            currentCell = theSpreadsheet.insertItem(cellToken.getRow(), cellToken.getColumn(), previousFormula);
        }


        /*
        // This code prints out the expression stack from
        // top to bottom (that is, reverse of postfix).
        while (!expTreeTokenStack.isEmpty())
        {
            expTreeToken = expTreeTokenStack.topAndPop();
            printExpressionTreeToken(expTreeToken);
        }
        */

        //changed this code - Add input formula
        //theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack, inputFormula, theSpreadsheet);
    }

    /**
     * read the .txt file and import cell value to the spreadsheet
     *
     * @param theSpreadsheet
     */
    private static void menuReadSpreadsheet(Spreadsheet theSpreadsheet) {
        CellToken cellToken = new CellToken();
        Stack expTreeTokenStack;

        try {
            File file = new File("textfile/spreadsheet.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            String[] tempArr;
            String inputFormula;
            String inputCell;

            if (theSpreadsheet.getNumColumns() >= 4 && theSpreadsheet.getNumRows() >= 4) {
                //read txt file and add item into the spreadsheet
                while ((line = br.readLine()) != null) {
                    //read the line and split "A1 : 10" into an array [A1,10]
                    tempArr = line.split(":");
                    inputCell = tempArr[0];             //A1
                    inputFormula = tempArr[1];          //10

                    //if formula is "null", set empty ""
                    if (inputFormula.equals("null")) {
                        inputFormula = "";
                    }

                    //get the cell token from the String "A1". This is the address of the cell.
                    CellToken.getCellToken(inputCell, 0, cellToken);

                    //Error check to make sure the row and column are within spreadsheet array bounds.
                    checkCellBound(theSpreadsheet, cellToken);
                    theSpreadsheet.creatCell(cellToken, inputFormula);
                    //Create cell from cell token
                    Cell currentCell = theSpreadsheet.getCellValue(cellToken);



                try{
                    //Create a stack of expression from the formula
                    expTreeTokenStack = Token.getFormula(inputFormula, theSpreadsheet, currentCell);
                    Stack expressionTreeCopy = expTreeTokenStack.copy();
                    if(expressionTreeCopy.size() != 0) {
                        ExpressionTree expressionTree = new ExpressionTree(null);
                        expressionTree.BuildExpressionTree(expTreeTokenStack);

                        if(expressionTreeCopy.size() > 2) {
                            Cell.validateInputFormula(expressionTreeCopy, inputFormula);
                        }
                        currentCell.setExpressionTree(expressionTree);
                    } else {
                        throw new IllegalArgumentException("Stack is empty");
                    }


                }catch (Exception e) {
                    System.out.println("ERROR: Invalid formula found at " + inputCell);
                }



                    //Create a new cell from the token with value, formula and dependency
                    //theSpreadsheet.creatCell(cellToken, inputFormula, expTreeTokenStack);

                    //theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack, inputFormula, theSpreadsheet);
                }

                //recalculate whole spreadsheet
                recalculateSpreadsheet(theSpreadsheet);

                if (cyclePresent) {       //resetting spreadsheet if a cycle is found
                    cyclePresent = false;
                    for (int i = 0; i < theSpreadsheet.getNumRows(); i++) {
                        for (int j = 0; j < theSpreadsheet.getNumColumns(); j++) {
                            theSpreadsheet.getSpreadsheet()[i][j] = new Cell("");
                        }
                    }
                }

                br.close();
            } else {
                System.out.println("WARNING!!!");
                System.out.println("The spreadsheet has less rows or columns than the txt file.");
                System.out.println("The file is not imported.");
            }
        } catch (IOException | Spreadsheet.CycleFoundException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Resetting dependencies after updating the formula
     *
     * @param theSpreadsheet current spreadsheet
     * @param cellToken      address of the current cell
     * @param inputFormula   new string of formula
     */
    private static void resettingDependencies(Spreadsheet theSpreadsheet, CellToken cellToken, String inputFormula) {
        //Update new formula to the current cell
        Cell cell = theSpreadsheet.getCellValue(cellToken);
        if (cell == null) {
            cell = new Cell("");
        }
        cell.setFormula(inputFormula);

        //Get a list of dependency
        LinkedList<Cell> list = cell.getDependsOn();

        //removing changed cell from feedsInto of other cells
        for (Cell c : list) {
            LinkedList<Cell> feedsList = c.getFeedsInto();
            for (Cell c2 : feedsList) {
                if (c2.equals(cell)) {
                    feedsList.remove(c2);
                    break;
                }
            }
        }
        //Remove old dependencies
        cell.clearDependencies();
    }

    /**
     * Return an array list of sorted cell
     *
     * @param theSpreadsheet current spreadsheet
     * @return ArrayList of cells sorted based on their dependencies
     * @throws Spreadsheet.CycleFoundException
     */
    private static ArrayList<Cell> topSortDependency(Spreadsheet theSpreadsheet) throws Spreadsheet.CycleFoundException {
        ArrayList<Cell> sortedCellArray = new ArrayList<>();
        try {
            Cell[][] spreadsheet = theSpreadsheet.getSpreadsheet();

            theSpreadsheet.topSort();       //finding which order to evaluate the cells

            for (Cell[] cRow : spreadsheet) {
                for (Cell cell : cRow) {
                    sortedCellArray.add(cell);
                }
            }

            Collections.sort(sortedCellArray, new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o1.getTopNum() - o2.getTopNum();
                }
            });

        } catch (Spreadsheet.CycleFoundException ioe) {
            System.out.println("ERROR: A Cycle has been found, recent values have been reverted.");
            cyclePresent = true;
        }
        return sortedCellArray;
    }

    /**
     * Recalculate the spreadsheet after updating the formula for one cell
     *
     * @param theSpreadsheet current Spreadsheet
     * @throws Spreadsheet.CycleFoundException
     */
    private static void recalculateSpreadsheet(Spreadsheet theSpreadsheet) throws Spreadsheet.CycleFoundException {
        ArrayList<Cell> sortedCellArray = topSortDependency(theSpreadsheet);
        CellToken cellToken = new CellToken();
        Stack expTreeTokenStack;

        //call evaluate method for each of the cells in the arrayList

        Cell[][] spreadsheet = theSpreadsheet.getSpreadsheet();
        for (Cell cell : sortedCellArray) {
            String formula = cell.getFormula();

            //Make a stack of expression


            //Build expression tree from the stack of expression

            //Evaluate the expression tree then Update value to the current cell
            if (cell.getFormula() != "") {
                int calculationResult = cell.getExpressionTree().Evaluate(theSpreadsheet);
                cell.setValue(calculationResult);
            }
        }
    }

    /**
     * Error check to make sure the row and column are within spreadsheet array bounds.
     *
     * @param theSpreadsheet the current Spreadsheet
     * @param cellToken      the address of the current cell
     */
    private static boolean checkCellBound(Spreadsheet theSpreadsheet, CellToken cellToken) {
        if ((cellToken.getRow() < 0) ||
                (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
                (cellToken.getColumn() < 0) ||
                (cellToken.getColumn() >= theSpreadsheet.getNumColumns())) {
            return false;
        }
        return true;
    }

    public static void menuSaveSpreadsheet(Spreadsheet theSpreadSheet) {
        theSpreadSheet.exportSpreadSheet();
    }

    public static void main(String[] args) throws Spreadsheet.CycleFoundException {
        //Setup the Spreadsheet
        Spreadsheet theSpreadsheet = setupSpreadsheet();        //creates a new spreadsheet (USER LIMITED TO 26 ROWS AND COLUMNS)
//        Spreadsheet theSpreadsheet = new Spreadsheet(4);

        boolean done = false;
        String command = "";


        while (!done) {
            System.out.println("\nChoose from the following commands:");
            System.out.println();
            System.out.println("p: print out the values in the spreadsheet");
            System.out.println("f: print out a cell's formula");
            System.out.println("a: print all cell formulas");
            System.out.println("c: change the formula of a cell");
            // BONUS
            System.out.println("r: read in a spreadsheet from a textfile");
            System.out.println("s: save the spreadsheet to a Spreadsheet");

            System.out.println();
            System.out.println("q: quit");

            System.out.println();
            System.out.print("\nEnter your command: ");
            //UPDATE


            command = readString().toLowerCase();

            // We care only about the first character of the string
            switch (command.charAt(0)) {
                case 'p':
                    menuPrintValues(theSpreadsheet);
                    break;

                case 'f':
                    menuPrintCellFormula(theSpreadsheet);
                    break;

                case 'a':
                    menuPrintAllFormulas(theSpreadsheet);
                    break;

                case 'c':
                    menuChangeCellFormula(theSpreadsheet);
                    break;

                // BONUS:
                case 'r':
                    menuReadSpreadsheet(theSpreadsheet);
                    break;

                case 's':
                    menuSaveSpreadsheet(theSpreadsheet);
                    break;

                case 'q':
                    done = true;
                    break;

                default:
                    System.out.println(command.charAt(0) + ": Bad command.");
                    break;
            }
            System.out.println();

        }

        System.out.println("Thank you for using our spreadsheet.");
    }

    public static Spreadsheet setupSpreadsheet() {
        String usInput = "";

        System.out.println(">>> Welcome to the TCSS 342 Spreadsheet <<<");
        System.out.println();

        boolean userSelected = false;
        while (!userSelected) {
            System.out.println("\nPlease select how would you like to setup your spreadsheet?");
            System.out.println("1. Square");
            System.out.println("2. Custom Rows and Columns");
            System.out.println("3. Help");
            System.out.print("\nPlease select(1/2/3): ");
            usInput = readString();
            switch (usInput) {
                case ("1"):
                    //Square Spreadsheet
                    System.out.println("You have selected a Square Spreadsheet");
                    int size = validateSelection("Please enter the size of the spreadsheet: ");
                    if (size == Integer.MIN_VALUE) {
                        break; //User has selected back
                    } else {
                        userSelected = true;
                        return new Spreadsheet(size);
                    }


                case ("2"):
                    System.out.println("You have selected a Custom spreadsheet");
                    int numberOfRows = validateSelection("Please enter the number of rows: ");

                    if (numberOfRows == Integer.MIN_VALUE) { //User wants to go back
                        break;
                    }
                    int numberOfColumns = validateSelection("Please enter the number of Columns: ");

                    if (numberOfRows == Integer.MIN_VALUE) { //User wants to go back
                        break;
                    }
                    userSelected = true;
                    return new Spreadsheet(numberOfRows, numberOfColumns);

                case ("3"):
                    String squareString = """
                            \n1. Square option allows you to create a spreadsheet with the same amount of rows
                            and columns, you will have to enter a single integer value.""";

                    String customString = """
                            \n2. Custom option allows you to create a spreadsheet with the custom number of rows 
                            and columns. NOTE you are limited to only creating upto 26 columns. You will be 
                            asked to enter numbers, first time you will enter the number of rows and second time
                            you will enter the number of columns.""";
                    System.out.println(squareString);
                    System.out.println(customString);
                    break;
                default:
                    System.out.println("\nERROR: Please enter correct a number corresponding to the option\n");
            }

        }
        return new Spreadsheet(4);
    }

    public static boolean validateNumber(String theString) {
        for (int i = 0; i < theString.length(); i++) {
            if (theString.charAt(i) > 57 || theString.charAt(i) < 48) {
                System.out.println("ERROR: Please enter valid input");
                return false;
            }
        }
        return true;
    }

    public static int validateSelection(String displayMessage) {
        String customSelection = "";
        Scanner scan = new Scanner(System.in);
        int userNumber = 0;
        boolean inputFound = false;
        while (!inputFound) {
            System.out.println("\nPress 'B' to go back.");
            System.out.print(displayMessage);
            customSelection = scan.next();
            switch (customSelection.toLowerCase()) {
                case ("b"):
                    inputFound = true;
                    return Integer.MIN_VALUE;
                default:
                    if (validateNumber(customSelection)) {
                        if (Integer.parseInt(customSelection) != 0 && Integer.parseInt(customSelection) < 27) {
                            return Integer.parseInt(customSelection);
                        } else {
                            System.out.println("Please enter a size between 1 and 26");
                        }
                    }

            }
        }
        return userNumber;
    }

}