package Sosa2011;

import geneExpressionProgramming.GEPDelegate;
import geneticAlgorithm.EvaluationInterface;

public class Sosa1994GEPDelegate2_3_4 extends
		GEPDelegate {

	@Override
	public int getNumberOfGenerations() {
		return 500;
	}
	
	@Override
	public EvaluationInterface getEvaluationInterface() {
		return new Evaluator();
	}
	
}
