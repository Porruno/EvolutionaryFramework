/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aso
 */

import CSP.CSP.*;
import CSP.tests.test1.CSPTestILSSimple;
import test.GroupOfTests;



/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alejandro Sosa
 */
public class GroupOfTestsHeuristics {
    public static XMLLoader instanceCSP = new XMLLoader();
    public static CSP cspInstanceTraining00 = new CSP(instanceCSP.createCSP("instanceCSPTraining.xml"));
    public static CSP cspInstanceTest00 = new CSP(instanceCSP.createCSP("instanceCSP00.xml"));
    public static CSP cspInstanceTest01 = new CSP(instanceCSP.createCSP("instanceCSP01.xml"));
    public static CSP cspInstanceTest02 = new CSP(instanceCSP.createCSP("instanceCSP02.xml"));
    public static CSP cspInstanceTest03 = new CSP(instanceCSP.createCSP("instanceCSP03.xml"));
    public static CSP cspInstanceTest04 = new CSP(instanceCSP.createCSP("instanceCSP04.xml"));
    public static CSP cspInstanceTest05 = new CSP(instanceCSP.createCSP("instanceCSP05.xml"));
    public static CSP cspInstanceTest06 = new CSP(instanceCSP.createCSP("instanceCSP06.xml"));
    public static CSP cspInstanceTest07 = new CSP(instanceCSP.createCSP("instanceCSP07.xml"));
    public static CSP cspInstanceTest08 = new CSP(instanceCSP.createCSP("instanceCSP08.xml"));
    public static CSP cspInstanceTest09 = new CSP(instanceCSP.createCSP("instanceCSP09.xml"));
    public static CSP cspInstanceTest10 = new CSP(instanceCSP.createCSP("instanceCSP10.xml"));
    public static CSP cspInstanceTest11 = new CSP(instanceCSP.createCSP("instanceCSP11.xml"));
    public static CSP cspInstanceTest12 = new CSP(instanceCSP.createCSP("instanceCSP12.xml"));
    public static CSP cspInstanceTest13 = new CSP(instanceCSP.createCSP("instanceCSP13.xml"));
    public static CSP cspInstanceTest14 = new CSP(instanceCSP.createCSP("instanceCSP14.xml"));
    public static CSP cspInstanceTest15 = new CSP(instanceCSP.createCSP("instanceCSP15.xml"));
    public static CSP cspInstanceTest16 = new CSP(instanceCSP.createCSP("instanceCSP16.xml"));
    public static CSP cspInstanceTest17 = new CSP(instanceCSP.createCSP("instanceCSP17.xml"));
    public static CSP cspInstanceTest18 = new CSP(instanceCSP.createCSP("instanceCSP18.xml"));
    public static CSP cspInstanceTest19 = new CSP(instanceCSP.createCSP("instanceCSP19.xml"));
    public static CSP cspInstanceTest20 = new CSP(instanceCSP.createCSP("instanceCSP20.xml"));
    public static CSP cspInstanceTest21 = new CSP(instanceCSP.createCSP("instanceCSP21.xml"));
    public static CSP cspInstanceTest22 = new CSP(instanceCSP.createCSP("instanceCSP22.xml"));
    public static CSP cspInstanceTest23 = new CSP(instanceCSP.createCSP("instanceCSP23.xml"));
    public static CSP cspInstanceTest24 = new CSP(instanceCSP.createCSP("instanceCSP24.xml"));
    public static CSP cspInstanceTest25 = new CSP(instanceCSP.createCSP("instanceCSP25.xml"));
    public static CSP cspInstanceTest26 = new CSP(instanceCSP.createCSP("instanceCSP26.xml"));
    public static CSP cspInstanceTest27 = new CSP(instanceCSP.createCSP("instanceCSP27.xml"));
    public static CSP cspInstanceTest28 = new CSP(instanceCSP.createCSP("instanceCSP28.xml"));
    public static CSP cspInstanceTest29 = new CSP(instanceCSP.createCSP("instanceCSP29.xml"));
    public static CSP cspInstanceTest30 = new CSP(instanceCSP.createCSP("instanceCSP30.xml"));
    public static CSP cspInstanceTest31 = new CSP(instanceCSP.createCSP("instanceCSP31.xml"));
    public static CSP cspInstanceTest32 = new CSP(instanceCSP.createCSP("instanceCSP32.xml"));
    public static CSP cspInstanceTest33 = new CSP(instanceCSP.createCSP("instanceCSP33.xml"));
    public static CSP cspInstanceTest34 = new CSP(instanceCSP.createCSP("instanceCSP34.xml"));
    public static CSP cspInstanceTest35 = new CSP(instanceCSP.createCSP("instanceCSP35.xml"));
    public static CSP cspInstanceTest36 = new CSP(instanceCSP.createCSP("instanceCSP36.xml"));
    public static CSP cspInstanceTest37 = new CSP(instanceCSP.createCSP("instanceCSP37.xml"));
    /*
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
    */
    public static void main(String[] args){
        
        
        //cspInstance.saveToFile("instanceCSP01Training.xml");
        /*
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
        */
        //GroupOfTests cspTests = new GroupOfTests("CSP", new Class<?>[]{CSPTestILSSimple.class}, 1);
        //cspTests.graph();
        //System.out.println(cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns().getAptitude());
        
        CSP csp = new CSP(cspInstanceTraining00);
        CSPSolver solver = new CSPSolver(csp);
        Variable[] variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        long checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest00);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest01);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest02);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest03);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest04);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest05);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest06);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest07);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest08);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest09);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest10);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest11);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest12);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest13);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest14);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest15);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest16);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest17);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest18);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest19);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest20);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest21);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest22);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest23);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest24);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest25);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest26);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest27);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest28);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest29);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest30);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest31);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest32);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest33);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest34);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest35);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest36);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
        csp = new CSP(cspInstanceTest37);
        solver = new CSPSolver(csp);
        variables = solver.solve(VariableOrderingHeuristics.MXC,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        checks = solver.getConstraintChecks();
        System.out.println(checks);
        
    }
    
}
