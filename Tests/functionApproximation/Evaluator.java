package functionApproximation;

import geneticAlgorithm.Genome;
import geneticAlgorithm.EvaluationInterface;

public class Evaluator implements EvaluationInterface {

	@Override
	public double evaluateGenome(Genome genome){
		double correctValue;
		double x;
		double errorAcum = 0;
		double genomeValue;
		for(int i = 1; i < 10; i++){
			x = Math.PI / i;

			correctValue = Math.cos(2 * x);
			Object result = genome.evaluate(new X(x));
			genomeValue = ((Number) result).doubleValue();
			errorAcum += Math.abs(correctValue - genomeValue);

		}
		return errorAcum;
	}

}
