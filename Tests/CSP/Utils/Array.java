package CSP.Utils;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains some basic functions for handling arrays.
 * ----------------------------------------------------------------------------
*/

public abstract class Array {

    public static String toString(Object elements[]) {
        int i;
        String text = "";
        for (i = 0; i < elements.length; i++) {
            text += elements[i] + " "; 
        }
        return text;
    }
    
    public static String toString(int elements[]) {
        int i;
        String text = "";
        for (i = 0; i < elements.length; i++) {
            text += elements[i] + " "; 
        }
        return text;
    }
    
    public static String toString(double elements[]) {
        int i;
        String text = "";
        for (i = 0; i < elements.length; i++) {
            text += elements[i] + " "; 
        }
        return text;
    }
    
}
