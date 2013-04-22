package CSP.GA;

import CSP.HyperHeuristic.BlockHyperHeuristic;
import CSP.HyperHeuristic.HyperHeuristic;
import CSP.Utils.Misc;
import java.io.File;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the basic functions to create and manipulate a population
 * for the generic genetic algorithm.
 * ----------------------------------------------------------------------------
*/

public class Population {
    
    private Individual individuals[];
    
    public static String populationsPath = "Data\\Runs\\";
    
    /** Creates a new instance of Population. The size of the population is defined
     * by the user through the value of size given as parameter.
     * @param size Size of the population.
     * @param type The type of the individual that must be used by the genetic algorithm.
    */
    public Population(int size, int type) {
        int i;
        individuals = new Individual[size];
        for (i = 0; i < individuals.length; i++) {
            switch(type) {
                case HyperHeuristic.BLOCK:
                    individuals[i] = new BlockIndividual();
                    break;
                default:
                    System.out.println("The type of individual is not defined in the system.");
                    System.out.println("The system will halt.");
                    System.exit(1);
            }            
        }
    }
    
    public Population(Individual individuals[], int type) {
        int i;
        this.individuals = new Individual[individuals.length];
        for (i = 0; i < individuals.length; i++) {
            switch(type) {
                case HyperHeuristic.BLOCK:
                    this.individuals[i] = new BlockIndividual((BlockIndividual) individuals[i]);
                    break;
                default:
                    System.out.println("The type of individual is not defined in the system.");
                    System.out.println("The system will halt.");
                    System.exit(1);
            }            
        }
    }
    
    /** Returns a reference to the individual at the position specified by a given index. 
     * @param index The index of the individual in the population.
     * @return A reference to the individual at the position specified by the given
     * index.
    */
    public Individual get(int index) {
        return individuals[index];
    }
    
    /** Sets the individual at the position specified by a given index.
     * @param index The position of the population where the individual will be 
     * set.
     * @param individual that will be set at the given position.
    */
    public void set(int index, Individual individual) {
        individuals[index] = individual;
    }
    
    /** Returns the size of the population.
     * @return The size of the population; it is, the number of individuals in 
     * the population.
    */
    public int size() {
        return individuals.length;
    }
    
    /** Sorts the population in decreasing order. The ordering is made through
     * bubblesort.
    */
    public void sort() {
        int i, j;
        Individual individualA, individualB;
        for (i = 0; i < size(); i++) {
            for (j = 0; j < size() - 1; j++) {
                individualA = get(j);                
                individualB = get(j + 1);
                if (individualB.getAptitude() > individualA.getAptitude()) {
                    set(j, individualB);
                    set(j + 1, individualA);                    
                }                
            }
        }
    }
    
    /** Creates a new instance of Population using data from a text file.
     * @param populationName The name of the population where the individuals 
     * will be loaded from.
     * @param type The type h hyper-heuristics in the population.
    */    
    public static Population loadFromFile(String populationName, int type) {
        int i;
        String text;
        String rows[];
        Population population = null;        
        text = CSP.Utils.Files.loadFromFile(populationsPath + populationName + "\\" + populationName + ".txt"); 
        rows = CSP.Utils.Misc.toStringArray(text);
        population = new Population(rows.length, type);
        for (i = 0; i < rows.length; i++) {
            population.set(i, readNextIndividual(populationName, rows, i, type));
        }        
        return population;
    }    
    
    /** Saves the Population to a text file.
     * @param populationName The name of the population where the individuals 
     * will be saved from.
    */      
    public void saveToFile(String populationName) {
        int i;        
        String string, fileName;
        GregorianCalendar day = new GregorianCalendar();
        File f, folder; 
        f = new File(populationsPath + "/" + populationName);
        if (f.exists() == false) {
            f.mkdirs();
        }        
        
        // If the folder for this particular population already exists, we delete
        // its contents
        folder = new File(populationsPath + populationName);
        File[] listOfFiles = folder.listFiles();        

        for (i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                listOfFiles[i].delete();
            }
        }        
        
        // We create the header of the file of results                
        string = "% This file was created on " + day.getTime().toString() + "\r\n";
        string += "% NAME, REVISIONS, AGE, APTITUDE\r\n";
        fileName = populationsPath + populationName + "\\" + populationName + ".txt";
        CSP.Utils.Files.saveToFile(string, fileName, false);        
        
        // Each individual in the population is saved
        for(i = 0; i < this.size(); i++) {     
            this.get(i).saveToFile(populationsPath + populationName + "\\" + Misc.toSpecialFormat(i) + ".txt");            
            string = Misc.toSpecialFormat(i) + ", " + Long.toString(this.get(i).getRevisions()) + ", " + this.get(i).getAge() + ", " + Double.toString(this.get(i).getAptitude()) + "\r\n";            
            
            // Updating the results file
            CSP.Utils.Files.saveToFile(string, fileName, true);                        
        }    
    }
    
    @Override
    /**
     * Returns the string representation of the instance.
     * @return The string representation of the instance.
    */
    public String toString() {
        int i;
        String text = "";
        for (i = 0; i < this.size(); i++) {
            text += individuals[i].toString() + "\r\n";
        }
        return text;
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS 
    ------------------------------------------------------------------------- */        
    
    /** Reads the next individual to be added to the population.
     * @param populationName The name of the population.
     * @param rows The content of the file divided into an array of strings.
     * @param i The index of the individual in the file.
    */    
    private static Individual readNextIndividual(String populationName, String rows[], int i, int type) {        
        Individual individual = null;
        StringTokenizer tokens;
        String token;
        String fileName = populationsPath + populationName + "\\";        
        tokens = new StringTokenizer(rows[i], ", ");
                
        token = tokens.nextToken();
        token = token.trim();
        fileName += token + ".txt";                
        switch(type) {
            case HyperHeuristic.BLOCK:
                individual = new BlockIndividual();
                individual.setHyperHeuristic(BlockHyperHeuristic.loadFromFile(fileName));
                break;
            default:
                System.out.println("The type of individual is not defined in the system.");
                System.out.println("The system will halt.");
                System.exit(1);
        }                        
        token = tokens.nextToken();
        token = token.trim();
        individual.setRevisions(Integer.parseInt(token));
        token = tokens.nextToken();
        token = token.trim();
        individual.setAge(Integer.parseInt(token));
        token = tokens.nextToken();
        token = token.trim();
        individual.setAptitude(Double.parseDouble(token));
        return individual;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS 
    ------------------------------------------------------------------------- */    
    
}
