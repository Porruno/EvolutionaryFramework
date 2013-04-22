package CSP.Utils;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains some statistical functions.
 * ----------------------------------------------------------------------------
*/

public abstract class StatisticalTools {
         
    /**
     * Calculates the mean of a given set of values
     * @param values An array of double with the values to analyse
    */
    public static double mean(double values[]) {
        int i;        
        double mean = 0;
        for (i = 0; i < values.length; i++) {
            mean += values[i];
        }
        return mean / values.length;
    }
    
    /**
     * Calculates the standard deviation of a given set of values
     * @param values An array of double with the values to analyse
    */
    public static double stdev(double values[]) {
        int i;
        double mean, stdev = 0;
        mean = mean(values);        
        for (i = 0; i < values.length; i++) {
            stdev += Math.pow((values[i] - mean), 2);
        }
        if (values.length > 1) {
            return Math.sqrt(stdev / (values.length - 1));
        } else {
            return 0;
        }
    }
    
    /**
     * Obtains the median of a given set of values
     * @param values An array of double with the values to analyse
    */
    public static double median(double values[]) {        
        double median = -1;
        double orderedvalues[] = sort(values);
        if (orderedvalues.length % 2 == 1) {
            median = orderedvalues[(int)(orderedvalues.length / 2)];            
        } else {
            median = (orderedvalues[(int)(orderedvalues.length / 2) - 1] + orderedvalues[(int)(orderedvalues.length / 2)]) / 2;
        }
        return median;
    }
    
    /**
     * Calculates the lower quartile of a given set of values
     * @param values An array of double with the values to analyse
    */    
    public static double lowerQuartile(double values[]) {        
        double subSet[] = lowerSubSet(values);
        return median(subSet);        
    }
    
    /**
     * Calculates the upper quartile of a given set of values
     * @param values An array of double with the values to analyse
    */    
    public static double upperQuartile(double values[]) {
        double subSet[] = upperSubSet(values);
        return median(subSet);
    }

    /**
     * Sorts a given set of values. The function does not change the original
     * array of double
     * @param values An array of double with the values to analyse
    */
    public static double[] sort(double values[]) {
        int i, n;
        double tempValue;                
        boolean swapped = false;
        double orderedValues[];                
        orderedValues = new double[values.length];
        for (i = 0; i < values.length; i++) {
            orderedValues[i] = values[i];
        }
        
        n = values.length;
        
        do {
            swapped = false;
            for (i = 0; i < n - 1; i++) {
                if (orderedValues[i] > orderedValues[i + 1]) {
                    tempValue = orderedValues[i + 1];                    
                    orderedValues[i + 1] = orderedValues[i];                    
                    orderedValues[i] = tempValue;                    
                    swapped = true;
                }             
            }
            n = n - 1;
        } while (swapped);          
        return orderedValues;
    }
    
    /**
     * Returns the max value in an array of double. The function does not change the original
     * array of double
     * @param values An array of double with the values to analyse
    */
    public static double max(double values[]) {
        int i;
        double max = Double.MIN_VALUE;
        for (i = 0; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
        }
        return max;
    }
    
    /**
     * Returns the max value in an array of double. The function does not change the original
     * array of double
     * @param values An array of double with the values to analyse
    */
    public static double min(double values[]) {
        int i;
        double min = Double.MAX_VALUE;
        for (i = 0; i < values.length; i++) {
            if (values[i] < min) {
                min = values[i];
            }
        }
        return min;
    }
    
    /** Rounds the value given as parameter to four decimal places. 
     * @param number A double value to be rounded.
     * @return The number rounded to four decimal places.
    */
    public static double round(double number) {
        if(Double.isInfinite(number))
            return number;
        return ((int)(number*10000))/10000.0;
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Generates a subset of an array which contains the values that are less 
     * or equal than the mean.
     * @param values An array of double where the values are stored.
    */
    private static double[] lowerSubSet(double values[]) {
        int i;
        double median = median(values);
        double subSet[], orderedValues[] = sort(values);
        subSet = new double[(int) (orderedValues.length / 2) + 1];                
        for (i = 0; orderedValues[i] < median; i++) {
            subSet[i] = orderedValues[i];            
        }
        subSet[i] = median;        
        return subSet;
    }
    
    /**
     * Generates a subset of an array which contains the values that are greater 
     * or equal than the mean.
     * @param values An array of double where the values are stored.
    */
    private static double[] upperSubSet(double values[]) {
        int i, j = 0;
        double median = median(values);
        double subSet[], orderedValues[] = sort(values);
        subSet = new double[(int) (orderedValues.length / 2) + 1];
        if (orderedValues.length % 2 == 0) {
            subSet[j++] = median;            
        }
        for (i = (int) (orderedValues.length / 2); i < orderedValues.length; i++) {
            subSet[j++] = orderedValues[i];            
        }
        return subSet;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
}
