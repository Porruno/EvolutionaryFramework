package CSP.HyperHeuristic;

import CSP.Framework.Framework;
import CSP.Framework.LocalImprovementFramework;
import CSP.Utils.Misc;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class provides the functions to create and handle matrix hyper-heuristics.
 * ----------------------------------------------------------------------------
*/

public class MatrixHyperHeuristic extends HyperHeuristic {

    public static final int MinSize = 5, MaxSize = 20;
    public int defaultHeuristic = -1;
    
    private static final int ByUsage = 0, ByDistance = 1; 
    private int decisionMatrix[][], usageMatrix[][];
    private int rows, columns, maxChanges = 5;
    private int decisionType = ByDistance;
        
    /**
     * Creates a new instance of MatrixHyperHeuristic.
    */
    public MatrixHyperHeuristic() {
        createDecisionMatrix();
        if (Framework.getHeuristics().length < maxChanges) {
            maxChanges = Framework.getHeuristics().length;
        }
        type = HyperHeuristic.MATRIX;        
    }

    /**
     * Creates a new instance of MatrixHyperHeuristic of a given size.
     * @param size The size of the decision matrix. The larger the size, the 
     * higher the resolution of the hyper-heuristic.
    */
    public MatrixHyperHeuristic(int size) {
        createDecisionMatrix(size);
        if (Framework.getHeuristics().length < maxChanges) {
            maxChanges = Framework.getHeuristics().length;
        }
        type = HyperHeuristic.MATRIX;        
    }
    
    /**
     * Creates a new instance of MatrixHyperHeuristic of a given size.
     * @param size The size of the decision matrix. The larger the size, the 
     * higher the resolution of the hyper-heuristic.
    */
    public MatrixHyperHeuristic(int size, int heuristic) {
        createDecisionMatrix(size, heuristic);
        if (Framework.getHeuristics().length < maxChanges) {
            maxChanges = Framework.getHeuristics().length;
        }
        type = HyperHeuristic.MATRIX;        
    }
    
    /**
     * Creates a new instance of MatrixHyperHeuristic from a matrix of doubles.
     * @param matrix A matrix of double where the data is stored.
    */
    public MatrixHyperHeuristic(int decisionMatrix[][]) {
        this.decisionMatrix = decisionMatrix;
        this.rows = decisionMatrix.length;
        this.columns = decisionMatrix[0].length;
        usageMatrix = new int[rows][columns];
        if (Framework.getHeuristics().length < maxChanges) {
            maxChanges = Framework.getHeuristics().length;
        }
        type = HyperHeuristic.MATRIX;        
    }
    
    /**
     * Creates a new instance of MatrixHyperHeuristic from an existing hyper-heuristic.
     * @param hyperHeuristic The hyper-heuristic where the data will be copied from.
    */
    public MatrixHyperHeuristic(MatrixHyperHeuristic hyperHeuristic) {
        int i, j;
        decisionMatrix = new int[hyperHeuristic.getRowCount()][hyperHeuristic.getColumnCount()];
        usageMatrix = new int[hyperHeuristic.getRowCount()][hyperHeuristic.getColumnCount()];
        for (i = 0; i < hyperHeuristic.getDecisionMatrix().length; i++) {
            for (j = 0; j < hyperHeuristic.getDecisionMatrix()[0].length; j++) {
                decisionMatrix[i][j] = hyperHeuristic.getDecisionMatrix()[i][j];
                usageMatrix[i][j] = hyperHeuristic.getUsageMatrix()[i][j];                
            }            
        }
        this.maxChanges = hyperHeuristic.maxChanges;
        rows = decisionMatrix.length;
        columns = decisionMatrix[0].length;
        type = HyperHeuristic.MATRIX;        
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
    public int[][] getDecisionMatrix() {
        return decisionMatrix;
    }    
        
    /** 
     * Loads a MatrixHyperHeuristic from a file.
     * @param fileName The name of the file where the data is stored.
     * @raturn A matrix hyper-heuristic instance.
    */
    public static MatrixHyperHeuristic loadFromFile(String fileName) {        
        int decisionMatrix[][];
        String rows[];
        rows = Misc.toStringArray(CSP.Utils.Files.loadFromFile(fileName));
        decisionMatrix = readRows(rows);        
        return new MatrixHyperHeuristic(decisionMatrix);        
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
        
    /**
     * Determines if a matrix hyper-heuristic is equivalent to this instance.
     * @param hyperHeuristic the matrix hyper-heuristic to be compared to the instance.
     * @return the percentage of cells that contain the same values.
    */
    public double compareTo(MatrixHyperHeuristic hyperHeuristic) {
        int i, j;
        int matrix[][] = hyperHeuristic.getDecisionMatrix();
        double equal = 0;
        for (i = 0; i < this.rows; i++) {
            for (j = 0; j < this.columns; j++) {
                if (this.decisionMatrix[i][j] == matrix[i][j]) {
                    equal++;
                }
            }
        }
        return equal / (rows * columns);
    }

    /**
     * Updates the values of the decision matrix in an attempt of improving it.
     * @return True if it was possible to improve the decision matrix, false otherwise.
    */    
    public boolean change() {
        int i, j, x = -1, y = -1, heuristicIndex;
        double distance, max;
        Random generator = new Random();
        max = Double.MIN_VALUE;
        for (i = 0; i < decisionMatrix.length; i++) {
            for (j = 0; j < decisionMatrix[0].length; j++) {
                switch (decisionType) {
                    case ByUsage:                    
                        if (usageMatrix[i][j] > 0) {                       
                            if (usageMatrix[i][j] > max && LocalImprovementFramework.getChangeMatrix()[i][j] < maxChanges) {
                                x = i;
                                y = j;
                                max = usageMatrix[i][j];
                            }
                        }
                        break;
                    case ByDistance:
                        distance = Math.sqrt(Math.pow(((double) i), 2) + Math.pow(((double) j), 2));
                        if (decisionMatrix[i][j] != -1) {
                            if (distance > max && LocalImprovementFramework.getChangeMatrix()[i][j] < maxChanges) {
                                x = i;
                                y = j;
                                max = distance;                                
                            }
                        }
                        break;                    
                }
            }
        }
        if (x == -1 || y == -1) {
            return false;
        } else {            
            if (Framework.getHeuristics().length > 4) {
                heuristicIndex = Framework.getHeuristics()[generator.nextInt(Framework.getHeuristics().length)];
            } else {
                heuristicIndex = Framework.getHeuristics()[LocalImprovementFramework.getChangeMatrix()[x][y]];
            }
            LocalImprovementFramework.getChangeMatrix()[x][y]++;
            decisionMatrix[x][y] = heuristicIndex;
            return true;
        }
    }

    /**
     * Returns the usage matrix.
     * @return The usage matrix.
    */
    public int[][] getUsageMatrix() {
        return usageMatrix;    
    }
        
    /**
     * Saves the usage matrix to a file.
     * @param fileName the name of the file where the matrix will be stored.     
    */
    public void saveUsageMatrixToFile(String fileName) {
        int i, j;        
        String text[] = new String[usageMatrix.length];        
        for(i = 0; i < usageMatrix.length; i++) {
            text[i] = "";
            for (j = 0; j < usageMatrix[i].length; j++) {                
                text[i] += Integer.toString(usageMatrix[i][j]) + " ";
            }            
        }
        CSP.Utils.Files.saveToFile(text, fileName);
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
        size = generator.nextInt(MaxSize - MinSize) + MinSize;
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
        decisionMatrix = new int[rows][columns];
        usageMatrix = new int[rows][columns];
        for (i = 0; i < rows; i++) {
            for (j = 0; j < columns; j++) {                
                decisionMatrix[i][j] = -1;
                usageMatrix[i][j] = 0;       
            }
        }        
    }
    
    /**
     * Creates a random square decision matrix.
     * @param size The size of the matrix.
     * @param heurisic The heuristic that will be used when no other has been specified.
     * @return A two-dimension integer matrix.
    */
    private void createDecisionMatrix(int size, int heuristic) {
        this.defaultHeuristic = heuristic;
        createDecisionMatrix(size);
    }
    
    /**
     * Reads each row of the matrix.
     * @param rows A String array with the rows of the matrix.
     * @return A two-dimension integer matrix.
    */
    private static int[][] readRows(String rows[]) {
        int i, j, rowCount, columnCount;
        int decisionMatrix[][];
        StringTokenizer tokens = new StringTokenizer(rows[0], " ,");        
        // Reading the rows number
        rowCount = Integer.parseInt(tokens.nextToken());
        // Reading the columns number
        columnCount = Integer.parseInt(tokens.nextToken());
        decisionMatrix = new int[rowCount][columnCount];
        try {
            for (i = 1; i < rows.length; i++) {
                tokens = new StringTokenizer(rows[i], " ,");
                j = 0;
                while (tokens.hasMoreTokens()) {
                    decisionMatrix[i - 1][j++] = Integer.parseInt(tokens.nextToken());
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
