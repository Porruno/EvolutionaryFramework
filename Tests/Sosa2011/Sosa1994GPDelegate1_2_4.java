package Sosa2011;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class Sosa1994GPDelegate1_2_4 extends
		GPDelegate {

	@Override
	public int getNumberOfGenerations() {
		return 1000;
	}
	
	@Override
	public EvaluationInterface getEvaluationInterface() {
		return new Evaluator();
	}
	
}
