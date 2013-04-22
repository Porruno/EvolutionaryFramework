package CSP.CSP;

import java.util.Random;
import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements the variable ordering heuristics used by the system.
 * ----------------------------------------------------------------------------
*/

public abstract class VariableOrderingHeuristics {
    
    public final static int NONE = 0, RND = 1, MRV = 2, RHO = 3, ENS = 4, K = 5, MXC = 6, MFD = 7, MBD = 8, FBZ = 9, BBZ = 10, _MRV = 11, _RHO = 12, _ENS = 13, _K = 14, _MXC = 15, _MFD = 16, _MBD = 17, _FBZ = 18, _BBZ = 19, GAP = 20;
    public final static int SIZE = 21;
    public static int SMALLEST = 0, LARGEST = 1;
    
    public static String noHeuristic(Variable variables[]) {
        if (variables.length > 0) {
            return variables[0].getId();
        } else {
            return null;
        }          
    }        
    
    /**
     * Returns the next variable to instantiate according to the heuristic MRV.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */    
    public static String MRV(Variable variables[]) {
        int i;                
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = variables[i].getDomain().length;            
        }        
        return selectNextVariable(variables, evaluations, SMALLEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the anti-heuristic MRV.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */    
    public static String _MRV(Variable variables[]) {
        int i;                
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = variables[i].getDomain().length;            
        }        
        return selectNextVariable(variables, evaluations, LARGEST);
    }
       
    /**
     * Returns the next variable to instantiate according to the heuristic MXC.
     * MXC selects the variable involved in the larger number of conflicts.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String MXC(Variable variables[]) {
        int i, j, conflicts;        
        double evaluations[] = new double[variables.length];        
        Constraint constraint;
        ArrayList constraints;
        for (i = 0; i < variables.length; i++) {
            conflicts = 0;
            constraints = variables[i].getConstraints();
            for (j = 0; j < constraints.size(); j++) {
                constraint = (Constraint) constraints.get(j);
                if (!constraint.isSecure() && constraint.getType() == Constraint.IN_EXTENSION && constraint.isConflict()) {
                    conflicts += constraint.getTuples().size();
                }
            }
            evaluations[i] = conflicts;            
        }
        return selectNextVariable(variables, evaluations, LARGEST);
    }

    /**
     * Returns the next variable to instantiate according to the anti-heuristic MXC.
     * MXC selects the variable involved in the larger number of conflicts.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String _MXC(Variable variables[]) {
        int i, j, conflicts;        
        double evaluations[] = new double[variables.length];        
        Constraint constraint;
        ArrayList constraints;
        for (i = 0; i < variables.length; i++) {
            conflicts = 0;
            constraints = variables[i].getConstraints();
            for (j = 0; j < constraints.size(); j++) {
                constraint = (Constraint) constraints.get(j);
                if (constraint.isSecure() && constraint.getType() == Constraint.IN_EXTENSION && constraint.isConflict()) {
                    conflicts += constraint.getTuples().size();
                }
            }
            evaluations[i] = conflicts;            
        }
        return selectNextVariable(variables, evaluations, SMALLEST);
    }

    
    /**
     * Returns the next variable to instantiate according to the heuristic RHO.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String RHO(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.solutionDensity(variables[i]);            
        }        
        return selectNextVariable(variables, evaluations, SMALLEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the anti-heuristic RHO.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String _RHO(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.solutionDensity(variables[i]);            
        }        
        return selectNextVariable(variables, evaluations, LARGEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the heuristic ENS.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String ENS(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = variables[i].getDomain().length * csp.solutionDensity(variables[i]);            
        }        
        return selectNextVariable(variables, evaluations, SMALLEST);
    }    
    
    /**
     * Returns the next variable to instantiate according to the anti-heuristic ENS.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String _ENS(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = variables[i].getDomain().length * csp.solutionDensity(variables[i]);            
        }        
        return selectNextVariable(variables, evaluations, LARGEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the heuristic K.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String K(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.kappa(variables[i]);                        
        }        
        return selectNextVariable(variables, evaluations, LARGEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the anti-heuristic K.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String _K(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.kappa(variables[i]);                        
        }
        return selectNextVariable(variables, evaluations, SMALLEST);
    }

    /**
     * Returns the next variable to instantiate according to the heuristic MFD.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String MFD(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.degree(variables[i], true);
        }
        return selectNextVariable(variables, evaluations, LARGEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the anti-heuristic MFD.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String _MFD(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.degree(variables[i], true);
        }
        return selectNextVariable(variables, evaluations, SMALLEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the heuristic MBD.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String MBD(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.degree(variables[i], false);
        }
        return selectNextVariable(variables, evaluations, LARGEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the anti-heuristic MBD.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String _MBD(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.degree(variables[i], false);
        }
        return selectNextVariable(variables, evaluations, SMALLEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the heuristic FBZ.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String FBZ(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.degree(variables[i], true) * variables[i].getDomain().length;
        }
        return selectNextVariable(variables, evaluations, LARGEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the anti-heuristic FBZ.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String _FBZ(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.degree(variables[i], true) * variables[i].getDomain().length;
        }
        return selectNextVariable(variables, evaluations, SMALLEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the heuristic FBZ.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String BBZ(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.degree(variables[i], false) * variables[i].getDomain().length;
        }
        return selectNextVariable(variables, evaluations, LARGEST);
    }
    
    /**
     * Returns the next variable to instantiate according to the anti-heuristic FBZ.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String _BBZ(CSP csp) {
        int i;        
        Variable variables[] = csp.getUninstantiatedVariables();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = csp.degree(variables[i], false) * variables[i].getDomain().length;
        }
        return selectNextVariable(variables, evaluations, SMALLEST);
    }
    
    /**
     * Returns the next variable to instantiate according to a random heuristic.
     * @param variables An array of Variable that will be ordered.
     * @return An array of Strings with the ids of the ordered variables.
    */
    public static String RND(Variable variables[]) {
        int i;
        Random generator = new Random();
        double evaluations[] = new double[variables.length];        
        for (i = 0; i < variables.length; i++) {
            evaluations[i] = generator.nextDouble();            
        }        
        return selectNextVariable(variables, evaluations, SMALLEST);
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
            case MRV:
                return "MRV";
            case RHO:
                return "RHO";
            case ENS:
                return "EN";
            case K:
                return "K";
            case MXC:
                return "MXC";            
            case MFD:
                return "MFD";
            case MBD:
                return "MBD";
            case FBZ:
                return "FBZ"; 
            case BBZ:
                return "BBZ";
            case _MRV:
                return "_MRV";                
            case _RHO:
                return "_RHO";
            case _ENS:
                return "_EN";
            case _K:
                return "_K";
            case _MXC:
                return "_MXC";
            case _MFD:
                return "_MFD";
            case _MBD:
                return "_MBD";
            case _FBZ:
                return "_FBZ"; 
            case _BBZ:
                return "_BBZ";
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
        } else if (heuristic.equalsIgnoreCase("MRV")) {
            return MRV;
        } else if (heuristic.equalsIgnoreCase("RHO")) {
            return RHO;
        }  else if (heuristic.equalsIgnoreCase("EN")) {
            return ENS;
        } else if (heuristic.equalsIgnoreCase("MXC")) {
            return MXC;
        } else if (heuristic.equalsIgnoreCase("MFD")) {
            return MFD;
        } else if (heuristic.equalsIgnoreCase("MBD")) {
            return MBD;
        } else if (heuristic.equalsIgnoreCase("FBZ")) {
            return FBZ;
        } else if (heuristic.equalsIgnoreCase("BBZ")) {
            return BBZ;
        } else if (heuristic.equalsIgnoreCase("_MRV")) {
            return _MRV;        
        } else if (heuristic.equalsIgnoreCase("_RHO")) {
            return _RHO;
        }  else if (heuristic.equalsIgnoreCase("_EN")) {
            return _ENS;
        } else if (heuristic.equalsIgnoreCase("_MXC")) {
            return _MXC;
        } else if (heuristic.equalsIgnoreCase("_MFD")) {
            return _MFD;
        } else if (heuristic.equalsIgnoreCase("_MBD")) {
            return _MBD;
        } else if (heuristic.equalsIgnoreCase("_FBZ")) {
            return _FBZ;
        } else if (heuristic.equalsIgnoreCase("_BBZ")) {
            return _BBZ;
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
     * Selects the next variable to instantiate.
     * @param variables An array of Variables to order.
     * @param evaluations An array of doubles with the evaluation of each variable.
     * @param size Determines if the function will return the variable with the 
     * larger evaluation or the one with the smaller evaluation.
     * @return The variable ID of the next variable to instantiate.
    */
    public static String selectNextVariable(Variable variables[], double evaluations[], int size) {
        int i;
        double eval;
        Variable selectedVariable = null;
        if (variables.length > 0) {
            eval = evaluations[0];
            selectedVariable = variables[0];
        } else {
            return null;
        }                
        for (i = 0; i < variables.length; i++) {
            if (size == LARGEST) {
                if (evaluations[i] < eval) {
                    eval = evaluations[i];
                    selectedVariable = variables[i];
                }
            } else {
                if (evaluations[i] > eval) {
                    eval = evaluations[i];
                    selectedVariable = variables[i];
                }
            }
        }
        return selectedVariable.getId();
    }
            
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
}
