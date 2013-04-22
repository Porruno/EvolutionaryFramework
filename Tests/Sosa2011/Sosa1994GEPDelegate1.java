package Sosa2011;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class Sosa1994GEPDelegate1 extends
		GPDelegate {

	@Override
	public int getNumberOfGenerations() {
		return 50;
	}
	
	@Override
	public EvaluationInterface getEvaluationInterface() {
		return new Evaluator();
	}
	
}
