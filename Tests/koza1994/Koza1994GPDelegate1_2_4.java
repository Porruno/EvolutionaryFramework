package koza1994;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class Koza1994GPDelegate1_2_4 extends
		GPDelegate {

	@Override
	public int getNumberOfGenerations() {
		return 30;
	}
	
	@Override
	public EvaluationInterface getEvaluationInterface() {
		return new Evaluator();
	}
	
}
