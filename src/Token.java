public class Token {

    /**
     * Return a string associated with a token
     * @param expTreeToken  an ExpressionTreeToken
     * @return a String associated with expTreeToken
     */
    static String printExpressionTreeToken (Token expTreeToken) {
        String returnString = "";

        if (expTreeToken instanceof OperatorToken) {
            returnString = ((OperatorToken) expTreeToken).getOperatorToken() + " ";
        } else if (expTreeToken instanceof CellToken) {
            returnString = CellToken.printCellToken((CellToken) expTreeToken) + " ";
        } else if (expTreeToken instanceof LiteralToken) {
            returnString = LiteralToken.getValue((LiteralToken) expTreeToken) + " ";
        } else {
            // This case should NEVER happen
            System.out.println("Error in printExpressionTreeToken.");
            System.exit(0);
        }
        return returnString;
    }

    /**
     * getFormula
     *
     * Given a string that represents a formula that is an infix
     * expression, return a stack of Tokens so that the expression,
     * when read from the bottom of the stack to the top of the stack,
     * is a postfix expression.
     *
     * A formula is defined as a sequence of tokens that represents
     * a legal infix expression.
     *
     * A token can consist of a numeric literal, a cell reference, or an
     * operator (+, -, *, /).
     *
     * Multiplication (*) and division (/) have higher precedence than
     * addition (+) and subtraction (-).  Among operations within the same
     * level of precedence, grouping is from left to right.
     *
     * This algorithm follows the algorithm described in Weiss, pages 105-108.
     */
    public static Stack getFormula(String formula, Spreadsheet theSpreadsheet, Cell currentCell) {
        Stack returnStack = new Stack();  // stack of Tokens (representing a postfix expression)
        boolean error = false;
        char ch = ' ';

        int literalValue = 0;

        //CellToken cellToken;
        int column = 0;
        int row = 0;

        int index = 0;  // index into formula
        Stack operatorStack = new Stack();  // stack of operators

        while (index < formula.length() ) {
            // get rid of leading whitespace characters
            while (index < formula.length() ) {
                ch = formula.charAt(index);
                if (!Character.isWhitespace(ch)) {
                    break;
                }
                index++;
            }

            if (index == formula.length() ) {
                error = true;
                break;
            }

            // ASSERT: ch now contains the first character of the next token.
            if (OperatorToken.isOperator(ch)) {
                // We found an operator token
                switch (ch) {
                    case OperatorToken.Plus:
                    case OperatorToken.Minus:
                    case OperatorToken.Mult:
                    case OperatorToken.Div:
                    case OperatorToken.LeftParen:
                        // push operatorTokens onto the output stack until
                        // we reach an operator on the operator stack that has
                        // lower priority than the current one.
                        OperatorToken stackOperator;
                        while (!operatorStack.isEmpty()) {
                            stackOperator = (OperatorToken) operatorStack.top();
                            if ( (stackOperator.priority() >= OperatorToken.operatorPriority(ch)) &&
                                    (stackOperator.getOperatorToken() != OperatorToken.LeftParen) ) {

                                // output the operator to the return stack
                                operatorStack.pop();
                                returnStack.push(stackOperator);
                            } else {
                                break;
                            }
                        }
                        break;

                    default:
                        // This case should NEVER happen
                        System.out.println("Error in getFormula.");
                        System.exit(0);
                        break;
                }
                // push the operator on the operator stack
                operatorStack.push(new OperatorToken(ch));

                index++;

            } else if (ch == ')') {    // maybe define OperatorToken.RightParen ?
                OperatorToken stackOperator;
                stackOperator = (OperatorToken) operatorStack.topAndPop();
                // This code does not handle operatorStack underflow.
                while (stackOperator.getOperatorToken() != OperatorToken.LeftParen) {
                    // pop operators off the stack until a LeftParen appears and
                    // place the operators on the output stack
                    returnStack.push(stackOperator);
                    stackOperator = (OperatorToken) operatorStack.topAndPop();
                }

                index++;
            } else if (Character.isDigit(ch)) {
                // We found a literal token
                literalValue = ch - '0';
                index++;
                while (index < formula.length()) {
                    ch = formula.charAt(index);
                    if (Character.isDigit(ch)) {
                        literalValue = (literalValue * 10) + (ch - '0');
                        index++;
                    } else {
                        break;
                    }
                }
                // place the literal on the output stack
                returnStack.push(new LiteralToken(literalValue));

            } else if (Character.isUpperCase(ch)) {
                // We found a cell reference token
                CellToken cToken = new CellToken();
                index = CellToken.getCellToken(formula, index, cToken);

                if (cToken.getRow() == CellToken.BadCell) {
                    error = true;
                    break;
                } else {
                    Cell newCell = theSpreadsheet.getCellValue(cToken);     //getting the cell corresponding to the cellToken

                    if(currentCell == null) {
                        currentCell = new Cell("");     //creating an empty cell
                    }
                    if(newCell == null){
                        //newCell = new Cell("");
                        newCell = theSpreadsheet.insertItem(cToken.getRow(), cToken.getColumn(), "");
                    }/*else{
                        newCell = theSpreadsheet.insertItem(cToken.getRow(), cToken.getColumn(), newCell.getFormula());
                    }*/
                    newCell.addFeedInto(currentCell);       //adding to the different dependency graph
                    currentCell.addDependency(newCell);

                    /*Cell newCell = theSpreadsheet.getCellValue(cToken);     //getting the cell corresponding to the cellToken
                    Cell oldCell = theSpreadsheet.getCellValue(cellToken);
                    newCell.addFeedInto(oldCell);       //adding to the different dependency graph
                    if(oldCell == null){
                        oldCell = new Cell("");     //creating an empty cell
                    }
                    oldCell.addDependency(newCell);*/

                    // place the cell reference on the output stack
                    returnStack.push(cToken);
                }

            } else {
                error = true;
                break;
            }
        }

        // pop all remaining operators off the operator stack
        while (!operatorStack.isEmpty()) {
            returnStack.push(operatorStack.topAndPop());
        }

        if (error) {
            // a parse error; return the empty stack
            returnStack.makeEmpty();
        }

        return returnStack;
    }

}

