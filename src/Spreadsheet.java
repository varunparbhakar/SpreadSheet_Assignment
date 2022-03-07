import java.io.BufferedWriter;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Spreadsheet {
    private final Cell[][] spreadsheet; //the two-dimensional array to act as the spreadsheet and hold the cell values
    private static int row;       //number of rows
    private static int col;       //num of columns
    private String fileName = "Saved_Spreadsheet.csv";

    public Spreadsheet(int num){
        row = num;
        col = num;
        spreadsheet = new Cell[row][col];
    }

    public Spreadsheet(int row, int col){
        Spreadsheet.row = row;
        Spreadsheet.col = col;
        spreadsheet = new Cell[row][col];
    }

    /**
     * get the cell corresponding to the cell token
     * @param cToken
     * @return
     */
    public Cell getCellValue(CellToken cToken){
        return spreadsheet[cToken.getRow()][cToken.getColumn()];
    }

    public Cell insertItem (int theRow, int theColumn, String theValue) {

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

    public Cell[][] getSpreadsheet(){return spreadsheet;}




    /**
     * Create a new cell, add formula, add dependencies
     * @param cellToken cell token of the new cell
     * @param formula formula input from the new cell
     //* @param expTreeTokenStack stack of expression from the formula
     */
    public void creatCell(CellToken cellToken, String formula){
        int rowNumber = cellToken.getRow();
        int colNumber = cellToken.getColumn();
        //if this is not null, then we know it was already created before because another cell depends on it
        if(spreadsheet[rowNumber][colNumber] != null){
            LinkedList<Cell> feedsList = spreadsheet[rowNumber][colNumber].getFeedsInto();
            for(Cell cell: feedsList){
                cell.clearDependencies();
            }


            Cell currentCell = new Cell(formula);

            spreadsheet[rowNumber][colNumber] = currentCell;

            //adding new dependencies
            for(Cell cell: feedsList){
                spreadsheet[rowNumber][colNumber].addFeedInto(cell);
                cell.addDependency(currentCell);
            }



        }else{
            Cell currentCell = new Cell(formula);
            spreadsheet[rowNumber][colNumber] = currentCell;
        }



//        //Add dependencies
//        Cell newCell = new Cell(formula);
//        while (!expTreeTokenStack.isEmpty()){
//            //pop and get the Cell Token from the stack of expression
//            CellToken newCellToken = new CellToken();
//            Object newObject = expTreeTokenStack.topAndPop();
//
//            //For the cell token, create a new cell, then add dependencies to current cell and new cell
//            if(newObject instanceof CellToken){
//                newCellToken = (CellToken) newObject;
//                int newRowNumber = newCellToken.getRow();
//                int newColNumber = newCellToken.getColumn();
//                spreadsheet[newRowNumber][newColNumber] = newCell;
//
//                System.out.println("Current cell " + CellToken.printCellToken(cellToken));
//                System.out.println("New cell " + CellToken.printCellToken(newCellToken));
//                addDependencies(currentCell, newCell);
//            }
//            System.out.println();
//        }
    }

    /**
     * Add dependency and Feed Into to cells
     * @param currentCell
     * @param newCell
     */
    public void addDependencies(Cell currentCell, Cell newCell){
        if(currentCell == null) {
            currentCell = new Cell("");     //creating an empty cell
        }
        if(newCell == null){
            newCell = new Cell("");
        }
        newCell.addFeedInto(currentCell);       //adding to the different dependency graph
        currentCell.addDependency(newCell);

//        System.out.println("Current cell depends on " + currentCell.getDependsOn().toString());
//        System.out.println("Current cell Feed Into " + currentCell.getFeedsInto().toString());
//        System.out.println("New cell depends on " + newCell.getDependsOn().toString());
//        System.out.println("New cell Feed Into " + newCell.getFeedsInto().toString());
    }

    /**
     * Update the formula and calculate the new value of the cell
     * @param cellToken cell address that user want to change update new formula, ie. cell A1
     * @param expTreeTokenStack stack of token from expression of new formula
     * @param formula new formula
     * @param theSpreadsheet current Spreadsheet
     */
    public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack, String formula,
                                                Spreadsheet theSpreadsheet){
        //Create a new cell with formula string from user input
        //assign new cell to the cell token address
        //the current cell will be updated with new formula string
        Cell myCell = new Cell(formula);

        int rowNumber = cellToken.getRow();
        int colNumber = cellToken.getColumn();
        spreadsheet[rowNumber][colNumber] = myCell;

        //build expression tree from the stack
        ExpressionTree expressionTree = new ExpressionTree(null);
        expressionTree.BuildExpressionTree(expTreeTokenStack);
        myCell.setExpressionTree(expressionTree);

        //Evaluate the expression tree
        //then Update value to the current cell
//        int calculationResult = expressionTree.Evaluate(theSpreadsheet);
//        myCell.setValue(calculationResult);
    }

    /**
     * print out all the values in the spreadsheet
     */
    public void printValues(){
        String str;
        printHeader();
        int rowNumber = 0;
        String rowString;

        for (Cell[] rows : spreadsheet) {
            rowString = String.format("%-6s|", "Row " + rowNumber);
            System.out.format(rowString);

            for (Cell number: rows) {
                if(number != null) {
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
     * @param cellToken
     */
    public void printCellFormula(CellToken cellToken){
        int row = cellToken.getRow();
        int column = cellToken.getColumn();

        Cell cell = spreadsheet[row][column];
        System.out.println(cell);

    }

    /**
     * prints all the formulas for all the cells in the spreadsheet
     */
    public void printAllFormulas(){
        String str;
        printHeader();
        int rowNumber = 0;
        String rowString;

        for (Cell[] rows : spreadsheet) {
            rowString = String.format("%-6s|", "Row " + rowNumber);
            System.out.format(rowString);
            for (Cell number: rows) {
                if(number != null) {
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
     * @throws CycleFoundException
     */
    public void topSort() throws CycleFoundException{

        Queue q = new Queue();
        int counter = 0;
        Cell c;

        for(Cell[] cellRow: spreadsheet){       //accessing every Cell
            for(Cell cell: cellRow){
                cell.setIndegree(cell.getNumDependencies());        //setting the indegrees
                if(cell != null){
                    if(cell.getIndegree() == 0){     //if there is no dependence add it to the queue
                        q.enqueue(cell);
                    } }
            }
        }

        while(!q.isEmpty()){            //while there are still more cells
            c = q.dequeue();
            c.setTopNum(++counter);     //setting the order, 1 is smallest topNum

            for(Cell cell: c.getFeedsInto()){
                cell.setIndegree(cell.getIndegree()-1);  //subtracting 1 from the indegree
                if(cell.getIndegree() == 0){               //if no more dependencies add to queue
                    q.enqueue(cell);
                }
            }
        }

        if (counter != (getNumColumns() * getNumRows())){
            throw new CycleFoundException("Cycle found");
        }

    }

    //creating a new exception to handle when a cycle is found
    class CycleFoundException extends  Exception{
        public CycleFoundException(String message) {
            super(message);
        }
    }

    public void printHeader(){
        char[] alphabet = " ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for(int i = 0; i <= spreadsheet.length; i++){
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
            //PrintWriter pw = new PrintWriter(new File(fileName));
            PrintWriter pw = new PrintWriter(new File("test.txt")); // Change it back to .CSV file name
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

        } catch (Exception E){
        }

    }

}
