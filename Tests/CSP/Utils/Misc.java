package CSP.Utils;

import java.util.Random;
import java.util.StringTokenizer;
import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains various utility functions.
 * ----------------------------------------------------------------------------
*/

public abstract class Misc {

    /** Returns a three digit string representation of a given integer.
     * @param x The number to be converted.
    */
    public static String toSpecialFormat(int x) {
        if ((x / 100) >= 1) {
            return Integer.toString(x);
        }
        if ((x / 10) >= 1) {
            return "0" + Integer.toString(x);
        }
        return "00" + Integer.toString(x);
    }         
    
    /** Converts a String instance into an array of strings. A new element of the
     * array is created when a return character is found.
     * @param string The instance of String to be divided and converted into an array.
     * @return An array of String where each element is a row of the original string.
    */
    public static String[] toStringArray(String string) {
        int i = 0;
        String text;
        String rows[];
        ArrayList temp;
        string = string.trim();
        StringTokenizer tokens = new StringTokenizer(string, "\n");        
        temp = new ArrayList(tokens.countTokens());        
        while (tokens.hasMoreTokens()) {
            text = tokens.nextToken().trim();            
            if (text.charAt(0) != '%') {
                temp.add(text);
            }
        }
        rows = new String[temp.size()];
        for (i = 0; i < temp.size(); i++) {
            rows[i] = (String) temp.get(i);
        }
        return rows;
    }

    /** Counts the number of line breaks in the instance of String given as parameter. 
     * @param string The instance of String to be count.
     * @return The number of lines in the given string.
    */
    public static int count(String string) {
        int i, count = 0;
        for (i = 0; i < string.length(); i++) {
            if (string.charAt(i) == 13) {
                count++;
            }
        }
        return count;
    }
    
    /** Selects a random subset of n elements from a set of elements by using Floyd’s Algorithm.
     * @param elements An instance of ArrayList where the elements are stored.
     * @param n The number of elements in the random indexes.
     * @return An instance of ArrayList with the n elements that form the random subset.
    */
    public static ArrayList randomSample(ArrayList elements, int n) {
        int i, m, pos;
        Random generator = new Random();
        ArrayList sample = new ArrayList(n);
        m = elements.size();
        for (i = m - n; i < m; i++) {
            pos = generator.nextInt(i + 1);
            Object item = elements.get(pos);
            if (sample.contains(item)) {
                sample.add(elements.get(i));
            } else {
                sample.add(item);
            }
        }
        return sample;
    }
    
    /** Selects a random subset of n elements from a set of elements. Repetitions
     * are allowed.
     * @param elements An instance of ArrayList where the elements are stored.
     * @param n The number of elements to select.
     * @return An instance of ArrayList with at most n elements that form the random subset.
    */
    public static ArrayList randomSampleWithRepetitions(ArrayList elements, int n) {
        int i, pos;
        ArrayList indexes = new ArrayList(n);
        ArrayList sample;
        Random generator = new Random();
        for (i = 0; i < n; i++) {
            pos = generator.nextInt(elements.size());
            if (!indexes.contains(pos)) {
                indexes.add(pos);
            }
        }
        sample = new ArrayList(indexes.size());
        for (i = 0; i < indexes.size(); i++) {
            sample.add(elements.get((Integer) indexes.get(i)));
        }
        return sample;
    }

    /**
     * Sums two matrices of integers.
     * @param matrixA The first matrix to be added.
     * @param matrixB The second matrix to be added.
     * @return A matrix of integers of the same size of matrixA and matrixB.
    */
    public static int[][] matrixSum(int matrixA[][], int matrixB[][]) {
        int i, j;
        int matrix[][];
        if (matrixA.length != matrixB.length) {
            System.out.println("The matrixes must have the same size.");
            System.exit(1);
        }
        matrix = new int[matrixA.length][matrixA.length];
        for (i = 0; i < matrix.length; i++) {
            for (j = 0; j < matrix.length; j++) {
                matrix[i][j] = matrixA[i][j] + matrixB[i][j];
            }
        }
        return matrix;
    }

    /**
     * Creates a copy of an array of integers.
     * @param values The array of integers where the values will be taken from.
     * @return An array of integers with the values from the original array.
    */
    public static int[] copy(int values[]) {
        int i;
        int array[] = new int[values.length];
        for (i = 0; i < values.length; i++) {
            array[i] = values[i];
        }
        return array;
    }

    /**
     * Returns the log 2 of a given number.
     * @param value The number that we want to obtain the log 2.
     * @return The logarithm base 2 of the number.
    */
    public static double log2(double value) {
        if (value <= 0) {
            return Double.NEGATIVE_INFINITY;
        } else {
            return Math.log10(value) / Math.log10(2);
        }
    }
    
}
