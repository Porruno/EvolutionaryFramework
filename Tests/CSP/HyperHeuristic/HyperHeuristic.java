package CSP.HyperHeuristic;

import java.util.GregorianCalendar;
import CSP.Framework.Framework;
import CSP.CSP.CSP;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the basic functions and attributes to create and handle 
 * a general hyper-heuristic.
 * ----------------------------------------------------------------------------
*/

public abstract class HyperHeuristic {
    
    public static final int UNKNOWN = -1;           // An unknown method
    public static final int RANDOM = 0;
    public static final int LOWLEVEL = 1;           // Low-level heuristics
    public static final int BLOCK = 2;              // Block hyper-heuristic
    public static final int NEURAL = 3;             // LVQ Neural Network hyper-heuristic
    public static final int MATRIX = 4;             // Matrix hyper-heuristic 
    public static final int SIMPLEMATRIX = 5;       //Simple matrix, test purposes.
    public static final int LCS = 6;                // LCS hyper-heuristic
    protected int type = UNKNOWN;    
    
    /**
        Returns the type of the hyper-heurusic.
    */
    public int getType() {
        return type;
    }
    
    /**
     * Creates a default header text for all hyper-heuristics.
    */
    protected String getHeader() {
        int i;
        int features[] = Framework.getFeatures();
        GregorianCalendar day = new GregorianCalendar();
        String text = "% This file was created on " + day.getTime().toString() + "\r\n";
        text += "% ";
        for (i = 0; i < features.length - 1; i++) {
            text += CSP.featureToString(features[i]) + ", ";
        }
        text += CSP.featureToString(features[i]) + ", Heuristic\r\n";
        return text;
    }
    
}
