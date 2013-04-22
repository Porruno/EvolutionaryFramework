package functionApproximation;

import geneticAlgorithm.EvaluationInterface;
import geneticProgramming.GPDelegate;

public class FunctionAproximationGEPDelegate1 extends
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
