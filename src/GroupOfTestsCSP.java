
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
public class GroupOfTestsCSP {
    public static CSP cspInstance = new CSP(20,10,0.80,0.80,0);
    public static CSP cspInstance0 = new CSP(20,10,0.05,0.05,0);
    public static CSP cspInstance1 = new CSP(20,10,0.05,0.05,0);
    public static CSP cspInstance2 = new CSP(20,10,0.10,0.10,0);
    public static CSP cspInstance3 = new CSP(20,10,0.10,0.10,0);
    public static CSP cspInstance4 = new CSP(20,10,0.15,0.15,0);
    public static CSP cspInstance5 = new CSP(20,10,0.15,0.15,0);
    public static CSP cspInstance6 = new CSP(20,10,0.20,0.20,0);
    public static CSP cspInstance7 = new CSP(20,10,0.20,0.20,0);
    public static CSP cspInstance8 = new CSP(20,10,0.25,0.25,0);
    public static CSP cspInstance9 = new CSP(20,10,0.25,0.25,0);
    public static CSP cspInstance10 = new CSP(20,10,0.30,0.30,0);
    public static CSP cspInstance11 = new CSP(20,10,0.30,0.30,0);
    public static CSP cspInstance12 = new CSP(20,10,0.35,0.35,0);
    public static CSP cspInstance13 = new CSP(20,10,0.35,0.35,0);
    public static CSP cspInstance14 = new CSP(20,10,0.40,0.40,0);
    public static CSP cspInstance15 = new CSP(20,10,0.40,0.40,0);
    public static CSP cspInstance16 = new CSP(20,10,0.45,0.45,0);
    public static CSP cspInstance17 = new CSP(20,10,0.45,0.45,0);
    public static CSP cspInstance18 = new CSP(20,10,0.50,0.50,0);
    public static CSP cspInstance19 = new CSP(20,10,0.50,0.50,0);
    public static CSP cspInstance20 = new CSP(20,10,0.55,0.55,0);
    public static CSP cspInstance21 = new CSP(20,10,0.55,0.55,0);
    public static CSP cspInstance22 = new CSP(20,10,0.60,0.60,0);
    public static CSP cspInstance23 = new CSP(20,10,0.60,0.60,0);
    public static CSP cspInstance24 = new CSP(20,10,0.65,0.65,0);
    public static CSP cspInstance25 = new CSP(20,10,0.65,0.65,0);
    public static CSP cspInstance26 = new CSP(20,10,0.70,0.70,0);
    public static CSP cspInstance27 = new CSP(20,10,0.70,0.70,0);
    public static CSP cspInstance28 = new CSP(20,10,0.75,0.75,0);
    public static CSP cspInstance29 = new CSP(20,10,0.75,0.75,0);
    public static CSP cspInstance30 = new CSP(20,10,0.80,0.80,0);
    public static CSP cspInstance31 = new CSP(20,10,0.80,0.80,0);
    public static CSP cspInstance32 = new CSP(20,10,0.85,0.85,0);
    public static CSP cspInstance33 = new CSP(20,10,0.85,0.85,0);
    public static CSP cspInstance34 = new CSP(20,10,0.90,0.90,0);
    public static CSP cspInstance35 = new CSP(20,10,0.90,0.90,0);
    public static CSP cspInstance36 = new CSP(20,10,0.95,0.95,0);
    public static CSP cspInstance37 = new CSP(20,10,0.95,0.95,0);
    
    public static void main(String[] args){
        cspInstance.saveToFile("instanceCSP01Training.xml");
        cspInstance0.saveToFile("instanceCSP00.xml");
        cspInstance1.saveToFile("instanceCSP01.xml");
        cspInstance2.saveToFile("instanceCSP02.xml");
        cspInstance3.saveToFile("instanceCSP03.xml");
        cspInstance4.saveToFile("instanceCSP04.xml");
        cspInstance5.saveToFile("instanceCSP05.xml");
        cspInstance6.saveToFile("instanceCSP06.xml");
        cspInstance7.saveToFile("instanceCSP07.xml");
        cspInstance8.saveToFile("instanceCSP08.xml");
        cspInstance9.saveToFile("instanceCSP09.xml");
        cspInstance10.saveToFile("instanceCSP10.xml");
        cspInstance11.saveToFile("instanceCSP11.xml");
        cspInstance12.saveToFile("instanceCSP12.xml");
        cspInstance13.saveToFile("instanceCSP13.xml");
        cspInstance14.saveToFile("instanceCSP14.xml");
        cspInstance15.saveToFile("instanceCSP15.xml");
        cspInstance16.saveToFile("instanceCSP16.xml");
        cspInstance17.saveToFile("instanceCSP17.xml");
        cspInstance18.saveToFile("instanceCSP18.xml");
        cspInstance19.saveToFile("instanceCSP19.xml");
        cspInstance20.saveToFile("instanceCSP20.xml");
        cspInstance21.saveToFile("instanceCSP21.xml");
        cspInstance22.saveToFile("instanceCSP22.xml");
        cspInstance23.saveToFile("instanceCSP23.xml");
        cspInstance24.saveToFile("instanceCSP24.xml");
        cspInstance25.saveToFile("instanceCSP25.xml");
        cspInstance26.saveToFile("instanceCSP26.xml");
        cspInstance27.saveToFile("instanceCSP27.xml");
        cspInstance28.saveToFile("instanceCSP28.xml");
        cspInstance29.saveToFile("instanceCSP29.xml");
        cspInstance30.saveToFile("instanceCSP30.xml");
        cspInstance31.saveToFile("instanceCSP31.xml");
        cspInstance32.saveToFile("instanceCSP32.xml");
        cspInstance33.saveToFile("instanceCSP33.xml");
        cspInstance34.saveToFile("instanceCSP34.xml");
        cspInstance35.saveToFile("instanceCSP35.xml");
        cspInstance36.saveToFile("instanceCSP36.xml");
        cspInstance37.saveToFile("instanceCSP37.xml");
        
        GroupOfTests cspTests = new GroupOfTests("CSP", new Class<?>[]{CSPTestGPSimple.class}, 1);
        cspTests.graph();
        System.out.println(cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns().getAptitude());
        
        
        CSP csp = new CSP(cspInstance0);
        GACSPSolver solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        Variable[] variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        long checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance1);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance2);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance3);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance4);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance5);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance6);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance7);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance8);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance9);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance10);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance11);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance12);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance13);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance14);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance15);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance16);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance17);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance18);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance19);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance20);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance21);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance22);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance23);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance24);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance25);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance26);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance27);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance28);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance29);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance30);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance31);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance32);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance33);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance34);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance35);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance36);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstance37);
        solver = new GACSPSolver(csp, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
    }
    
}
