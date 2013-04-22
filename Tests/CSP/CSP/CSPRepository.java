package CSP.CSP;

import CSP.Framework.EvolutionaryFramework;
import CSP.Framework.Framework;
import CSP.HyperHeuristic.*;
import CSP.LCS.ClassifierSystem;
import CSP.Utils.Files;
import CSP.Utils.Misc;
import CSP.Utils.StatisticalTools;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.StringTokenizer;

/** 
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements a repository of Constraint Satisfaction Problems. 
 * ----------------------------------------------------------------------------
*/
public class CSPRepository {

    public static String repositoriesPath = "Data\\Repositories\\";
    private ArrayList<CSP> csps = new ArrayList<CSP>(500);
    private ArrayList<Long> bestResults = new ArrayList<Long>(500);
    private ArrayList<Long> worstResults = new ArrayList<Long>(500);
    private ArrayList<Long> averageResults = new ArrayList<Long>(500);
    private String name;       
    
    /** 
     * Creates a new instance of CSPRepository. 
    */
    public CSPRepository() {  
        csps = new ArrayList<CSP>(50);
        bestResults = new ArrayList<Long>(50);
        worstResults = new ArrayList<Long>(50);
        averageResults = new ArrayList<Long>(50);        
        name = "";
    }

    /** 
     * Adds a set of random CSP instances to the repository.
     * @param n The number of variables in each problem.
     * @param m The uniform domain size of the variables.
     * @param p1 The probability that a constraint between two variables occurs.
     * @param p2 The probability that a conflict exists over a constraint.
     * @param randomModel The random model that will be used to generate the instances.
     * @param instances the number of instances for to be added.      
    */
    public void add(int n, int m, double p1, double p2, int randomModel, int instances) {
        int i;
        for (i = 0; i < instances; i++) {
            csps.add(new CSP(n, m, p1, p2, randomModel));
            bestResults.add((long) 0);
            worstResults.add((long) 0);
            averageResults.add((long) 0);
        }
    }    
    
    /** 
     * Adds a set of random CSP instances to the repository.
     * @param n The number of variables in each problem.
     * @param m The uniform domain size of the variables.
     * @param sat Determines if the instances are satisfiable or unsatisfiable.
     * @param randomModel The random model that will be used to generate the instances.
     * @param instances the number of instances for to be added.      
    */
    public void add(int n, int m, boolean sat, int randomModel, int instances) {        
        double p1, p2;
        Random generator;
        CSP csp;
        CSPSolver solver;
        generator = new Random();       
        while (instances > 0) {
            p1 = generator.nextDouble();
            p2 = generator.nextDouble();
            csp = new CSP(n, m, p1, p2, randomModel);
            solver = new CSPSolver(csp);
            solver.solve(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE, false);
            System.out.print("\t=> Created with (p1 = " + p1 + ", p2 = " + p2 + "), solutions = " + solver.getNumberOfSolutions() + ": ");
            if ((solver.getNumberOfSolutions() > 0 && sat) || (solver.getNumberOfSolutions() == 0 && !sat)){
                csps.add(csp);
                bestResults.add((long) 0);
                worstResults.add((long) 0);
                averageResults.add((long) 0);
                instances--;
                System.out.println("accepted.");
            } else {
                System.out.println("rejected.");
            }
            
        }
    }
    
    /** 
     * Adds a set of random CSP instances to the repository.
     * @param n The number of variables in each problem.
     * @param m The uniform domain size of the variables.     
     * @param randomModel The random model that will be used to generate the instances.
     * @param instances the number of instances for to be added.      
    */
    public void add(int n, int m, int randomModel, int instances) {        
        int i;
        double p1, p2;
        Random generator;
        CSP csp;        
        generator = new Random();       
        for (i = 0; i < instances; i++) {
            p1 = generator.nextDouble();
            p2 = generator.nextDouble();
            csp = new CSP(n, m, p1, p2, randomModel);
            csps.add(csp);
            bestResults.add((long) 0);
            worstResults.add((long) 0);
            averageResults.add((long) 0);
        }
    }
    
    /** 
     * Adds a set of hard random CSP instances to the repository.
     * @param n The number of variables in each problem.
     * @param m The uniform domain size of the variables.
     * @param hardness Determines the maximum distance in the value of k to 1.0,
     * which corresponds to really hard instances.
     * @param randomModel The random model that will be used to generate the instances.
     * @param instances the number of instances for to be added.      
    */
    public void add(int n, int m, double hardness, int randomModel, int instances) {        
        double p1, p2, k;
        Random generator;
        CSP csp;        
        generator = new Random();       
        while (instances > 0) {
            p1 = generator.nextDouble();
            p2 = generator.nextDouble();
            csp = new CSP(n, m, p1, p2, randomModel);
            k = csp.getKappa();
            System.out.print("\t=> Created with (p1 = " + p1 + ", p2 = " + p2 + "), k = " + k + ": ");
            if (Math.abs(1.0 - k) <= hardness) {                
                csps.add(csp);
                bestResults.add((long) 0);
                worstResults.add((long) 0);
                averageResults.add((long) 0);
                instances--;
                System.out.println("accepted.");
            } else {
                System.out.println("rejected.");
            }            
        }
    }
    
    /** 
     * Adds the instances the repository needed to create a constraint plot. The 
     * instances are generated with model B or F.
     * @param n The number of variables in each problem.
     * @param m The uniform domain size of the variables.
     * @param p1 The constraint density of the instances.
     * @param deltaP2 The change in the constraint tightness.
     * @param instancesPerPoint The number of instances per point.
    */
    public void createConstraintPlot(int n, int m, double p1, double deltaP2, int randomModel, int instancesPerPoint) {
        int j;
        double i;        
        for (i = 0; i <= 1.0 + 0.0001; i += deltaP2) {
            i = (double) Math.round(i * 10000) / 10000;            
            for (j = 0; j < instancesPerPoint; j++) {
                add(new CSP(n, m, p1, i, randomModel));
            }
        }        
    }
    
    /** 
     * Adds the instances the repository needed to create a constraint plot. The 
     * instances are generated with model E.
     * @param n The number of variables in each problem.
     * @param m The uniform domain size of the variables.
     * @param deltaP The change in the proportion of conflicts within the instance.
     * @param instancesPerPoint The number of instances per point.
     */
    public void createConstraintPlot(int n, int m, double deltaP, int instancesPerPoint) {
        int j;
        double i;
        for (i = 0; i <= 1.0 + 0.0001; i += deltaP) {
            i = (double) Math.round(i * 10000) / 10000;            
            for (j = 0; j < instancesPerPoint; j++) {
                add(new CSP(n, m, i));
            }
        }
    }
    
    /** 
     * Adds a CSP instance to the repository.
     * @param csp The CSP instance to be added to the repository.
     */
    public void add(CSP csp) {        
        csps.add(new CSP(csp));
        bestResults.add((long) 0);
        worstResults.add((long) 0);
        averageResults.add((long) 0);
    }

    /**
     * Adds all the instances contained in a given folder.
     * @param folderName the name of the folder where the instances are contained.
    */
    public void addAllInFolder(String folderName) {
        int i;
        String fileNames[] = Files.listAllFilesInFolder(folderName);
        for (i = 0; i < fileNames.length; i++) {
            this.add(CSP.loadFromFile(folderName + "\\" + fileNames[i])); 
        }
    }
    
    /*
     * Sets the name of the repository.
    */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * Returns the name of the repository.
     * @return The name of the repository.
    */
    public String getName() {
        return name;
    }
    
    /**
     * Adds a grid to the repository. The instances are generated with model B or F.
     * @param n The number of variables in each instance.
     * @param m The uniform domain size for each variable.
     * @param instances The number of instances per point in the grid.
    */
    public void addGrid(int n, int m, double deltaP1, double deltaP2, int randomModel, int instances) {
        int k;
        double i, j;
        i = 0.0 + deltaP1;
        for (; i <= 1.0 + 0.0001; i += deltaP1) {
            i = (double) Math.round(i * 10000) / 10000;
            j = 0.0 + deltaP2;
            for (; j <= 1.0 + 0.0001; j += deltaP2) {
                j = (double) Math.round(j * 10000) / 10000;
                for (k = 0; k < instances; k++) {                    
                    add(new CSP(n, m, i, j, randomModel));
                }
            }
        }
    }
    
    /** 
     * Deletes all the instances in the repository.
    */
    public void clear() {
        csps.clear();
    }

    /** 
     * Removes a given instance from the repository.
     * @param index The index of the instance to be removed from the repository.
     */
    public void remove(int index) {
        csps.remove(index);
    }

    /** 
     * Returns the size of the repository. This is the number of CSP instances
     * in the repository.
     * @return The size of the repository.
    */
    public int size() {
        return csps.size();
    }

    /** 
     * Returns a reference to the CSP instance at a given position.
     * @param index The position of the instance we want to get.
     * @return A reference to the CSP instance at a given position.
    */
    public CSP get(int index) {
        return csps.get(index);
    }   
    
    /** 
     * Gets the best result obtained with the low-level heuristics for a given 
     * instance.
     * @param index the index of the instance in the repository associated to
     * the best result.
     * @return The best result for the instance.     
    */
    public long getBestResult(int index) {
        return bestResults.get(index);
    }
    
    /** Gets the best result obtained with the low-level heuristics for a given 
     * instance.
     * @param index the index of the instance in the repository associated to
     * the best result.
     * @return The best result for the instance.     
    */
    public long getWorstResult(int index) {
        return worstResults.get(index);
    }   
    
    /** 
     * Gets the best result obtained with the low-level heuristics for a given 
     * instance.
     * @param index the index of the instance in the repository associated to
     * the best result.
     * @return The best result for the instance.     
    */
    public long getAverageResult(int index) {
        return averageResults.get(index);
    }   

    /** 
     * Creates a new instance of CSPRepository using data from a given folder.
     * @param repositoryName Name of the folder where the CSP instances are to be
     * loaded from.
     * @return A new instance of CSPRepository.
    */
    public static CSPRepository loadFromFile(String repositoryName) {
        int i, size, chars_read = 0;
        File f;
        FileReader fr;
        String rows[];
        CSPRepository repository = new CSPRepository();
        f = new File(repositoriesPath + repositoryName + "\\" + repositoryName + ".txt");
        repository.setName(repositoryName);
        size = (int) f.length();
        try {
            char[] data = new char[size];
            fr = new FileReader(f);
            while (fr.ready()) {
                chars_read += fr.read(data, chars_read, size - chars_read);
            }
            rows = Misc.toStringArray(new String(data, 0, chars_read));
            for (i = 0; i < rows.length; i++) {                
                readNextCSP(repository, repositoryName, rows, i);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while attempting to load the repository \'" + repositoryName + "\'.");
            System.out.println("Exception text: " + e.toString());
            System.exit(1);
        }
        return repository;
    }

    /** 
     * Saves the repository.
     * @param repositoryName Name of the repository where the CSP instances will 
     * be saved.
    */
    public void saveToFile(String repositoryName) {
        int i, j, variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic;
        int orderingHeuristics[];        
        CSPSolver solver;
        String results;
        File f, folder;
        // If the folder for this particular repository already exists, we delete
        // its contents
        folder = new File(repositoriesPath + repositoryName);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (i = 0; i < listOfFiles.length; i++) {
                if (listOfFiles[i].isFile()) {
                    listOfFiles[i].delete();
                }
            }
        } else {
            folder.mkdir();
        }
        // We create the header of the file of results
        try {
            f = new File(repositoriesPath + repositoryName + "\\" + repositoryName + ".txt");
            FileWriter fw = new FileWriter(f);
            orderingHeuristics = EvolutionaryFramework.getHeuristics();
            fw.write("% ID");
            for (j = 0; j < orderingHeuristics.length; j++) {
                variableOrderingHeuristic = Framework.decodeHeuristicIndex(orderingHeuristics[j])[0];
                valueOrderingHeuristic = Framework.decodeHeuristicIndex(orderingHeuristics[j])[1];
                fw.write(", " + VariableOrderingHeuristics.heuristicToString(variableOrderingHeuristic) + " & " + ValueOrderingHeuristics.heuristicToString(valueOrderingHeuristic));
            }
            fw.write("\r\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("An error occurred while attempting to save the file \'" + repositoryName + "\'.");
            System.out.println("Exception text: " + e.toString());
            System.exit(1);
        }
        orderingHeuristics = Framework.getHeuristics();
        // Each problem in the repository is saved
        for (i = 0; i < this.size(); i++) {
            this.get(i).saveToFile(repositoriesPath + repositoryName + "\\" + Misc.toSpecialFormat(i) + ".txt");
            // We solve every instance by using all the possible combinations of 
            // low-level heuristics
            results = "";
            for (j = 0; j < orderingHeuristics.length; j++) {
                solver = new CSPSolver(this.get(i));
                //results += Utils.Misc.toSpecialFormat(get(i).getConstraintDensity()) + ", " + Utils.Misc.toSpecialFormat(get(i).getConstraintTightness());
                variableOrderingHeuristic = EvolutionaryFramework.decodeHeuristicIndex(orderingHeuristics[j])[0];
                valueOrderingHeuristic = EvolutionaryFramework.decodeHeuristicIndex(orderingHeuristics[j])[1];
                constraintOrderingHeuristic = EvolutionaryFramework.decodeHeuristicIndex(orderingHeuristics[j])[2];
                solver.solve(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic, false);
                results += ", " + Long.toString(solver.getConstraintChecks());
            }
            results = Misc.toSpecialFormat(i) + results + "\r\n";
            // Updating the results file         
            try {
                f = new File(repositoriesPath + repositoryName + "\\" + repositoryName + ".txt");
                FileWriter fw = new FileWriter(f, true);
                fw.write(results);
                fw.close();
            } catch (IOException e) {
                System.out.println("An error occurred while attempting to save the file \'" + repositoryName + "\'.");
                System.out.println("Exception text: " + e.toString());
                System.exit(1);
            }
        }
    }

    /**
     * Compares the best result of each instance in the repository
     * against the result of the simple heuristics.
    */
    public void solve(int variableOrderingHeuristic, int valueOrderingHeuristic, int constraintOrderingHeuristic, String fileName) {
        solve(new HyperHeuristic[]{new Heuristic(variableOrderingHeuristic, valueOrderingHeuristic, constraintOrderingHeuristic)}, fileName);
    }

    /**
     * Compares the best result of each instance in the repository
     * against the result of a given hyper-heuristic.
    */
    public void solve(HyperHeuristic hyperHeuristics[], String fileName) {
        int i, j, sat = 0, unsat = 0;
        long startTime, endTime;
        double time;        
        GregorianCalendar day = new GregorianCalendar();
        String lines[] = new String[csps.size() + 4];
        HyperHeuristic hyperHeuristic;
        CSP csp;
        CSPSolver solver;
        Variable solution[] = null;
        startTime = System.currentTimeMillis();
        lines[0] = "% This file was created on " + day.getTime().toString();
        lines[1] = "% ID, BEST, WORST, AVG, HYPER-HEURISTICS";
        System.out.println(lines[1]);
        for (i = 0; i < csps.size(); i++) {
            csp = (CSP) csps.get(i);
            solver = new CSPSolver(csp);
            lines[i + 2] = i + ", " + (Long) bestResults.get(i) + ", " + (Long) worstResults.get(i) + ", " + (Long) averageResults.get(i);
            
            // Begin - Experimental
            /*
            for (j = 0; j < hyperHeuristics.length; j++) {
                hyperHeuristic = hyperHeuristics[j];
                solution = solver.solve((Heuristic) hyperHeuristic, true);
                lines[i + 2] += ", " + solver.getConstraintChecks();
                solver = new CSPSolver(csp);
                solution = solver.solve((Heuristic) hyperHeuristic, false);
                lines[i + 2] += ", " + solver.getConstraintChecks();
            }
            */
            // End - Experimental
            
            
            for (j = 0; j < hyperHeuristics.length; j++) {
                hyperHeuristic = hyperHeuristics[j];
                switch (hyperHeuristic.getType()) {
                    case HyperHeuristic.LOWLEVEL:
                        solution = solver.solve((Heuristic) hyperHeuristic);
                        break;
                    case HyperHeuristic.BLOCK:
                        solution = solver.solve((BlockHyperHeuristic) hyperHeuristic);
                        break;
                    case HyperHeuristic.NEURAL:
                        solution = solver.solve((NeuralNetworkHyperHeuristic) hyperHeuristic);
                        break;
                    case HyperHeuristic.MATRIX:
                        solution = solver.solve((MatrixHyperHeuristic) hyperHeuristic);
                        break;
                    case HyperHeuristic.SIMPLEMATRIX:
                        solution = solver.solve((SimpleMatrixHyperHeuristic) hyperHeuristic);
                        break;
                    case HyperHeuristic.LCS:
                        solution = solver.solve((ClassifierSystem) hyperHeuristic);
                        break;
                }
                if (solution != null) {
                    sat++;
                } else {
                    unsat++;
                }
                lines[i + 2] += ", " + solver.getConstraintChecks();
            }            
            System.out.println(lines[i + 2]);
        }        
        endTime = System.currentTimeMillis();
        time = (double) (endTime - startTime) / 1000;
        lines[i + 2] = "% SAT: " + sat/hyperHeuristics.length + " / UNSAT: " + unsat/hyperHeuristics.length;
        lines[i + 3] = "% Time required to solve the repository \'" + getName() + "\': " + time + " seconds.";
        System.out.println(lines[i + 2]);
        System.out.println(lines[i + 3]);   
        Files.saveToFile(lines, fileName);
    }       
        
    /**
     * Returns some basic statistics about the instances within the repository.
     * @return Some basic statistics about the instances within the repository.
    */
    public void getStatistics() {
        int i;
        CSP csp;
        DecimalFormat format = new DecimalFormat("0.000");
        System.out.println("%ID\tn\tm\tp1\tp2\tk\trho\tE[N]");
        for (i = 0; i < this.size(); i++) {
            csp = this.get(i);
            System.out.println(i + "\t" + csp.getUninstantiatedVariables().length + "\t" + format.format(csp.getDomainAverage()) + "\t" + format.format(csp.getConstraintDensity()) + "\t" + format.format(csp.getConstraintTightness()) + "\t" + format.format(csp.getKappa()) + "\t" + format.format(csp.getSolutionDensity()) + "\t" + format.format(csp.getExpectedNumberOfSolutions()));
        }
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /** 
     * Reads the next CSP instance to be added to the repository.
     * @param repositoryName The name og the repository.
     * @param rows The content of the file divided into an array of strings.
     * @param i The index of the CSP instance in the file.
    */
    private static void readNextCSP(CSPRepository repository, String repositoryName, String rows[], int i) {
        int k = 0;
        double checks[];
        boolean start = true;
        CSP csp;
        StringTokenizer tokens;
        String temp, fileName = repositoriesPath + repositoryName + "\\";        
        tokens = new StringTokenizer(rows[i], ",");
        checks = new double[tokens.countTokens() - 1];
        while (tokens.hasMoreTokens()) {
            if (start) {
                // We read the name of the file
                fileName += tokens.nextToken().trim() + ".txt";
                start = false;
            } else {
                temp = tokens.nextToken().trim();
                if (!temp.equalsIgnoreCase("")) {                    
                    checks[k++] = Long.parseLong(temp);
                }
            }
        }        
        csp = CSP.loadFromFile(fileName);
        System.out.println("File \'" + fileName + "\' has been loaded.");
        repository.add(csp);
        repository.setBestResult(i, (long) StatisticalTools.min(checks));
        repository.setWorstResult(i, (long) StatisticalTools.max(checks));
        repository.setAverageResult(i, (long) StatisticalTools.mean(checks));
    }
    
    /**
     * Sets the best result obtained with the low-level heuristics for a given 
     * instance.
     * @param index the index of the instance in the repository associated to
     * the best result.
     * @param bestResult The best result obtained for the instance.
    */
    private void setBestResult(int index, long bestResult) {
        bestResults.set(index, bestResult);
    }

    /** 
     * Sets the worst result obtained with the low-level heuristics for a given 
     * instance.
     * @param index the index of the instance in the repository associated to
     * the worst result.
     * @param worstResult The worst result obtained for the instance.
    */
    private void setWorstResult(int index, long worstResult) {
        worstResults.set(index, worstResult);
    }
    
    /**
     * Sets the average result obtained with the low-level heuristics for a given 
     * instance.
     * @param index the index of the instance in the repository associated to
     * the best result.
     * @param averageResult The average result obtained for the instance.
    */
    private void setAverageResult(int index, long averageResult) {
        averageResults.set(index, averageResult);
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
}
