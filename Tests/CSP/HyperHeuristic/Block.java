package CSP.HyperHeuristic;

import CSP.Framework.EvolutionaryFramework;
import java.util.*;

/** 
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class defines a block which is the basic structure used to represent the 
 * state of the problem and implement the mutation and crossover operators in the
 * process of the Genetic Algorithm.
 * 
 * A block can be seen as:
 * 
 *  _  _ _  _ _  _ _ _ _ _  _ _ _
 * |_A0_|_A1_|_A2_|_..._|_An_|_H_|
 *  
 * Where A0...An are double values in the rank [0, 1]. Each one of these values 
 * represents a normalized value of a particular characteristic of the problem 
 * state. The value for H is an integer which represents the index of the ordering
 * heuristics associated to the coded state. This index maps an integer to a 
 * combination of variable and value ordering heuristics.
 * 
 * ----------------------------------------------------------------------------
*/

public class Block {        
    
    private double values[];    
    private int heuristic;        
    
    /** Creates a new random instance of Block */
    public Block() {
        int i, hIndex;        
        Random generator = new Random();
        values = new double[EvolutionaryFramework.getFeatures().length];
        for (i = 0; i < values.length; i++) {            
            set(i, generator.nextDouble());            
        }        
        hIndex = generator.nextInt(EvolutionaryFramework.getHeuristics().length);
        setHeuristic(EvolutionaryFramework.getHeuristics()[hIndex]);
    }
    
    /** Creates a new instance of Block from a array of doubles and an ordering
     * heuristic index.
     * @param values An array of doubles wich represents the state of the problem.
     * @param heuristic The index of the ordering heuristic associated to the state.
    */
    public Block(double values[], int heuristic) {
        int i;
        this.values = new double[EvolutionaryFramework.getFeatures().length];
        for (i = 0; i < values.length; i++) {
            set(i, values[i]);
        }
        this.heuristic = heuristic;
    }
    
    /**
     * Creates a new instance of Block from an existing instance.
     * @param block The block to be copied.
    */
    public Block(Block block) {
        int i;
        values = new double[block.size()];
        for (i = 0; i < block.size(); i++) {
            values[i] = block.get(i);
        }
        heuristic = block.getHeuristic();
    }
    
    /** Creates a new instance of Block from a given string.
     * @param text The string that contains the data to create the new instance.
    */
    public Block(String text) {
        int i, heuristic = -1;
        double values[] = new double[EvolutionaryFramework.getFeatures().length];
        String token;
        StringTokenizer tokens;        
        tokens = new StringTokenizer(text, ", ");
        i = 0;
        while (tokens.hasMoreTokens()) {
            if (i == EvolutionaryFramework.getFeatures().length) {
                token = tokens.nextToken();
                token = token.trim();
                heuristic = Integer.parseInt(token);
            } else {
                token = tokens.nextToken();
                token = token.trim();
                values[i] = Double.parseDouble(token);
                i++;
            }
        }
        this.values = values;
        this.heuristic = heuristic;
    }
    
    /**
     * Returns the size of the block (without the heuristic).
     * @return The size of the block (without the heuristic).
    */
    public int size() {
        return values.length;
    }
    
    /** Sets the value of the block at the position specified by the value of
     * an index. This represents an specific characteristic of the state.
     * @param index The index of the characteristic of the state.      
    */    
    public void set(int index, double value) {
        if (value < 0) {
            value = 0;
        }
        if (value > 1) {
            value = 1;
        }
        values[index] = value;        
    }
    
    /** Returns the value of the block at the position specified by the value of
     * an index. This represents an specific characteristic of the state.
     * @param index The index of the characteristic of the state. 
     * @return The value of the block at the position specified by the value of
     * an index.
    */
    public double get(int index) {
        return values[index];
    }    
    
    /** Sets the ordering heuristic index associated to the block.
     * @param heuristic Index of the ordering heuristic.
    */ 
    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }    
    
    /** Returns the ordering heuristic index associated to the state. 
     * @return The ordering heuristic index associated to the state. 
    */    
    public int getHeuristic() {
        return heuristic;
    }       
    
    @Override
    /** Returns the string representation of the instance of Block. 
     * @return The string representation of the instance of Block.
    */
    public String toString() {
        int i;
        String text = "";
        for (i = 0; i < values.length; i++) {
            text = text + CSP.Utils.StatisticalTools.round(get(i)) + ", ";
        }
        text = text + heuristic;
        return text;
    }
    
    
}

    