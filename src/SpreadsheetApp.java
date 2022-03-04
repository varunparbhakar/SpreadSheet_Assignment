/*
 * Driver program of a spreadsheet application.
 * Text-based user interface.
 *
 * @author Donald Chinn
 */

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class SpreadsheetApp {

    /**
     * Read a string from standard input.
     * All characters up to the first carriage return are read.
     * The return string does not include the carriage return.
     *
     * @return a line of input from standard input
     */
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

        System.out.println("Enter the cell to change: ");
        inputCell = readString().toUpperCase();
        CellToken.getCellToken(inputCell, 0, cellToken);

        //Error check to make sure the row and column are within spreadsheet array bounds.
        checkCellBound(theSpreadsheet, cellToken);

        System.out.println("Enter the cell's new formula: ");
        inputFormula = readString();

        //Print the value of spreadsheet before reevaluation
        System.out.println("Spreadsheet before reevaluation ");
        menuPrintAllFormulas(theSpreadsheet);
        menuPrintValues(theSpreadsheet);
        System.out.println();

        //reset dependencies if cell token is not null
        if (cellToken != null) {
            resettingDependencies(theSpreadsheet, cellToken, inputFormula);
        }

        //Create cell from cell token
        Cell currentCell = theSpreadsheet.getCellValue(cellToken);

        //Creat a stack of expression from the formula
        expTreeTokenStack = Token.getFormula(inputFormula, theSpreadsheet, currentCell);
        theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack, inputFormula, theSpreadsheet);

        recalculateSpreadsheet(theSpreadsheet);

        //Print the value of spreadsheet after reevaluation
        System.out.println("Spreadsheet after reevaluation ");
        menuPrintAllFormulas(theSpreadsheet);
        menuPrintValues(theSpreadsheet);
        System.out.println();

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

            //read txt file and add item into the spreadsheet
            while ((line = br.readLine()) != null) {
                //read the line and split "A1 : 10" into an array [A1,10]
                tempArr = line.split(":");
                inputCell = tempArr[0];             //A1
                inputFormula = tempArr[1];          //10

                //if formula is "null", set empty ""
                if(inputFormula.equals("null")){
                    inputFormula = "";
                }

                //get the cell token from the String "A1". This is the address of the cell.
                CellToken.getCellToken(inputCell, 0, cellToken);

                //Error check to make sure the row and column are within spreadsheet array bounds.
                checkCellBound(theSpreadsheet, cellToken);

                //Create cell from cell token
                Cell currentCell = theSpreadsheet.getCellValue(cellToken);

                //Creat a stack of expression from the formula
                expTreeTokenStack = Token.getFormula(inputFormula, theSpreadsheet, currentCell);

                //Create a new cell from the token with value, formula and dependency
                //theSpreadsheet.creatCell(cellToken, inputFormula, expTreeTokenStack);

                theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack, inputFormula, theSpreadsheet);
            }

            br.close();
        } catch (IOException ioe) {
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
        if(cell == null){
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
            for (Cell[] cRow : spreadsheet) {
                for (Cell cell : cRow) {
                    if(cell != null) {
                        cell.setIndegree(cell.getNumDependencies());
                    }
                }
            }
            theSpreadsheet.topSort();       //finding which order to evaluate the cells

            for (Cell[] cRow : spreadsheet) {
                for (Cell cell : cRow) {
                    sortedCellArray.add(cell);
                }
            }
            System.out.println("Sort Cell Array before sort " + sortedCellArray.toString());

            Collections.sort(sortedCellArray, new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o1.getTopNum() - o2.getTopNum();
                }
            });

            //figure out why A1 is at the back of the list even tho it depends on 1 thing
            Collections.reverse(sortedCellArray);
            System.out.println("Sort Cell Array after reverse " + sortedCellArray.toString());
            System.out.println();

        } catch (Spreadsheet.CycleFoundException ioe) {
            ioe.printStackTrace();
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

        //call evaluate method for each of the cells in the arrayList
        Cell[][] spreadsheet = theSpreadsheet.getSpreadsheet();
        for (Cell cellArray : sortedCellArray) {
            for (Cell[] cRow : spreadsheet) {
                for (Cell cell : cRow) {
                    String formulaOfCellSpreadsheet = cell.getFormula();
                    String formulaOfCellArray = cellArray.getFormula();
                    if (formulaOfCellSpreadsheet == formulaOfCellArray) {
                        //Get the string formula in the current cell
                        String formula = cell.getFormula();

                        //Make a stack of expression
                        Stack expTreeTokenStack = Token.getFormula(formula, theSpreadsheet, cell);

                        //Build expression tree from the stack of expression
                        ExpressionTree expressionTree = new ExpressionTree(null);
                        expressionTree.BuildExpressionTree(expTreeTokenStack);

                        //Evaluate the expression tree then Update value to the current cell
                        int calculationResult = expressionTree.Evaluate(theSpreadsheet);
                        cell.setValue(calculationResult);
                    }
                }
            }
        }

    }

    /**
     * Error check to make sure the row and column are within spreadsheet array bounds.
     *
     * @param theSpreadsheet the current Spreadsheet
     * @param cellToken      the address of the current cell
     */
    private static void checkCellBound(Spreadsheet theSpreadsheet, CellToken cellToken) {
        if ((cellToken.getRow() < 0) ||
                (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
                (cellToken.getColumn() < 0) ||
                (cellToken.getColumn() >= theSpreadsheet.getNumColumns())) {

            System.out.println("Bad cell.");
            return;
        }
    }

    public static void main(String[] args) throws Spreadsheet.CycleFoundException {
        Spreadsheet theSpreadsheet = new Spreadsheet(4);        //creates a new spreadsheet with 8 rows and cols

        boolean done = false;
        String command = "";

        System.out.println(">>> Welcome to the TCSS 342 Spreadsheet <<<");
        System.out.println();
        System.out.println();

        while (!done) {
            System.out.println("Choose from the following commands:");
            System.out.println();
            System.out.println("p: print out the values in the spreadsheet");
            System.out.println("f: print out a cell's formula");
            System.out.println("a: print all cell formulas");
            System.out.println("c: change the formula of a cell");
            // BONUS
            System.out.println("r: read in a spreadsheet from a textfile");
            //BONUS System.out.println("s: save the spreadsheet to a textfile");

            System.out.println();
            System.out.println("q: quit");

            System.out.println();
            System.out.println("Enter your command: ");
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
/*
                case 's':
                    menuSaveSpreadsheet(theSpreadsheet);
                    break;
                    */

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

}