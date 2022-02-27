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

    public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack){

    }

    /**
     * prints out the associated formula to the cell token
     * @param cellToken
     */
    public void printCellFormula(CellToken cellToken){

    }

    /**
     * prints all the formulas for all the cells in the spreadsheet
     */
    public void printAllFormulas(){

    }

}
