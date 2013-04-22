/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CSP.CSP;

import CSP.HyperHeuristic.Heuristic;
import geneticAlgorithm.Genome;

/**
 *
 * @author Lay
 */
public class GACSPSolver extends CSPSolver{
    protected Genome hyperheuristic;
    
    public GACSPSolver(CSP csp, Genome hyperheuristic){
        super(csp);
        this.hyperheuristic = hyperheuristic;
    }
    
    public Variable[] solve(int valueOrderingHeuristic, int constraintOrderingHeuristic){
        Heuristic heuristic = new Heuristic(VariableOrderingHeuristics.GAP, valueOrderingHeuristic, constraintOrderingHeuristic);
        return super.solve(heuristic);
    }
    
    @Override
    protected String selectNextVariable(CSP csp, int variableOrderingHeuristic) {
        if(csp.getUninstantiatedVariables().length == 0) return null;
        csp.prepareForEvaluation();
        Variable selectedVariable = (Variable) this.hyperheuristic.evaluate(csp);
        
        return selectedVariable.getId();
    }
       
}
