
import CSP.CSP.*;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Lay
 */
public class main {
    public static void main(String[] args){
        //GroupOfTestsSosa2011.main(null);
        //GroupOfTestsChoosingName.main(null);      
        GroupOfTestsCSP.main(null);
        //CSP csp = new CSP(20,10,0.4,0.4,0);
        System.out.println();
        CSP csp = new XMLLoader().createCSP("instanceCSP01Training.xml");
        CSPSolver solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        //System.out.println(solver.getNumberOfSolutions());
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP00.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        //System.out.println(solver.getNumberOfSolutions());
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP01.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        //System.out.println(solver.getNumberOfSolutions());
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP02.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP03.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP04.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP05.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP06.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP07.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP08.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP09.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP10.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP11.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        //System.out.println(solver.getNumberOfSolutions());
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP12.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP13.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP14.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP15.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP16.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP17.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP18.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP19.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP20.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP21.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        //System.out.println(solver.getNumberOfSolutions());
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP22.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP23.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP24.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP25.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP26.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP27.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP28.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP29.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP30.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP31.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP32.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP33.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP34.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP35.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP36.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
        
        csp = new XMLLoader().createCSP("instanceCSP37.xml");
        solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.RHO, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getConstraintChecks());
    }   
}