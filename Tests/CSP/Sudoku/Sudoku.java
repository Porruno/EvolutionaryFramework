package CSP.Sudoku;

import CSP.CSP.CSP;
import CSP.CSP.CSPSolver;
import CSP.CSP.ConstraintOrderingHeuristics;
import CSP.CSP.ValueOrderingHeuristics;
import CSP.CSP.Variable;
import CSP.CSP.VariableOrderingHeuristics;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
 * ----------------------------------------------------------------------------
 * This class provides the functionality to represent Sudoku as a CSP instance.
 * ----------------------------------------------------------------------------
*/

public class Sudoku {
    
    private CSP csp;
    
    /**
     * Creates a new instance of Sudoku.
     * @param board A matrix of integers with the representation of the board.
    */
    public Sudoku(int board[][]) {
        int i, j, k, c = 0;
        int domain[];
        String variableId;
        String variableIdsRow[] = null, variableIdsColumn[] = null;
        Variable variables[][] = new Variable[board.length][board.length];        
        for (i = 0; i < board.length; i++) {
            for (j = 0; j < board[i].length; j++) {
                variableId = "V_" + i + "_" + j;
                if (board[i][j] == -1) {
                    domain = new int[board.length];
                    for (k = 0; k < domain.length; k++) {
                        domain[k] = k + 1;
                    }
                } else {                    
                    domain = new int[]{board[i][j]};
                }
                variables[i][j] = new Variable(variableId, domain);                
            }
        }
        csp = new CSP(variables);               
        for (i = 0; i < board.length;i++) {
            k = 0;
            variableIdsRow = new String[board.length];
            variableIdsColumn = new String[board.length];
            for (j = 0; j < board.length; j++) {
                variableIdsColumn[k] = "V_" + j + "_" + i; 
                variableIdsRow[k++] = "V_" + i + "_" + j; 
            }
            //System.out.println(Utils.Array.toString(variableIdsColumn));
            csp.addConstraint("C" + (c++), variableIdsRow, "global:allDifferent");
            csp.addConstraint("C" + (c++), variableIdsColumn, "global:allDifferent");
        }        
        System.out.println(csp);
    }
    
    /**
     * Loads a Sudoku from a file
     * @param fileName The name of the file where the board is saved.
     * @return An instance of Sudoku.
    */
    public static Sudoku loadFromFile(String fileName) {
        int board[][] = new int[4][4];
        board[0][0] = -1;
        board[0][1] = -1;
        board[0][2] = -1;
        board[0][3] = -1;
        board[1][0] = 3;
        board[1][1] = 2;
        board[1][2] = -1;
        board[1][3] = 1;
        board[2][0] = 4;
        board[2][1] = 3;
        board[2][2] = -1;
        board[2][3] = 2;
        board[3][0] = -1;
        board[3][1] = -1;
        board[3][2] = -1;
        board[3][3] = -1;
        return new Sudoku(board);
    }
    
    /**
     * Returns the CSP representation of the current Sudoku instance.
     * @return The CSP representation
    */
    public CSP getAsCSP() {
        return csp;
    }
    
    /**
     * Solves the Sudoku instance.
    */
    public void solve() {
        int i, j, k = 0;
        int solvedBoard[][] = new int[(int) Math.sqrt((double) csp.getVariables().length)][(int) Math.sqrt((double) csp.getVariables().length)];
        CSPSolver solver = new CSPSolver(csp);        
        Variable solution[] = solver.solve(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE, false);
        if (solver.getNumberOfSolutions() > 0) {
            for (i = 0; i < solvedBoard.length; i++) {
                for (j = 0; j < solvedBoard.length; j++) {
                    solvedBoard[i][j] = solution[k++].getDomain()[0];
                }
            }
        } else {
            for (i = 0; i < solvedBoard.length; i++) {
                for (j = 0; j < solvedBoard.length; j++) {
                    if (solution[k].getDomain().length > 1) {
                        solvedBoard[i][j] = -1;
                    } else {
                        solvedBoard[i][j] = solution[k++].getDomain()[0];
                    }
                }
            }
        }
        printBoard(solvedBoard);
    }
    
    public void printBoard(int board[][]) {
        int i, j;
        for (i = 0; i < board.length;i++) {
            System.out.println("\n---------");
            System.out.print("|");
            for (j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + "|" );
            }
        }
        System.out.println("\n---------");
    }
    
}
