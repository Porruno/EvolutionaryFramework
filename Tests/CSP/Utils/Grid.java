package CSP.Utils;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to produce the grids of instances for the
 * different combinations of parameters.
 * ----------------------------------------------------------------------------
 */

import CSP.CSP.*;
import CSP.Framework.Framework;
import CSP.HyperHeuristic.Heuristic;
import CSP.HyperHeuristic.HyperHeuristic;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public abstract class Grid {
  
    public static boolean plotPatternMode = false;       
    
    /**
     * Creates a grid of instances and solves it with each heuristic in the array.
     * @param n The number of variables.
     * @param m The domain size for each variable.
     * @param deltaP The steps in the axes.
     * @param orderingHeuristics The indexes of the ordering heuristics to be used
     * to generate the grid.
     * @param randomModel The generation model used to generate the grid.
     * @param fileName the name of the file where the results will be saved.
    */    
    public static void createGrid(int n, int m, double deltaP, int instances, int orderingHeuristics[], int randomModel, String fileName) {
        if (deltaP < 0 || deltaP > 1.0) {
            throw new IllegalArgumentException("Argument deltaP must be a value in the range [0, 1]");
        }
        generateGrid(n, m, deltaP, instances, orderingHeuristics, randomModel, fileName);
    }
    
    /**
     * Creates a grid of instances and solves it with each hyper-heuristic in 
     * the array.
     * @param n The number of variables.
     * @param m The domain size for each variable.
     * @param deltaP The steps in the axes.
     * @param orderingHeuristics The indexes of the ordering heuristics to be used
     * to generate the grid.
     * @param randomModel The generation model used to generate the grid.
     * @param fileName the name of the file where the results will be saved.
    */    
    public static void createGrid(int n, int m, double deltaP, int instances, HyperHeuristic hyperHeuristics[], int randomModel, String fileName) {
        if (deltaP < 0 || deltaP > 1.0) {
            throw new IllegalArgumentException("Argument deltaP must be a value in the range [0, 1]");
        }
        generateGrid(n, m, deltaP, instances, hyperHeuristics, randomModel, fileName);
    }
    
    /**
     * Creates a grid of instances and solves it with each heuristic in the array.
     * @param n The number of variables.
     * @param m The domain size for each variable.
     * @param deltaP The steps in the axes.
     * @param orderingHeuristics The indexes of the ordering heuristics to be used
     * to generate the grid.
     * @param randomModel The generation model used to generate the grid.
     * @param fileName the name of the file where the results will be saved.
    */    
    public static void createBranchingGrid(int n, int m, double deltaP, int instances, Heuristic heuristic, int randomModel, String fileName) {
        if (deltaP < 0 || deltaP > 1.0) {
            throw new IllegalArgumentException("Argument deltaP must be a value in the range [0, 1]");
        }
        generateBranchingGrid(n, m, deltaP, instances, heuristic, randomModel, fileName);
    }
    
    /**
     * Prints the pattern that a given hyper-heuristic has learnt.
     * @param The hyper-heuristic we want to test.
    */
    public static void printPattern(HyperHeuristic hyperHeuristic) {
        int i, j, heuristic = -1;        
        double p1, p2, delta;
        CSP csp;
        plotPatternMode = true;
        p1 = 0;        
        delta = 0.05;
        for (i = 0; i < 20; i++) {
            p1 = p1 + delta;
            p2 = 0;
            System.out.println();            
            for (j = 0; j < 20; j++) {
                p2 = p2 + delta;
                csp = new CSP(20, 10, p1, p2, CSP.MODEL_B);                
                heuristic = CSPSolver.getOutput(csp, hyperHeuristic);
                int variableOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[0];
                int valueOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[1];
                int constraintOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[2];
                System.out.print(Framework.codeHeuristicIndex(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic) + " ");                
            }            
        }
        System.out.println();        
        plotPatternMode = false;
    }
    
    /* ------------------------------------------------------------------------
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Generates a grid of instances and solves it with the given heuristic.
     * @param n The number of variables.
     * @param m The domain size for each variable.
     * @param deltaP The steps in the axes.
     * @param orderingHeuristics The indexes of the ordering heuristics to be used
     * to generate the grid.
     * @param randomModel The generation model used to generate the grid.
     * @param fileName the name of the file where the results will be saved.
    */
    private static void generateGrid(int n, int m, double deltaP, int instances, HyperHeuristic hyperHeuristics[], int randomModel, String fileName) {
        int i, j, k, l, size;
        String line = "";
        String labels[] = new String[hyperHeuristics.length];
        size = (int) (1 / deltaP);
        ArrayList data = new ArrayList(size * size * instances);        
        CSP csp;
        CSPSolver solver;
        int counter = 0;
        for (i = 1; i <= size; i++) {                        
            for (j = 1; j <= size; j++) {                
                for (k = 0; k < instances; k++) {                    
                    csp = new CSP(n, m, (double) i * deltaP, (double) j * deltaP, randomModel);
                    line = csp.getConstraintDensity() + " " + csp.getConstraintTightness() + " ";
                    for (l = 0; l < hyperHeuristics.length; l++) {                        
                        solver = new CSPSolver(csp);
                        counter++;                        
                        solver.solve(hyperHeuristics[l]);
                        labels[l] = "HH" + l;
                        line = line + solver.getNumberOfSolutions() + " " + solver.getConstraintChecks() + " " + solver.getTime() + " " + solver.getSolutionDepth() + " " + solver.getMaximumDepth() + " ";                        
                    }
                    data.add(line);
                    System.out.println(line);
                }
            }
        }
        saveGridToFile(n, m, instances, data, labels, fileName);        
    }
    
    /**
     * Generates a grid of instances and solves it with the given heuristic.
     * @param n The number of variables.
     * @param m The domain size for each variable.
     * @param deltaP The steps in the axes.
     * @param orderingHeuristics The indexes of the ordering heuristics to be used
     * to generate the grid.
     * @param randomModel The generation model used to generate the grid.
     * @param fileName the name of the file where the results will be saved.
    */
    private static void generateGrid(int n, int m, double deltaP, int instances, int orderingHeuristics[], int randomModel, String fileName) {
        int i, j, k, l, size, variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic;                
        String line = "";
        String labels[] = new String[orderingHeuristics.length];
        size = (int) (1 / deltaP);
        ArrayList data = new ArrayList(size * size * instances);        
        CSP csp;
        CSPSolver solver;
        int counter = 0;
        for (i = 1; i <= size; i++) {                        
            for (j = 1; j <= size; j++) {                
                for (k = 0; k < instances; k++) {                    
                    csp = new CSP(n, m, (double) i * deltaP, (double) j * deltaP, randomModel);
                    line = csp.getConstraintDensity() + " " + csp.getConstraintTightness() + " ";
                    for (l = 0; l < orderingHeuristics.length; l++) {
                        variableOrderingHeuristic = Framework.decodeHeuristicIndex(orderingHeuristics[l])[0];
                        valueOrderingHeuristic = Framework.decodeHeuristicIndex(orderingHeuristics[l])[1];
                        constraintOrderingHeuristic = Framework.decodeHeuristicIndex(orderingHeuristics[l])[2];
                        labels[l] = VariableOrderingHeuristics.heuristicToString(variableOrderingHeuristic) + " & " + ValueOrderingHeuristics.heuristicToString(valueOrderingHeuristic);
                        solver = new CSPSolver(csp);
                        counter++;                        
                        solver.solve(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic, false);
                        line = line + solver.getNumberOfSolutions() + " " + solver.getConstraintChecks() + " " + solver.getTime() + " " + solver.getSolutionDepth() + " " + solver.getMaximumDepth() + " ";                        
                    }
                    data.add(line);
                    System.out.println(line);
                }
            }
        }        
        saveGridToFile(n, m, instances, data, labels, fileName);        
    }
        
    /**
     * Generates a grid of instances and solves it with the given heuristic and 
     * using the three branching strategies.
     * @param n The number of variables.
     * @param m The domain size for each variable.
     * @param deltaP The steps in the axes.
     * @param heuristic The ordering heuristic to be used to generate the grid.
     * @param randomModel The generation model used to generate the grid.
     * @param fileName the name of the file where the results will be saved.
    */
    private static void generateBranchingGrid(int n, int m, double deltaP, int instances, Heuristic heuristic, int randomModel, String fileName) {
        int i, j, k, size, variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic;
        String line = "";
        String labels[] = new String[1];
        size = (int) (1 / deltaP);
        ArrayList data = new ArrayList(size * size * instances);
        CSP csp;
        CSPSolver solver;
        int counter = 0;
        for (i = 1; i <= size; i++) {
            for (j = 1; j <= size; j++) {
                for (k = 0; k < instances; k++) {
                    csp = new CSP(n, m, (double) i * deltaP, (double) j * deltaP, randomModel);
                    line = csp.getConstraintDensity() + " " + csp.getConstraintTightness() + " ";
                    variableOrderingHeuristic = heuristic.getVariableOrderingHeuristic();
                    valueOrderingHeuristic = heuristic.getValueOrderingHeuristic();
                    constraintOrderingHeuristic = heuristic.getConstraintOrderingHeuristic();
                    labels[0] = VariableOrderingHeuristics.heuristicToString(variableOrderingHeuristic) + " & " + ValueOrderingHeuristics.heuristicToString(valueOrderingHeuristic) + " & " + ConstraintOrderingHeuristics.heuristicToString(constraintOrderingHeuristic);
                    solver = new CSPSolver(csp);
                    counter++;
                    // We solve with binary branching
                    solver.solve(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic, true);
                    line = line + solver.getNumberOfSolutions() + " " + solver.getConstraintChecks() + " " + solver.getTime() + " " + solver.getSolutionDepth() + " " + solver.getMaximumDepth() + " ";
                    // We solve with non-binary branching
                    solver.solve(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic, false);
                    line = line + solver.getNumberOfSolutions() + " " + solver.getConstraintChecks() + " " + solver.getTime() + " " + solver.getSolutionDepth() + " " + solver.getMaximumDepth() + " ";
                    /*
                    // We solve with mixed branching
                    solver.solve(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic);
                    line = line + solver.getNumberOfSolutions() + " " + solver.getConstraintChecks() + " " + solver.getTime() + " " + solver.getSolutionDepth() + " " + solver.getMaximumDepth() + " ";
                    */
                    data.add(line);
                    System.out.println(line);
                }
            }
        }
        saveGridToFile(n, m, instances, data, labels, fileName);
    }
    
    /**
     * Saves the grid to a file.
    */
    private static void saveGridToFile(int n, int m, int instances, ArrayList data, String labels[], String fileName) {        
        int i;
        String text = "";
        GregorianCalendar day = new GregorianCalendar();
        text += "% This file was created on " + day.getTime().toString() + ".\r\n";
        text += "% n = " + n + ", m = " + m + ", instancesPerPoint = " + instances + "\r\n";
        text += "%";
        for (i = 0; i < labels.length; i++) {            
            text += " " + labels[i];
        }
        text += "\r\n";
        text += "% P1 P2 SOLVABLE CHECKS TIME SOL_DEPTH MAX_DEPTH:\r\n";
        for (i = 0; i < data.size(); i++) {
            text += (String) data.get(i) + "\r\n";
        }
        Files.saveToFile(text, fileName, false);
    }

    /* ------------------------------------------------------------------------
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */    
}
