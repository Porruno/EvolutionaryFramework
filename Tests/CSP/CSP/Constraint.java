package CSP.CSP;

import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to create and handle constraints.
 * ----------------------------------------------------------------------------
*/

public class Constraint {

    public final static boolean SUPPORTS = true, CONFLICTS = false;
    public final static int IN_EXTENSION = 0, IN_INTENSION = 1, GLOBAL = 2;
    
    private int arity, type;
    private boolean supports, secure;
    private Variable scope[];    
    private ArrayList<int[]> tuples;
    private String constraintId, function;
    private Double pxi;
    
    /**
     * Creates a new instance of Constraint. This constructor creates global constraints and constraints defined in intension.
     * @param constraintId A text String with the unique identifier for the constraint.
     * @param scope An array of Variable that indicates the variables involved in the constraint.
     * @param function A text String with the global function used in the constraint. 
    */
    public Constraint(String constraintId, Variable scope[], String function) {
        int i;
        if (function.equalsIgnoreCase("global:allDifferent")) {
            type = GLOBAL;
        } else if (function.startsWith("global:")){
            System.out.println("The global function '" + function + "' is not defined in the system.");
            System.out.println("The constraint cannot be created.");
            System.exit(1);
        } else {
            type = IN_INTENSION;
        }
        this.constraintId = constraintId;
        this.scope = new Variable[scope.length];
        for (i = 0; i < scope.length; i ++) {
            this.scope[i] = scope[i];
        }
        this.arity = scope.length;
        this.function = function;
        tuples = new ArrayList<int[]>(0);
        setSecure(false);     
    }        
    
    /**
     * Creates a new instance of Constraint. This constructor creates constraints defined in extension.
     * @param constraintId A text String with the unique identifier for the constraint.
     * @param scope An array of Variable that indicates the variables involved in the constraint.
     * @param supports A boolean value that indicates if the tuples are supporting values or conflicts.
     * Use true when you want to indicate that the tuples contain supporting values, false otherwise.
    */    
    public Constraint(String constraintId, Variable scope[], boolean supports) {
        int i;
        function = "";
        type = IN_EXTENSION;
        this.constraintId = constraintId;
        this.scope = new Variable[scope.length];
        for (i = 0; i < scope.length; i ++) {
            this.scope[i] = scope[i];
        }
        arity = scope.length;
        this.supports = supports;
        tuples = new ArrayList<int[]>(10); 
        setSecure(false);
    }
    
    /**
     * Creates a new instance of Constraint. This constructor creates constraints defined in extension.
     * @param constraintId A text String with the unique identifier for the constraint.
     * @param scope An array of Variable that indicates the variables involved in the constraint.
     * @param tuples An instance of ArrayList that contains the tuples for the constraint.
     * @param supports A boolean value that indicates if the tuples are for supporting values or for conflicts.
     * Use true when you want to indicate that the tuples contain supporting values, false otherwise.
    */
    public Constraint(String constraintId, Variable scope[], ArrayList tuples, boolean supports) {
        int i;
        function = "";
        type = IN_EXTENSION;
        this.constraintId = constraintId;
        this.scope = new Variable[scope.length];
        for (i = 0; i < scope.length; i ++) {
            this.scope[i] = scope[i];
        }
        arity = scope.length;
        this.supports = supports;
        this.tuples = tuples;
        setSecure(false);
    }    
      
    /**
     * Adds a tuple to the constraint. This function has sense only if the constraint was defined as a constraint in extension.
     * @param tuple An array of integers that represents a tuple for the constraint. The arity of the tuple must be the same
     * than the number of variables in the scope, otherwise the tuple will not be added to the constraint.
    */
    public void addTuple(int tuple[]) {
        if (tuple.length != arity) {
            return;
        } else {
            tuples.add(tuple);
        }
    }
    
    /**
     * Set the tuples that restrict the constraint.
     * @param tuples The prohibit tuples within the constraint.
    */
    public void setTuples(ArrayList tuples) {
        this.tuples = tuples;
    }
    
    /**
     * Secures a constraint. When a constraint is secure, it is not checked again. This helps improve the speed to solve the CSP.
     * @param secure The boolean value to be used. Use true to say that the constraint is secure, false otherwise.
    */
    public final void setSecure(boolean secure) {
        this.secure = secure;
    }
    
    /**
     * Returns the unique identifier of the constraint.
     * @return A text String that contains the unique identifier of the constraint.
    */
    public String getId() {
        return constraintId;
    }
    
    public boolean supports(){
        return this.supports;
    }
    
    /**
     * Returns the function associated to the constraint. This function has no sense if the
     * constraint was defined in extension.
     * @return An instance of String that contains the function associated to the constraint.
    */
    public String getFunction() {
        return function;
    }
    
    /**
     * Returns the scope of the constraint.
     * @return An array of Variable that contains the variables involved in the constraint.
    */
    public Variable[] getScope() {
        return scope;
    }
    
    /**
     * Returns the type of the constraint.
     * @return An integer that represents the type of the constraint. 
    */
    public int getType() {
        return type;
    }
    
    /**
     * Returns the tuples contained in the CSP instance.
     * @return An instance of ArrayList with the tuples contained in the CSP instance.
    */
    public ArrayList<int[]> getTuples() {
        return tuples;
    }
    
    /**
     * Returns true when the tuples represent conflicts, and returns false when they represent allowed values.
     * @return true when the tuples represent conflicts, and returns false when they represent allowed values.
    */
    public boolean isConflict() {
        return !supports;
    }
    
    /**
     * Returns true when the constraint is secure, false otherwise.
     * @return true when the constraint is secure, false otherwise.
    */
    public boolean isSecure() {
        return secure;
    }
    
    public void setPxi(){
        int possibleAssignations = 1;
        for(Variable variable : this.getScope()){
            possibleAssignations *= variable.getDomain().length;
        }
        
        int allowedAssignations =   this.supports()?
                                    possibleAssignations - this.getTuples().size() :
                                    this.getTuples().size();
        this.pxi = (double) allowedAssignations / possibleAssignations;
    }
    
    public Double getPxi(){
        return this.pxi;
    }
    
    @Override
    /**
     * Returns the string representation of the constraint.
     * @return The string representation of the constraint.
    */
    public String toString() {
        int i, j, tuple[];
        String stringRepresentation = constraintId + " = [ ";
        stringRepresentation = constraintId + " [ ";
        for (i = 0; i < scope.length; i ++) {
            stringRepresentation = stringRepresentation + scope[i].getId() + " ";
        }
        stringRepresentation = stringRepresentation + "] ";
        if (this.type == Constraint.GLOBAL || this.type == Constraint.IN_INTENSION) {
            stringRepresentation = stringRepresentation + " [ " + function + " ]";
        } else {
            stringRepresentation = stringRepresentation + " [";
            for (i = 0; i < tuples.size(); i++) {
                tuple = tuples.get(i);
                stringRepresentation = stringRepresentation + " ( ";
                for (j = 0; j < arity; j++) {
                    stringRepresentation = stringRepresentation + tuple[j] + " ";
                }
                stringRepresentation = stringRepresentation + ") ";
            }
            if (supports) {
                stringRepresentation = stringRepresentation + " ( Supports ) ";
            } else {
                stringRepresentation = stringRepresentation + " ( Conflicts ) ";
            }
            
            stringRepresentation = stringRepresentation + " ]";
        }
        return stringRepresentation;
    }    
    
}
