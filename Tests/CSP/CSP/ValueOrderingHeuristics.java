package CSP.CSP;

import java.util.Random;
import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements the value ordering heuristics used by the system. 
 * ----------------------------------------------------------------------------
*/

public abstract class ValueOrderingHeuristics {

    public final static int NONE = 0, RND = 1, MNC = 2, FREQ = 3, MPC = 4, MXC = 5;
    public final static int SIZE = 6;
    
    public static int[] noHeuristic(int values[]) {        
        return values;
    }
    
    /**
     * Orders the values in the domain of a given variable according to a random
     * ordering heuristic.
     * @param variable The variable which domain will be ordered.
     * @return The ordered domain of the variable.
    */
    public static int[] RND(Variable variable) {
        int i;
        Random generator = new Random();
        int values[] = variable.getDomain();        
        double evaluations[] = new double[values.length];
        for (i = 0; i < values.length; i++) {            
            evaluations[i] = generator.nextDouble();
        }
        return bubbleSort(values, evaluations, false);
    }
    
    /**
     * Orders the values in the domain of a given variable according to the MNC
     * ordering heuristic.
     * @param variable The variable which domain will be ordered.
     * @return The ordered domain of the variable.
    */
    public static int[] MNC(Variable variable) {
        int i, j, k, eval, variableIndex = -1;
        int values[] = variable.getDomain();
        int tuple[];
        double evaluations[] = new double[values.length];
        Constraint constraint;        
        ArrayList constraints = variable.getConstraints();
        for (i = 0; i < values.length; i++) {
            eval = 0;
            for (j = 0; j< constraints.size(); j++) {
                constraint = (Constraint) constraints.get(j);
                if (constraint.getType() == Constraint.IN_EXTENSION && constraint.isConflict() && !constraint.isSecure()) {                    
                    for (k = 0; k < constraint.getScope().length; k++) {
                        if (constraint.getScope()[k] == variable) {
                            variableIndex = k;
                        }
                    }                                     
                    for (k = 0; k < constraint.getTuples().size(); k++) {
                        tuple = (int []) (constraint.getTuples().get(k));
                        if (tuple[variableIndex] == values[i]) {
                            eval++;
                        }
                    }
                }
            }
            evaluations[i] = eval;
        }
        return bubbleSort(values, evaluations, true);                     
    }
    
    /**
     * Orders the values in the domain of a given variable according to the FREQ
     * heuristic.
     * @param variable The variable which domain will be ordered.
     * @param solver The instance of CSPSolver currently solving the instance.
     * @return The ordered domain of the variable.
    */
    public static int[] FREQ(Variable variable, CSPSolver solver) {
        int i;
        int values[] = new int[variable.getDomain().length];
        double evaluations[] = new double[values.length];
        for (i = 0; i < values.length; i++) {
            values[i] = variable.getDomain()[i];            
            evaluations[i] = solver.findR(variable.getId(), variable.getDomain()[i]);            
        }
        return bubbleSort(values, evaluations, true);
    } 
    
    /**
     * Orders the values in the domain of a given variable according to the MPC
     * heuristic.
     * @param variable The variable which domain will be ordered.
     * @return The ordered domain of the variable.
    */
    public static int[] MPC(Variable variable) {        
        int i, j, k, eval, variableIndex = -1;
        int values[] = variable.getDomain();
        int tuple[];
        double conflicts;
        double evaluations[] = new double[values.length];
        Constraint constraint;
        ArrayList constraints = variable.getConstraints();
        for (i = 0; i < values.length; i++) {
            eval = 1;
            for (j = 0; j< constraints.size(); j++) {
                constraint = (Constraint) constraints.get(j);
                if (constraint.getType() == Constraint.IN_EXTENSION && constraint.isConflict() && !constraint.isSecure()) {
                    for (k = 0; k < constraint.getScope().length; k++) {
                        if (constraint.getScope()[k] == variable) {
                            variableIndex = k;
                        }
                    }
                    conflicts = 0;
                    for (k = 0; k < constraint.getTuples().size(); k++) {
                        tuple = (int []) (constraint.getTuples().get(k));
                        if (tuple[variableIndex] == values[i]) {
                            conflicts++;
                        }
                    }
                    eval *= conflicts;
                }
            }
            evaluations[i] = eval;
        }
        return bubbleSort(values, evaluations, true);
    }
    
    /**
     * Orders the values in the domain of a given variable according to the MXC
     * ordering heuristic.
     * @param variable The variable which domain will be ordered.
     * @return The ordered domain of the variable.
    */
    public static int[] MXC(Variable variable) {
        int i, j, k, eval, variableIndex = -1;
        int values[] = variable.getDomain();
        int tuple[];
        double evaluations[] = new double[values.length];
        Constraint constraint;        
        ArrayList constraints = variable.getConstraints();
        for (i = 0; i < values.length; i++) {
            eval = 0;
            for (j = 0; j< constraints.size(); j++) {
                constraint = (Constraint) constraints.get(j);
                if (constraint.getType() == Constraint.IN_EXTENSION && constraint.isConflict() && !constraint.isSecure()) {                    
                    for (k = 0; k < constraint.getScope().length; k++) {
                        if (constraint.getScope()[k] == variable) {
                            variableIndex = k;
                        }
                    }                                     
                    for (k = 0; k < constraint.getTuples().size(); k++) {
                        tuple = (int []) (constraint.getTuples().get(k));
                        if (tuple[variableIndex] == values[i]) {
                            eval++;
                        }
                    }
                }
            }
            evaluations[i] = eval;
        }
        return bubbleSort(values, evaluations, false); 
    }
    
    /**
     * Returns the String representation of a given heuristic index.
     * @heuristic The heuristic index that will be used for the conversion.
     * @return The String representation of the heuristic index.
    */
    public static String heuristicToString(int heuristic) {
        switch (heuristic) {
            case NONE:
                return "NILL";
            case MNC:
                return "MNC";
            case FREQ:
                return "FREQ";
            case MPC:
                return "MPC";
            case MXC:
                return "MXC";
            case RND:
                return "RND";
            default:
                return "?";
        }
    }
    
    /**
     * Converts a text string to an integer.
     * @param heuristic The text string with the name of the heuristic.
     * @return An integer with the index of the variable ordering heuristic.
    */
    public static int stringToHeuristic(String heuristic) {
        if (heuristic.equalsIgnoreCase("NILL")) {
            return NONE;
        } else if (heuristic.equalsIgnoreCase("MINC")) {
            return MNC;
        } else if (heuristic.equalsIgnoreCase("FREQ")) {
            return FREQ;
        } else if (heuristic.equalsIgnoreCase("MPC")) {
            return MPC;
        } else if (heuristic.equalsIgnoreCase("MXC")) {
            return MXC;
        } else if (heuristic.equalsIgnoreCase("RND")) {
            return RND;
        } else {
            return NONE;
        }
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Orders the values in an ascendant ot descendant way.
     * @param variables An array of integers to order.
     * @param evaluations An array of doubles with the evaluation of each value.
     * @param ascendingOrder A boolean value that determines if the function will
     * order the values in an ascendant way.
     * @return The ordered array of values.
    */
    private static int[] bubbleSort(int values[], double evaluations[], boolean ascendingOrder) {
        int i, n;
        double tempEvaluation;                
        boolean swapped = false;
        int tempValue, orderedValues[];
        n = evaluations.length;
        orderedValues = new int[values.length];
        for (i = 0; i < values.length; i++) {
            orderedValues[i] = values[i];
        }        
        do {
            swapped = false;
            for (i = 0; i < n - 1; i++) {
                if (ascendingOrder) {
                    if (evaluations[i] > evaluations[i + 1]) {
                        tempValue = orderedValues[i + 1];
                        tempEvaluation = evaluations[i + 1];
                        orderedValues[i + 1] = orderedValues[i];
                        evaluations[i + 1] = evaluations[i];
                        orderedValues[i] = tempValue;
                        evaluations[i] = tempEvaluation;
                        swapped = true;
                    }
                } else {
                    if (evaluations[i] < evaluations[i + 1]) {
                        tempValue = orderedValues[i + 1];
                        tempEvaluation = evaluations[i + 1];
                        orderedValues[i + 1] = orderedValues[i];
                        evaluations[i + 1] = evaluations[i];
                        orderedValues[i] = tempValue;
                        evaluations[i] = tempEvaluation;
                        swapped = true;
                    }
                }
            }
            n = n - 1;
        } while (swapped);  
        return orderedValues;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
}
