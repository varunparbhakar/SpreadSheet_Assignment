import java.io.BufferedWriter;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Spreadsheet {
    private static int row;       //number of rows
    private static int col;       //num of columns
    private final Cell[][] spreadsheet; //the two-dimensional array to act as the spreadsheet and hold the cell values
    private final String fileName = "Saved_Spreadsheet.csv";

    public Spreadsheet(int num) {
        row = num;
        col = num;
        spreadsheet = new Cell[row][col];
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                spreadsheet[i][j] = insertItem(i, j, "");
            }
        }
    }

    public Spreadsheet(int row, int col) {
        Spreadsheet.row = row;
        Spreadsheet.col = col;
        spreadsheet = new Cell[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                spreadsheet[i][j] = insertItem(i, j, "");
            }
        }
    }

    /**
     * get the cell corresponding to the cell token
     *
     * @param cToken
     * @return
     */
    public Cell getCellValue(CellToken cToken) {
        return spreadsheet[cToken.getRow()][cToken.getColumn()];
    }

    public Cell insertItem(int theRow, int theColumn, String theValue) {

        Cell myCell = new Cell(theValue);
        spreadsheet[theRow][theColumn] = myCell;
        return myCell;
    }

    public int getNumRows() {
        return row;
    }

    public int getNumColumns() {
        return col;
    }

    public Cell[][] getSpreadsheet() {
        return spreadsheet;
    }


    /**
     * Create a new cell, add formula, add dependencies
     *
     * @param cellToken cell token of the new cell
     * @param formula   formula input from the new cell
     *                  //* @param expTreeTokenStack stack of expression from the formula
     */
    public void creatCell(CellToken cellToken, String formula) {
        int rowNumber = cellToken.getRow();
        int colNumber = cellToken.getColumn();
        //if this is not null, then we know it was already created before because another cell depends on it
        if (spreadsheet[rowNumber][colNumber] != null) {
            LinkedList<Cell> feedsList = spreadsheet[rowNumber][colNumber].getFeedsInto();
            for (Cell cell : feedsList) {
                cell.clearDependencies();
            }


            Cell currentCell = new Cell(formula);

            spreadsheet[rowNumber][colNumber] = currentCell;

            //adding new dependencies
            for (Cell cell : feedsList) {
                spreadsheet[rowNumber][colNumber].addFeedInto(cell);
                cell.addDependency(currentCell);
            }


        } else {
            Cell currentCell = new Cell(formula);
            spreadsheet[rowNumber][colNumber] = currentCell;
        }
    }


    /**
     * Update the formula and calculate the new value of the cell
     *
     * @param cellToken         cell address that user want to change update new formula, ie. cell A1
     * @param expTreeTokenStack stack of token from expression of new formula
     */
    public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack) {

        int rowNumber = cellToken.getRow();
        int colNumber = cellToken.getColumn();
        Cell myCell = spreadsheet[rowNumber][colNumber];

        //build expression tree from the stack
        ExpressionTree expressionTree = new ExpressionTree(null);
        expressionTree.BuildExpressionTree(expTreeTokenStack);
        myCell.setExpressionTree(expressionTree);

    }

    /**
     * print out all the values in the spreadsheet
     */
    public void printValues() {
        String str;
        printHeader();
        int rowNumber = 0;
        String rowString;

        for (Cell[] rows : spreadsheet) {
            rowString = String.format("%-6s|", "Row " + rowNumber);
            System.out.format(rowString);

            for (Cell number : rows) {
                if (number != null) {
                    str = String.format("%-6d|", number.getValue());
                } else {
                    str = String.format("%-6d|", 0);
                }
                System.out.format(str);
            }
            System.out.println();
            rowNumber++;
        }
    }

    /**
     * prints out the associated formula to the cell token
     *
     * @param cellToken
     */
    public void printCellFormula(CellToken cellToken) {
        int row = cellToken.getRow();
        int column = cellToken.getColumn();

        Cell cell = spreadsheet[row][column];
        System.out.println(cell);

    }

    /**
     * prints all the formulas for all the cells in the spreadsheet
     */
    public void printAllFormulas() {
        String str;
        printHeader();
        int rowNumber = 0;
        String rowString;

        for (Cell[] rows : spreadsheet) {
            rowString = String.format("%-6s|", "Row " + rowNumber);
            System.out.format(rowString);
            for (Cell number : rows) {
                if (number != null) {
                    str = String.format("%-6s|", number.getFormula());
                } else {
                    str = String.format("%-6s|", " ");
                }
                System.out.format(str);
            }
            System.out.println();
            rowNumber++;
        }
    }

    /**
     * sorts the cells in the spreadsheet in topological order and evaluates the cells values while the sorting is occurring
     *
     * @throws CycleFoundException
     */
    public void topSort() throws CycleFoundException {

        Queue q = new Queue();
        int counter = 0;
        Cell c;

        for (Cell[] cellRow : spreadsheet) {       //accessing every Cell
            for (Cell cell : cellRow) {
                cell.setIndegree(cell.getNumDependencies());        //setting the indegrees
                if (cell != null) {
                    if (cell.getIndegree() == 0) {     //if there is no dependence add it to the queue
                        q.enqueue(cell);
                    }
                }
            }
        }

        while (!q.isEmpty()) {            //while there are still more cells
            c = q.dequeue();
            c.setTopNum(++counter);     //setting the order, 1 is smallest topNum

            for (Cell cell : c.getFeedsInto()) {
                cell.setIndegree(cell.getIndegree() - 1);  //subtracting 1 from the indegree
                if (cell.getIndegree() == 0) {               //if no more dependencies add to queue
                    q.enqueue(cell);
                }
            }
        }

        if (counter != (getNumColumns() * getNumRows())) {

            throw new CycleFoundException("Cycle found");
        }

    }

    public void printHeader() {
        char[] alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for (int i = 0; i <= getNumColumns(); i++) {
            String str = String.format("%-7c", alphabet[i]);
            System.out.format(str);
        }

        System.out.println();
    }

    /**
     * This method takes the current spreadsheet and exports it
     * to a .cvs file format
     */
    public void exportSpreadSheet() {
        try {
            PrintWriter pw = new PrintWriter(new File(fileName));

            StringBuilder sb = new StringBuilder();

            //Reading every Cell
            for (int i = 0; i < getNumRows(); i++) {
                for (int j = 0; j < getNumColumns(); j++) {
                    Cell myCell = spreadsheet[i][j];
                    sb.append(myCell.getFormula());
                    sb.append(",");
                }
                sb.append("\n");
            }
            //Writing to spreadsheet and closing the file
            pw.write(sb.toString());
            pw.close();
            System.out.println("Your spreadsheet has been saved with the name " + fileName);

        } catch (Exception E) {
        }

    }

    //creating a new exception to handle when a cycle is found
    class CycleFoundException extends Exception {
        public CycleFoundException(String message) {
            super(message);
        }
    }

}
