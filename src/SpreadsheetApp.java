/*
 * Driver program of a spreadsheet application.
 * Text-based user interface.
 *
 * @author Donald Chinn
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SpreadsheetApp {

    /**
     * Read a string from standard input.
     * All characters up to the first carriage return are read.
     * The return string does not include the carriage return.
     * @return  a line of input from standard input
     */
    public static String readString() {
        BufferedReader inputReader;
        String returnString = "";
        char ch;

        inputReader = new BufferedReader (new InputStreamReader(System.in));

        // read all characters up to a carriage return and append them
        // to the return String
        try {
            returnString = inputReader.readLine();
        }
        catch (IOException e) {
            System.out.println("Error in reading characters in readString.");
        }
        return returnString;
    }

    /**
     * print out the values in the spreadsheet, calls printValues method in the Spreadsheet class
     * @param theSpreadsheet
     */
    private static void menuPrintValues(Spreadsheet theSpreadsheet) {
        theSpreadsheet.printValues();
    }

    /**
     * print out a cell's formula
     * @param theSpreadsheet the spreadsheet containing the cell to print out
     */
    private static void menuPrintCellFormula(Spreadsheet theSpreadsheet) {
        CellToken cellToken = new CellToken();
        String inputString;

        System.out.println("Enter the cell: ");  //getting user input
        inputString = readString();
        cellToken = theSpreadsheet.getCellToken(inputString, 0, cellToken);  //assigning the cellToken's values according to input

        System.out.println(printCellToken(cellToken));      //printing out the coordinates of the cell, ex: A3
        System.out.println(": ");

        if ((cellToken.getRow() < 0) ||
                (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
                (cellToken.getColumn() < 0) ||
                (cellToken.getColumn() >= theSpreadsheet.getNumColumns())) {

            System.out.println("Bad cell.");
            return;
        }

        theSpreadsheet.printCellFormula(cellToken);     //printing out the formula associated with the cell token
        System.out.println();
    }

    /**
     * calls the spreadsheet class's print all formulas method
     * @param theSpreadsheet
     */
    private static void menuPrintAllFormulas(Spreadsheet theSpreadsheet) {
        theSpreadsheet.printAllFormulas();
        System.out.println();
    }


    private static void menuChangeCellFormula(Spreadsheet theSpreadsheet) {
        String inputCell;
        String inputFormula;
        CellToken cellToken = new CellToken();
        Stack expTreeTokenStack;
        // ExpressionTreeToken expTreeToken;

        System.out.println("Enter the cell to change: ");
        inputCell = readString();
        theSpreadsheet.getCellToken (inputCell, 0, cellToken);

        // error check to make sure the row and column
        // are within spreadsheet array bounds.
        if ((cellToken.getRow() < 0) ||
                (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
                (cellToken.getColumn() < 0) ||
                (cellToken.getColumn() >= theSpreadsheet.getNumColumns()) ) {

            System.out.println("Bad cell.");
            return;
        }

        System.out.println("Enter the cell's new formula: ");
        inputFormula = readString();
        expTreeTokenStack = Token.getFormula(inputFormula);

        /*
        // This code prints out the expression stack from
        // top to bottom (that is, reverse of postfix).
        while (!expTreeTokenStack.isEmpty())
        {
            expTreeToken = expTreeTokenStack.topAndPop();
            printExpressionTreeToken(expTreeToken);
        }
        */

        theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack);
        System.out.println();
    }

    public static void main(String[] args) {
        Spreadsheet theSpreadsheet = new Spreadsheet(8);        //creates a new spreadsheet with 8 rows and cols

        //Inserting the Items by Hand
        theSpreadsheet.insertItem(0,0,"5+25+78+10");
        theSpreadsheet.insertItem(2,1,"73+24");

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
    /* BONUS
            System.out.println("r: read in a spreadsheet from a textfile");
            System.out.println("s: save the spreadsheet to a textfile");
     */
            System.out.println();
            System.out.println("q: quit");

            System.out.println();
            System.out.println("Enter your command: ");
            command = readString();

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

                    /* BONUS:
                case 'r':
                    menuReadSpreadsheet(theSpreadsheet);
                    break;

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

    /**
     *  Given a CellToken, print it out as it appears on the
     *  spreadsheet (e.g., "A3")
     *  @param cellToken  a CellToken
     *  @return  the cellToken's coordinates
     */
    public static String printCellToken (CellToken cellToken) {
        char ch;
        String returnString = "";
        int col;
        int largest = 26;  // minimum col number with number_of_digits digits
        int number_of_digits = 2;

        col = cellToken.getColumn();

        // compute the biggest power of 26 that is less than or equal to col
        // We don't check for overflow of largest here.
        while (largest <= col) {
            largest = largest * 26;
            number_of_digits++;
        }
        largest = largest / 26;
        number_of_digits--;

        // append the column label, one character at a time
        while (number_of_digits > 1) {
            ch = (char) (((col / largest) - 1) + 'A');
            returnString += ch;
            col = col % largest;
            largest = largest  / 26;
            number_of_digits--;
        }

        // handle last digit
        ch = (char) (col + 'A');
        returnString += ch;

        // append the row as an integer
        returnString += cellToken.getRow();

        return returnString;
    }
}
