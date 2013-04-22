/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nombreIdeal.GADelegates;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;
import nombreIdeal.Evaluator;

/**
 *
 * @author Lay
 */
public class ChoosingName_GADelegate extends GPDelegate{

    @Override
    public int getNumberOfGenerations() {
        return 100;
    }

    @Override
    public EvaluationInterface getEvaluationInterface() {
        return new Evaluator();
    }
    
}
