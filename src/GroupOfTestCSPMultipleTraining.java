
import CSP.CSP.*;
import CSP.tests.test1.CSPTestGEPCoevolutive;
import CSP.tests.test1.CSPTestGEPSimple;
import CSP.tests.test1.CSPTestGPCoevolutive;
import CSP.tests.test1.CSPTestGPSimple;
import test.GroupOfTests;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Lay
 */
public class GroupOfTestCSPMultipleTraining {
    
    public static XMLLoader instanceCSP = new XMLLoader();
    
    CSP cspInstanceTraining01 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-1.xml"));
    
    public static void main(String[] args){  
        
        GroupOfTests cspTests = new GroupOfTests("CSP", new Class<?>[]{CSPTestGPSimple.class}, 1);
        cspTests.graph();
        System.out.println(cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns().getAptitude());
        
        
        CSP csp = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-1.xml"));
        GACSPSolver solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        //CSPSolver solver = new CSPSolver(cspInstance37);
        Variable[] variables = solver.solve(VariableOrderingHeuristics.MFD, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        long checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        //csp = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-2.xml"));
        //solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        //variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        //checks = solver.getConstraintChecks();
        //System.out.println(checks);
        
        
    }
    
}
