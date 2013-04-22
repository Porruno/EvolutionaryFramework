package CSP.HyperHeuristic;

import CSP.Utils.Misc;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains the functions to create simple decision matrix 
 * hyper-heuristics.
 * ----------------------------------------------------------------------------
*/

public class SimpleMatrixHyperHeuristic extends HyperHeuristic {

    public static final int MIN_SIZE = 5, MAX_SIZE = 20;    
    public static final int DETERMINISTIC = 0, PROBABILISTIC = 1;
    private double decisionMatrix[][];
    private int mode, rows, columns;    
    
    /**
     * Creates a new instance of MatrixHyperHeuristic.
    */
    public SimpleMatrixHyperHeuristic(int style) {
        createDecisionMatrix();
        type = HyperHeuristic.SIMPLEMATRIX;
        this.mode = style;
    }

    /**
     * Creates a new instance of MatrixHyperHeuristic of a given size.
     * @param size The size of the decision matrix. The larger the size, the 
     * higher the resolution of the hyper-heuristic.
    */
    public SimpleMatrixHyperHeuristic(int size, int style) {
        createDecisionMatrix(size);
        type = HyperHeuristic.SIMPLEMATRIX;
        this.mode = style;
    }        
    
    /**
     * Creates a new instance of MatrixHyperHeuristic from a matrix of doubles.
     * @param matrix A matrix of double where the data is stored.
    */
    public SimpleMatrixHyperHeuristic(double decisionMatrix[][], int mode) {
        this.decisionMatrix = decisionMatrix;
        this.rows = decisionMatrix.length;
        this.columns = decisionMatrix[0].length;
        type = HyperHeuristic.SIMPLEMATRIX;
        this.mode = mode;
    }
    
    /**
     * Creates a new instance of MatrixHyperHeuristic from an existing hyper-heuristic.
     * @param hyperHeuristic The hyper-heuristic where the data will be copied from.
    */
    public SimpleMatrixHyperHeuristic(SimpleMatrixHyperHeuristic hyperHeuristic) {
        int i, j;       
        decisionMatrix = new double[hyperHeuristic.getRowCount()][hyperHeuristic.getColumnCount()];        
        for (i = 0; i < hyperHeuristic.getDecisionMatrix().length; i++) {
            for (j = 0; j < hyperHeuristic.getDecisionMatrix()[0].length; j++) {
                decisionMatrix[i][j] = hyperHeuristic.getDecisionMatrix()[i][j];                
            }            
        }        
        rows = decisionMatrix.length;
        columns = decisionMatrix[0].length;
        type = HyperHeuristic.SIMPLEMATRIX;
        mode = hyperHeuristic.getMode();
    }
      
    /**
     * Returns the number of rows in the matrix.
     * @return The number of rows in the matrix.
    */
    public int getRowCount() {
        return rows;
    }

    /**
     * Returns the number of columns in the matrix.
     * @return The number of columns in the matrix.
    */
    public int getColumnCount() {
        return columns;
    }
    
    /**
     * Returns the decision matrix.
     * @return The decision matrix.
    */
    public double[][] getDecisionMatrix() {
        return decisionMatrix;
    }    
    
    /**
     * Sets the mode of the hyper-heuristic.
    */
    public void setMode(int mode) {
        this.mode = mode;
    }
    
    /**
     * Returns the hyper-heuristic mode.
     * @return The hyper-heuristic mode.
    */
    public int getMode() {
        return mode;
    }
    
    /** 
     * Loads a MatrixHyperHeuristic from a file.
     * @param fileName The name of the file where the data is stored.
     * @raturn A matrix hyper-heuristic instance.
    */
    public static SimpleMatrixHyperHeuristic loadFromFile(String fileName, int mode) {
        double decisionMatrix[][];
        String rows[];
        rows = Misc.toStringArray(CSP.Utils.Files.loadFromFile(fileName));
        decisionMatrix = readRows(rows);
        return new SimpleMatrixHyperHeuristic(decisionMatrix, mode);
    }
    
    /** 
     * Saves the hyper-heuristic to a text file.
     * @param fileName The name of the file where the hyper-heuristic will be stored.
    */
    public void saveToFile(String fileName) {        
        String text = getHeader() + rows + " " + columns + "\r\n";
        text += this.toString();
        CSP.Utils.Files.saveToFile(text, fileName, false);
    }            
           
    @Override
    /**
     * Returns the string representation of the instance.
     * @return The string representation of the instance.
    */
    public String toString() {
        int i, j;
        String text = "";
        for (i = 0; i < rows; i++) {            
            for (j = 0; j < columns; j++) {
                text = text + decisionMatrix[i][j] + " ";
            }
            text = text + "\r\n";
        }
        return text;
    }
            
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */   
    
    /**
     * Creates a random square decision matrix.
     * @return A two-dimension integer matrix.
    */
    private void createDecisionMatrix() {
        int size;
        Random generator = new Random();
        size = generator.nextInt(MAX_SIZE - MIN_SIZE) + MIN_SIZE;
        createDecisionMatrix(size);
    }
    
    /**
     * Creates a random square decision matrix.
     * @param size The size of the matrix. 
     * @return A two-dimension integer matrix.
    */
    private void createDecisionMatrix(int size) {
        int i, j;
        rows = size;
        columns = size;
        decisionMatrix = new double[rows][columns];        
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {                
                decisionMatrix[i][j] = -1;                               
            }
        }        
    }
        
    /**
     * Reads each row of the matrix.
     * @param rows A String array with the rows of the matrix.
     * @return A two-dimension integer matrix.
    */
    private static double[][] readRows(String rows[]) {
        int i, j, rowCount, columnCount;
        double decisionMatrix[][];
        StringTokenizer tokens;   
        rowCount = 20;        
        columnCount = 20;
        decisionMatrix = new double[rowCount][columnCount];
        try {
            for (i = 0; i < rows.length; i++) {
                tokens = new StringTokenizer(rows[i], " ,");
                j = 0;
                while (tokens.hasMoreTokens()) {
                    decisionMatrix[i][j++] = Double.parseDouble(tokens.nextToken());
                }
            }
            if (rowCount != decisionMatrix.length || columnCount != decisionMatrix[0].length) {
                System.out.println("The matrix and the header description do not match.");
                System.out.println("The system will halt.");
                System.exit(1);
            }
        } catch (Exception e) {
            System.out.println("An error ocurred when attempting to load the hyper-heuristic.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        return decisionMatrix;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
        
}
