package CSP.CSP;

import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to create and handle in intension constraints.
 * ----------------------------------------------------------------------------
*/

public class InIntensionConstraintChecker {
    
    private final int DEFAULT_SIZE = 100;
    
    private String text;
    private ArrayList tokens;
    private Constraint constraint;
    
    private Variable variables[];    
    
    /**
     * Creates a new instance of InIntensionConstraintChecker.
    */
    public InIntensionConstraintChecker(CSP csp, Constraint constraint) {
        tokens = new ArrayList(DEFAULT_SIZE);
        this.text = constraint.getFunction();
        this.constraint = constraint;
        variables = csp.getVariables();
        tokens = parse();        
    }         
    
    /**
     * Analyses the text and evaluates the function.
    */
    public int check() {
        boolean substitution = true;
        int i;
        while (substitution) {
            for (i = tokens.size() - 1; i >= 0; i--) {
                substitution = false;
                if (((Token) tokens.get(i)).getType() == Token.FUNCTION) {
                    substitution = true;
                    if (evaluate(((Token) tokens.get(i)).getValue(), i)) {
                        return 1;
                    }
                }
            }
        }
        if (Integer.parseInt(((Token) tokens.get(0)).getValue()) == 0) {
            return 0;
        } else {
            return 2;
        }
    }          
        
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Parses the text to obtain the tokens list.
    */
    private ArrayList parse() {
        int i;
        String currentText = "";
        for (i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '(') {
                tokens.add(new Token(Token.FUNCTION, currentText.toUpperCase()));
                currentText = "";
            } else if (text.charAt(i) == ' ') {                
            // We keep reading every space                        
            } else if (text.charAt(i) == ')' || text.charAt(i) == ',') {
                if (!currentText.equalsIgnoreCase("")) {
                    if (Character.isDigit(currentText.charAt(0))) {
                        tokens.add(new Token(Token.VALUE, currentText.toUpperCase()));
                    } else {
                        tokens.add(new Token(Token.VARIABLE, currentText.toUpperCase()));      
                    }
                }
                currentText = "";
            } else {
                currentText = currentText + text.charAt(i);

            }
        }
        return tokens;
    }
        
    /**
     * Evaluates the current operation and updates the operations array.
     * @param function The string command of the operation to be done.
     * @param index The position of the operation in the array.
    */
    private boolean evaluate(String function, int index) {        
        if (function.equalsIgnoreCase("ADD")) {
            return functionADD(index);
        } else if (function.equalsIgnoreCase("SUB")) {
            return functionSUB(index);
        } else if (function.equalsIgnoreCase("MUL")) {
            return functionMUL(index);
        } else if (function.equalsIgnoreCase("DIV")) {
            return functionDIV(index);
        } else if (function.equalsIgnoreCase("MOD")) {
            return functionMOD(index);
        } else if (function.equalsIgnoreCase("GT")) {
            return functionGT(index);
        } else if (function.equalsIgnoreCase("GE")) {
            return functionGE(index);
        } else if (function.equalsIgnoreCase("LT")) {
            return functionLT(index);
        } else if (function.equalsIgnoreCase("LE")) {
            return functionLE(index);
        } else if (function.equalsIgnoreCase("EQ")) {
            return functionEQ(index);
        } else if (function.equalsIgnoreCase("NE")) {
            return functionNE(index);
        } else if (function.equalsIgnoreCase("OR")) {
            return functionOR(index);
        } else if (function.equalsIgnoreCase("AND")) {
            return functionAND(index);
        } else if (function.equalsIgnoreCase("ABS")) {
            return functionABS(index);
        } else if (function.equalsIgnoreCase("NOT")) {
            return functionNOT(index);
        }
        System.out.println("The function '" + function + "' is not defined in the system.");
        System.out.println("The system will halt.");
        System.exit(1);
        return false;
    }
    
    /**
     * Substitutes a variable by its current value.
     * @param variableName The name of the variable to be substituted.
     * @return The current value of the variable.
    */
    private Variable getVariable(String variableName) {
        int i;
        for (i = 0; i < variables.length; i++) {
            if (variables[i].getId().equalsIgnoreCase(variableName)) {
                return variables[i];
            }
        }
        System.out.println("The variable '" + variableName + "' does not exist in the context.");        
        System.exit(1);
        return null;
    }    
    
    private boolean functionADD(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }
        tokens.set(index, new Token(Token.VALUE, Integer.toString(op[0] + op[1])));        
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionSUB(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }
        tokens.set(index, new Token(Token.VALUE, Integer.toString(op[0] - op[1])));        
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionMUL(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }
        tokens.set(index, new Token(Token.VALUE, Integer.toString(op[0] * op[1])));        
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionDIV(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }
        tokens.set(index, new Token(Token.VALUE, Integer.toString(op[0] / op[1])));
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionMOD(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }
        tokens.set(index, new Token(Token.VALUE, Integer.toString(op[0] % op[1])));
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionEQ(int index) {        
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }
        if (op[0] == op[1]) {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(1)));
        } else {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(0)));
        }
        //System.out.println("\t" + op[0] + " == " + op[1] + " ? = " + (Token) tokens.get(index));
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionNE(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }        
        if (op[0] != op[1]) {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(1)));
        } else {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(0)));
        }
        //System.out.println("\t" + op[0] + " != " + op[1] + " ? = " + (Token) tokens.get(index));
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionGE(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }if (op[0] >= op[1]) {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(1)));
        } else {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(0)));
        }        
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionGT(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }
        if (op[0] > op[1]) {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(1)));
        } else {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(0)));
        }        
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionLE(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }   
        if (op[0] <= op[1]) {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(1)));
        } else {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(0)));
        }        
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionLT(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }        
        if (op[0] < op[1]) {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(1)));
        } else {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(0)));
        }        
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionOR(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }        
        if ((op[0] != 0) || (op[1] != 0)){
            tokens.set(index, new Token(Token.VALUE, Integer.toString(1)));
        } else {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(0)));
        }        
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionAND(int index) {
        int op[] = getOperands(index);        
        if (op[0] == Integer.MIN_VALUE || op[1] == Integer.MIN_VALUE) {
            return true;
        }
        if ((op[0] != 0) && (op[1] != 0)) {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(1)));
        } else {
            tokens.set(index, new Token(Token.VALUE, Integer.toString(0)));
        }   
        tokens.remove(index + 1);
        tokens.remove(index + 1);
        return false;
    }
    
    private boolean functionNOT(int index) {
        int op = getOperand(index);        
        if (op == 0) {
            op = 1;
        } else {
            op = 0;
        }
        tokens.set(index, new Token(Token.VALUE, Integer.toString(op)));
        tokens.remove(index + 1);  
        return false;
    }
    
    private boolean functionABS(int index) {
        int op = getOperand(index);                
        tokens.set(index, new Token(Token.VALUE, Integer.toString(Math.abs(op))));        
        tokens.remove(index + 1);  
        return false;
    }
    
    /**
     * Returns an operand.
     * @param index The index of the current token.
     * @returns An operand.
    */    
    private int getOperand(int index) {
        int op;
        String v1;
        Variable var1;
        // It takes one argument
        v1 = ((Token) tokens.get(index + 1)).getValue();
        // If the argument are variables, we substitute them by their values
        if (((Token) tokens.get(index + 1)).getType() == Token.VARIABLE) {
            var1 = getVariable(v1);
            if (!var1.isInstantiated()) {
                op = Integer.MIN_VALUE;
            } else {
                op = var1.getDomain()[0];
            }
        } else {
            op = Integer.parseInt(v1);
        }
        return op;
    }
    
    /**
     * Returns two operands.
     * @param index The index of the current token.
     * @returns Two operands.
    */
    private int[] getOperands(int index) {
        int op[] = new int[2];
        String v1,  v2;
        Variable var1, var2;
        // It takes two arguments
        v1 = ((Token) tokens.get(index + 1)).getValue();
        v2 = ((Token) tokens.get(index + 2)).getValue();
        // If the argument are variables, we substitute them by their values
        if (((Token) tokens.get(index + 1)).getType() == Token.VARIABLE) {
            var1 = getVariable(v1);
            if (!var1.isInstantiated()) {
                op[0] = Integer.MIN_VALUE;
            } else {
                op[0] = var1.getDomain()[0];
            }
        } else {
            op[0] = Integer.parseInt(v1);
        }
        if (((Token) tokens.get(index + 2)).getType() == Token.VARIABLE) {
            var2 = getVariable(v2);
            if (!var2.isInstantiated()) {
                op[1] = Integer.MIN_VALUE;
            } else {
                op[1] = var2.getDomain()[0];
            }
        } else {
            op[1] = Integer.parseInt(v2);
        }
        return op;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */               
    
}
