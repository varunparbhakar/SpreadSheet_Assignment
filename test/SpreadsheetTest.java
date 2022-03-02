import org.junit.jupiter.api.Test;

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
    void changeCellFormulaAndRecalculate() {
        Spreadsheet theSpreadsheet = new Spreadsheet(4);
        CellToken cellToken = new CellToken();
        Stack expTreeTokenStack;
        CellToken.getCellToken ("A1", 0, cellToken);

        String inputFormula = "12/3+3";
        expTreeTokenStack = Token.getFormula(inputFormula, theSpreadsheet, cellToken);
        theSpreadsheet.changeCellFormulaAndRecalculate(cellToken, expTreeTokenStack, inputFormula);

        Cell myCell = new Cell(inputFormula);
        myCell = theSpreadsheet.getCell(cellToken);

        String cellFormula = myCell.getFormula();
        int cellValue = myCell.getValue();

        //print
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