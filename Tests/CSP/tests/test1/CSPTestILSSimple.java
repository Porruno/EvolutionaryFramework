package CSP.tests.test1;

import CSP.CSP.*;
import CSP.tests.test1.components.ADF0Components;
import CSP.tests.test1.components.ADF0Input;
import CSP.tests.test1.components.ArithmeticComponents;
import CSP.tests.test1.components.Components;
import geneticAlgorithm.EvaluationInterface;
import geneticAlgorithm.GADelegate;
import geneticAlgorithm.Genome;
import geneticAlgorithm.selection.TournamentSelection;
import iteratedLocalSearch.GPPopulationDelegate;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;
import java.util.HashMap;
import test.Test;

/**
 *
 * @author Jesús Irais González Romero/Alejandro Sosa  Ascencio
 */
public class CSPTestILSSimple extends Test implements EvaluationInterface {

    //public static XMLLoader instanceCSP = new XMLLoader();
    
    public static CSP cspForEvaluation = new CSP(20, 10, .45, .45, 0);
    
    //CSP cspInstanceTraining01 = new CSP(instanceCSP.createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-1.xml"));
    //public static CSP cspForEvaluation = new XMLLoader().createCSP("jobShop-e0ddr1\\e0ddr1-10-by-5-1.xml");
    @Override
    public String getName() {
        return "ILS";
    }

    @Override
    public GADelegate configure() {
        cspForEvaluation.saveToFile("instanceCSPTraining.xml");
        GPPopulationDelegate mainPopulationDelegate =
                new GPPopulationDelegate(new ADF[]{}, 1, false, new TournamentSelection(1, 0),
                1, 0.0, new Class<?>[]{Components.class, ArithmeticComponents.class}, Variable.class,
                CSP.class, new HashMap(), 6);
        mainPopulationDelegate.initiate();
        return new GADelegate(mainPopulationDelegate, 3000, new ADF[]{}, this);
    }

    @Override
    public double evaluateGenome(Genome genome) {
        CSP csp = new CSP(cspForEvaluation);
        GACSPSolver solver = new GACSPSolver(csp, genome);
        Variable[] variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        /*
         *
         * double violationsAcum = 0; for(Variable variable : variables){
         * variable.setNumberOfConflicts(); violationsAcum +=
         * variable.getNumberOfConflicts(); }
         */

        long checks = solver.getConstraintChecks();
        return checks;

    }
}