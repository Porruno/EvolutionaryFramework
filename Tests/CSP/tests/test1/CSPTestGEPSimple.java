package CSP.tests.test1;

import CSP.CSP.*;
import CSP.tests.test1.components.ADF0Components;
import CSP.tests.test1.components.ADF0Input;
import CSP.tests.test1.components.ArithmeticComponents;
import CSP.tests.test1.components.Components;
import geneExpressionProgramming.GEPPopulationDelegate;
import geneticAlgorithm.EvaluationInterface;
import geneticAlgorithm.GADelegate;
import geneticAlgorithm.Genome;
import geneticAlgorithm.selection.TournamentSelection;
import grammar.adf.ADF;
import grammar.adf.ADFDelegate;
import java.util.HashMap;
import test.Test;

/**
 *
 * @author Jesús Irais González Romero
 */

public class CSPTestGEPSimple extends Test implements EvaluationInterface{
    //CSP cspForEvaluation = new XMLLoader().createCSP("aeou.xml");
    //static CSP cspForEvaluation = new CSP(20, 10, .4, .4, 0);
    static CSP cspForEvaluation = new XMLLoader().createCSP("instanceCSP01.xml");
    @Override
    public String getName() {
        return "GEP";
    }
    

    @Override
    public GADelegate configure() {
        GEPPopulationDelegate adf0PopulationDelegate = new GEPPopulationDelegate(
                new ADF[0], 500, false, new TournamentSelection(499, 2),
                new Class<?>[]{ADF0Components.class, ArithmeticComponents.class},
                Variable.class, ADF0Input.class, new HashMap(),
                10, new CSPLinkerFunction(), .05,
                .1, .1, .1, .1, .1, .1);
        adf0PopulationDelegate.initiate();
        ADFDelegate adf0Delegate = new ADFDelegate(ADFDelegate.ADFType.SIMPLE, adf0PopulationDelegate);
        ADF adf0 = new ADF(0, adf0Delegate);
        
        GEPPopulationDelegate mainPopulationDelegate = new GEPPopulationDelegate(
                new ADF[]{adf0}, 500, false, new TournamentSelection(499,2), 
                new Class<?>[]{Components.class, ArithmeticComponents.class},
            Variable.class, CSP.class, new HashMap(), 20,  new CSPLinkerFunction(), 
                .05, .1, .1, .1, .1, .1, .1);
        mainPopulationDelegate.initiate();
        return new GADelegate(mainPopulationDelegate, 2000, new ADF[]{adf0}, this);
    }
    
    @Override
    public double evaluateGenome(Genome genome) {
        CSP csp = new CSP(cspForEvaluation);
        GACSPSolver solver = new GACSPSolver(csp, genome);
        Variable[] variables = solver.solve(ValueOrderingHeuristics.MNC, ConstraintOrderingHeuristics.NONE);
        
        long checks = solver.getConstraintChecks();
        return checks;
        
    }
    
}