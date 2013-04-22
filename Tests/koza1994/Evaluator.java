package koza1994;

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
			x = i * .2;

			correctValue = Math.pow(x, 6) - 2*Math.pow(x, 4) + Math.pow(x, 2);
			Object result = genome.evaluate(new X(x));
			genomeValue = ((Number) result).doubleValue();
			if(Double.isNaN(genomeValue) || Double.isInfinite(genomeValue)){
				genomeValue = 0;
			}
			errorAcum += Math.abs(correctValue - genomeValue);

		}
		return errorAcum;
	}

}
