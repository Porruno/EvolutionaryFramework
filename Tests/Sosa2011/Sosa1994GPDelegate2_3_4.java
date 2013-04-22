package Sosa2011;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class Sosa1994GPDelegate2_3_4 extends
		GPDelegate {

	@Override
	public int getNumberOfGenerations() {
		return 2000;
	}
	
	@Override
	public EvaluationInterface getEvaluationInterface() {
		return new Evaluator();
	}
	
}
