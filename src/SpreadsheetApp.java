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
        CellToken.getCellToken(inputString, 0, cellToken);

        System.out.print(CellToken.printCellToken(cellToken));
        System.out.print(": ");

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
        //UPDATE
        inputCell = readString().toUpperCase();
        CellToken.getCellToken (inputCell, 0, cellToken);



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

        Cell cell = theSpreadsheet.getCellValue(cellToken); //resetting dependencies
        cell.setFormula(inputFormula);
        LinkedList<Cell> list = cell.getDependsOn();
        for(Cell c: list) {             //removing changed cell from feedsInto of other cells
            LinkedList<Cell> feedsList = c.getFeedsInto();
            for(Cell c2: feedsList){
                if(c2.equals(cell)){
                    feedsList.remove(c2);
                }
            }

        }
        cell.clearDependencies();


        expTreeTokenStack = Token.getFormula(inputFormula, theSpreadsheet, cellToken);
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
        theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack, inputFormula, theSpreadsheet);
        System.out.println();
    }

    /**
     * read the .csv file and import cell value to the spreadsheet
     * @param theSpreadsheet
     */
    private static void menuReadSpreadsheet(Spreadsheet theSpreadsheet){
        CellToken cellToken = new CellToken();
        Stack expTreeTokenStack;

        try {
            File file = new File("textfile/spreadsheet.txt");
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line = "";
            String[] tempArr;
            String inputFormula = null;
            String inputCell = null;
            ArrayList<String> stringArrayList = new ArrayList<>();

            //read txt file and add item into the spreadsheet
            while((line = br.readLine()) != null) {
                tempArr = line.split(":");
                inputCell = tempArr[0];
                inputFormula = tempArr[1];

                CellToken.getCellToken (inputCell, 0, cellToken);

                //UPDATE
                // error check to make sure the row and column
                // are within spreadsheet array bounds.
                if ((cellToken.getRow() < 0) ||
                        (cellToken.getRow() >= theSpreadsheet.getNumRows()) ||
                        (cellToken.getColumn() < 0) ||
                        (cellToken.getColumn() >= theSpreadsheet.getNumColumns()) ) {

                    System.out.println("Bad cell.");
                    return;
                }
                theSpreadsheet.creatCell(cellToken, inputFormula);
                expTreeTokenStack = Token.getFormula(inputFormula, theSpreadsheet, cellToken);


                //theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack, inputFormula, theSpreadsheet);

            }

            Cell[][] spreadsheet = theSpreadsheet.getSpreadsheet();
            for(Cell[] cRow: spreadsheet){
                for(Cell cell: cRow){
                    cell.setIndegree(cell.getNumDependencies());
                }
            }
            theSpreadsheet.topSort();       //finding which order to evaluate the cells

            ArrayList<Cell> allCells = new ArrayList<>();
            for(Cell[] cRow: spreadsheet){
                for(Cell cell: cRow){
                    allCells.add(cell);
                }
            }

            Collections.sort(allCells, new Comparator<Cell>() {
                @Override
                public int compare(Cell o1, Cell o2) {
                    return o1.getTopNum() - o2.getTopNum();
                }
            });


            //figure out why A1 is at the back of the list even tho it depends on 1 thing

            Collections.reverse(allCells);

            //call evaluate method for each of the cells in the arrayList

            br.close();
        } catch(IOException | Spreadsheet.CycleFoundException ioe){
            ioe.printStackTrace();
        }
    }


    public static void main(String[] args) {
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