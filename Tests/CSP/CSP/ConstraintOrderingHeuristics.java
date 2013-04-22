package CSP.CSP;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements the constraint ordering heuristics used by the system.
 * ----------------------------------------------------------------------------
*/

public class ConstraintOrderingHeuristics {

    public final static int NONE = 0, RND = 1, MNC = 2, MXC = 3;
    public final static int SIZE = 4;
    private static final int DESCENDANT = 0, ASCENDANT = 1;
    
    public static void noHeuristic(CSP csp) {
        return;
    }
    
    /**
     * Implements a random constraint ordering heuristic. The order of the 
     * constraints is randomly defined.
     * @param csp The CSP instance where the constraints are coded.
     * @param variable The variable which constraints are to be ordered.
    */
    public static void RND(CSP csp, Variable variable) {
        int i;        
        Random generator = new Random();
        ArrayList constraints = variable.getConstraints();
        double evaluations[] = new double[constraints.size()];
        for (i = 0; i < evaluations.length; i++) {         
            evaluations[i] = generator.nextInt();
        }
        bubbleSort(constraints, evaluations, ASCENDANT);
    }
    
    /**
     * Implements a MNC constraint ordering heuristic. The constraints are ordered
     * from the ones with fewer tuples to the ones with more tuples.
     * @param csp The CSP instance where the constraints are coded.
     * @param variable The variable which constraints are to be ordered.
    */
    public static void MNC(CSP csp, Variable variable) {
        int i;        
        Constraint constraint;        
        ArrayList constraints = variable.getConstraints();
        double evaluations[] = new double[constraints.size()];
        for (i = 0; i < evaluations.length; i++) {
            constraint =  ((Constraint) constraints.get(i));
            if (constraint.isConflict()) {
                evaluations[i] = constraint.getTuples().size();
            } else {
                // Check before deploying...
                evaluations[i] = constraint.getTuples().size();
            }
        }
        bubbleSort(constraints, evaluations, ASCENDANT);
    }
    
    /**
     * Implements a MXC constraint ordering heuristic. The constraints are ordered
     * from the ones with more tuples to the ones with fewer tuples.
     * @param csp The CSP instance where the constraints are coded.
     * @param variable The variable which constraints are to be ordered.
    */
    public static void MXC(CSP csp, Variable variable) {
        int i;        
        Constraint constraint;        
        ArrayList constraints = variable.getConstraints();
        double evaluations[] = new double[constraints.size()];
        for (i = 0; i < evaluations.length; i++) {
            constraint =  ((Constraint) constraints.get(i));
            if (constraint.isConflict()) {
                evaluations[i] = constraint.getTuples().size();
            } else {
                // Check before deploying...
                evaluations[i] = constraint.getTuples().size();
            }
        }
        bubbleSort(constraints, evaluations, DESCENDANT);
    }
    
    /**
     * Returns the String representation of a given heuristic index.
     * @heuristic The heuristic index that will be used for the conversion.
     * @return The String representation of the heuristic index.
    */
    public static String heuristicToString(int heuristic) {
        switch(heuristic) {
            case NONE:
                return "NILL";
            case RND:
                return "RND";
            case MXC:
                return "MXC";
            case MNC:
                return "MNC";
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
        } else if (heuristic.equalsIgnoreCase("RND")) {
            return RND;
        } else if (heuristic.equalsIgnoreCase("MXC")) {
            return MXC;
        } else if (heuristic.equalsIgnoreCase("MNC")) {
            return MNC;
        } else {
            return NONE;
        }
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    private static void bubbleSort(ArrayList elements, double evaluations[], int ascendingOrder) {
        int i, n;
        double tempEvaluation;                
        boolean swapped = false;
        Constraint tempConstraint;
        n = evaluations.length;                
        do {
            swapped = false;
            for (i = 0; i < n - 1; i++) {
                if (ascendingOrder == 1) {
                    if (evaluations[i] > evaluations[i + 1]) {
                        tempConstraint = (Constraint) elements.get(i + 1);
                        tempEvaluation = evaluations[i + 1];
                        elements.set(i + 1, (Constraint) elements.get(i));
                        evaluations[i + 1] = evaluations[i];
                        elements.set(i, tempConstraint);                        
                        evaluations[i] = tempEvaluation;
                        swapped = true;
                    }
                } else {
                    if (evaluations[i] < evaluations[i + 1]) {
                        tempConstraint = (Constraint) elements.get(i + 1);
                        tempEvaluation = evaluations[i + 1];
                        elements.set(i + 1, (Constraint) elements.get(i));
                        evaluations[i + 1] = evaluations[i];
                        elements.set(i, tempConstraint);                        
                        evaluations[i] = tempEvaluation;
                        swapped = true;
                    }
                }
            }
            n = n - 1;
        } while (swapped);        
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
}
