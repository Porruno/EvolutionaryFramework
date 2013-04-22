package CSP.CSP;

import CSP.Utils.Array;
import java.util.ArrayList;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class contains an experimental algorithm to solve CSP.
 * ----------------------------------------------------------------------------
 */
public class IncrementalCSPSolver {        
    
    int constraintCounter;
    CSP csp;
    
    /**
     * Creates a new instance of CSPSolver.
     * @param csp The CSP instance to solve.
    */
    public IncrementalCSPSolver(CSP csp) {
        /*
        int i, j, k, index, value, times;
        int array[];
        Constraint constraint;        
        ArrayList constraints = csp.getConstraints();
        ArrayList tuples, previous = null, matrix = new ArrayList();
        for (i = 0; i < constraints.size(); i++) {
            constraint = (Constraint) constraints.get(i);
            tuples = constraint.getTuples();
            matrix = new ArrayList(tuples.size());            
            for (j = 0; j < tuples.size(); j++) {
                array = createArray(csp.getVariables().length);
                matrix.add(array);
            }            
            for (j = 0; j < constraint.getScope().length; j++) {
                index = getIndexOfVariable(constraint.getScope()[j].getId(), csp.getVariables());                
                for (k = 0; k < tuples.size(); k++) {
                    value = ((int [])tuples.get(k))[j];
                    array = (int []) matrix.get(k);
                    updateArray(array, index, value);
                }                
            }            
            if (i == 0) {
                previous = matrix;                
            } else {                
                previous = combine(matrix, previous);                
            }
            times = previous.size() - 1;
            while (times > 0) {
                previous.remove(1);
                times--;
            }
            //print(previous);
            if (previous.isEmpty()) {
                System.out.println("The instance is unsat.");
                System.exit(1);                
            }
            System.out.println(((double) (i + 1) / constraints.size()) * 100 + "% completed. [" + previous.size() + "]");
        }        
        System.out.println("Solutions found :" + previous.size());
        print(previous);
        //System.out.println("First solution: " + Utils.Array.toString((int []) previous.get(0)));         
        */        
        this.csp = csp;
    }
    
    /**
     * Solves the CSP instance.
     * @return A boolean value indicating if the instance has been solver or not.
    */
    public boolean solve() {
        ArrayList matrixA;        
        constraintCounter = 0;
        matrixA = constraintToMatrix(csp, (Constraint) csp.getConstraints().get(0));
        return solve(matrixA, 1);
    }
    
    public final void print(ArrayList matrix) {
        int i;
        int array[];
        System.out.println("-------------");
        for (i = 0; i < matrix.size(); i++) {
            array = (int []) matrix.get(i);
            System.out.println(Array.toString(array));
        }
        System.out.println("-------------");
    }
    
    /* ------------------------------------------------------------------------ 
    BEGIN - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
    /**
     * Produces one step of the solution process.
     * @param matrix A matrix of conflicts.
     * @param constraint The constraint to combine with the matrix of conflicts.
    */
    private boolean solve(ArrayList matrix, int constraintIndex) {
        int i;
        int array[];
        Constraint constraint;
        ArrayList newMatrix, matrixA;                
        constraint = (Constraint) csp.getConstraints().get(constraintIndex);
        // There are no more constraints to check        
        if (constraint == null) {
            System.out.println("A solution has been found:");
            print(matrix);
            return true;
        }        
        matrixA = constraintToMatrix(csp, constraint);        
        for (i = 0; i < matrix.size(); i++) {
            array = (int[]) matrix.get(i);
            newMatrix = combine(array, matrixA);
            if (!newMatrix.isEmpty()) {
                if(solve(newMatrix, constraintIndex + 1)) {
                    return true;
                }
            }
        }
        return false;
    }        
    
    private ArrayList constraintToMatrix(CSP csp, Constraint constraint) {
        int i, j, index, value;
        int array[];
        ArrayList tuples, matrix;
        tuples = constraint.getTuples();
        matrix = new ArrayList(tuples.size());
        for (i = 0; i < tuples.size(); i++) {
            array = createArray(csp.getVariables().length);
            matrix.add(array);
        }
        for (i = 0; i < constraint.getScope().length; i++) {
            index = getIndexOfVariable(constraint.getScope()[i].getId(), csp.getVariables());
            for (j = 0; j < tuples.size(); j++) {
                value = ((int[]) tuples.get(j))[i];
                array = (int[]) matrix.get(j);
                updateArray(array, index, value);
            }
        }
        return matrix;
    }        
    
    /**
     * Returns the index of the specified variable.
    */
    private static int getIndexOfVariable(String variableId, Variable variables[]) {
        int i;
        for (i = 0; i < variables.length; i++) {
            if (variables[i].getId().equalsIgnoreCase(variableId)) {
                return i;
            }
        }
        System.out.println("The variable \'" + variableId + "\' is not defined.");
        System.out.println("The system will halt.");
        System.exit(1);
        return -1;
    }
    
    /**
     * Creates and initializes the array of conflicts.
     * @param n The number of variables within the instance.
    */
    private static int[] createArray(int n) {
        int i;
        int array[] = new int[n];
        for (i = 0; i < array.length; i++) {
            array[i] = -1;
        }
        return array;
    }
    
    /**
     * Updates an array.
     * @array The array to update.
     * @index The index to update.
     * @value The value to be used to update the array.
    */
    private static void updateArray(int array[], int index, int value) {
        array[index] = value;
    }
    
    /**
     * Combines two arrays of conflicts.
     * @param arrayA The first conflict.
     * @param arrayB The second conflict.
     * @return A conflict that combines arrayA and arrayB.
    */
    private static int[] combineArrays(int arrayA[], int arrayB[]) {
        int i;
        int array[];
        if (arrayA.length != arrayB.length) {
            System.out.println("The lengths of the arrays dows not match.");
            System.out.println("The system will halt.");
            System.exit(1);
        }
        array = new int[arrayA.length];
        for (i = 0; i < array.length; i++) {
            // If they have been specified
            if (arrayA[i] != -1 && arrayB[i] != -1) {
                if (arrayA[i] == arrayB[i]) {
                    array[i] = arrayA[i];                    
                } else {
                    // There is a failure, the entry cannot be added 
                    return null;
                }
            } else {
                if (arrayA[i] != -1) {
                    array[i] = arrayA[i];
                } else {
                    array[i] = arrayB[i];
                }
            }
        }
        return array;
    }
    
    /**
     * Combines an array with a matrix of conflicts.
     * @param array An array of integers that represents the conflicts.
     * @param matrix A matrix of conflicts.
     * @return An instance of ArrayList that contains the conflicts of the 
     * resulting matrix of conflicts.
    */
    public static ArrayList combine(int array[], ArrayList matrix) {
        int i;
        int entry[];
        ArrayList newMatrix = new ArrayList();
        for (i = 0; i < matrix.size(); i++) {
            entry = combineArrays(array, (int[]) matrix.get(i));
            if (entry != null) {
                newMatrix.add(entry);
            }
        }
        return newMatrix;
    }
    
    /**
     * Combines two matrices of conflicts.
     * @param matrixA The first matrix of conflicts.
     * @param matrixB The second matrix of conflicts.
     * @return An instance of ArrayList that contains the conflicts of the 
     * resulting matrix of conflicts.
    */
    private static ArrayList combine(ArrayList matrixA, ArrayList matrixB) {
        int i, j;
        int array[];
        ArrayList matrix = new ArrayList();
        for (i = 0; i < matrixA.size(); i++) {
            for (j = 0; j < matrixB.size(); j++) {
                array = combineArrays((int []) matrixA.get(i), (int [])matrixB.get(j));
                if (array != null) {
                    matrix.add(array);
                }
            }
        }
        return matrix;
    }
    
    /* ------------------------------------------------------------------------ 
    END - UTILITY FUNCTIONS
    ------------------------------------------------------------------------- */
    
}
