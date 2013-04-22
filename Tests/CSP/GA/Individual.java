package CSP.GA;

import CSP.HyperHeuristic.BlockHyperHeuristic;
import CSP.HyperHeuristic.HyperHeuristic;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class presents the basic description of an individual used to run a
 * generic genetic algorithm.
 * ----------------------------------------------------------------------------
*/

public class Individual {

    protected double aptitude;
    protected int age, revisions;
    protected HyperHeuristic hyperHeuristic;
    private boolean evaluated;
    
    /** Creates a new instance of Individual. */
    public Individual() {
        setAptitude(0);
        setRevisions(0);
        setAge(0); 
        evaluated = false;
        hyperHeuristic = null;
    }
    
    /** 
     * Creates a new instance of Individual from an existing instance.
     * @param individual The instance where the data will be copied from. 
    */
    public Individual(Individual individual) {
        /*
        setAptitude(individual.getAptitude());
        setRevisions(individual.getRevisions());
        setAge(individual.getAge());
        */
        setAptitude(0);
        setRevisions(0);
        setAge(1);
        evaluated = false;
        hyperHeuristic = individual.getHyperHeuristic();
    }
    
    /** Creates a new instance of Individual. */
    public Individual(HyperHeuristic hyperHeuristic) {
        setAptitude(0);
        setRevisions(0);
        setAge(0); 
        evaluated = false;
        this.hyperHeuristic = hyperHeuristic;
    }        
    
    /** Returns the aptitude of the individual.
     * @return The aptitude of the individual.
    */
    public double getAptitude() {
        return aptitude;
    }
    
    /** Returns the number of revisions that have been done on the individual.
     * @return The number of revisions that have been done on the individual.
    */
    public int getRevisions() {
        return revisions;
    }
    
    /** Returns the age of the individual.
     * @return the age of the individual.
    */
    public int getAge() {
        return age;
    }
    
    /** Returns the hyper-heuristic coded within the individual.
     * @return A reference to the hyper-heuristic coded within the individual.
    */
    public HyperHeuristic getHyperHeuristic() {
        return hyperHeuristic;
    }
    
    /**
     * Returns the evaluated state of the individual.
     * @return True if the individual has been previously evaluated, false otherwise.
    */
    public boolean isEvaluated() {
        return evaluated;
    }
    
    /** Sets the aptitude of the individual.
     * @param aptitude Value of the new aptitude of the individual.
    */
    public final void setAptitude(double aptitude) {
        if (aptitude >= 0) {
            if (Double.isInfinite(aptitude)) {
                this.aptitude = Double.MAX_VALUE;
            } else {
                this.aptitude = aptitude;
            }
        } else {
            System.out.println("Trying to assign a negative aptitude to the individual.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
    }
    
    /** Sets the number of revisions that have been done on the individual.
     * @param revisions Number of revisions that have been done on the individual.
    */
    public final void setRevisions(int revisions) {
        if (revisions >= 0) {
            this.revisions = revisions;
        } else {
            System.out.println("Trying to assign a negative number of revisions to the individual.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
    }        
     
    /** Sets the age of the individual.
     * @param age The age of the individual.
    */
    public final void setAge(int age) {
        if (age >= 0) {
            this.age = age;
        } else {
            System.out.println("Trying to assign a negative age to the individual.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
    }
    
    /** Sets the hyper-heuristic coded within the individual.
     * @param hyperHeuristic A reference to the hyper-heuristic that will be associated to the individual.
    */
    public final void setHyperHeuristic(HyperHeuristic hyperHeuristic) {
        this.hyperHeuristic = hyperHeuristic;
    }        
    
    /**
     * Sets the evaluated state of the individual.
     * @param evaluated A boolean value with the state of the individual.
    */
    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }
    
    /**
     * Saves the hyper-heuristic associated to the individual to a text file.
     * @param fileName The name of the file where the hyper-heuristic will be stored.
    */
    public void saveToFile(String fileName) {
        switch(hyperHeuristic.getType()) {
            case HyperHeuristic.BLOCK:
                ((BlockHyperHeuristic) hyperHeuristic).saveToFile(fileName);
                break;
            default:
                System.out.println("The type of individual is not defined in the system.");
                System.out.println("The system will halt.");
                System.exit(1);
        }
    }
    
    @Override
    /**
     * Returns the string representation of the instance.
     * @return The string representation of the instance.
    */
    public String toString() {
        String text = revisions + ", " + age + ", " + aptitude;
        return text;
    }
        
}
