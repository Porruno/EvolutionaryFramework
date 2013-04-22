package CSP.CSP;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements the Token class. A token is an ordered pair <TYPE, VALUE>
 * which represents a information about what is defined in the text.
 * ----------------------------------------------------------------------------
*/

public class Token {
    
    // For XML Parser
    public final static int TAG_START = 0;
    public final static int TAG_END = 1;
    public final static int PARAMETER = 2;
    public final static int VALUE = 3;
    public final static int TEXT = 4;
    
    // For FunctionParser
    public final static int FUNCTION = 5;
    public final static int VARIABLE = 6;
    
    private int type;
    private String value;
    
    /**
     * Creates a new instance of Token.
     * @param type An integer with the type of the token.
     * @param value The string value of the token.
    */
    public Token(int type, String value) {
        this.type = type;
        this.value = value.trim();
    }
    
    /**
     * This is a copy constructor. Creates a new instance of Token which has the
     * same information than the token given as parameter.
     * @param token A token instance to be copied into a new instance of Token.
    */
    public Token(Token token) {
        this.type = token.getType();
        this.value = token.getValue();
    }        
    
    /**
     * Returns the type of the token.
     * @return The type of the token.
    */
    public int getType() {
        return type;
    }
    
    /**
     * Returns the string value of the token.
     * @return The string value of the token.
    */
    public String getValue() {
        return value;
    }
            
    @Override
    /**
     * Returns the string representation of the token.
     * @return The string representation of the token.
    */
    public String toString() {
        return "<" + typeToString(type) + ", " + value + ">";
    }    
    
     /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */               

    
    /**
     * Returns the string representation of the type of the token.
     * @return The string representation of the type of the token.
    */
    private static String typeToString(int type) {
        switch (type) {
            case TAG_START:
                return "TAG_START";
            case TAG_END:
                return "TAG_END";
            case PARAMETER:
                return "PARAMETER";
            case VALUE:
                return "VALUE";
            case TEXT:
                return "TEXT";
            case FUNCTION:
                return "FUNCTION";
            case VARIABLE:
                return "VARIABLE";
        }
        return "NULL";
    }
    
     /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */               

}
