import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class SpreadsheetTest {

    @Test
    void getCell() {
    }

    @Test
    void insertItem() {
    }

    @Test
    void getNumRows() {
    }

    @Test
    void getNumColumns() {
    }

    @Test
    void printValues() {
    }

    @Test
    void changeCellFormulaAndRecalculateLiteral() {
        Spreadsheet theSpreadsheet = new Spreadsheet(4);
        CellToken cellToken = new CellToken();
        Stack expTreeTokenStack;
        CellToken.getCellToken ("A1", 0, cellToken);

        String inputFormula = "12/3+3";

        //Create cell from cell token
        Cell currentCell = theSpreadsheet.getCellValue(cellToken);

        expTreeTokenStack = Token.getFormula(inputFormula, theSpreadsheet, currentCell);
        theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack);

        Cell myCell = new Cell(inputFormula);
        myCell = theSpreadsheet.getCellValue(cellToken);

        String cellFormula = myCell.getFormula();
        int cellValue = myCell.getValue();
        LinkedList<Cell> dependCell = myCell.getDependsOn();
        LinkedList<Cell> feedIntoCell = myCell.getFeedsInto();

        //print
        System.out.println("This Cell is A1");
        System.out.println(myCell.getFormula() + " = " + myCell.getValue());

        assertEquals(7,cellValue);
        assertEquals(inputFormula,cellFormula);
    }

    @Test
    void printCellFormula() {
    }

    @Test
    void printAllFormulas() {
    }

    @Test
    void topSort() {
    }
}