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
     * get the cell corresponding to the cell token
     * @param cToken
     * @return
     */
    public Cell getCell(CellToken cToken){
        return spreadsheet[cToken.getRow()][cToken.getColumn()];
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
        String str;
        printHeader();

        for (Cell[] rows : spreadsheet) {
            for (Cell number: rows) {
                if(number != null) {
                    str = String.format("%-6d|", number.getValue());
                } else {
                    str = String.format("%-6d|", 0);
                }
                System.out.format(str);
            }
            System.out.println();
        }
    }

    /**
     * Update the formula and calculate the new value of the cell
     * @param cellToken cell address that user want to change update new formula, ie. cell A1
     * @param expTreeTokenStack stack of token from expression of new formula
     */
    public void changeCellFormulaAndRecalculate(CellToken cellToken, Stack expTreeTokenStack, String formula,
                                                Spreadsheet theSpreadsheet){  //TODO
        //Create a new cell with formula string from user input
        //assign new cell to the cell token address
        //the current cell will be updated with new formula string
        Cell myCell = new Cell(formula);
        row = cellToken.getRow();
        col = cellToken.getColumn();
//        System.out.println(CellToken.printCellToken(cellToken));;
//        System.out.println(row + " " + col);
        spreadsheet[row][col] = myCell;

        //build expression tree from the stack
        ExpressionTree expressionTree = new ExpressionTree(null);
        expressionTree.BuildExpressionTree(expTreeTokenStack);

        //Evaluate the expression tree
        //then Update value to the current cell
        int calculationResult = expressionTree.Evaluate(theSpreadsheet, cellToken);
        myCell.setValue(calculationResult);
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
        for (Cell[] rows : spreadsheet) {
            for (Cell number: rows) {
                if(number != null) {
                    str = String.format("%-6s|", number.getFormula());
                } else {
                    str = String.format("%-6s|", null);
                }
                System.out.format(str);
            }
            System.out.println();
        }
    }

    /**
     * sorts the cells in the spreadsheet in topological order and evaluates the cells values while the sorting is occurring
     * @throws CycleFoundException
     */
    public void topSort() throws CycleFoundException{       //TODO
        Queue q = new Queue();
        int counter = 0;
        Cell c;

        for(Cell[] cellRow: spreadsheet){       //accessing every Cell
            for(Cell cell: cellRow){
                if(cell.getNumDependencies() == 0){     //if there is no dependence add it to the queue
                    q.enqueue(cell);
                }
            }
        }

        while(!q.isEmpty()){            //while there are still more cells
            c = q.dequeue();
            c.setTopNum(++counter);     //setting the order

            for(Cell cell: c.getFeedsInto()){
                cell.setIndegree(cell.getIndegree()-1);
                if(cell.getIndegree() == 0){            //subtracting 1 from the indegree
                    q.enqueue(cell);
                }
            }
        }

        if (counter != spreadsheet.length){
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
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        for(int i = 0; i < spreadsheet.length; i++){
            String str = String.format("%-8c", alphabet[i]);
            System.out.format(str);
        }

        System.out.println();
    }

}
