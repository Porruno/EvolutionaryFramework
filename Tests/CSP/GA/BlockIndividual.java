package CSP.GA;

import CSP.HyperHeuristic.BlockHyperHeuristic;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012 
 * ----------------------------------------------------------------------------
 * This class contains the functions to create and handle an individual for 
 * running the genetic algorithm for block hyper-heuristics. The individuals
 * are formed by a series of blocks and can be affected by the crossover
 * and mutation operators.
 * ----------------------------------------------------------------------------
*/

public class BlockIndividual extends Individual {
    
    /** Creates a new random instance of Individual with the default number of 
     * blocks.      
    */
    public BlockIndividual() {
        super();                
        hyperHeuristic = new BlockHyperHeuristic();
    }
    
    /** Creates a new random instance of Individual with a number of blocks 
     * defined by the user.
    */
    public BlockIndividual(int size) {
        super();
        hyperHeuristic = new BlockHyperHeuristic(size);
    }
    
    /** Creates a new instance of Individual from another instance. This function
     * is a copy constructor. The aptitude, revisions and CSP instances linked to
     * the new instance are not copied from the original individual.
     * @param individual The instance that will be copied into the new individual.
    */
    public BlockIndividual(BlockIndividual individual) {
        super(individual);
        hyperHeuristic = new BlockHyperHeuristic((BlockHyperHeuristic) individual.getHyperHeuristic());
    }
    
}
