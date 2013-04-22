package CSP.CAP;

import CSP.CSP.CSP;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions and fields to create and handle basic 
 * cripto-arithmetic problems. At this point, the class only supports the
 * addition opearation beetween the operands.
 * ----------------------------------------------------------------------------
*/

public class CAP {

    String operands[];    
    String result;
    
    /**
     * Creates a new instance of CAP.
     * @param text The text with the cripto arithmetic expression.
    */
    public CAP(String text) {
        int i = 0;
        StringTokenizer tokens = new StringTokenizer(text, "+=");
        if (!text.contains("=")) {
            System.out.println("The cripto-arithmetic expression must contain the \'=\' character.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        operands = new String[tokens.countTokens() - 1];        
        for (i = 0; i < operands.length; i++) {            
            operands[i++] = invert(tokens.nextToken().trim());
        }
        result = invert(tokens.nextToken().trim());
    }
    
    /**
     * Returns the instance coded as a CSP.
     * @return The instance coded as a CSP.
    */
    public CSP toCSP() {        
        int i, j;
        String x;
        CSP csp;
        ArrayList<String> variableIds = new ArrayList<String>(10);
        for (i = 0; i < operands.length; i++) {
            for (j = 0; j < operands[i].length(); j++) {
                x = operands[i].substring(j, j + 1);
                //System.out.println(x);
                if (!variableIds.contains(x)) {
                    variableIds.add(x);
                }
            }
        }
        return null;
    }
    
    @Override
    /**
     * Returns the string representation of the instance.
     * @return The string representation of the instance.
    */
    public String toString() {
        int i;
        String text = "";
        for (i = 0; i < operands.length; i++) {
            text += "+: " + operands[i] + "\r\n";
        }
        text += "=: " + result;
        return text;
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Returns an inverted version of the string.
     * @param text The text to invert.
     * @return An inverted version of the string.
    */
    private String invert(String text) {
        int i;
        String inverted = "";
        for (i = text.length(); i > 0; i--) {            
            inverted = inverted.concat(text.substring(i - 1, i));
        }
        return inverted;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
}
