package CSP.Framework;
import CSP.CSP.CSP;
import CSP.CSP.CSPSolver;
import CSP.HyperHeuristic.NeuralNetworkHyperHeuristic;
import CSP.Utils.Files;
import java.util.Random;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to handle the neural network framework.
 * ----------------------------------------------------------------------------
*/

public abstract class NeuralNetworkFramework extends Framework{   
    
    public static void createPrototypeArrayLists(String fileName, int heuristicsPerTrial, boolean simple) {
        int i, j, heuristicIndex, bestHeuristicIndex;
        long bestResult;
        String line;
        Random generator = new Random();
        CSP csp;
        CSPSolver solver;
        if (!heuristicsSet) {
            System.out.println("=> Running with default heuristics.");
        }
        if (!featuresSet) {
            System.out.println("=> Running with default features.");
        }
        if (!repositorySet) {
            System.out.println("=> The repository has not been set.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        if (Framework.getHeuristics().length > heuristicsPerTrial) {
            heuristicsPerTrial = Framework.getHeuristics().length;
        }
        Files.saveToFile("", fileName, false);
        for (i = 0; i < repository.size(); i++) {
            csp = repository.get(i);
            bestHeuristicIndex = -1;
            bestResult = Long.MAX_VALUE;
            for (j = 0; j < heuristicsPerTrial; j++) {
                if (Framework.getHeuristics().length > 4) {
                    heuristicIndex = Framework.getHeuristics()[generator.nextInt(Framework.getHeuristics().length)];
                } else {
                    heuristicIndex = Framework.getHeuristics()[j];
                }
                solver = new CSPSolver(csp);
                solver.solve(Framework.decodeHeuristicIndex(heuristicIndex)[0], Framework.decodeHeuristicIndex(heuristicIndex)[1], Framework.decodeHeuristicIndex(bestHeuristicIndex)[2], false);
                if (solver.getConstraintChecks() < bestResult) {
                    bestResult = solver.getConstraintChecks();
                    bestHeuristicIndex = heuristicIndex;
                }
            }
            if (!simple) {
                solver = new CSPSolver(csp);
                solver.setPrototypeMode(true, fileName);
                solver.solve(Framework.decodeHeuristicIndex(bestHeuristicIndex)[0], Framework.decodeHeuristicIndex(bestHeuristicIndex)[1], Framework.decodeHeuristicIndex(bestHeuristicIndex)[2], false);
            } else {
                line = "";
                for (j = 0; j < Framework.getFeatures().length; j++) {
                    line += csp.getNormalizedFeature(Framework.getFeatures()[j]) + ", ";
                }
                line += bestHeuristicIndex + "\r\n";                
                Files.saveToFile(line, fileName, true);
            }
        }        
    }
    
    public static NeuralNetworkHyperHeuristic run(String fileName, double learningRate, int ArrayListsPerHeuristic, int cycles) {
        int i, j;
        int classes[];
        NeuralNetworkHyperHeuristic hyperHeuristic;        
        classes = new int[Framework.getHeuristics().length * ArrayListsPerHeuristic];
        j = 0;
        for (i = 0; i < classes.length; i++) {
            classes[i] = Framework.getHeuristics()[j++];            
            if (j == Framework.getHeuristics().length) {
                j = 0;
            }
        }
        hyperHeuristic = new NeuralNetworkHyperHeuristic(Framework.getFeatures().length, classes, learningRate);
        hyperHeuristic.train(fileName, cycles);
        return hyperHeuristic;
    }
    
}
