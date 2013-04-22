package koza1994;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class Koza1994GPDelegate3 extends
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
