public class Spreadsheet {
    private Cell[][] spreadsheet; //the two-dimensional array to act as the spreadsheet and hold the cell values
    private static int row;       //number of rows
    private static int col;       //num of columns

    public Spreadsheet(int num){
        row = num;
        col = num;
        spreadsheet = new Cell[row][col];
    }

    public Spreadsheet(int row, int col){
        this.row = row;
        this.col = col;
        spreadsheet = new Cell[row][col];
    }

    /**
     * Inserts an item into a specified place in the spreadsheet
     * @param theRow
     * @param theColumn
     * @param theValue (String, The formula)
     */
    public void insertItem (int theRow, int theColumn, String theValue) {
        Cell myCell = new Cell(theValue);
        spreadsheet[theRow][theColumn] = myCell;
    }

    public int getNumRows() {
        return row;
    }

    public int getNumColumns() {
        return col;
    }
    
     /**
     * takes in an input String and returns the associated CellToken
     * @param inputCell
     * @param num
     * @param cellToken
     * @return
     */
    public CellToken getCellToken (String inputCell,int num,CellToken cellToken){
        return cellToken;
    }  //TODO

    /**
     * print out all the values in the spreadsheet
     */
    public void printValues(){
        for (Cell[] rows : spreadsheet) {
            for (Cell number: rows) {
                System.out.print(number + ", ");
            }
            System.out.println();

        }

    }

    public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack){  //TODO

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
        for (Cell[] rows : spreadsheet) {
            for (Cell number: rows) {
                System.out.print(number + ", ");
            }
            System.out.println();
        }
    }

    /**
     * sorts the cells in the spreadsheet in topological order and evaluates the cells values while the sorting is occurring
     * @throws CycleFoundException
     */
    public void topSort() throws CycleFoundException{       //TODO

    }

    //creating a new exception to handle when a cycle is found
    class CycleFoundException extends  Exception{       //TODO

    }

}
