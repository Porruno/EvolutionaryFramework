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
import geneticProgramming.GPPopulationDelegate;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;
import java.util.HashMap;
import test.Test;

/**
 *
 * @author Jesús Irais González Romero
 */
public class CSPTestGPSimple extends Test implements EvaluationInterface {
    //CSP cspForEvaluation = new XMLLoader().createCSP("aeou.xml");

    //static CSP cspForEvaluation = new CSP(20, 10, .25, .25, 0);
    static CSP cspForEvaluation = new XMLLoader().createCSP("instanceCSPTraining.xml");
    @Override
    public String getName() {
        return "GP";
    }

    @Override
    public GADelegate configure() {
        GPPopulationDelegate adf0PopulationDelegate =
                new GPPopulationDelegate(new ADF[0], 50, false, new TournamentSelection(45, 2),
                .05, .95, new Class<?>[]{ADF0Components.class, ArithmeticComponents.class},
                Variable.class, ADF0Input.class, new HashMap(), 4);
        adf0PopulationDelegate.initiate();

        ADFDelegate adf0Delegate = new ADFDelegate(ADFDelegate.ADFType.SIMPLE, adf0PopulationDelegate);
        ADF adf0 = new ADF(0, adf0Delegate);


        GPPopulationDelegate mainPopulationDelegate =
                new GPPopulationDelegate(new ADF[]{adf0}, 50, false, new TournamentSelection(45, 2),
                .05, .95, new Class<?>[]{Components.class, ArithmeticComponents.class}, Variable.class,
                CSP.class, new HashMap(), 6);
        mainPopulationDelegate.initiate();
        return new GADelegate(mainPopulationDelegate, 50, new ADF[]{adf0}, this);
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