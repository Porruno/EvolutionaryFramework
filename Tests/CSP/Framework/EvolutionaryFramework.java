package CSP.Framework;

import CSP.HyperHeuristic.Block;
import CSP.CSP.CSP;
import CSP.GA.GeneticAlgorithm;
import CSP.GA.Population;
import CSP.HyperHeuristic.BlockHyperHeuristic;
import CSP.HyperHeuristic.HyperHeuristic;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to run the evolutionary framework.
 * ----------------------------------------------------------------------------
*/

public abstract class EvolutionaryFramework extends Framework{                  
    
    private static int populationSize = 50;
    private static String populationsPath = Population.populationsPath;
    
    public static void setCrossoverRate(double probability) {
        GeneticAlgorithm.setCrossoverRate(probability);
    }
    
    public static void setMutationRate(double probability) {
        GeneticAlgorithm.setMutationRate(probability);
    }
    
    public static void setPopulationSize(int populationSize) {
        if (populationSize % 2 != 0) {
            System.out.println("=> The population size must be an even number.");
            populationSize = populationSize + 1;
            System.out.println("=> The population size has been adjusted to " + populationSize);
        }
        EvolutionaryFramework.populationSize = populationSize;
    }

    public static int getPopulationSize() {
        return populationSize;
    }
    
    public static BlockHyperHeuristic run(String runName, int cycles, int type) {
        if (!heuristicsSet) {
            System.out.println("=> Running with default heuristics.");
        }
        if (!featuresSet) {
            System.out.println("=> Running with default features.");
        }
        if (!repositorySet) {
            System.out.println("=> The repository has not been set. The system will halt.");
            System.exit(1);
        }
        BlockHyperHeuristic hyperHeuristic;
        Population.populationsPath = EvolutionaryFramework.populationsPath + runName + "\\";        
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(populationSize, Framework.getRespository(), HyperHeuristic.BLOCK, type);
        hyperHeuristic = (BlockHyperHeuristic) geneticAlgorithm.run(cycles, runName);
        Population.populationsPath = EvolutionaryFramework.populationsPath;
        return hyperHeuristic;
    }
    
    
    public static BlockHyperHeuristic continueRun(String runName, int populationNumber, int cycles, int type) {
        if (!heuristicsSet) {
            System.out.println("=> Running with default heuristics.");
        }
        if (!featuresSet) {
            System.out.println("=> Running with default features.");
        }
        if (!repositorySet) {
            System.out.println("=> The repository has not been set. The system will halt.");
            System.exit(1);
        }
        BlockHyperHeuristic hyperHeuristic;
        Population.populationsPath = EvolutionaryFramework.populationsPath + runName + "\\";        
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(Framework.getRespository(), populationNumber, HyperHeuristic.BLOCK, type);
        hyperHeuristic = (BlockHyperHeuristic) geneticAlgorithm.run(cycles, runName);
        Population.populationsPath = EvolutionaryFramework.populationsPath;
        return hyperHeuristic;
    }    
     
    /** Calculates the euclidean distance between a block and a given CSP instance.
     * @param block The block to be used to calculate the distance.
     * @param csp The CSP instance which distance from the block will be calculated.
     * @return The euclidean distance from the block to the CSP instance.
     */
    public static double distance(Block block, CSP csp) {
        int i;
        double sum = 0;
        for (i = 0; i < Framework.getFeatures().length; i++) {
            sum = sum + Math.pow(block.get(i) - csp.getNormalizedFeature(Framework.getFeatures()[i]), 2);
        }        
        return Math.sqrt(sum);
    }

}
