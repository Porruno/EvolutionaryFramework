
package CSP.inputPoint;

import CSP.CSP.CSP;
import CSP.CSP.CSPRepository;
import CSP.CSP.CSPSolver;
import CSP.CSP.ConstraintOrderingHeuristics;
import CSP.CSP.ValueOrderingHeuristics;
import CSP.CSP.VariableOrderingHeuristics;
import CSP.Framework.Framework;
import CSP.LCS.ClassifierSystem;
import CSP.NeuralNetworks.BP;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * @author José Carlos Ortiz Bayliss 
 * Monterrey, Nuevo León, México
 * August 2006 - March 2012
*/

public class Main {

    public static void main(String[] args) {
        
        Framework.setHeuristics(new int[]{
                    Framework.codeHeuristicIndex(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE),
                    Framework.codeHeuristicIndex(VariableOrderingHeuristics.ENS, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE),
                    Framework.codeHeuristicIndex(VariableOrderingHeuristics.MFD, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE),
                    Framework.codeHeuristicIndex(VariableOrderingHeuristics.K, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE)             
        });
        
        Framework.setFeatures(new int[]{
                    CSP.CONSTRAINT_DENSITY,
                    CSP.CONSTRAINT_TIGHTNESS                    
                });
        /*
        HyperHeuristic heuristics[] = new HyperHeuristic[]{
            new Heuristic(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE),
            new Heuristic(VariableOrderingHeuristics._MRV, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE)
        };        
        Utils.Grid.createGrid(20, 10, 0.05, 10, heuristics, CSP.MODEL_B, "grid_MRV.txt");
        heuristics = new HyperHeuristic[]{
            new Heuristic(VariableOrderingHeuristics.K, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE),
            new Heuristic(VariableOrderingHeuristics._K, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE)
        };        
        Utils.Grid.createGrid(20, 10, 0.05, 10, heuristics, CSP.MODEL_B, "grid_K.txt");
        heuristics = new HyperHeuristic[]{
            new Heuristic(VariableOrderingHeuristics.MXC, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE),
            new Heuristic(VariableOrderingHeuristics._MXC, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE)
        };        
        Utils.Grid.createGrid(20, 10, 0.05, 10, heuristics, CSP.MODEL_B, "grid_MXC.txt");
        heuristics = new HyperHeuristic[]{
            new Heuristic(VariableOrderingHeuristics.ENS, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE),
            new Heuristic(VariableOrderingHeuristics._ENS, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE)
        };        
        Utils.Grid.createGrid(20, 10, 0.05, 10, heuristics, CSP.MODEL_B, "grid_ENS.txt");
        System.exit(1);
        */
        /*
        Classifier a = new Classifier(new double[]{0.5, 0.6}, 10, 0.77);
        ArrayList<Classifier> list1, list2;
        list1 = new ArrayList<Classifier>(3);
        list2 = new ArrayList<Classifier>(3);
        list1.add(a);
        System.out.println("list1[0] = " + list1.get(0));
        list2.add(list1.get(0));
        list2.get(0).setFitness(0.11111);
        System.out.println("list2[0] = " + list2.get(0));
        System.out.println("list1[0] = " + list1.get(0));
        System.exit(1);
        */
        /*
        XmlTest.readFile("conflictCSP2.txt");
        System.exit(1);
        */
        /*
        HyperHeuristic heuristics[] = new HyperHeuristic[] {
            new Heuristic(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE),
            new Heuristic(VariableOrderingHeuristics.ENS, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE),
            new Heuristic(VariableOrderingHeuristics.MFD, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE),
            new Heuristic(VariableOrderingHeuristics.MXC, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE)
        }; 
                
        Grid.createGrid(20, 10, 0.05, 10, heuristics, CSP.MODEL_F, "grid_F_MRV_ENS_MFD_MXC.txt");
        System.exit(1);        
        */               
        /*
        run("lcsHH01.txt");
        run("lcsHH02.txt");
        run("lcsHH03.txt");
        run("lcsHH04.txt");
        run("lcsHH05.txt");
        run("lcsHH06.txt");
        run("lcsHH07.txt");
        run("lcsHH08.txt");
        run("lcsHH09.txt");
        run("lcsHH10.txt");
        */
        /*
        CSPRepository rep = CSPRepository.loadFromFile("F Training Set");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH01.txt")}, "output01.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH02.txt")}, "output02.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH03.txt")}, "output03.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH04.txt")}, "output04.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH05.txt")}, "output05.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH06.txt")}, "output06.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH07.txt")}, "output07.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH08.txt")}, "output08.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH09.txt")}, "output09.txt");
        rep.solve(new HyperHeuristic[]{ClassifierSystem.loadFromFile("lcsHH10.txt")}, "output10.txt");        
        //Grid.printPattern(ClassifierSystem.loadFromFile("lcsHH02.txt"));
        System.exit(1);
        */        
        /*
        HyperHeuristic hyperHeuristics[] = new HyperHeuristic[] {
            //new Heuristic(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE),
            //new Heuristic(VariableOrderingHeuristics.ENS, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE),
            //new Heuristic(VariableOrderingHeuristics.MFD, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE),
            //new Heuristic(VariableOrderingHeuristics.K, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE),            
            ClassifierSystem.loadFromFile("lcsHH01.txt"),
            ClassifierSystem.loadFromFile("lcsHH02.txt"),
            ClassifierSystem.loadFromFile("lcsHH03.txt"),
            ClassifierSystem.loadFromFile("lcsHH04.txt"),
            ClassifierSystem.loadFromFile("lcsHH05.txt"),
            ClassifierSystem.loadFromFile("lcsHH06.txt"),
            ClassifierSystem.loadFromFile("lcsHH07.txt"),
            ClassifierSystem.loadFromFile("lcsHH08.txt"),
            ClassifierSystem.loadFromFile("lcsHH09.txt"),
            ClassifierSystem.loadFromFile("lcsHH10.txt"),            
        };        
        CSPRepository repository = CSPRepository.loadFromFile("F Large Testing");
        repository.solve(hyperHeuristics, "F Large Testing.txt");
        System.exit(1);
        */
        /*
        CSPRepository repository = new CSPRepository();
        repository.add(30, 10, CSP.MODEL_F, 300);
        repository.getStatistics();
        repository.saveToFile("F Large Testing");
        System.exit(1);   
        */
        
        // Binary vs Non-binary branching                        
        /*
        // TRAINING THE NETWORK
        int i, j;
        double input[], output[];
        BP network = new BP(3, new int[]{5}, 1, 0.8, 0.3);
        String lines[] = Utils.Misc.toStringArray(Utils.Files.loadFromFile("branchingPatterns.txt"));
        StringTokenizer tokens;
        for (i = 0; i < 1000; i++) {
            for (j = 0; j < lines.length; j++) {
                tokens = new StringTokenizer(lines[j], ", ");
                input = new double[3];
                output = new double[1];
                input[0] = Double.parseDouble(tokens.nextToken()) / 50;
                input[1] = Double.parseDouble(tokens.nextToken());
                input[2] = Double.parseDouble(tokens.nextToken());
                output[0] = Double.parseDouble(tokens.nextToken());
                network.train(input, output);
            }
        }        
        
        // TESTING HOW WELL IT LEARNS        
        for (i = 0; i < lines.length; i++) {
            tokens = new StringTokenizer(lines[i], ", ");
            input = new double[3];
            
            input[0] = Double.parseDouble(tokens.nextToken()) / 50;
            input[1] = Double.parseDouble(tokens.nextToken());
            input[2] = Double.parseDouble(tokens.nextToken());
            if ((network.getOutput(input))[0] < 0.5) {
                System.out.println("0");
            } else {
                System.out.println("1");
            }
        }
        */
        /*
        // CREATION OF THE PATTERNS        
        int j, n;
        CSP csp;
        CSPSolver solver;
        double p1, p2;
        long binaryChecks, nonBinaryChecks;
        String line;
        Random generator = new Random();
        DecimalFormat format = new DecimalFormat("0.0000");
        Utils.Files.saveToFile("% n, p1, p2, binary, non-binary\r\n", "branchingResults.txt", false);
        Utils.Files.saveToFile("% n, p1, p2, 0 = binary / 1 = non-binary\r\n", "branchingPatterns.txt", false);
        for (j = 0; j < 1000; j++) {            
            n = generator.nextInt(16) + 15;
            p1 = generator.nextDouble();
            p2 = generator.nextDouble();
            System.out.println("Instance " + (j + 1) + ": n = " + n + ",p1 = " + format.format(p1) + ", p2 = " + format.format(p2) + "...");
            csp = new CSP(n, 10, p1, p2, CSP.MODEL_B);
            solver = new CSPSolver(csp);
            solver.solve(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE, true);
            binaryChecks = solver.getConstraintChecks();
            solver = new CSPSolver(csp);
            solver.solve(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE, false);
            nonBinaryChecks = solver.getConstraintChecks();            
            line = n + ", " + format.format(p1) + ", " + format.format(p2)+ ", " + binaryChecks + ", " + nonBinaryChecks + "\r\n";
            Utils.Files.saveToFile(line, "branchingResults.txt", true);
            if (binaryChecks < nonBinaryChecks) {
                line = n + ", " + format.format(p1) + ", " + format.format(p2)+ ", 0" + "\r\n";
            } else {
                line = n + ", " + format.format(p1) + ", " + format.format(p2)+ ", 1" + "\r\n";
            }
            Utils.Files.saveToFile(line, "branchingPatterns.txt", true);            
        }
        */
        /*
        double p1, p2;
        long binaryChecks, nonBinaryChecks;
        String line;        
        DecimalFormat format = new DecimalFormat("0.0000");
        CSP csp = CSP.loadFromFile("Subs\\002.txt");
        CSPSolver solver;
        solver = new CSPSolver(csp);
        solver.solve(VariableOrderingHeuristics.MFD, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE, true);
        binaryChecks = solver.getConstraintChecks();
        solver = new CSPSolver(csp);
        solver.solve(VariableOrderingHeuristics.MFD, ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE, false);
        nonBinaryChecks = solver.getConstraintChecks();
        p1 = csp.getConstraintDensity();
        p2 = csp.getConstraintTightness();
        line = csp.getVariables().length + ", " + format.format(p1) + ", " + format.format(p2) + ", " + binaryChecks + ", " + nonBinaryChecks + "\r\n";
        System.out.println(line);         
        */
        
        CSP csp = new CSP(20, 10, 0.425, 0.425, CSP.MODEL_B);
        //System.out.println(csp);
        CSPSolver solver = new CSPSolver(csp);        
        solver.solve(VariableOrderingHeuristics.MRV, ValueOrderingHeuristics.NONE, ConstraintOrderingHeuristics.NONE);
        System.out.println(solver.getNumberOfSolutions());
    }
    
    public static void run(String name) {
        int i, j, cycles = 30;
        CSPSolver solver;
        ClassifierSystem lcs = new ClassifierSystem(0);
        CSPRepository repository = CSPRepository.loadFromFile("F Training Set");
        lcs.setExplorationMode(true);
        for (i = 0; i < cycles; i++) {
            System.out.println("Cycle " + (i + 1) + "...");
            for (j = 0; j < repository.size(); j++) {
                solver = new CSPSolver(repository.get(j));
                solver.solve(lcs);                                                
            }
            lcs.remove();
        }
        lcs.saveToFile(name);
    }

}
