
package CSP.LCS;

import CSP.Framework.Framework;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to create and handle classifiers to be used
 * by the classifier system.
 * ----------------------------------------------------------------------------
*/

public class Classifier {
    
    private double condition[];
    private int action;
    private double radius, fitness;
    
    /**
     * Creates a new instance of Classifier.
     * @param condition An array of doubles with the vector of input features.
     * @param action The index of the action to be performed (the heuristic to
     * be applied)
     * @param radius The radius of action of the classifier.
     * @param fitness The fitness of the classifier.
    */
    public Classifier(double condition[], int action, double radius, double fitness) {
        this.condition = condition;
        this.action = action;
        this.radius = radius;
        this.fitness = fitness;
    }
    
    /**
     * Creates a new instance of Classifier.
     * @param size The number of features 
    */
    public Classifier(int size) {
        int i;
        Random generator = new Random();
        condition = new double[size];
        for (i = 0; i < size; i++) {
            condition[i] = generator.nextDouble();
        }
        action = Framework.getHeuristics()[generator.nextInt(Framework.getHeuristics().length)];
        radius = generator.nextDouble() / 2;
        fitness = 0.0;
    }    
    
    /**
     * Returns the condition of the classifier.
     * @return The condition of the classifier.
    */
    public double[] getCondition() {
        int i;
        double condition[] = new double[this.condition.length];
        for (i = 0; i < condition.length; i++) {
            condition[i] = this.condition[i];
        }
        return condition;
    }
    
    /**
     * Returns the action of the classifier.
     * @return The action of the classifier.
    */
    public int getAction() {
        return action;
    }
    
    /**
     * Returns the radius of action of the classifier.
     * @return The radius of action of the classifier.
    */
    public double getRadius() {
        return radius;
    }
    
    /**
     * Sets the radius of the classifier.
     * @param radius The new radius of the classifier.
    */
    public void setRadius(double radius) {
        this.radius = radius;
    }
    
    /**
     * Returns the fitness of the classifier.
     * @return The fitness of the classifier.
    */
    public double getFitness() {
        return fitness;
    }
    
    /**
     * Sets the fitness of the classifier.
     * @param fitness The new fitness of the classifier.
    */
    public void setFitness(double fitness) {
        this.fitness = fitness;        
    }
    
    /**
     * Returns the String representation of the instance.
     * @return The String representation of the instance.
    */
    @Override
    public String toString() {
        int i;        
        DecimalFormat format = new DecimalFormat("0.0000");
        String string = "";
        for (i = 0; i < condition.length; i++) {            
            string += format.format(condition[i]) + ", ";
        }
        string += action + ", " + format.format(radius) + ", " + format.format(fitness);
        return string;
    }
    
}
