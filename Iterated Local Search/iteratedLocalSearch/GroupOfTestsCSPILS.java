package iteratedLocalSearch;



import CSP.CSP.*;
import CSP.tests.test1.CSPTestGPSimple;
import CSP.tests.test1.CSPTestILSSimple;
import java.io.FileWriter;
import test.GroupOfTests;
import com.csvreader.CsvWriter;
import java.io.IOException;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alejandro Sosa
 */
public class GroupOfTestsCSPILS {
    //public static XMLLoader instanceCSP = new XMLLoader();
    public static int depth = 15;
    String outputFile = "ILSvsGPvsHeuristics.csv";
    /*public static CSP cspInstanceTraining01 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-1.xml"));
    public static CSP cspInstanceTest01 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-2.xml"));
    public static CSP cspInstanceTest02 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-3.xml"));
    public static CSP cspInstanceTest03 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-4.xml"));
    public static CSP cspInstanceTest04 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-5.xml"));
    public static CSP cspInstanceTest05 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-6.xml"));
    public static CSP cspInstanceTest06 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-7.xml"));
    public static CSP cspInstanceTest07 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-8.xml"));
    public static CSP cspInstanceTest08 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-9.xml"));
    public static CSP cspInstanceTest09 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-10.xml"));
    */
    public static CSP cspInstanceTraining = new CSP(20,10,0.5,0.5,0);
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
    
    public static void main(String[] args) throws IOException{
        
        CsvWriter writer = new CsvWriter ("productos.csv");
        writer.write("GP");
        writer.write("ILS");
        writer.write("RND");
        writer.write("MRV");
        writer.write("RHO");
        writer.write("ENS");
        writer.write("K");
        writer.write("MXC");
        writer.write("MFD");
        writer.write("MBD");
        writer.write("FBZ");
        writer.write("BBZ");
        writer.endRecord();
        cspInstanceTraining.saveToFile("instanceCSPTraining.xml");
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
        
        CSP[] instances = {cspInstance0, cspInstance1, cspInstance2, cspInstance3, cspInstance4, cspInstance5, cspInstance6, cspInstance7, cspInstance8, cspInstance9, cspInstance10, cspInstance11, cspInstance12, cspInstance13, cspInstance14, cspInstance15, cspInstance16, cspInstance17, cspInstance18, cspInstance19, cspInstance20, cspInstance21, cspInstance22, cspInstance23, cspInstance24, cspInstance25, cspInstance26, cspInstance27, cspInstance28, cspInstance29, cspInstance30, cspInstance31, cspInstance32, cspInstance33, cspInstance34, cspInstance35, cspInstance36, cspInstance37};
        
        GroupOfTests cspTests = new GroupOfTests("CSP", new Class<?>[]{CSPTestGPSimple.class,CSPTestILSSimple.class}, 1);
        //cspTests.graph();
        //System.out.println(cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns().getAptitude());
        //System.out.println(cspTests.getTestResultAtIndex(1).getBestGenomeOfAllRuns().getAptitude());
       
        //CSP csp = new CSP(cspInstance0);
        //GACSPSolver solver = new GACSPSolver(cspInstance0, cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
        //Variable[] variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        //long checks = solver.getConstraintChecks();
        //System.out.println(checks);
        GACSPSolver solver;
        GACSPSolver solver1;
        CSPSolver solver2;
        Variable[] variables;
        long checks;
        
        for(int i=0;i<=37;i++){
            solver = new GACSPSolver(instances[i], cspTests.getTestResultAtIndex(0).getBestGenomeOfAllRuns());
            variables = solver.solve(ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE);
            checks = solver.getConstraintChecks();
            writer.write(""+checks);
            
            solver1 = new GACSPSolver(instances[i], cspTests.getTestResultAtIndex(1).getBestGenomeOfAllRuns());
            variables = solver1.solve(ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE);
            checks = solver1.getConstraintChecks();
            writer.write(""+checks);
            
            for(int heuristic=1;heuristic<=10;heuristic++){
                solver2 = new CSPSolver(instances[i]);
                variables = solver2.solve(heuristic,ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
                checks = solver2.getConstraintChecks();
                writer.write(""+checks);
            }
            writer.endRecord();
        }
        writer.close();
        
    }
    public int getDepth(){
        return depth;
    }
    
}
