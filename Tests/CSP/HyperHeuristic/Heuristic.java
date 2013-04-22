package CSP.HyperHeuristic;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class provides the basic funtionality to create and handle hyper-heuristics.
 * ----------------------------------------------------------------------------
*/

public class Heuristic extends HyperHeuristic {

    private int variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic;

    /**
     * Creates a new hyper-heuristic.
     * @param variableOrderingHeuristic The variable ordering heuristic that will
     * be used by the hyper-heuristic.
     * * @param valueOrderingHeuristic The value ordering heuristic that will
     * be used by the hyper-heuristic.
    */
    public Heuristic(int variableOrderingHeuristic, int valueOrderingHeuristic, int constraintOrderingHeuristic) {
        this.variableOrderingHeuristic = variableOrderingHeuristic;
        this.valueOrderingHeuristic = valueOrderingHeuristic;
        this.constraintOrderingHeuristic = constraintOrderingHeuristic;
        type = HyperHeuristic.LOWLEVEL;
    }

    /**
     * Returns the variable ordering heuristic coded within the hyper-heuristic.
     * @return The variable ordering heuristic coded within the hyper-heuristic.
    */
    public int getVariableOrderingHeuristic() {
        return variableOrderingHeuristic;
    }

    /**
     * Returns the value ordering heuristic coded within the hyper-heuristic.
     * @return The value ordering heuristic coded within the hyper-heuristic.
    */    
    public int getValueOrderingHeuristic() {
        return valueOrderingHeuristic;
    }
    
    /**
     * Returns the constraint ordering heuristic coded within the hyper-heuristic.
     * @return The constraint ordering heuristic coded within the hyper-heuristic.
    */    
    public int getConstraintOrderingHeuristic() {
        return constraintOrderingHeuristic;
    }
}