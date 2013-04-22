package CSP.Utils;

import java.util.Random;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class implements the functions for creating and handling combinations
 * and permutations.
 * ----------------------------------------------------------------------------
*/

public abstract class Combinations {
    
    /**
     * Returns a random permutation of the array of integers using the Durstenfeld
     * algortihm for shuffling permutations. This algorithm is the modern version
     * of the Fisher–Yates shuffle algorithm.
     * @param elements The array of integers that will be shuffled.     
    */
    public static void nextPermutation(Object elements[]) {
        int i, j;
        Object temp;
        Random generator = new Random();
        for (i = elements.length - 1; i >= 1; i--) {
            j = generator.nextInt(i + 1);
            temp = elements[j];
            elements[j] = elements[i];
            elements[i] = temp;
        }        
    }
    
    /**
     * Returns a sequence of elements with repeteated values.
     * @param elements The array of integers that will be shuffled.
     * @param values The values that each position can take.
     * @return A sequence of elements with repeated values.
    */    
    public static Object[] nextSequence(int size, Object values[]) {
        int i, index;
        Random generator = new Random();
        Object elements[] = new Object[size];
        for (i = 0; i < elements.length; i++) {
            index = generator.nextInt(values.length);
            elements[i] = values[index];
        }
        return elements;
    }
    
    /**
     * Calculates the factorial of a given number.
     * @param n The number which we want to calculate its factorial.
     * @return The factorial of the number.
    */
    public static double factorial(double n) {
        if (n > 1) {
            return n * factorial(n - 1);
        } else {
            return 1;
        }
    }
    
}
