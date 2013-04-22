package CSP.CSP;
import CSP.Framework.EvolutionaryFramework;
import CSP.Framework.Framework;
import CSP.Framework.LocalImprovementFramework;
import CSP.HyperHeuristic.*;
import CSP.LCS.ClassifierSystem;
import CSP.Utils.Files;
import CSP.Utils.Misc;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class solves a Constraint Satisfaction Problem. Contains a series of 
 * methods and heuristics that allow us to solve the instances.
 * ----------------------------------------------------------------------------
*/

public class CSPSolver {

    public final static long MAX_CONSTRAINT_CHECKS = 80000000;
    private static boolean localImprovement;
    private int constraintChecks, backtracks, expandedNodes, safeJump, solutionDepth, maxDepth, solutions;
    public int valuesR[], r[];
    private double time;
    private Variable solution[];
    private ArrayList leftToCheck, instantiated, levels, solutionNodes, treeNodes, solutionHeuristicOrdering;
    private CSP instance;
    public String solutionOrdering[], varIds[];
    private boolean stopped, prototypingMode = false;
    String prototypeFileName;
    
    private static final double PENALTY = 0.000;
    
    // Only for testing purposes
    public boolean backJumping = false;
    private boolean findAllSolutions = false;
    
    /**
     * Creates a new instance of CSPSolver.
     * @param csp The instance of CSP that will be solved.
    */
    public CSPSolver(CSP csp) {
        prototypingMode = false;        
        initSolver(csp);
    }
    
    /**
     * Runs the solver.
     * @param variableOrderingHeuristic An integer with the index of the variable ordering heuristic to be used by the solver. 
     * @param valueOrderingHeuristic An integer with the index of the value ordering heuristic to be used by the solver.
     * @return An array of Variable with the solution to the CSP instance. If the instance has no solution, the function
     * returns null.
    */
    public Variable[] solve(int variableOrderingHeuristic, int valueOrderingHeuristic, int constraintOrderingHeuristic, boolean binaryBranching) {
        long startTime, endTime;        
        instance = convertConstraints(instance);
        initSolver(instance);
        startTime = System.currentTimeMillis();        
        Heuristic heuristics = new Heuristic(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic);
        if (!binaryBranching) {
            recursiveBacktracking(instance, heuristics, 0);
        } else {                        
            String currentVariable = selectNextVariable(instance, variableOrderingHeuristic);
            recursiveBinaryBacktracking(instance, heuristics, currentVariable, 0);
        }
        endTime = System.currentTimeMillis();
        time = (double)(endTime - startTime) / 1000;        
        return solution;
    }        

    /**
     * Runs the solver.
     * @param variableOrderingHeuristic An integer with the index of the variable ordering heuristic to be used by the solver. 
     * @param valueOrderingHeuristic An integer with the index of the value ordering heuristic to be used by the solver.
     * @return An array of Variable with the solution to the CSP instance. If the instance has no solution, the function
     * returns null.
    */
    public Variable[] solve(int variableOrderingHeuristic, int valueOrderingHeuristic, int constraintOrderingHeuristic) {
        long startTime, endTime;        
        instance = convertConstraints(instance);
        initSolver(instance);
        startTime = System.currentTimeMillis();        
        Heuristic heuristics = new Heuristic(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic);        
        recursiveBacktracking(instance, heuristics, 0);
        endTime = System.currentTimeMillis();
        time = (double)(endTime - startTime) / 1000;        
        return solution;
    }
    
    /**
     * Runs the solver.
     * @param hyperHeuristic An instance of Individual that codes a valid hyper-heuristic.     
     * @return An array of Variable with the solution to the CSP instance. If the instance has no solution, the function
     * returns null.
    */
    public Variable[] solve(HyperHeuristic hyperHeuristic) {
        long startTime, endTime;        
        instance = convertConstraints(instance);
        initSolver(instance);
        startTime = System.currentTimeMillis();         
        recursiveBacktracking(instance, hyperHeuristic, 0);        
        endTime = System.currentTimeMillis();
        time = (double)(endTime - startTime) / 1000;        
        return solution;
    }   
       
    /**
     * Returns the number of consistency checks used by the solver to find a solution. 
     * @return A long with the number of consistency checks used by the solver to find a solution. If the CSP instance has not been 
     * solved the function returns 0.
    */
    public long getConstraintChecks() {
        return constraintChecks;
    }
      
    /**
     * Returns true if the solving process was stopped before finishing the search.
     * @return true if the solver was forced to stop, false otherwise.
    */
    public boolean stopped() {
        return stopped;
    }
    
    /**
     * Returns the depth of the tree where the solution was found. If backjumping is disabled, the solution depth must be 
     * equal to the number of variables in the CSP instance.
     * @return An integer with the depth of the solution of the tree where the solution was found.  If the instance has not been solved, the
     * function returns -1.
    */    
    public int getSolutionDepth() {
        return solutionDepth;
    }
    
    /**
     * Returns the maximum depth of the search tree.
     * @return An integer with the maximum depth of the search tree.  If the instance has not been solved, the function returns -1.
    */
    public int getMaximumDepth() {
        return maxDepth;
    }
    
    /**
     * Returns the time (in seconds) used to solve the CSP instance. It is important to notice that the time may change for
     * different computers.
     * @return A double with the time (in seconds) used to solve the CSP instance.
    */
    public double getTime() {
        return time;
    }
    
    /**
     * Returns the number of backtrack movements used by the solver to find a solution. 
     * @return A long with the number of backtrack movements used by the solver to find a solution. If the CSP instance has not been 
     * solved the function returns 0.
    */
    public long getBacktracks() {
        return backtracks;
    }
    
    /**
     * Returns the number of nodes expanded by the solver to find a solution. 
     * @return A long with the number of nodes expanded by the solver to find a solution. If the CSP instance has not been 
     * solved the function returns 0.
    */
    public long getExpandedNodes() {
        return expandedNodes;
    }
    
    /**
     * Returns the solution nodes visited during the search.
     * @return An array of arrays of double with the solution nodes in the search tree.     
    */
    public double[][] getSolutionNodes() {
        int i, j = 0;
        double node[][] = new double[solutionNodes.size()][];
        for (i = solutionNodes.size() - 1; i >= 0; i--) {
            node[j++] = (double[]) solutionNodes.get(i);
        }
        return node;
    }        
    
    /**
     * Returns the nodes visited during the search.
     * @return An array of arrays of double with the nodes in the search tree.     
    */
    public double[][] getTreeNodes() {
        int i, j = 0;
        double node[][] = new double[treeNodes.size()][];
        for (i = treeNodes.size() - 1; i >= 0; i--) {
            node[j++] = (double[]) treeNodes.get(i);
        }
        return node;
    }   
    
    /**
     * Returns the number of solutions found during the search.
     * @return The number of solutions found during the search.
    */
    public int getNumberOfSolutions() {
        return solutions;
    }
    
    
    /**
     * Saves the search tree into a file.
     * @param fileName The name of the file where the tree will be saved.
    */
    public void saveSearchTreeToFile(String fileName) {
        int i;
        double nodes[][]; 
        GregorianCalendar day = new GregorianCalendar();
        try {
            File f = new File(fileName);
            FileWriter fw = new FileWriter(f);

            fw.write("% This file was created on " + day.getTime().toString() + ".\r\n");
            fw.write("% This file is to be used by matlab script analyseGrid (version 2.0).\r\n");
            fw.write("% DESCRIPTION OF THE FILE:\r\n");
            fw.write(instance.getVariables().length + " " + instance.getVariables()[0].getDomain().length + " 0 0 \r\n"); // Check the number....
            fw.write("% DESCRIPTION OF THE COLUMNS:\r\n");
            fw.write("% 1 - Y COORDINATE (FEATURE 1) \r\n");
            fw.write("% 2 - X COORDINATE (FEATURE 2) \r\n");
            fw.write("% 3 - DEPTH OF THE NODE \r\n");
            fw.write("% 4 - HEURISTIC USED \r\n");
            nodes = getTreeNodes();
            for (i = 0; i < nodes.length; i++) {
                fw.write(nodes[i][1] + " " + nodes[i][0] + " " + nodes[i][2] + " " + nodes[i][3] + "\r\n");
            }
            fw.write("-1 -1 -1 -1\r\n");            
            nodes = getSolutionNodes();
            for (i = 0; i < nodes.length; i++) {
                fw.write(nodes[i][1] + " " + nodes[i][0] + " " + nodes[i][2] + " " + nodes[i][3] + "\r\n");
            }            
            fw.close();
        } catch (IOException e) {
            System.out.println("The system cannot create the output file \"" + fileName + "\".");
        }
    }     

    /**
     * Saves the solution path into a file.
     * @param fileName The name of the file where the path will be saved.
    */
    public void saveSolutionPathToFile(String fileName) {
        int i;
        double nodes[][]; 

        GregorianCalendar day = new GregorianCalendar();
        try {
            File f = new File(fileName);
            FileWriter fw = new FileWriter(f);

            fw.write("% This file was created on " + day.getTime().toString() + ".\r\n");
            fw.write("% This file is to be used by matlab script analyseGrid (version 2.0).\r\n");
            fw.write("% DESCRIPTION OF THE FILE:\r\n");
            fw.write(instance.getVariables().length + " " + instance.getVariables()[0].getDomain().length + " 0 0 \r\n"); // Check the number....
            fw.write("% DESCRIPTION OF THE COLUMNS:\r\n");
            fw.write("% 1 - y COORDINATE (FEATURE 1) \r\n");
            fw.write("% 2 - X COORDINATE (FEATURE 2) \r\n");
            fw.write("% 3 - DEPTH OF THE NODE \r\n");
            fw.write("% 4 - HEURISTIC USED \r\n");
            nodes = getSolutionNodes();
            for (i = 0; i < nodes.length; i++) {
                fw.write(nodes[i][1] + " " + nodes[i][0] + " " + nodes[i][2] + " " + nodes[i][3] + "\r\n");
            }            
            fw.close();
        } catch (IOException e) {
            System.out.println("The system cannot create the output file \"" + fileName + "\".");
        }
    }       
    
    public void setPrototypeMode(boolean state, String fileName) {
        prototypingMode = state;
        prototypeFileName = fileName;
    }
    
    /**
     * Determines the heuristic index to be used given the current instance and
     * a hyper-heuristic.
     * @param csp The current csp instance.
     * @param hyperHeuristic The hyper-heuristic to be used.
    */
    public static int getOutput(CSP csp, HyperHeuristic hyperHeuristic) {
        int output;
        switch(hyperHeuristic.getType()) {
            case HyperHeuristic.LOWLEVEL:
                output = getOutput((Heuristic) hyperHeuristic);                
                break;
            case HyperHeuristic.BLOCK:
                output = getOutput(csp, (BlockHyperHeuristic) hyperHeuristic);
                break;
            case HyperHeuristic.NEURAL:
                output = getOutput(csp, (NeuralNetworkHyperHeuristic) hyperHeuristic);
                break;
            case HyperHeuristic.MATRIX:
                output = getOutput(csp, (MatrixHyperHeuristic) hyperHeuristic);                
                break;
            case HyperHeuristic.SIMPLEMATRIX:
                output = getOutput(csp, (SimpleMatrixHyperHeuristic) hyperHeuristic);                
                break;
            case HyperHeuristic.LCS:
                output = getOutput(csp, (ClassifierSystem) hyperHeuristic);                
                break;    
            case HyperHeuristic.RANDOM:
                output = getOutput();                
                break;
            default:
                output = 0;
        }
        return output;
    }        

    /**
     * Returns the order in which the variables were instantiated to find the solution.
     * In case the solver is looking for all the solutions, the function returns the
     * order of the last solution.
     *
    */
    public String[] getOrdering() {
        return solutionOrdering;
    }

    /**
     * Returns the heuristics in the order they were used to create the variable ordering.
     * In case the solver is looking for all the solutions, the function returns the
     * order of the last solution.
     *
    */
    public String[] getHeuristicOrdering() {
        int i;
        String ordering[] = new String[solutionHeuristicOrdering.size()];
        for (i = 0; i < solutionHeuristicOrdering.size(); i++) {
            ordering[i] = (String) solutionHeuristicOrdering.get(i);
        }
        return ordering;
    }        
    
    /**
     * Returns the frequency of removal counter for a given variable and value.
     * @param varaibleId The Id of the variable.
     * @param value The value to be updated.
     */
    public int findR(String variableId, int value) {
        int i;
        for (i = 0; i < varIds.length; i++) {
            if (varIds[i].equalsIgnoreCase(variableId) && valuesR[i] == value) {
                return r[i];
            }
        }
        System.out.println("Registering the value of R failed. Variable " + variableId + " and Value " + value + " not found.");
        System.out.println("The system will halt.");
        System.exit(1);
        return 0;
    }
    
    /**
     * Changes the state of the local improvement method.
     * @param localImprovement Activates or deactivates the local improvement method.
    */
    public static void setMode(boolean localImprovement) {
        CSPSolver.localImprovement = localImprovement;
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Initializes the CSP solver.
     * @param csp The CSP instance that will be solved.
    */
    private void initSolver(CSP csp) {        
        constraintChecks = 0;   
        backtracks = 0;
        expandedNodes = 0;        
        solutionOrdering = new String[csp.getVariables().length];
        solutionHeuristicOrdering = new ArrayList(csp.getVariables().length);
        instance = new CSP(csp);
        solutionDepth = -1;
        maxDepth = -1;
        time = -1;
        safeJump = Integer.MAX_VALUE;
        instantiated = new ArrayList(csp.getVariables().length);
        levels = new ArrayList(csp.getVariables().length);
        solutionNodes = new ArrayList(1000);
        treeNodes = new ArrayList(1000);
        stopped = false;
        solutions = 0;        
        int size = 0;
        int domain[];        
        for (int i = 0; i < instance.getVariables().length; i++) {            
            domain = instance.getVariables()[i].getDomain();
            size += domain.length;
        }
        varIds = new String[size];
        valuesR = new int[size];
        r = new int[size];
        int k = 0;
        for (int i = 0; i < instance.getVariables().length; i++) {                        
            domain = instance.getVariables()[i].getDomain();
            for (int j = 0; j < domain.length; j++) {                
                varIds[k] = instance.getVariables()[i].getId();
                valuesR[k] = domain[j];
                r[k] = 0;
                k++;
            }
        }        
    }   

    /**
     * Internally runs the solver. Only called from inside the class.
     * @param instance The CSP instance that will be solved.
     * @param variableOrderingHeuristic An integer with the index of the variable ordering heuristic to be used by the solver. 
     * @param valueOrderingHeuristic An integer with the index of the value ordering heuristic to be used by the solver.
     * @param findAllSolutions A boolean value to indicate if the solver must look for all the numberOfSolutions. If set to false,
     * the solver only finds the first solution and stops.
     * @param depth An integer with the current depth of the search tree.
     * @return A boolean value  array of Variable with the solution to the CSP instance. If the instance has no solution, the function
     * returns null.
    */
    private boolean recursiveBinaryBacktracking(CSP instance, HyperHeuristic hyperHeuristic, String variableId, int depth) {
        int heuristic, currentValue, variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic;
        int values[];
        boolean failure = false;
        String currentVariable;
        CSP csp, left, right;
        if (constraintChecks > MAX_CONSTRAINT_CHECKS) {
            stopped = true;
            return false;
        }               
        csp = new CSP(instance);
        heuristic = getOutput(csp, hyperHeuristic);
        variableOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[0];
        valueOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[1];
        constraintOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[2];
        if (variableId != null) {
            failure = true;
            if (checkConstraints(csp, variableId, constraintOrderingHeuristic, false)) {
                if (ac3(csp, csp.getVariable(variableId), constraintOrderingHeuristic)) {
                    failure = false;
                }                
            }
        }
        if (failure) {
            return false;
        }
        currentVariable = selectNextVariable(csp, variableOrderingHeuristic);
        if (currentVariable == null) {
            solutions++;
            solution = csp.getVariables();
            if (!findAllSolutions) {
                return true;
            } else {                               
                return false;
            }
        }
        values = valueOrdering(csp.getVariable(currentVariable), valueOrderingHeuristic);
        if (values.length == 0) {
            return false;
        }
        currentValue =  values[0];        
        /*
        System.out.println("LEFT:");
        for (int xxx = 0; xxx < depth; xxx++) {
            System.out.print(" ");
        }        
        System.out.println(currentVariable + " = " + currentValue);       
        */        
        left = new CSP(csp);
        left.getVariable(currentVariable).setValue(currentValue);
        if (recursiveBinaryBacktracking(left, hyperHeuristic, currentVariable, depth + 1)) {
            return true;
        }
        right = new CSP(csp);
        right.getVariable(currentVariable).removeValue(currentValue);
        updateConstraints(right);
        /*
        System.out.println("RIGHT:");
        for (int xxx = 0; xxx < depth; xxx++) {
            System.out.print(" ");
        }
        System.out.println(currentVariable + " != " + currentValue);
        */
        //System.out.println(right);
        if (recursiveBinaryBacktracking(right, hyperHeuristic, currentVariable, depth + 1)) {
            return true;            
        }
        return false;        
    }
    
    public static void printVariableDomains(Variable[] variables){
        for(Variable variable : variables){
            for(Integer integer : variable.getDomain()){
                System.out.printf(integer + ", ", integer);
            }
            System.out.println();
        }
    }
    
    
    /**
     * Internally runs the solver. Only called from inside the class.
     * @param instance The CSP instance that will be solved.
     * @param hyperHeuristic The hyper-heuristic taht will be used to solve the instance. 
     * @param depth An integer with the current depth of the search tree.
     * @return A boolean value  array of Variable with the solution to the CSP instance. If the instance has no solution, the function
     * returns null.
    */
    private boolean recursiveBacktracking(CSP instance, HyperHeuristic hyperHeuristic, int depth) {        
        int i, k, x, y, top, heuristic, variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic;
        int orderedDomain[];
        int usageMatrix[][];
        double feature1, feature2;
        String currentVariable;        
        CSP csp = new CSP(instance);        
        if (constraintChecks > MAX_CONSTRAINT_CHECKS) {
            stopped = true;
            return false;
        }
        if (hyperHeuristic.getType() == HyperHeuristic.MATRIX) {
            usageMatrix = ((MatrixHyperHeuristic) hyperHeuristic).getUsageMatrix();
            // The first feature for the rows, the second for the columns
            feature1 = csp.getNormalizedFeature(Framework.getFeatures()[0]);
            feature2 = csp.getNormalizedFeature(Framework.getFeatures()[1]);
            x = (int) (feature1 * (usageMatrix.length - 1));
            y = (int) (feature2 * (usageMatrix[0].length - 1));
            usageMatrix[x][y]++;
        }
        //EXPERIMENTAL - Local improvement for Vector Hyper-heuristics
        if (localImprovement && hyperHeuristic.getType() == HyperHeuristic.BLOCK) {
            double distance;
            double minDistance = Double.MAX_VALUE;
            int index = -1;
            for (i = 0; i < ((BlockHyperHeuristic) hyperHeuristic).size(); i++) {
                distance = EvolutionaryFramework.distance(((BlockHyperHeuristic) hyperHeuristic).get(i), csp);
                if (distance < minDistance) {
                    minDistance = distance;
                    index = i;
                }
            }
            if (minDistance > LocalImprovementFramework.minDistance) {
                double features[] = new double[Framework.getFeatures().length];
                for (i = 0; i < features.length; i++) {
                    features[i] = csp.getFeature(Framework.getFeatures()[i]);
                }
                ((BlockHyperHeuristic) hyperHeuristic).add(new Block(features, LocalImprovementFramework.defaultHeuristic));
            }
        }
        // END - Experimental     
        heuristic = getOutput(csp, hyperHeuristic);
        variableOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[0];
        valueOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[1];
        constraintOrderingHeuristic = Framework.decodeHeuristicIndex(heuristic)[2];        
        currentVariable = selectNextVariable(csp, variableOrderingHeuristic);                        
        addRecentInstantiations(csp, depth);
        if (currentVariable != null) {
            top = instantiated.size();
            if (depth > maxDepth) {
                maxDepth = depth;
            }
            orderedDomain = valueOrdering(csp.getVariable(currentVariable), valueOrderingHeuristic);
            for (i = 0; i < orderedDomain.length; i++) {
                /*
                for (int xx = 0; xx < depth; xx++) {
                    System.out.print(" ");
                }                
                System.out.println(currentVariable + " = " + orderedDomain[i] + " (" + constraintChecks + ")");
                */
                expandedNodes++;
                instantiated.add(currentVariable);
                levels.add(depth);
                // We try the next possible value            
                csp.getVariable(currentVariable).setValue(orderedDomain[i]);
                updateConstraints(csp);                
                if (checkConstraints(csp, currentVariable, constraintOrderingHeuristic, false)) {
                    if (ac3(csp, csp.getVariable(currentVariable), constraintOrderingHeuristic)) {
                        if (recursiveBacktracking(csp, hyperHeuristic, depth + 1)) {                            
                            treeNodes.add(new double[]{instance.getNormalizedFeature(Framework.getFeatures()[0]), instance.getNormalizedFeature(Framework.getFeatures()[1]), depth, Framework.codeHeuristicIndex(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic)});
                            solutionNodes.add(new double[]{instance.getNormalizedFeature(Framework.getFeatures()[0]), instance.getNormalizedFeature(Framework.getFeatures()[1]), depth, Framework.codeHeuristicIndex(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic)});
                            solutionHeuristicOrdering.add(VariableOrderingHeuristics.heuristicToString(variableOrderingHeuristic));
                            if (prototypingMode) {
                                addPrototype(csp, Framework.codeHeuristicIndex(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic), prototypeFileName);
                            }
                            
                            if (hyperHeuristic.getType() == HyperHeuristic.LCS) {
                                ((ClassifierSystem) hyperHeuristic).updateFitness(1 - ((double)i / (double) orderedDomain.length));
                                //((ClassifierSystem) hyperHeuristic).updateFitness(constraintChecks);
                            }                              
                            
                            return true;
                        } else if (depth > safeJump) {
                            backtracks++;
                            if (hyperHeuristic.getType() == HyperHeuristic.LCS) {
                                ((ClassifierSystem) hyperHeuristic).updateFitness(PENALTY);
                            }
                            return false;
                        } else {
                            safeJump = Integer.MAX_VALUE;
                            treeNodes.add(new double[]{instance.getNormalizedFeature(Framework.getFeatures()[0]), instance.getNormalizedFeature(Framework.getFeatures()[1]), depth, Framework.codeHeuristicIndex(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic)});                            
                        }
                    }
                }
                // We undo the changes in the domains done by the constraint propagation
                while (instantiated.size() > top) {
                    instantiated.remove(instantiated.size() - 1);
                    levels.remove(levels.size() - 1);
                }                
                csp = new CSP(instance);
                csp.getVariable(currentVariable).removeValue(orderedDomain[i]);
                updateConstraints(csp);                             
            }
            backJumping(csp, csp.getVariable(currentVariable), constraintOrderingHeuristic, depth);            
            if (hyperHeuristic.getType() == HyperHeuristic.LCS) {                
                ((ClassifierSystem) hyperHeuristic).updateFitness(PENALTY);                
            }                        
            backtracks++;
            return false;
        }
        if (checkConstraints(csp, constraintOrderingHeuristic)) {
            solutionDepth = depth;
            solution = csp.getVariables();
            addRecentInstantiations(csp, depth + 1);
            solutionOrdering = new String[csp.getVariables().length];
            for (k = 0; k < instantiated.size(); k++) {
                solutionOrdering[k] = (String) instantiated.get(k);
            }
            solutionHeuristicOrdering.add(VariableOrderingHeuristics.heuristicToString(variableOrderingHeuristic));
            solutions++;
            if (!findAllSolutions) {                  
                return true;
            } else {                        
                return false;
            }
        } else {
            if (hyperHeuristic.getType() == HyperHeuristic.LCS) {                
                ((ClassifierSystem) hyperHeuristic).updateFitness(PENALTY);                
            }     
            return false;
        }
    }    

    /**
     * Adds assignments made during the previous level of the search. It is used by the backjumping algorithm.
    */
    private void addRecentInstantiations(CSP csp, int depth) {
        int i;        
        Variable variables[] = csp.getVariables();
        String variableIds[] = new String[variables.length];
        for (i = 0; i < variables.length; i++) {
            variableIds[i] = variables[i].getId();
        }                
        for (i = 0; i < variableIds.length; i++) {
            if (csp.getVariable(variableIds[i]).isInstantiated()) {
                if (!instantiated.contains(variableIds[i])) {
                    instantiated.add(variableIds[i]);
                    levels.add(depth - 1);             
                }
            }
        }
    }
    
    /**
     * Checks if an instance is solved.
     * @return True if the instance is solved, false otherwise.
    */    
    private boolean checkConstraints(CSP csp, int constraintOrderingHeuristic) {
        int i;
        Variable variables[] = csp.getVariables();
        for (i = 0; i < variables.length; i++) {
            if (!checkConstraints(csp, variables[i].getId(), constraintOrderingHeuristic, false)) {
                return false;
            }
        }
        return true;
    }  
    
    /**
     * Checks the constraints where the given variable is involved. Returns true
     * if no constraint is broken, false otherwise.
     * @return True if no constraint is broken, false otherwise.
    */
    private boolean checkConstraints(CSP csp, String variableId, int constraintOrderingHeuristic, boolean constraintPropagation) {
        int i;
        Constraint constraint;
        Variable variable = null, variables[] = csp.getVariables();
        ArrayList localConstraints;
        for (i = 0; i < variables.length; i++) {
            if (variables[i].getId().equalsIgnoreCase(variableId)) {
                variable = variables[i];
                break;
            }
        }
        if (variable == null) {
            System.out.println("The variable '" + variableId + "' has not been defined.");
            System.out.println("The constraint cannot be checked.");
            System.exit(1);
        }               
        constraintOrdering(csp, variable, constraintOrderingHeuristic);
        localConstraints = variable.getConstraints();
        for (i = 0; i < localConstraints.size(); i++) {
            constraint = (Constraint) localConstraints.get(i);
            if (!constraint.isSecure()) {
                constraintChecks++;
                switch (checkConstraint(csp, constraint)) {
                    case 0:
                        return false;
                    case 1:
                        break;
                    case 2:
                        if (!constraintPropagation) {
                            constraint.setSecure(true);
                        }
                        break;
                }
            }
        }
        return true;
    }

    /**
     * Checks the given constraint.
     * @return 0 if the constraint is broken, 1 if it involves variables not yet
     * instantiated (the constraint is not broken) and 2 if the constraint is satisfied.
    */
    private int checkConstraint(CSP csp, Constraint constraint) {        
        switch (constraint.getType()) {
            case Constraint.IN_EXTENSION:
                return checkInExtensionConstraint(constraint);
            case Constraint.IN_INTENSION:                
                return checkInIntensionConstraint(csp, constraint);
            case Constraint.GLOBAL:
                return checkGlobalConstraint(constraint);
        }
        return 0;  
    }        

    /**
     * Checks an in extension constraint.
     @return 0 if the constraint is broken, 1 if it involves variables not yet
     * instantiated (the constraint is not broken) and 2 if the constraint is satisfied.
    */
    private int checkInExtensionConstraint(Constraint constraint) {
        int i;
        int values[];
        Variable scope[];
        ArrayList tuples;
        scope = constraint.getScope();
        for (i = 0; i < scope.length; i++) {
            if (!scope[i].isInstantiated()) {
                // If one of the variables is not instantiated, 
                // the constraint is not violated.
                return 1;
            }
        }
        values = new int[scope.length];
        for (i = 0; i < scope.length; i++) {
            values[i] = (scope[i].getDomain())[0];
        }
        tuples = constraint.getTuples();
        for (i = 0; i < tuples.size(); i++) {
            if (equals(values, (int[]) tuples.get(i))) {
                if (constraint.isConflict()) {
                    return 0;
                } else {
                    return 2;
                }
            }
        }
        if (constraint.isConflict()) {
            return 2;
        } else {
            return 0;
        }
    }

    /**
     * Checks an in intension constraint.
     @return 0 if the constraint is broken, 1 if it involves variables not yet
     * instantiated (the constraint is not broken) and 2 if the constraint is satisfied.
    */
    private int checkInIntensionConstraint(CSP csp, Constraint constraint) {
        InIntensionConstraintChecker checker = new InIntensionConstraintChecker(csp, constraint);        
        return checker.check();
    }

    /**
     * Checks a global constraint.
     @return 0 if the constraint is broken, 1 if it involves variables not yet
     * instantiated (the constraint is not broken) and 2 if the constraint is satisfied.
    */
    private int checkGlobalConstraint(Constraint constraint) {
        String function = constraint.getFunction();
        if (function.equalsIgnoreCase("global:allDifferent")) {
            return globalAllDifferent(constraint.getScope());
        }
        return 0;
    }

    /**
     * Checks a global all different constraint.
     @return 0 if the constraint is broken, 1 if it involves variables not yet
     * instantiated (the constraint is not broken) and 2 if the constraint is satisfied.
    */
    private int globalAllDifferent(Variable variables[]) {
        int i;
        ArrayList usedValues = new ArrayList(variables.length);
        for (i = 0; i < variables.length; i++) {
            if (variables[i].isInstantiated()) {
                if (usedValues.contains(variables[i].getDomain()[0])) {                    
                    return 0;                    
                } else {
                    usedValues.add(variables[i].getDomain()[0]);                    
                }                
            }
        }        
        return 1;
    }

    /**
     * Compares two arrays of integers.
     * @return true if both arrays contain the same elements, 0 otherwise.
    */
    private boolean equals(int arrayA[], int arrayB[]) {
        int i;
        if (arrayA.length != arrayB.length) {
            return false;
        } else {
            for (i = 0; i < arrayA.length; i++) {
                if (arrayA[i] != arrayB[i]) {
                    return false;
                }
            }
            return true;
        }
    }   
    
    /**
     * Updates the constraints within the instance.
     * @param csp The CSP instance to be updated.
    */
    private void updateConstraints(CSP csp) {
        int i, j, k, l;
        int tuple[], domain[];
        boolean found;
        Variable scope;
        ArrayList constraints = csp.getConstraints();
        ArrayList tuples;
        Constraint constraint;
        for (i = 0; i < constraints.size(); i++) {
            constraint = (Constraint) constraints.get(i);
            if (!constraint.isSecure()) {
                constraintChecks++;
                tuples = constraint.getTuples();
                for (j = 0; j < tuples.size(); j++) {
                    tuple = (int[]) tuples.get(j);
                    for (k = 0; k < tuple.length; k++) {
                        scope = constraint.getScope()[k];
                        domain = scope.getDomain();
                        found = false;
                        for (l = 0; l < domain.length; l++) {
                            if (domain[l] == tuple[k]) {
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            tuples.remove(j);
                            j--;
                            break;
                        }
                    }
                }

            }
        }
    }
    
    /**
     * Implements tha AC3 algorithm. Returns true if the problem is still 
     * consistent, false otherwise.
     * @param csp The CSP instance where the propagation will take place.
     * @variable The variable where the propagation will start.
     * @return true if the problem is still consistent, false otherwise.
    */
    private boolean ac3(CSP csp, Variable variable, int constraintOrderingHeuristic) {        
        Constraint constraint;        
        leftToCheck = new ArrayList(variable.getConstraints());      
        while (leftToCheck.size() > 0) {          
            if (constraintChecks > MAX_CONSTRAINT_CHECKS) {
                return false;
            }
            constraint = (Constraint) leftToCheck.remove(0);            
            if (!constraint.isSecure()) {          
                if (constraint.getScope().length == 2) {                    
                    if (!removeInconsistentValues(csp, constraint, constraintOrderingHeuristic)) {                        
                        return false;
                    }                    
                } else if (constraint.getScope().length == 3) {
                    return true;
                }
                // The arity is not supported by AC3
                else {
                    return true;
                }
            }
        }
        updateConstraints(csp);                 
        return true;        
    }
    
    /**
     * Removes inconsistent values from the domain of the remaining variables.
     * @param csp The CSP instance where the removal will take place.
     * @param constraint The constraint where the propagation starts.
     * @param constraintOrderingHeuristic The constraint ordering heuristic used
     * to check the constraints.
    */    
    private boolean removeInconsistentValues(CSP csp, Constraint constraint, int constraintOrderingHeuristic) {
        boolean remove = true;
        int i, j;
        int domainX[], domainY[];
        Variable x, y;        
        ArrayList newDomain, constraints;
        if (constraint.getScope()[0].isInstantiated() && constraint.getScope()[1].isInstantiated()) {
            return true;
        }
        if (constraint.getScope()[1].isInstantiated() && !constraint.getScope()[0].isInstantiated()) {
            x = constraint.getScope()[0];
            y = constraint.getScope()[1];
        } else if (constraint.getScope()[0].isInstantiated() && !constraint.getScope()[1].isInstantiated()) {
            y = constraint.getScope()[0];
            x = constraint.getScope()[1];
        } else {
            return true;
        }        
        domainX = new int[x.getDomain().length];
        domainY = new int[y.getDomain().length];
        for (i = 0; i < x.getDomain().length; i++) {            
            domainX[i] = x.getDomain()[i];
        }
        for (i = 0; i < y.getDomain().length; i++) {            
            domainY[i] = y.getDomain()[i];
        }        
        newDomain = new ArrayList(domainX.length);
        for (i = 0; i < domainX.length; i++) {
            x.setValue(domainX[i]);
            remove = true;
            for (j = 0; j < domainY.length; j++) {
                y.setValue(domainY[j]);   
                if (checkConstraints(csp, x.getId(), constraintOrderingHeuristic, true)) {
                    remove = false;
                    j = domainY.length;
                }
            }
            if(!remove) {
                newDomain.add(domainX[i]);
            } else {
                updateR(x.getId(), domainX[i]);
            }
        }        
        if (newDomain.isEmpty()) {
            return false;
        }        
        domainX = new int[newDomain.size()];
        for (i = 0; i < newDomain.size(); i++) {
            domainX[i] = (Integer) newDomain.get(i);
        }        
        x.setDomain(domainX);
        y.setDomain(domainY);        
        if (remove) {
            constraints = x.getConstraints();
            for (i = 0; i < constraints.size(); i++) {                
                if (!leftToCheck.contains((Constraint) constraints.get(i))) {
                    leftToCheck.add((Constraint) constraints.get(i));    
                }
            }
        }        
        return true;
    }
       
    /**
     * Updates the frequency of removal counter of a given variable and value.
     * @param varaibleId The Id of the variable.
     * @param value The value to be updated.
    */ 
    private void updateR(String variableId, int value) {
        int i;
        for (i = 0; i < varIds.length; i++) {
            if (varIds[i].equalsIgnoreCase(variableId) && valuesR[i] == value) {
                r[i]++;
                return;
            }
        }
        System.out.println("Registering the value of R failed. Variable " + variableId + " and Value " + value + " not found.");
        System.out.println("The system will halt.");
        System.exit(1);
    }
    
    private void backJumping(CSP instance, Variable variable, int constraintOrderingHeuristic, int depth) {
        int i, j;        
        boolean safe;
        String id;
        Variable currentVariable;
        CSP csp;
        if (!backJumping || instantiated.isEmpty()) {
            safeJump = depth - 1;
            return;
        }  
        int domain[] = Misc.copy(variable.getDomain());            
        id = variable.getId();        
        csp = new CSP(instance);
        for (i = 0; i < csp.getVariables().length; i++) {
            csp.getVariables()[i].setInstantiated(false);
        }
        for (i = 0; i < instantiated.size(); i++) {            
            currentVariable = csp.getVariable((String)instantiated.get(i));
            currentVariable.setValue((instance.getVariable((String)instantiated.get(i))).getDomain()[0]);
            safe = false;            
            for (j = 0; j < domain.length; j++) {
                csp.getVariable(id).setValue(domain[j]);
                if (checkConstraints(csp, currentVariable.getId(), constraintOrderingHeuristic, true)) {
                    safe = true;
                    break;
                }
            }            
            if (!safe) {
                safeJump = (Integer) levels.get(i);
                return;
            }
        }        
        safeJump = depth - 1;
    }
    
    /**
     * Selects the next variable to instantiate according to a given heuristic.
     * @param csp The CSP instance to be treated.
     * @param variableOrderingHeuristic The variable ordering heuristic that will
     * be used.
    */
    protected String selectNextVariable(CSP csp, int variableOrderingHeuristic) {
        Variable variables[] = csp.getUninstantiatedVariables();
        switch (variableOrderingHeuristic) {
            case VariableOrderingHeuristics.RND:
                return VariableOrderingHeuristics.RND(variables);
            case VariableOrderingHeuristics.MRV:
                return VariableOrderingHeuristics.MRV(variables);
            case VariableOrderingHeuristics.RHO:
                return VariableOrderingHeuristics.RHO(csp);
            case VariableOrderingHeuristics.ENS:
                return VariableOrderingHeuristics.ENS(csp);
            case VariableOrderingHeuristics.K:
                return VariableOrderingHeuristics.K(csp);
            case VariableOrderingHeuristics.MXC:
                return VariableOrderingHeuristics.MXC(variables);
            case VariableOrderingHeuristics.MFD:
                return VariableOrderingHeuristics.MFD(csp);
            case VariableOrderingHeuristics.MBD:
                return VariableOrderingHeuristics.MBD(csp);
            case VariableOrderingHeuristics.FBZ:
                return VariableOrderingHeuristics.FBZ(csp);
            case VariableOrderingHeuristics.BBZ:
                return VariableOrderingHeuristics.BBZ(csp);
            case VariableOrderingHeuristics._MRV:
                return VariableOrderingHeuristics._MRV(variables);
            case VariableOrderingHeuristics._RHO:
                return VariableOrderingHeuristics._RHO(csp);
            case VariableOrderingHeuristics._ENS:
                return VariableOrderingHeuristics._ENS(csp);
            case VariableOrderingHeuristics._K:
                return VariableOrderingHeuristics._K(csp);
            case VariableOrderingHeuristics._MXC:
                return VariableOrderingHeuristics._MXC(variables);
            case VariableOrderingHeuristics._MFD:
                return VariableOrderingHeuristics._MFD(csp);
            case VariableOrderingHeuristics._MBD:
                return VariableOrderingHeuristics._MBD(csp);
            case VariableOrderingHeuristics._FBZ:
                return VariableOrderingHeuristics._FBZ(csp);
            case VariableOrderingHeuristics._BBZ:
                return VariableOrderingHeuristics._BBZ(csp);
            default:
                return VariableOrderingHeuristics.noHeuristic(variables);
        }
    }
    
    /**
     * Orders values to try according to a given heuristic.
     * @param csp The CSP instance to be treated.
     * @param valueOrderingHeuristic The value ordering heuristic that will
     * be used.
    */
    private int[] valueOrdering(Variable variable, int valueOrderingHeuristic) {
        switch (valueOrderingHeuristic) {
            case ValueOrderingHeuristics.RND:
                return ValueOrderingHeuristics.RND(variable);
            case ValueOrderingHeuristics.MNC:
                return ValueOrderingHeuristics.MNC(variable);
            case ValueOrderingHeuristics.FREQ:
                return ValueOrderingHeuristics.FREQ(variable, this);
            case ValueOrderingHeuristics.MPC:
                return ValueOrderingHeuristics.MPC(variable);
            default:
                return ValueOrderingHeuristics.noHeuristic(variable.getDomain());
        }
    }
    
    /**
     * Orders the constraints according to a given heuristic.
     * @param csp The CSP instance to be treated.
     * @param valueOrderingHeuristic The value ordering heuristic that will
     * be used.
    */
    private void constraintOrdering(CSP csp, Variable variable, int constraintOrderingHeuristic) {
        switch (constraintOrderingHeuristic) {
            case ConstraintOrderingHeuristics.RND:
                ConstraintOrderingHeuristics.RND(csp, variable);
                break;
            case ConstraintOrderingHeuristics.MXC:
                ConstraintOrderingHeuristics.MXC(csp, variable);
                break;
            case ValueOrderingHeuristics.MNC:
                ConstraintOrderingHeuristics.MNC(csp, variable);
                break;
            default:
                ConstraintOrderingHeuristics.noHeuristic(csp);
        }
    }
    
    /**
     * Transforms the support constraints into conflict constraints.
     * @param csp The CSP instance where the constraints are stored.
     * @return A CSP which contains only conflict constraints.
    */
    private static CSP convertConstraints(CSP csp) {        
        int i, j, k;
        int d1[], d2[], tuple[], tupleToRemove[];
        Variable scope[] = null;
        ArrayList tuples;
        ArrayList tuplesToRemove;
        Constraint constraint;
        for (i = 0; i < csp.getConstraints().size(); i++) {
            constraint = (Constraint) (csp.getConstraints().get(i));
            if (!constraint.isConflict()) {
                scope = constraint.getScope();
                d1 = scope[0].getDomain();
                d2 = scope[1].getDomain();
                tuples = new ArrayList(50);
                for (j = 0; j < d1.length; j++) {
                    for (k = 0; k < d2.length; k++) {
                        tuples.add(new int[]{d1[j], d2[k]});                                                
                    }
                }                
                for (j = 0; j < tuples.size(); j++) {
                    tuplesToRemove = constraint.getTuples();
                    for (k = 0; k < tuplesToRemove.size(); k++) {
                        tuple = (int[]) tuples.get(j);
                        tupleToRemove = (int[]) tuplesToRemove.get(k);                        
                        if ((tuple[0] == tupleToRemove[0] && tuple[1] == tupleToRemove[1]) || (tuple[0] == tupleToRemove[1] && tuple[1] == tupleToRemove[0]))  {
                            tuples.remove(j);
                            j = j - 1;
                            break;
                        }
                    }
                }                
                constraint = new Constraint(constraint.getId(), scope, tuples, false);
                csp.getConstraints().set(i, constraint);
            }            
        }
        return csp;
    }
    
    /**
     * Adds a prototype ArrayList to the training file for the neural network.
     * @param heuristic The heuristic associated to the input ArrayList.
    */
    private void addPrototype(CSP csp, int heuristic, String fileName) {
        int i;
        String line = "";
        for (i = 0; i < Framework.getFeatures().length; i++) {            
            line += csp.getNormalizedFeature(Framework.getFeatures()[i]) + ", ";
        }
        line += heuristic + "\r\n";        
        Files.saveToFile(line, fileName, true);
    }
    
    /** Decodes a given hyperheuristic to solve specific CSP instance.
     * @param csp The CSP instance to be solved.
     * @param hyperheuristic The hyper-heuristic to be used to solve the CSP instance.
     * @return The heuristic to be used. 
    */
    private static int getOutput(Heuristic hyperHeuristic) {        
        int variableOrderingHeuristic = hyperHeuristic.getVariableOrderingHeuristic();
        int valueOrderingHeuristic = hyperHeuristic.getValueOrderingHeuristic();
        int constraintOrderingHeuristic = hyperHeuristic.getConstraintOrderingHeuristic();
        return Framework.codeHeuristicIndex(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic);
    }
    
    /** Decodes a given hyperheuristic to solve specific CSP instance.
     * @param csp The CSP instance to be solved.
     * @param hyperheuristic The hyper-heuristic to be used to solve the CSP instance.
     * @return The heuristic to be used. 
    */
    private static int getOutput(CSP csp, BlockHyperHeuristic hyperHeuristic) {
        int i, h = -1;
        double minimal = Double.MAX_VALUE, distance;
        Block block;               
        for (i = 0; i < hyperHeuristic.size(); i++) {
            block = hyperHeuristic.get(i);
            distance = EvolutionaryFramework.distance(block, csp);            
            if (distance < minimal) {
                minimal = distance;
                h = i;   
            }
        }
        if (!localImprovement) {
            hyperHeuristic.useBlock(h);
        }
        block = hyperHeuristic.get(h);
        return block.getHeuristic();
    }        
    
    /** Decodes a given hyperheuristic to solve specific CSP instance.
     * @param csp The CSP instance to be solved.
     * @param hyperheuristic The hyper-heuristic to be used to solve the CSP instance.
     * @return The heuristic to be used. 
     */
    private static int getOutput(CSP csp, NeuralNetworkHyperHeuristic hyperHeuristic) {
        int i;        
        double input[] = new double[Framework.getFeatures().length];
        for (i = 0; i < input.length; i++) {
            input[i] = csp.getNormalizedFeature(Framework.getFeatures()[i]);
        }        
        return hyperHeuristic.getOutput(input);        
    }
    
    /** Decodes a given hyperheuristic to solve specific CSP instance.
     * @param csp The CSP instance to be solved.
     * @param hyperheuristic The hyper-heuristic to be used to solve the CSP instance.
     * @return The heuristic to be used. 
     */
    private static int getOutput(CSP csp, MatrixHyperHeuristic hyperHeuristic) {
        int decisionMatrix[][];
        int x, y, rows, columns;
        double feature1, feature2;
        Random generator = new Random();
        feature1 = csp.getNormalizedFeature(Framework.getFeatures()[0]);
        feature2 = csp.getNormalizedFeature(Framework.getFeatures()[1]);
        decisionMatrix = hyperHeuristic.getDecisionMatrix();
        rows = decisionMatrix.length - 1;
        columns = decisionMatrix[0].length - 1;
        x = (int) Math.round(feature1 * (double) rows);
        y = (int) Math.round(feature2 * (double) columns);
        if (decisionMatrix[x][y] == -1) {
            if (hyperHeuristic.defaultHeuristic != -1) {
                decisionMatrix[x][y] = hyperHeuristic.defaultHeuristic;
            } else {
                decisionMatrix[x][y] = Framework.getHeuristics()[generator.nextInt(Framework.getHeuristics().length)];
            }
        }
        return decisionMatrix[x][y];            
    }
    
    /** Decodes a given hyperheuristic to solve specific CSP instance.
     * @param csp The CSP instance to be solved.
     * @param hyperheuristic The hyper-heuristic to be used to solve the CSP instance.
     * @return The heuristic to be used. 
     */
    private static int getOutput(CSP csp, SimpleMatrixHyperHeuristic hyperHeuristic) {
        double decisionMatrix[][];
        int x, y, rows, columns;
        double feature1, feature2;
        Random generator = new Random();
        feature1 = csp.getNormalizedFeature(Framework.getFeatures()[0]);
        feature2 = csp.getNormalizedFeature(Framework.getFeatures()[1]);
        decisionMatrix = hyperHeuristic.getDecisionMatrix();
        rows = decisionMatrix.length - 1;
        columns = decisionMatrix[0].length - 1;
        x = (int) Math.round(feature1 * (double) rows);
        y = (int) Math.round(feature2 * (double) columns);
        switch(hyperHeuristic.getMode()) {            
            case SimpleMatrixHyperHeuristic.DETERMINISTIC:
                if (decisionMatrix[x][y] > 0.5) {
                    return 7;
                } else if (decisionMatrix[x][y] < 0.5) {
                    return 8;
                }
                if (generator.nextDouble() < 0.5) {
                    return 7;
                } else {
                    return 8;
                }
            case SimpleMatrixHyperHeuristic.PROBABILISTIC:
                if (generator.nextDouble() < decisionMatrix[x][y]) {
                    return 7;
                } else {
                    return 8;
                }
        }
        System.out.println("Fatal error en CSPSolver.java/getOutput.");
        System.out.println("The system will halt.");
        System.exit(1);
        return 0;
    }
    
    /** Decodes a given hyperheuristic to solve specific CSP instance.
     * @param csp The CSP instance to be solved.
     * @param hyperheuristic The hyper-heuristic to be used to solve the CSP instance.
     * @return The heuristic to be used. 
     */
    private static int getOutput(CSP csp, ClassifierSystem lcs) {
        int i;
        double features[] = new double[Framework.getFeatures().length];
        for (i = 0; i < features.length; i++) {
            features[i] = csp.getNormalizedFeature(Framework.getFeatures()[i]);
        }
        return lcs.run(features);
    }
    
    /** 
     * Decodes a given random hyperheuristic to solve specific CSP instance.
     */
    private static int getOutput() {
        Random generator = new Random();
        int index = generator.nextInt(Framework.getHeuristics().length);
        return Framework.getHeuristics()[index];
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */   
}