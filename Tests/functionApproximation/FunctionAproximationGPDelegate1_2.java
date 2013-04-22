package functionApproximation;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class FunctionAproximationGPDelegate1_2 extends
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
