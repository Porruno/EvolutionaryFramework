package CSP.Framework;

import CSP.CSP.CSP;
import CSP.CSP.CSPSolver;
import CSP.CSP.ValueOrderingHeuristics;
import CSP.CSP.VariableOrderingHeuristics;
import CSP.GA.Population;
import CSP.HyperHeuristic.Block;
import CSP.HyperHeuristic.BlockHyperHeuristic;
import CSP.HyperHeuristic.MatrixHyperHeuristic;
import CSP.Utils.Misc;
import java.io.File;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions and fields to create and handle the 
 * local improvement framework.
 * ----------------------------------------------------------------------------
*/

public abstract class LocalImprovementFramework extends Framework{            
        
    private static String populationsPath = Population.populationsPath;    
    private static int changeMatrix[][];
    private static int heuristicsToTry[];
    
    public static int defaultHeuristic = 0;
    public static double minDistance = 0.1;
    
    public static MatrixHyperHeuristic run(String runName, int defaultHeuristic, int size, int cycles) {
        int i, j, variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic;        
        long checks, averageStart = 0, averageBest, average, minAverage = 0;
        CSP csp;
        CSPSolver solver;
        if (!heuristicsSet) {
            System.out.println("=> Running with default heuristics.");
        }
        if (!featuresSet) {
            System.out.println("=> Running with default features.");
        }
        if (Framework.getFeatures().length > 2) {
            System.out.println("=> Running with more than two features. Only the first two will be considered.");
        }
        if (!repositorySet) {
            System.out.println("=> The repository has not been set.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        File f; 
        f = new File(populationsPath + "/" + runName);
        if (f.exists() == false) {
            f.mkdirs();
        }
        Population.populationsPath = LocalImprovementFramework.populationsPath + runName + "\\";
        MatrixHyperHeuristic temp;
        MatrixHyperHeuristic hyperHeuristic = new MatrixHyperHeuristic(size, defaultHeuristic);
        createChangeMatrix(hyperHeuristic);        
        averageStart = 0;
        averageBest = 0;
        for (j = 0; j < repository.size(); j++) {
            csp = repository.get(j);
            solver = new CSPSolver(csp);
            solver.solve(hyperHeuristic);
            checks = solver.getConstraintChecks();            
            averageStart += checks;
            averageBest += repository.getBestResult(j);
        }
        hyperHeuristic.saveToFile(Population.populationsPath + "\\protoHyperHeuristic.txt");
        averageStart = averageStart / repository.size();
        averageBest = averageBest / repository.size();        
        minAverage = averageStart;
        average = minAverage;                
        for (i = 0; i < cycles; i++) {            
            temp = new MatrixHyperHeuristic(hyperHeuristic);
            if (hyperHeuristic.change()) {
                average = 0;
                for (j = 0; j < repository.size(); j++) {
                    csp = repository.get(j);
                    solver = new CSPSolver(csp);
                    solver.solve(hyperHeuristic);
                    checks = solver.getConstraintChecks();
                    average += checks;
                }
                average = average / repository.size();
                if (average < minAverage) {
                    minAverage = average;
                    System.out.println("The change is accepted. (" + average + ")");
                    hyperHeuristic.saveToFile(Population.populationsPath + "\\hyperHeuristic" + Misc.toSpecialFormat(i) + ".txt");
                } else {
                    System.out.println("The change is cancelled. (" + average + ")");                    
                    hyperHeuristic = new MatrixHyperHeuristic(temp);
                }
            }                      
        }
        System.out.println("Average at start: " + averageStart);
        System.out.println("Average at end: " + minAverage);        
        double proportion = ((double) minAverage / (double) averageStart);
        System.out.println("Reduction? = " + (100 - (int)(proportion * 100)) + "%");        
        hyperHeuristic.saveToFile(Population.populationsPath + "\\finalHyperHeuristic.txt");
        Population.populationsPath = LocalImprovementFramework.populationsPath;
        System.out.println("Average (Best): " + averageBest);
        for (i = 0; i < Framework.getHeuristics().length; i++) {        
            average = 0;
            variableOrderingHeuristic = decodeHeuristicIndex(Framework.getHeuristics()[i])[0];
            valueOrderingHeuristic = decodeHeuristicIndex(Framework.getHeuristics()[i])[1];
            constraintOrderingHeuristic = decodeHeuristicIndex(Framework.getHeuristics()[i])[2];
            for (j = 0; j < repository.size(); j++) {
                csp = repository.get(j);
                solver = new CSPSolver(csp);                
                solver.solve(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic, false);
                average += solver.getConstraintChecks();
            }
            average = average / repository.size();
            System.out.println("Average (" + VariableOrderingHeuristics.heuristicToString(variableOrderingHeuristic) + "/" + ValueOrderingHeuristics.heuristicToString(valueOrderingHeuristic) + "): " + average);
        }
        return hyperHeuristic;
    }
     
    public static BlockHyperHeuristic run(String runName, int defaultHeuristic, double minDistance, int cycles) {
        int i, j, variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic;        
        long checks, averageStart = 0, averageBest, average, minAverage = 0;
        CSP csp;
        CSPSolver solver;
        if (!heuristicsSet) {
            System.out.println("=> Running with default heuristics.");
        }
        if (!featuresSet) {
            System.out.println("=> Running with default features.");
        }
        if (Framework.getFeatures().length > 2) {
            System.out.println("=> Running with more than two features. Only the first two will be considered.");
        }
        if (!repositorySet) {
            System.out.println("=> The repository has not been set.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        File f;
        f = new File(populationsPath + "/" + runName);
        if (f.exists() == false) {
            f.mkdirs();
        }
        LocalImprovementFramework.defaultHeuristic = defaultHeuristic;        
        LocalImprovementFramework.minDistance = minDistance;
        Population.populationsPath = LocalImprovementFramework.populationsPath + runName + "\\";
        BlockHyperHeuristic temp;
        BlockHyperHeuristic hyperHeuristic = new BlockHyperHeuristic(1);
        hyperHeuristic.get(0).setHeuristic(defaultHeuristic);
        createChangeMatrix();
        heuristicsToTry = new int[Framework.getHeuristics().length - 1];
        for (i = 0, j = 0; i < Framework.getHeuristics().length; i++) {
            if (Framework.getHeuristics()[i] != defaultHeuristic) {
                heuristicsToTry[j++] = Framework.getHeuristics()[i];
            }
        }                
        CSPSolver.setMode(true);
        averageStart = 0;
        averageBest = 0;
        for (j = 0; j < repository.size(); j++) {
            csp = repository.get(j);
            solver = new CSPSolver(csp);
            solver.solve(hyperHeuristic);
            checks = solver.getConstraintChecks();            
            averageStart += checks;
            averageBest += repository.getBestResult(j);
        }        
        hyperHeuristic.saveToFile(Population.populationsPath + "\\protoHyperHeuristic.txt");
        averageStart = averageStart / repository.size();
        averageBest = averageBest / repository.size();        
        System.out.println("Average at start: " + averageStart);
        minAverage = averageStart;
        average = minAverage;
        for (i = 0; i < cycles; i++) {            
            temp = new BlockHyperHeuristic(hyperHeuristic);
            //System.out.println("\t" + hyperHeuristic);
            if (change(hyperHeuristic)) {
                average = 0;
                for (j = 0; j < repository.size(); j++) {
                    csp = repository.get(j);
                    solver = new CSPSolver(csp);
                    solver.solve(hyperHeuristic);
                    checks = solver.getConstraintChecks();
                    average += checks;
                }
                average = average / repository.size();
                if (average < minAverage) {
                    minAverage = average;
                    System.out.println("The change is accepted. (" + average + ")");
                    hyperHeuristic.saveToFile(Population.populationsPath + "\\hyperHeuristic" + Misc.toSpecialFormat(i) + ".txt");
                } else {
                    System.out.println("The change is cancelled. (" + average + ")");                    
                    hyperHeuristic = new BlockHyperHeuristic(temp);
                    //System.out.println("\t" + hyperHeuristic);
                }
            }
        }
        System.out.println("Average at start: " + averageStart);
        System.out.println("Average at end: " + minAverage);        
        double proportion = ((double) minAverage / (double) averageStart);
        System.out.println("Reduction? = " + (100 - (int)(proportion * 100)) + "%");        
        hyperHeuristic.saveToFile(Population.populationsPath + "\\finalHyperHeuristic.txt");
        Population.populationsPath = LocalImprovementFramework.populationsPath;
        System.out.println("Average (Best): " + averageBest);
        for (i = 0; i < Framework.getHeuristics().length; i++) {        
            average = 0;
            variableOrderingHeuristic = decodeHeuristicIndex(Framework.getHeuristics()[i])[0];
            valueOrderingHeuristic = decodeHeuristicIndex(Framework.getHeuristics()[i])[1];
            constraintOrderingHeuristic = decodeHeuristicIndex(Framework.getHeuristics()[i])[2];
            for (j = 0; j < repository.size(); j++) {
                csp = repository.get(j);
                solver = new CSPSolver(csp);                
                solver.solve(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic, false);
                average += solver.getConstraintChecks();
            }
            average = average / repository.size();
            System.out.println("Average (" + VariableOrderingHeuristics.heuristicToString(variableOrderingHeuristic) + "/" + ValueOrderingHeuristics.heuristicToString(valueOrderingHeuristic) + "): " + average);
        }
        return hyperHeuristic;
    }
    
    /**
     * Returns the change matrix.
    */
    public static int[][] getChangeMatrix() {
        return changeMatrix;
    }
    
    /**
     * Sets the usage matrix back to the default values.
    */
    public static void resetChangeMatrix(double distance) {
        int i, j;        
        for (i = 0; i < changeMatrix.length; i++) {
            for (j = 0; j < changeMatrix[0].length; j++) {
                if (changeMatrix[i][j] > 0 && Math.sqrt(Math.pow(((double) i), 2) + Math.pow(((double) j), 2)) > distance) {
                    System.out.println("\tReseting point (" + i + ", " + j + ").");
                    changeMatrix[i][j] = 0;
                }
            }
        }
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */   
    
    /**
     * Creates the change matrix.
     * @param hyperHeuristic The matrix hyper-heuristic.
     * @return A two-dimension integer matrix.
    */
    private static void createChangeMatrix(MatrixHyperHeuristic hyperHeuristic) {
        int i, j, rows, columns;
        rows = hyperHeuristic.getRowCount();
        columns = hyperHeuristic.getColumnCount();
        changeMatrix = new int[rows][columns];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {                
                changeMatrix[i][j] = 0;
            }
        }        
    }
    
    /**
     * Creates the change matrix.
     * @param hyperHeuristic The block hyper-heuristic.
     * @return A two-dimension integer matrix.
    */
    private static void createChangeMatrix() {
        int i, columns = 100; //The number is static at this point to reuse the data structures.        
        changeMatrix = new int[columns][1];
        for (i = 0; i < columns; i++) {
            changeMatrix[i][0] = 0;
        }
    }        
        
    private static boolean change(BlockHyperHeuristic hyperHeuristic) {
        int i, index = -1;
        double distance, max;
        Block block;
        max = Double.MIN_VALUE;
        for (i = 0; i < hyperHeuristic.size(); i++) {
            block = hyperHeuristic.get(i);
            distance = distanceToOrigin(block);
            if (distance > max && LocalImprovementFramework.getChangeMatrix()[i][0] < Framework.getHeuristics().length - 1) {
                max = distance;
                index = i;
                break;
            }
        }
        if (index == -1) {
            return false;
        } else {                        
            hyperHeuristic.get(index).setHeuristic(heuristicsToTry[changeMatrix[index][0]]);
            changeMatrix[index][0]++;
            return true;
        }
    }
    
    /**
     * Returns the distance from the vector to the origin.
    */
    private static double distanceToOrigin(Block block) {
        int i;
        double distance = 0;
        for (i = 0; i < Framework.getFeatures().length; i++) {
            distance += Math.pow(block.get(i), 2);
        }                
        return Math.sqrt(distance);
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */   
    
}
