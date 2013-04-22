package CSP.Framework;

import CSP.CSP.*;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements the core of the distinct frameworks within the system.
 * ----------------------------------------------------------------------------
*/

public abstract class Framework {
        
    protected static int heuristics[] = {
        codeHeuristicIndex(VariableOrderingHeuristics.RND, ValueOrderingHeuristics.RND, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.ENS, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.K, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.MXC, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.MFD, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.MBD, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.FBZ, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.BBZ, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND),
        codeHeuristicIndex(VariableOrderingHeuristics.GAP, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.RND)
    };        
    protected static int features[] = {CSP.CONSTRAINT_DENSITY, CSP.CONSTRAINT_TIGHTNESS};
    protected static CSPRepository repository;
    
    protected static boolean heuristicsSet = false;
    protected static boolean featuresSet = false;
    protected static boolean repositorySet = false;

    //private final static int VARIABLE_ORDERING_HEURISTICS_COUNT = 8;
    //private final static int VALUE_ORDERING_HEURISTICS_COUNT = 3;
    
    /**
     * Sets the features that will be used to represent the problem state.
     * @param features An array of integers with the features to be used.
     * @since HHFramework 7.0
    */
    public static void setFeatures(int features[]) {
        int i;
        featuresSet = true;
        EvolutionaryFramework.features = new int[features.length];
        for (i = 0; i < features.length; i++) {
            EvolutionaryFramework.features[i] = features[i];
        }
    }

    /**
     * Sets the heuristics that will be used by the framework.
     * @param features An array of integers with the indexes of the heuristics.
     * @since HHFramework 7.0
    */
    public static void setHeuristics(int heuristics[]) {
        int i;
        heuristicsSet = true;
        EvolutionaryFramework.heuristics = new int[heuristics.length];
        for (i = 0; i < heuristics.length; i++) {
            EvolutionaryFramework.heuristics[i] = heuristics[i];
        }
    }        
    
    public static int[] getHeuristics() {
        return heuristics;
    }
    
    public static void setRepository(String repositoryName) {
        repositorySet = true;
        Framework.repository = CSPRepository.loadFromFile(repositoryName);
    }
    
    public static CSPRepository getRespository() {
        return repository;
    }
    
    public static String getHeuristicsAsString() {
        int i, variableOrderingHeuristic, valueOrderingHeuristic;
        String result = "{ ";
        for (i = 0; i < heuristics.length; i++) {
            variableOrderingHeuristic = decodeHeuristicIndex(heuristics[i])[0];
            valueOrderingHeuristic = decodeHeuristicIndex(heuristics[i])[1];
            result = result + "<" + VariableOrderingHeuristics.heuristicToString(variableOrderingHeuristic) + ", " + ValueOrderingHeuristics.heuristicToString(valueOrderingHeuristic) + "> ";
        }
        result = result + "}";
        return result;
    }
    
    public static int[] getFeatures() {
        return features;
    }
    /**
     * WARNING: It does not work when no variable ordering heuristic is provided!
    */
    public static int codeHeuristicIndex(int variableOrderingHeuristic, int valueOrderingHeuristic, int constraintOrderingHeuristic) {
        if (variableOrderingHeuristic >= VariableOrderingHeuristics.SIZE || valueOrderingHeuristic >= ValueOrderingHeuristics.SIZE || constraintOrderingHeuristic >= ConstraintOrderingHeuristics.SIZE) {
            System.out.println("Heuristic index out of bounds. The system will halt.");
            System.exit(1);
        }
        return constraintOrderingHeuristic * (VariableOrderingHeuristics.SIZE * ValueOrderingHeuristics.SIZE) + (VariableOrderingHeuristics.SIZE * valueOrderingHeuristic) + variableOrderingHeuristic;
    }
    
    /** Decodes the given hyperheuristic index.
     * @param heuristicIndex The hyperheuristic index to be decoded.
     * @return An array of two integers. The first integer represents the variable
     * ordering heuristic index coded in the hyperheuristic. The second one 
     * represents the index of the value ordering heuristic.
    */
    public static int[] decodeHeuristicIndex(int heuristicIndex) {
        int variableOrderingHeuristic = VariableOrderingHeuristics.NONE;
        int valueOrderingHeuristic = ValueOrderingHeuristics.NONE;
        int constraintOrderingHeuristic = ConstraintOrderingHeuristics.NONE;
        int decodedHeuristics[];                  
        variableOrderingHeuristic = (heuristicIndex % VariableOrderingHeuristics.SIZE);        
        constraintOrderingHeuristic = (heuristicIndex / (VariableOrderingHeuristics.SIZE * ValueOrderingHeuristics.SIZE));
        if (constraintOrderingHeuristic > 0) {            
            valueOrderingHeuristic = (heuristicIndex - (constraintOrderingHeuristic * VariableOrderingHeuristics.SIZE * ValueOrderingHeuristics.SIZE) - variableOrderingHeuristic) / VariableOrderingHeuristics.SIZE;
        } else {
            valueOrderingHeuristic = (heuristicIndex / VariableOrderingHeuristics.SIZE);            
        }
        if (variableOrderingHeuristic >= VariableOrderingHeuristics.SIZE || valueOrderingHeuristic >= ValueOrderingHeuristics.SIZE || constraintOrderingHeuristic >= ConstraintOrderingHeuristics.SIZE) {
            System.out.println("The decoded heurist does not exist. The system will halt.");
            System.exit(1);
        }        
        decodedHeuristics = new int[3];
        decodedHeuristics[0] = variableOrderingHeuristic;
        decodedHeuristics[1] = valueOrderingHeuristic;
        decodedHeuristics[2] = constraintOrderingHeuristic;
        return decodedHeuristics;
    }        
    
}
