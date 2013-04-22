package functionApproximation;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class FunctionAproximationGEPDelegate4 extends
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
